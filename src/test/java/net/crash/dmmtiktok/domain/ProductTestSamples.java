package net.crash.dmmtiktok.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class ProductTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Product getProductSample1() {
        return new Product()
            .id("id1")
            .productId("productId1")
            .name("name1")
            .shop("shop1")
            .sold(1L)
            .comment(1L)
            .sku("sku1")
            .productLink("productLink1");
    }

    public static Product getProductSample2() {
        return new Product()
            .id("id2")
            .productId("productId2")
            .name("name2")
            .shop("shop2")
            .sold(2L)
            .comment(2L)
            .sku("sku2")
            .productLink("productLink2");
    }

    public static Product getProductRandomSampleGenerator() {
        return new Product()
            .id(UUID.randomUUID().toString())
            .productId(UUID.randomUUID().toString())
            .name(UUID.randomUUID().toString())
            .shop(UUID.randomUUID().toString())
            .sold(longCount.incrementAndGet())
            .comment(longCount.incrementAndGet())
            .sku(UUID.randomUUID().toString())
            .productLink(UUID.randomUUID().toString());
    }
}
