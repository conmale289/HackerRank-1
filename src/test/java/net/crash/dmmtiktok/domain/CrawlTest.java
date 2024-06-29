package net.crash.dmmtiktok.domain;

import static net.crash.dmmtiktok.domain.CrawlTestSamples.*;
import static net.crash.dmmtiktok.domain.ProductTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import net.crash.dmmtiktok.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CrawlTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Crawl.class);
        Crawl crawl1 = getCrawlSample1();
        Crawl crawl2 = new Crawl();
        assertThat(crawl1).isNotEqualTo(crawl2);

        crawl2.setId(crawl1.getId());
        assertThat(crawl1).isEqualTo(crawl2);

        crawl2 = getCrawlSample2();
        assertThat(crawl1).isNotEqualTo(crawl2);
    }

    @Test
    void productTest() {
        Crawl crawl = getCrawlRandomSampleGenerator();
        Product productBack = getProductRandomSampleGenerator();

        crawl.setProduct(productBack);
        assertThat(crawl.getProduct()).isEqualTo(productBack);

        crawl.product(null);
        assertThat(crawl.getProduct()).isNull();
    }
}
