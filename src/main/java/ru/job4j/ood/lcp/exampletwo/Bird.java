package ru.job4j.ood.lcp.exampletwo;

public class Bird implements EatAble, FlyAble {
    @Override
    public void eat() {
        System.out.println("bird can eat");
    }

    @Override
    public void fly() {
        System.out.println("bird can fly");
    }
}
