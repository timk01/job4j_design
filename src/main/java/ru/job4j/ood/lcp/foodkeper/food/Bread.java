package ru.job4j.ood.lcp.foodkeper.food;

import java.util.GregorianCalendar;

public class Bread extends Food {
    public Bread(String name,
                 double price,
                 double discount,
                 GregorianCalendar createDate,
                 GregorianCalendar expiryDate) {
        super(name, price, discount, createDate, expiryDate);
    }
}
