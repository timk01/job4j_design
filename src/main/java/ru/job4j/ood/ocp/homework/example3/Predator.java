package ru.job4j.ood.ocp.homework.example3;

public final class Predator extends Creature {

    private final int attack;

    public Predator(int speed, int hp, int maxHp, int attack) {
        super(speed, hp, maxHp);
        this.attack = attack;
    }
}
