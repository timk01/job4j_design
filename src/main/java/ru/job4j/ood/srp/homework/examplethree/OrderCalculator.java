package ru.job4j.ood.srp.homework.examplethree;

public class OrderCalculator {
    public double calculateTotalPrice(OrderInfo orderInfo) {
        return orderInfo.getQuantity() * orderInfo.getPricePerItem();
    }
}