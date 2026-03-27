package ru.job4j.ood.dip.exampleone;

public class DatabaseOrderRepository implements OrderRepository {
    private final Database database;

    public DatabaseOrderRepository(Database database) {
        this.database = database;
    }

    @Override
    public void save(Order order) {
        database.save(order);
    }
}