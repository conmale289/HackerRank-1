package net.crash.dmmtiktok.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import net.crash.dmmtiktok.repository.CrawlRepository;
import net.crash.dmmtiktok.service.CrawlService;
import net.crash.dmmtiktok.service.dto.CrawlDTO;
import net.crash.dmmtiktok.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link net.crash.dmmtiktok.domain.Crawl}.
 */
@RestController
@RequestMapping("/api/crawls")
public class CrawlResource {

    private static final Logger log = LoggerFactory.getLogger(CrawlResource.class);

    private static final String ENTITY_NAME = "crawl";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CrawlService crawlService;

    private final CrawlRepository crawlRepository;

    public CrawlResource(CrawlService crawlService, CrawlRepository crawlRepository) {
        this.crawlService = crawlService;
        this.crawlRepository = crawlRepository;
    }

    /**
     * {@code POST  /crawls} : Create a new crawl.
     *
     * @param crawlDTO the crawlDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new crawlDTO, or with status {@code 400 (Bad Request)} if the crawl has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<CrawlDTO> createCrawl(@Valid @RequestBody CrawlDTO crawlDTO) throws URISyntaxException {
        log.debug("REST request to save Crawl : {}", crawlDTO);
        if (crawlDTO.getId() != null) {
            throw new BadRequestAlertException("A new crawl cannot already have an ID", ENTITY_NAME, "idexists");
        }
        crawlDTO = crawlService.save(crawlDTO);
        return ResponseEntity.created(new URI("/api/crawls/" + crawlDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, crawlDTO.getId()))
            .body(crawlDTO);
    }

    /**
     * {@code PUT  /crawls/:id} : Updates an existing crawl.
     *
     * @param id the id of the crawlDTO to save.
     * @param crawlDTO the crawlDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated crawlDTO,
     * or with status {@code 400 (Bad Request)} if the crawlDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the crawlDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<CrawlDTO> updateCrawl(
        @PathVariable(value = "id", required = false) final String id,
        @Valid @RequestBody CrawlDTO crawlDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Crawl : {}, {}", id, crawlDTO);
        if (crawlDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, crawlDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!crawlRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        crawlDTO = crawlService.update(crawlDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, crawlDTO.getId()))
            .body(crawlDTO);
    }

    /**
     * {@code PATCH  /crawls/:id} : Partial updates given fields of an existing crawl, field will ignore if it is null
     *
     * @param id the id of the crawlDTO to save.
     * @param crawlDTO the crawlDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated crawlDTO,
     * or with status {@code 400 (Bad Request)} if the crawlDTO is not valid,
     * or with status {@code 404 (Not Found)} if the crawlDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the crawlDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CrawlDTO> partialUpdateCrawl(
        @PathVariable(value = "id", required = false) final String id,
        @NotNull @RequestBody CrawlDTO crawlDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Crawl partially : {}, {}", id, crawlDTO);
        if (crawlDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, crawlDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!crawlRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CrawlDTO> result = crawlService.partialUpdate(crawlDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, crawlDTO.getId())
        );
    }

    /**
     * {@code GET  /crawls} : get all the crawls.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of crawls in body.
     */
    @GetMapping("")
    public ResponseEntity<List<CrawlDTO>> getAllCrawls(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Crawls");
        Page<CrawlDTO> page = crawlService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /crawls/:id} : get the "id" crawl.
     *
     * @param id the id of the crawlDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the crawlDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<CrawlDTO> getCrawl(@PathVariable("id") String id) {
        log.debug("REST request to get Crawl : {}", id);
        Optional<CrawlDTO> crawlDTO = crawlService.findOne(id);
        return ResponseUtil.wrapOrNotFound(crawlDTO);
    }

    /**
     * {@code DELETE  /crawls/:id} : delete the "id" crawl.
     *
     * @param id the id of the crawlDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCrawl(@PathVariable("id") String id) {
        log.debug("REST request to delete Crawl : {}", id);
        crawlService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
