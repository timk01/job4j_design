package ru.job4j.ood.ocp.parkinglot.car;

public class Truck extends Car {
    private static final int MINIMUM_TRUCK_SIZE = 2;

    public Truck(int size, String model) {
        super(truckSizeValidator(size), model);
    }

    private static int truckSizeValidator(int size) {
        if (size < MINIMUM_TRUCK_SIZE) {
            throw new IllegalArgumentException("truck size cannot be less than " + MINIMUM_TRUCK_SIZE);
        }
        return size;
    }
}
