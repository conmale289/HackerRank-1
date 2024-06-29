package net.crash.dmmtiktok.domain;

import static net.crash.dmmtiktok.domain.CrawlTestSamples.*;
import static net.crash.dmmtiktok.domain.ImageTestSamples.*;
import static net.crash.dmmtiktok.domain.ProductTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import net.crash.dmmtiktok.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProductTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Product.class);
        Product product1 = getProductSample1();
        Product product2 = new Product();
        assertThat(product1).isNotEqualTo(product2);

        product2.setId(product1.getId());
        assertThat(product1).isEqualTo(product2);

        product2 = getProductSample2();
        assertThat(product1).isNotEqualTo(product2);
    }

    @Test
    void imagesTest() {
        Product product = getProductRandomSampleGenerator();
        Image imageBack = getImageRandomSampleGenerator();

        product.addImages(imageBack);
        assertThat(product.getImages()).containsOnly(imageBack);
        assertThat(imageBack.getProduct()).isEqualTo(product);

        product.removeImages(imageBack);
        assertThat(product.getImages()).doesNotContain(imageBack);
        assertThat(imageBack.getProduct()).isNull();

        product.images(new HashSet<>(Set.of(imageBack)));
        assertThat(product.getImages()).containsOnly(imageBack);
        assertThat(imageBack.getProduct()).isEqualTo(product);

        product.setImages(new HashSet<>());
        assertThat(product.getImages()).doesNotContain(imageBack);
        assertThat(imageBack.getProduct()).isNull();
    }

    @Test
    void crawlsTest() {
        Product product = getProductRandomSampleGenerator();
        Crawl crawlBack = getCrawlRandomSampleGenerator();

        product.addCrawls(crawlBack);
        assertThat(product.getCrawls()).containsOnly(crawlBack);
        assertThat(crawlBack.getProduct()).isEqualTo(product);

        product.removeCrawls(crawlBack);
        assertThat(product.getCrawls()).doesNotContain(crawlBack);
        assertThat(crawlBack.getProduct()).isNull();

        product.crawls(new HashSet<>(Set.of(crawlBack)));
        assertThat(product.getCrawls()).containsOnly(crawlBack);
        assertThat(crawlBack.getProduct()).isEqualTo(product);

        product.setCrawls(new HashSet<>());
        assertThat(product.getCrawls()).doesNotContain(crawlBack);
        assertThat(crawlBack.getProduct()).isNull();
    }
}
