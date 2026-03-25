package ru.job4j.ood.icp.exampletwo;

public class Dog implements MammalBadEx {
    @Override
    public void eat() {

    }

    @Override
    public void sleep() {

    }

    @Override
    public void layEggs() {
        throw new UnsupportedOperationException();
    }
}
