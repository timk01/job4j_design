package ru.job4j.ood.lcp.exampletwo;

public class Pingiun implements SwimAble, EatAble {
    @Override
    public void eat() {
        System.out.println("pinguin can eat");
    }

    @Override
    public void swim() {
        System.out.println("pinguin can swim");
    }
}
