package ru.job4j.ood.ocp.parkinglot;

import ru.job4j.ood.ocp.parkinglot.car.Car;
import ru.job4j.ood.ocp.parkinglot.car.PassengerCar;
import ru.job4j.ood.ocp.parkinglot.parking.CarParking;
import ru.job4j.ood.ocp.parkinglot.parking.Parking;
import ru.job4j.ood.ocp.parkinglot.parking.Result;

public class Main {
    public static void main(String[] args) {
        Parking parking = new CarParking();
        Car car = new PassengerCar(1, 1, "zhuk");
        Result result = parking.park(car);
        System.out.println(result.isParked());
    }
}
