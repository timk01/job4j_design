package ru.job4j.ood.ocp.parkinglot.parking;

import ru.job4j.ood.ocp.parkinglot.car.Car;

import java.util.ArrayList;
import java.util.List;

public class CarParking implements Parking {
    private final int freeCarPlaces;
    private final int freeTruckPlaces;
    private List<ParkingPlace> carParkingLine;
    private List<ParkingPlace> truckParkingLine;

    public CarParking(int freeCarPlaces, int freeTruckPlaces) {
        if (freeCarPlaces < 0 || freeTruckPlaces < 0) {
            throw new IllegalStateException("freeCarPlaces or freeTruckPlaces cannot be negative!");
        }
        this.freeCarPlaces = freeCarPlaces;
        this.freeTruckPlaces = freeTruckPlaces;

        carParkingLine = new ArrayList<>();
        for (int i = 0; i < freeCarPlaces; i++) {
            carParkingLine.add(new ParkingPlace(true, null));
        }

        truckParkingLine = new ArrayList<>();
        for (int i = 0; i < freeTruckPlaces; i++) {
            truckParkingLine.add(new ParkingPlace(true, null));
        }
    }

    @Override
    public Result park(Car car) {
        if (car.getSize() == 1) {
            return new Result(doSimpleParking(car, carParkingLine));
        }

        int truckSize = car.getSize();
        if (truckSize > 1) {
            if (doSimpleParking(car, truckParkingLine)) {
                return new Result(true);
            }

            return doTruckParking(car, truckSize);
        }

        return new Result(false);
    }

    private boolean doSimpleParking(Car car, List<ParkingPlace> parkingLine) {
        for (ParkingPlace parkingPlace : parkingLine) {
            if (parkingPlace.isFree()) {
                parkingPlace.setFree(false);
                parkingPlace.setCar(car);
                return true;
            }
        }
        return false;
    }

    private Result doTruckParking(Car car, int truckSize) {
        List<Integer> indexes = new ArrayList<>();
        int counter = 0;
        for (int i = 0; i < carParkingLine.size(); i++) {
            if (carParkingLine.get(i).isFree()) {
                counter++;
                indexes.add(i);
                if (truckSize == counter) {
                    break;
                }
            } else {
                counter = 0;
                indexes.clear();
            }
        }

        if (truckSize != counter) {
            return new Result(false);
        }
        for (Integer index : indexes) {
            carParkingLine.get(index).setFree(false);
            carParkingLine.get(index).setCar(car);
        }
        return new Result(true);
    }

    @Override
    public Result unPark(Car car) {
        if (car.getSize() == 1 && doSimpleUnparking(car, carParkingLine)) {
                return new Result(true);
        }

        if (car.getSize() > 1) {
            if (doSimpleUnparking(car, truckParkingLine)) {
                return new Result(true);
            }

            return unparkTruck(car);
        }

        return new Result(false);
    }

    private boolean doSimpleUnparking(Car car, List<ParkingPlace> parkingLine) {
        for (ParkingPlace parkingPlace : parkingLine) {
            if (!parkingPlace.isFree()
                    && parkingPlace.getCar().getCarId() == car.getCarId()) {
                parkingPlace.setFree(true);
                parkingPlace.setCar(null);
                return true;
            }
        }
        return false;
    }

    private Result unparkTruck(Car car) {
        boolean isFreed = false;
        for (ParkingPlace carparkingPlace : carParkingLine) {
            if (!carparkingPlace.isFree()
                    && carparkingPlace.getCar().getCarId() == car.getCarId()) {
                carparkingPlace.setFree(true);
                carparkingPlace.setCar(null);
                isFreed = true;
            }
        }
        return new Result(isFreed);
    }

    @Override
    public Car findCarById(int id) {
        Car car = findCarOnParking(id, carParkingLine);
        if (car != null) {
            return car;
        }
        return findCarOnParking(id, truckParkingLine);
    }

    private Car findCarOnParking(int id, List<ParkingPlace> parkingLine) {
        for (ParkingPlace parkingPlace : parkingLine) {
            Car car = parkingPlace.getCar();
            if (car != null && car.getCarId() == id) {
                return car;
            }
        }
        return null;
    }
}
