package ru.job4j.ood.ocp.parkinglot.car;

public class PassengerCar extends Car {
    private static final int PASSENGER_CAR_SIZE = 1;
    public PassengerCar(int size, String model) {
        super(PASSENGER_CAR_SIZE, model);
    }
}
