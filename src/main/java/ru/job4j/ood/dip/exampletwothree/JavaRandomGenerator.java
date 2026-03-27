package ru.job4j.ood.dip.exampletwothree;

import java.util.Random;

public class JavaRandomGenerator implements RandomGenerator {
    private final Random random = new Random();

    @Override
    public int nextInt(int bound) {
        return random.nextInt(bound);
    }
}