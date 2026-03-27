package ru.job4j.ood.dip.exampletwothree;

public class GameServiceBadEx {
    private final JavaRandomBadEx random = new JavaRandomBadEx();

    public int nextMove() {
        return random.nextInt(10);
    }
}