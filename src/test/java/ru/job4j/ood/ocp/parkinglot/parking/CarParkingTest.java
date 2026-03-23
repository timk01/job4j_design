package ru.job4j.ood.ocp.parkinglot.parking;

import org.junit.jupiter.api.Test;
import ru.job4j.ood.ocp.parkinglot.car.Car;
import ru.job4j.ood.ocp.parkinglot.car.PassengerCar;
import ru.job4j.ood.ocp.parkinglot.car.Truck;

import static org.assertj.core.api.Assertions.*;

class CarParkingTest {

    @Test
    void whenParkingHasNoCarsSlotsAtAllThenResultIsFalse() {
        CarParking carParking = new CarParking(0, 1);
        Car car = new PassengerCar(1, "minivan");
        Result result = carParking.park(car);
        assertThat(result.isParked()).isEqualTo(false);
    }

    @Test
    void whenParkingHasNoTrucksSlotsAtAllButHasOneCaSlotThenResultIsFalse() {
        CarParking carParking = new CarParking(1, 0);
        Car truck = new Truck(2, "middle truck");
        Result result = carParking.park(truck);
        assertThat(result.isParked()).isEqualTo(false);
    }

    @Test
    void whenParkingHas1FreeCarSlotThenResultIsTrue() {
        CarParking carParking = new CarParking(1, 0);
        Car car = new PassengerCar(1, "minivan");
        Result result = carParking.park(car);
        assertThat(result.isParked()).isEqualTo(true);
    }

    @Test
    void whenParkingHas1FreeTruckSlotThenResultIsTrue() {
        CarParking carParking = new CarParking(0, 1);
        Car truck = new Truck(2, "middle truck");
        Result result = carParking.park(truck);
        assertThat(result.isParked()).isEqualTo(true);
    }

    @Test
    void whenParkingHas1CarSlotButItIsNotFreeThenResultIsFalse() {
        CarParking carParking = new CarParking(1, 0);
        Car car1 = new PassengerCar(1, "minivan");
        carParking.park(car1);
        Car car2 = new PassengerCar(1, "minivan");
        Result result = carParking.park(car2);
        assertThat(result.isParked()).isEqualTo(false);
    }

    @Test
    void whenParkingHas1TruckSlotButItIsNotFreeAnd0CarsSlotsThenResultIsFalse() {
        CarParking carParking = new CarParking(0, 1);
        Car truck1 = new Truck(2, "middle truck");
        carParking.park(truck1);
        Car truck2 = new Truck(2, "middle truck");
        Result result = carParking.park(truck2);
        assertThat(result.isParked()).isEqualTo(false);
    }

    @Test
    void whenParkingHasNoTruckSlotAndHasSeveralEmptyCarsSlotsThenResultIsTrue() {
        CarParking carParking = new CarParking(2, 0);
        Car truck1 = new Truck(2, "middle truck");
        Result result = carParking.park(truck1);
        assertThat(result.isParked()).isEqualTo(true);
    }

    @Test
    void whenParkingHasNoTruckSlotAndHasSeveralCarsBusySlotsThenResultIsFalse() {
        CarParking carParking = new CarParking(2, 0);
        Car car1 = new PassengerCar(1, "minivan");
        carParking.park(car1);
        Car truck1 = new Truck(2, "middle truck");
        Result result = carParking.park(truck1);
        assertThat(result.isParked()).isEqualTo(false);
    }

    @Test
    void whenParkingHasNoTruckSlotAndHasSeveralParkedCarsButWithNotEnoughSlotsForTruckThenResultIsFalse() {
        CarParking carParking = new CarParking(4, 0);
        Car car1 = new PassengerCar(1, "minivan");
        Car car2 = new PassengerCar(1, "minivan");
        Car car3 = new PassengerCar(1, "minivan");
        carParking.park(car1);
        carParking.park(car2);
        carParking.park(car3);
        Car truck1 = new Truck(2, "middle truck");
        Result result = carParking.park(truck1);
        assertThat(result.isParked()).isEqualTo(false);
    }

    @Test
    void whenParkingHasNoTruckSlotAndHasSeveralParkedCarsButWithEnoughSlotsForTruckThenResultIsTrue() {
        CarParking carParking = new CarParking(4, 0);
        Car car1 = new PassengerCar(1, "minivan");
        Car car2 = new PassengerCar(1, "minivan");
        carParking.park(car1);
        carParking.park(car2);
        Car truck1 = new Truck(2, "middle truck");
        Result result = carParking.park(truck1);
        assertThat(result.isParked()).isEqualTo(true);
    }

    @Test
    void whenParkingHasBothSlotsButStillNotEnoughPlaceForTruckWhenResultIsFalse() {
        CarParking carParking = new CarParking(4, 1);
        Car car1 = new PassengerCar(1, "minivan");
        Car car2 = new PassengerCar(1, "minivan");
        Car car3 = new PassengerCar(1, "minivan");
        carParking.park(car1);
        carParking.park(car2);
        carParking.park(car3);
        Car truck1 = new Truck(2, "middle truck");
        carParking.park(truck1);
        Car truck2 = new Truck(2, "middle truck");
        Result result = carParking.park(truck2);
        assertThat(result.isParked()).isEqualTo(false);
    }

    @Test
    void whenNothingTtoUnpark() {
        CarParking carParking = new CarParking(1, 0);
        Car truck = new Truck(2, "middle truck");
        Result result = carParking.unPark(truck);
        assertThat(result.isParked()).isEqualTo(false);
    }

    @Test
    void whenCarIsParkedThenUnparkSuccessed() {
        CarParking carParking = new CarParking(1, 0);
        Car car = new PassengerCar(1, "minivan");
        carParking.park(car);
        Result result = carParking.unPark(car);
        assertThat(result.isParked()).isEqualTo(true);
    }

    @Test
    void whenAnotherCarIsParkedThenThisCarUnparkFailed() {
        CarParking carParking = new CarParking(1, 0);
        Car car1 = new PassengerCar(1, "minivan");
        carParking.park(car1);
        Car car2 = new PassengerCar(1, "minivan");
        Result result = carParking.unPark(car2);
        assertThat(result.isParked()).isEqualTo(false);
    }

    @Test
    void whenTruckIsParkedThenUnparkSuccessed() {
        CarParking carParking = new CarParking(0, 1);
        Car truck = new Truck(2, "middle truck");
        carParking.park(truck);
        Result result = carParking.unPark(truck);
        assertThat(result.isParked()).isEqualTo(true);
    }

    @Test
    void whenBothCarAreParkedThenUnparkSuccessed() {
        CarParking carParking = new CarParking(2, 0);
        Car car1 = new PassengerCar(1, "minivan");
        carParking.park(car1);
        Car car2 = new PassengerCar(1, "minivan");
        carParking.park(car2);
        Result result1 = carParking.unPark(car1);
        Result result2 = carParking.unPark(car2);
        assertThat(result1.isParked()).isEqualTo(true);
        assertThat(result2.isParked()).isEqualTo(true);
    }

    @Test
    void whenTruckIsParkedOnCarPlacesThenUnparkSuccessed() {
        CarParking carParking = new CarParking(2, 0);
        Car truck = new Truck(2, "middle truck");
        carParking.park(truck);
        Result result = carParking.unPark(truck);
        assertThat(result.isParked()).isEqualTo(true);
    }

    @Test
    void whenTruckIsParkedOnCarPlacesWithCarParkedThenUnparkSuccessed() {
        CarParking carParking = new CarParking(3, 0);
        Car car1 = new PassengerCar(1, "minivan");
        carParking.park(car1);
        Car truck = new Truck(2, "middle truck");
        carParking.park(truck);
        Result result = carParking.unPark(truck);
        assertThat(result.isParked()).isEqualTo(true);
    }

    @Test
    void whenNothingIsFoundThenReturnNull() {
        CarParking carParking = new CarParking(3, 0);
        Car foundCar = carParking.findCarById(100);
        assertThat(foundCar).isNull();
    }

    @Test
    void whenCarIsFoundThenReturnCar() {
        CarParking carParking = new CarParking(3, 0);
        Car car = new PassengerCar(1, "minivan");
        carParking.park(car);
        Car foundCar = carParking.findCarById(car.getCarId());
        assertThat(foundCar).isEqualTo(car);
    }

    @Test
    void whenOtherCarsAreParkedThenUnknownIdIsNotFound() {
        CarParking carParking = new CarParking(3, 0);
        Car car1 = new PassengerCar(1, "minivan");
        Car car2 = new PassengerCar(1, "minivan");
        carParking.park(car1);
        carParking.park(car2);
        Car foundCar = carParking.findCarById(100);
        assertThat(foundCar).isNull();
    }

    @Test
    void whenTruckIsFoundThenReturnTruck() {
        CarParking carParking = new CarParking(0, 2);
        Car truck = new Truck(2, "middle truck");
        carParking.park(truck);
        Car foundTruck = carParking.findCarById(truck.getCarId());
        assertThat(foundTruck).isEqualTo(truck);
    }
}