package ru.job4j.ood.dip.exampletwothree;

import java.util.Random;

public class JavaRandomBadEx {
    public int nextInt(int inbound) {
        return new Random().nextInt(inbound);
    }
}
