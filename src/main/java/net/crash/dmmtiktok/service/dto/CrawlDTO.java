package net.crash.dmmtiktok.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link net.crash.dmmtiktok.domain.Crawl} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CrawlDTO implements Serializable {

    private String id;

    @NotNull
    private Instant crawledDate;

    private Long sold;

    private ProductDTO product;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Instant getCrawledDate() {
        return crawledDate;
    }

    public void setCrawledDate(Instant crawledDate) {
        this.crawledDate = crawledDate;
    }

    public Long getSold() {
        return sold;
    }

    public void setSold(Long sold) {
        this.sold = sold;
    }

    public ProductDTO getProduct() {
        return product;
    }

    public void setProduct(ProductDTO product) {
        this.product = product;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CrawlDTO)) {
            return false;
        }

        CrawlDTO crawlDTO = (CrawlDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, crawlDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CrawlDTO{" +
            "id='" + getId() + "'" +
            ", crawledDate='" + getCrawledDate() + "'" +
            ", sold=" + getSold() +
            ", product=" + getProduct() +
            "}";
    }
}
