package ru.job4j.ood.lcp.examplethree;

public final class Herbivore extends Creature {
    private final int speed;

    private int hp;

    private final int maxHp;

    public Herbivore(int hp, int maxHp, int speed) {
        this.hp = hp;
        this.maxHp = maxHp;
        this.speed = speed;
    }

    @Override
    public String toString() {
        return "Herbivore{"
                + "speed=" + speed
                + ", hp=" + hp
                + ", maxHp=" + maxHp
                + '}';
    }
}