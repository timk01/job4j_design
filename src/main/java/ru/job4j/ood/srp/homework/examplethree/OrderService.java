package ru.job4j.ood.srp.homework.examplethree;

public class OrderService {
    public void sendOrder(OrderInfo orderInfo) {
        System.out.println("Order was sent to email: " + orderInfo.getCustomerEmail());
    }
}