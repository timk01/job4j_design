package ru.job4j.gc.ref.usage;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.util.Random;

public class SoftWeakReference {
    public static void main(String[] args) {
        StringBuilder builder = new StringBuilder();
        builder.append(1)
                .append(" soft")
                .append(" reference");
        for (int i = 0; i < 100_000; i++) {
            builder.append(" ").append(new Random().nextInt(10));
        }
        ReferenceQueue<StringBuilder> referenceQueue = new ReferenceQueue<>();
        SoftReference<StringBuilder> reference2 = new SoftReference<>(builder, referenceQueue);

        builder = null;
        System.gc();
        StringBuilder stringBuilder = reference2.get();
        if (stringBuilder != null) {
            System.out.println(stringBuilder.toString());
        } else {
            System.out.println("empty StringBuilder");
        }
    }
}
