package ru.job4j.ood.ocp.parkinglot.car;

public abstract class Car {
    private int id;
    private int size = 1;
    private String model;

    public Car(int id, int size, String model) {
        this.id = id;
        this.size = size;
        this.model = model;
    }

    public Car(int id, String model) {
        this.id = id;
        this.model = model;
    }
}
