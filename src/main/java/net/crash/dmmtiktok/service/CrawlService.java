package net.crash.dmmtiktok.service;

import java.util.Optional;
import net.crash.dmmtiktok.domain.Crawl;
import net.crash.dmmtiktok.repository.CrawlRepository;
import net.crash.dmmtiktok.service.dto.CrawlDTO;
import net.crash.dmmtiktok.service.mapper.CrawlMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link net.crash.dmmtiktok.domain.Crawl}.
 */
@Service
public class CrawlService {

    private static final Logger log = LoggerFactory.getLogger(CrawlService.class);

    private final CrawlRepository crawlRepository;

    private final CrawlMapper crawlMapper;

    public CrawlService(CrawlRepository crawlRepository, CrawlMapper crawlMapper) {
        this.crawlRepository = crawlRepository;
        this.crawlMapper = crawlMapper;
    }

    /**
     * Save a crawl.
     *
     * @param crawlDTO the entity to save.
     * @return the persisted entity.
     */
    public CrawlDTO save(CrawlDTO crawlDTO) {
        log.debug("Request to save Crawl : {}", crawlDTO);
        Crawl crawl = crawlMapper.toEntity(crawlDTO);
        crawl = crawlRepository.save(crawl);
        return crawlMapper.toDto(crawl);
    }

    /**
     * Update a crawl.
     *
     * @param crawlDTO the entity to save.
     * @return the persisted entity.
     */
    public CrawlDTO update(CrawlDTO crawlDTO) {
        log.debug("Request to update Crawl : {}", crawlDTO);
        Crawl crawl = crawlMapper.toEntity(crawlDTO);
        crawl = crawlRepository.save(crawl);
        return crawlMapper.toDto(crawl);
    }

    /**
     * Partially update a crawl.
     *
     * @param crawlDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<CrawlDTO> partialUpdate(CrawlDTO crawlDTO) {
        log.debug("Request to partially update Crawl : {}", crawlDTO);

        return crawlRepository
            .findById(crawlDTO.getId())
            .map(existingCrawl -> {
                crawlMapper.partialUpdate(existingCrawl, crawlDTO);

                return existingCrawl;
            })
            .map(crawlRepository::save)
            .map(crawlMapper::toDto);
    }

    /**
     * Get all the crawls.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    public Page<CrawlDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Crawls");
        return crawlRepository.findAll(pageable).map(crawlMapper::toDto);
    }

    /**
     * Get one crawl by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    public Optional<CrawlDTO> findOne(String id) {
        log.debug("Request to get Crawl : {}", id);
        return crawlRepository.findById(id).map(crawlMapper::toDto);
    }

    /**
     * Delete the crawl by id.
     *
     * @param id the id of the entity.
     */
    public void delete(String id) {
        log.debug("Request to delete Crawl : {}", id);
        crawlRepository.deleteById(id);
    }
}
