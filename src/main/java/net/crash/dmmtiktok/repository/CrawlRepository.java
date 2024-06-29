package net.crash.dmmtiktok.repository;

import net.crash.dmmtiktok.domain.Crawl;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the Crawl entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CrawlRepository extends MongoRepository<Crawl, String> {}
