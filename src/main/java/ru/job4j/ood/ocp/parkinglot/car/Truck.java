package ru.job4j.ood.ocp.parkinglot.car;

public class Truck extends Car {
    private int size;
    public Truck(int id, int size, String model) {
        super(id, model);
        if (size <= 1) {
            throw new IllegalArgumentException("truck size cannot be less than 2");
        }
        this.size = size;
    }
}
