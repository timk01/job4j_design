package ru.job4j.gc.ref.usage;

import java.util.WeakHashMap;
import java.util.concurrent.TimeUnit;

public class WeakReference {
    public static void main(String[] args) throws InterruptedException {
        WeakHashMap<UniqueImageName, BigImage> map = new WeakHashMap<>();
        BigImage bigImage = new BigImage("unique_image_id", 100_000_000.0);
        UniqueImageName imageName = new UniqueImageName("name_of_big_image");

        map.put(imageName, bigImage);

        map.get(imageName);
        imageName = null;
        System.out.println(map.isEmpty());
        System.gc();

        TimeUnit.SECONDS.sleep(5);
        System.out.println(map.isEmpty());
    }
}

class BigImage {
    private String name;
    private double size;

    public BigImage(String name, double size) {
        this.name = name;
        this.size = size;
    }
}

class UniqueImageName {
    private String name;

    public UniqueImageName(String name) {
        this.name = name;
    }
}
