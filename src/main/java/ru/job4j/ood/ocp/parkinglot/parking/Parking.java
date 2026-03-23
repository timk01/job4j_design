package ru.job4j.ood.ocp.parkinglot.parking;

import ru.job4j.ood.ocp.parkinglot.car.Car;

public interface Parking {
    Result park(Car car);
    Result unPark(Car car);
    Car findCarById(int id);
}
