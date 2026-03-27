package ru.job4j.ood.dip.exampleone;

public class OrderServiceBadEx {
    private final Database database = new Database();

    public void save(Order order) {
        database.save(order);
    }
}