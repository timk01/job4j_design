package ru.job4j.ood.dip.exampletwothree;

public class GameService {
    private final RandomGenerator random;

    public GameService(RandomGenerator random) {
        this.random = random;
    }

    public int nextMove() {
        return random.nextInt(10);
    }
}