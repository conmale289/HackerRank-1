package net.crash.dmmtiktok.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import net.crash.dmmtiktok.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CrawlDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CrawlDTO.class);
        CrawlDTO crawlDTO1 = new CrawlDTO();
        crawlDTO1.setId("id1");
        CrawlDTO crawlDTO2 = new CrawlDTO();
        assertThat(crawlDTO1).isNotEqualTo(crawlDTO2);
        crawlDTO2.setId(crawlDTO1.getId());
        assertThat(crawlDTO1).isEqualTo(crawlDTO2);
        crawlDTO2.setId("id2");
        assertThat(crawlDTO1).isNotEqualTo(crawlDTO2);
        crawlDTO1.setId(null);
        assertThat(crawlDTO1).isNotEqualTo(crawlDTO2);
    }
}
