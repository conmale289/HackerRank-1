package net.crash.dmmtiktok.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class CrawlTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Crawl getCrawlSample1() {
        return new Crawl().id("id1").sold(1L);
    }

    public static Crawl getCrawlSample2() {
        return new Crawl().id("id2").sold(2L);
    }

    public static Crawl getCrawlRandomSampleGenerator() {
        return new Crawl().id(UUID.randomUUID().toString()).sold(longCount.incrementAndGet());
    }
}
