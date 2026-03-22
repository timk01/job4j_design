package ru.job4j.ood.lcp.foodkeper.food;

import java.util.GregorianCalendar;

public class Eggs extends Food {
    public Eggs(String name,
                double price,
                double discount,
                GregorianCalendar createDate,
                GregorianCalendar expiryDate) {
        super(name, price, discount, createDate, expiryDate);
    }
}
