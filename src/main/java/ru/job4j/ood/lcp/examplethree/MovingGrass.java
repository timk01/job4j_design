package ru.job4j.ood.lcp.examplethree;

public class MovingGrass implements RunAble {
    @Override
    public void runFrom(Creature creature) {
        System.out.println("running!");
    }
}
