package net.crash.dmmtiktok.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A Product.
 */
@Document(collection = "product")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Product implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Field("product_id")
    private String productId;

    @NotNull
    @Field("name")
    private String name;

    @Field("shop")
    private String shop;

    @Field("sold")
    private Long sold;

    @Field("comment")
    private Long comment;

    @Field("sku")
    private String sku;

    @Field("product_link")
    private String productLink;

    @DBRef
    @Field("images")
    @JsonIgnoreProperties(value = { "product" }, allowSetters = true)
    private Set<Image> images = new HashSet<>();

    @DBRef
    @Field("crawls")
    @JsonIgnoreProperties(value = { "product" }, allowSetters = true)
    private Set<Crawl> crawls = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public Product id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProductId() {
        return this.productId;
    }

    public Product productId(String productId) {
        this.setProductId(productId);
        return this;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getName() {
        return this.name;
    }

    public Product name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShop() {
        return this.shop;
    }

    public Product shop(String shop) {
        this.setShop(shop);
        return this;
    }

    public void setShop(String shop) {
        this.shop = shop;
    }

    public Long getSold() {
        return this.sold;
    }

    public Product sold(Long sold) {
        this.setSold(sold);
        return this;
    }

    public void setSold(Long sold) {
        this.sold = sold;
    }

    public Long getComment() {
        return this.comment;
    }

    public Product comment(Long comment) {
        this.setComment(comment);
        return this;
    }

    public void setComment(Long comment) {
        this.comment = comment;
    }

    public String getSku() {
        return this.sku;
    }

    public Product sku(String sku) {
        this.setSku(sku);
        return this;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getProductLink() {
        return this.productLink;
    }

    public Product productLink(String productLink) {
        this.setProductLink(productLink);
        return this;
    }

    public void setProductLink(String productLink) {
        this.productLink = productLink;
    }

    public Set<Image> getImages() {
        return this.images;
    }

    public void setImages(Set<Image> images) {
        if (this.images != null) {
            this.images.forEach(i -> i.setProduct(null));
        }
        if (images != null) {
            images.forEach(i -> i.setProduct(this));
        }
        this.images = images;
    }

    public Product images(Set<Image> images) {
        this.setImages(images);
        return this;
    }

    public Product addImages(Image image) {
        this.images.add(image);
        image.setProduct(this);
        return this;
    }

    public Product removeImages(Image image) {
        this.images.remove(image);
        image.setProduct(null);
        return this;
    }

    public Set<Crawl> getCrawls() {
        return this.crawls;
    }

    public void setCrawls(Set<Crawl> crawls) {
        if (this.crawls != null) {
            this.crawls.forEach(i -> i.setProduct(null));
        }
        if (crawls != null) {
            crawls.forEach(i -> i.setProduct(this));
        }
        this.crawls = crawls;
    }

    public Product crawls(Set<Crawl> crawls) {
        this.setCrawls(crawls);
        return this;
    }

    public Product addCrawls(Crawl crawl) {
        this.crawls.add(crawl);
        crawl.setProduct(this);
        return this;
    }

    public Product removeCrawls(Crawl crawl) {
        this.crawls.remove(crawl);
        crawl.setProduct(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Product)) {
            return false;
        }
        return getId() != null && getId().equals(((Product) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Product{" +
            "id=" + getId() +
            ", productId='" + getProductId() + "'" +
            ", name='" + getName() + "'" +
            ", shop='" + getShop() + "'" +
            ", sold=" + getSold() +
            ", comment=" + getComment() +
            ", sku='" + getSku() + "'" +
            ", productLink='" + getProductLink() + "'" +
            "}";
    }
}
