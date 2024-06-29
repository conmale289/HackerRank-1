package net.crash.dmmtiktok.web.rest;

import static net.crash.dmmtiktok.domain.CrawlAsserts.*;
import static net.crash.dmmtiktok.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;
import net.crash.dmmtiktok.IntegrationTest;
import net.crash.dmmtiktok.domain.Crawl;
import net.crash.dmmtiktok.repository.CrawlRepository;
import net.crash.dmmtiktok.service.dto.CrawlDTO;
import net.crash.dmmtiktok.service.mapper.CrawlMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Integration tests for the {@link CrawlResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CrawlResourceIT {

    private static final Instant DEFAULT_CRAWLED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CRAWLED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Long DEFAULT_SOLD = 1L;
    private static final Long UPDATED_SOLD = 2L;

    private static final String ENTITY_API_URL = "/api/crawls";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ObjectMapper om;

    @Autowired
    private CrawlRepository crawlRepository;

    @Autowired
    private CrawlMapper crawlMapper;

    @Autowired
    private MockMvc restCrawlMockMvc;

    private Crawl crawl;

    private Crawl insertedCrawl;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Crawl createEntity() {
        Crawl crawl = new Crawl().crawledDate(DEFAULT_CRAWLED_DATE).sold(DEFAULT_SOLD);
        return crawl;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Crawl createUpdatedEntity() {
        Crawl crawl = new Crawl().crawledDate(UPDATED_CRAWLED_DATE).sold(UPDATED_SOLD);
        return crawl;
    }

    @BeforeEach
    public void initTest() {
        crawl = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedCrawl != null) {
            crawlRepository.delete(insertedCrawl);
            insertedCrawl = null;
        }
    }

    @Test
    void createCrawl() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Crawl
        CrawlDTO crawlDTO = crawlMapper.toDto(crawl);
        var returnedCrawlDTO = om.readValue(
            restCrawlMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(crawlDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            CrawlDTO.class
        );

        // Validate the Crawl in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedCrawl = crawlMapper.toEntity(returnedCrawlDTO);
        assertCrawlUpdatableFieldsEquals(returnedCrawl, getPersistedCrawl(returnedCrawl));

        insertedCrawl = returnedCrawl;
    }

    @Test
    void createCrawlWithExistingId() throws Exception {
        // Create the Crawl with an existing ID
        crawl.setId("existing_id");
        CrawlDTO crawlDTO = crawlMapper.toDto(crawl);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCrawlMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(crawlDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Crawl in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    void checkCrawledDateIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        crawl.setCrawledDate(null);

        // Create the Crawl, which fails.
        CrawlDTO crawlDTO = crawlMapper.toDto(crawl);

        restCrawlMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(crawlDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void getAllCrawls() throws Exception {
        // Initialize the database
        insertedCrawl = crawlRepository.save(crawl);

        // Get all the crawlList
        restCrawlMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(crawl.getId())))
            .andExpect(jsonPath("$.[*].crawledDate").value(hasItem(DEFAULT_CRAWLED_DATE.toString())))
            .andExpect(jsonPath("$.[*].sold").value(hasItem(DEFAULT_SOLD.intValue())));
    }

    @Test
    void getCrawl() throws Exception {
        // Initialize the database
        insertedCrawl = crawlRepository.save(crawl);

        // Get the crawl
        restCrawlMockMvc
            .perform(get(ENTITY_API_URL_ID, crawl.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(crawl.getId()))
            .andExpect(jsonPath("$.crawledDate").value(DEFAULT_CRAWLED_DATE.toString()))
            .andExpect(jsonPath("$.sold").value(DEFAULT_SOLD.intValue()));
    }

    @Test
    void getNonExistingCrawl() throws Exception {
        // Get the crawl
        restCrawlMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putExistingCrawl() throws Exception {
        // Initialize the database
        insertedCrawl = crawlRepository.save(crawl);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the crawl
        Crawl updatedCrawl = crawlRepository.findById(crawl.getId()).orElseThrow();
        updatedCrawl.crawledDate(UPDATED_CRAWLED_DATE).sold(UPDATED_SOLD);
        CrawlDTO crawlDTO = crawlMapper.toDto(updatedCrawl);

        restCrawlMockMvc
            .perform(
                put(ENTITY_API_URL_ID, crawlDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(crawlDTO))
            )
            .andExpect(status().isOk());

        // Validate the Crawl in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedCrawlToMatchAllProperties(updatedCrawl);
    }

    @Test
    void putNonExistingCrawl() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        crawl.setId(UUID.randomUUID().toString());

        // Create the Crawl
        CrawlDTO crawlDTO = crawlMapper.toDto(crawl);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCrawlMockMvc
            .perform(
                put(ENTITY_API_URL_ID, crawlDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(crawlDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Crawl in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchCrawl() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        crawl.setId(UUID.randomUUID().toString());

        // Create the Crawl
        CrawlDTO crawlDTO = crawlMapper.toDto(crawl);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCrawlMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(crawlDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Crawl in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamCrawl() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        crawl.setId(UUID.randomUUID().toString());

        // Create the Crawl
        CrawlDTO crawlDTO = crawlMapper.toDto(crawl);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCrawlMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(crawlDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Crawl in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateCrawlWithPatch() throws Exception {
        // Initialize the database
        insertedCrawl = crawlRepository.save(crawl);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the crawl using partial update
        Crawl partialUpdatedCrawl = new Crawl();
        partialUpdatedCrawl.setId(crawl.getId());

        restCrawlMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCrawl.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedCrawl))
            )
            .andExpect(status().isOk());

        // Validate the Crawl in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCrawlUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedCrawl, crawl), getPersistedCrawl(crawl));
    }

    @Test
    void fullUpdateCrawlWithPatch() throws Exception {
        // Initialize the database
        insertedCrawl = crawlRepository.save(crawl);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the crawl using partial update
        Crawl partialUpdatedCrawl = new Crawl();
        partialUpdatedCrawl.setId(crawl.getId());

        partialUpdatedCrawl.crawledDate(UPDATED_CRAWLED_DATE).sold(UPDATED_SOLD);

        restCrawlMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCrawl.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedCrawl))
            )
            .andExpect(status().isOk());

        // Validate the Crawl in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCrawlUpdatableFieldsEquals(partialUpdatedCrawl, getPersistedCrawl(partialUpdatedCrawl));
    }

    @Test
    void patchNonExistingCrawl() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        crawl.setId(UUID.randomUUID().toString());

        // Create the Crawl
        CrawlDTO crawlDTO = crawlMapper.toDto(crawl);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCrawlMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, crawlDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(crawlDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Crawl in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchCrawl() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        crawl.setId(UUID.randomUUID().toString());

        // Create the Crawl
        CrawlDTO crawlDTO = crawlMapper.toDto(crawl);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCrawlMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(crawlDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Crawl in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamCrawl() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        crawl.setId(UUID.randomUUID().toString());

        // Create the Crawl
        CrawlDTO crawlDTO = crawlMapper.toDto(crawl);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCrawlMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(crawlDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Crawl in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteCrawl() throws Exception {
        // Initialize the database
        insertedCrawl = crawlRepository.save(crawl);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the crawl
        restCrawlMockMvc
            .perform(delete(ENTITY_API_URL_ID, crawl.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return crawlRepository.count();
    }

    protected void assertIncrementedRepositoryCount(long countBefore) {
        assertThat(countBefore + 1).isEqualTo(getRepositoryCount());
    }

    protected void assertDecrementedRepositoryCount(long countBefore) {
        assertThat(countBefore - 1).isEqualTo(getRepositoryCount());
    }

    protected void assertSameRepositoryCount(long countBefore) {
        assertThat(countBefore).isEqualTo(getRepositoryCount());
    }

    protected Crawl getPersistedCrawl(Crawl crawl) {
        return crawlRepository.findById(crawl.getId()).orElseThrow();
    }

    protected void assertPersistedCrawlToMatchAllProperties(Crawl expectedCrawl) {
        assertCrawlAllPropertiesEquals(expectedCrawl, getPersistedCrawl(expectedCrawl));
    }

    protected void assertPersistedCrawlToMatchUpdatableProperties(Crawl expectedCrawl) {
        assertCrawlAllUpdatablePropertiesEquals(expectedCrawl, getPersistedCrawl(expectedCrawl));
    }
}
