package ru.job4j.objectsstream;

import java.io.Serializable;

public class Car implements Serializable {

    private static final long serialVersionUID = 12L;

    private String brand;

    private String model;

    private int year;

    private int randomInt;

    public Car(String brand, String model, int year) {
        this.brand = brand;
        this.model = model;
        this.year = year;
    }

    public void setRandomInt(int randomInt) {
        this.randomInt = randomInt;
    }

    @Override
    public String toString() {
        return "Car{"
                + "brand='" + brand + '\''
                + ", model='" + model + '\''
                + ", year=" + year
                + '}';
    }
}