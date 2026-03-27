package ru.job4j.ood.dip.exampleone;

public class Database {
    public void save(Order order) {
        System.out.println("Order saved: " + order);
    }

    public Order findById(int id) {
        System.out.println("Find order by id: " + id);
        return null;
    }

    public void delete(int id) {
        System.out.println("Delete order by id: " + id);
    }
}