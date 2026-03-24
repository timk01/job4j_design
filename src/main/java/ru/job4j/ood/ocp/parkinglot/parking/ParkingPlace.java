package ru.job4j.ood.ocp.parkinglot.parking;

import ru.job4j.ood.ocp.parkinglot.car.Car;

public class ParkingPlace {
    private boolean isFree;
    private Car car;

    public ParkingPlace(boolean isFree, Car car) {
        this.isFree = isFree;
        this.car = car;
    }

    public boolean isFree() {
        return isFree;
    }
    public Car getCar() {
        return car;
    }

    public void setFree(boolean free) {
        isFree = free;
    }
    public void setCar(Car car) {
        this.car = car;
    }
}
