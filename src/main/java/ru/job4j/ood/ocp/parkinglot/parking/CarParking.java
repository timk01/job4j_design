package ru.job4j.ood.ocp.parkinglot.parking;

import ru.job4j.ood.ocp.parkinglot.car.Car;

import java.util.List;

public class CarParking implements Parking {
    private int freeTruckPlaces;
    private int freeCarPlaces;
    private List<ParkingPlace> placeStatuses;

    @Override
    public Result park(Car car) {
        return null;
    }

    @Override
    public Result unPark(Car car) {
        return null;
    }

    @Override
    public Car findCarById(int id) {
        return null;
    }
}
