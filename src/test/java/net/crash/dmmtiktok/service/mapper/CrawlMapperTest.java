package net.crash.dmmtiktok.service.mapper;

import static net.crash.dmmtiktok.domain.CrawlAsserts.*;
import static net.crash.dmmtiktok.domain.CrawlTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CrawlMapperTest {

    private CrawlMapper crawlMapper;

    @BeforeEach
    void setUp() {
        crawlMapper = new CrawlMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getCrawlSample1();
        var actual = crawlMapper.toEntity(crawlMapper.toDto(expected));
        assertCrawlAllPropertiesEquals(expected, actual);
    }
}
