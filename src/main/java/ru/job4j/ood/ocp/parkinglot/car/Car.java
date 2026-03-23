package ru.job4j.ood.ocp.parkinglot.car;

import java.util.Objects;

public abstract class Car {
    private static int globalId;
    private final int carId;
    private final int size;
    private final String model;

    public Car(int size, String model) {
        globalId++;
        carId = globalId;
        this.size = size;
        this.model = model;
    }

    public int getSize() {
        return size;
    }

    public String getModel() {
        return model;
    }

    public int getCarId() {
        return carId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Car car = (Car) o;
        return carId == car.carId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(carId);
    }
}
