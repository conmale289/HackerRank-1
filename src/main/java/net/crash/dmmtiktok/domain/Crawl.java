package net.crash.dmmtiktok.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A Crawl.
 */
@Document(collection = "crawl")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Crawl implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Field("crawled_date")
    private Instant crawledDate;

    @Field("sold")
    private Long sold;

    @DBRef
    @Field("product")
    @JsonIgnoreProperties(value = { "images", "crawls" }, allowSetters = true)
    private Product product;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public Crawl id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Instant getCrawledDate() {
        return this.crawledDate;
    }

    public Crawl crawledDate(Instant crawledDate) {
        this.setCrawledDate(crawledDate);
        return this;
    }

    public void setCrawledDate(Instant crawledDate) {
        this.crawledDate = crawledDate;
    }

    public Long getSold() {
        return this.sold;
    }

    public Crawl sold(Long sold) {
        this.setSold(sold);
        return this;
    }

    public void setSold(Long sold) {
        this.sold = sold;
    }

    public Product getProduct() {
        return this.product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Crawl product(Product product) {
        this.setProduct(product);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Crawl)) {
            return false;
        }
        return getId() != null && getId().equals(((Crawl) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Crawl{" +
            "id=" + getId() +
            ", crawledDate='" + getCrawledDate() + "'" +
            ", sold=" + getSold() +
            "}";
    }
}
