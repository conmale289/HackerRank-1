package net.crash.dmmtiktok.domain;

import java.util.UUID;

public class ImageTestSamples {

    public static Image getImageSample1() {
        return new Image().id("id1").src("src1");
    }

    public static Image getImageSample2() {
        return new Image().id("id2").src("src2");
    }

    public static Image getImageRandomSampleGenerator() {
        return new Image().id(UUID.randomUUID().toString()).src(UUID.randomUUID().toString());
    }
}
