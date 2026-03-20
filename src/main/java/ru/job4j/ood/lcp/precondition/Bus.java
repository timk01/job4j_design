package ru.job4j.ood.lcp.precondition;

public class Bus extends AutoTransport {
    public Bus(int fuel) {
        super(fuel);
    }

    public void move(float km) {
        if (km < 0) {
            throw new IllegalArgumentException("invalid distance!");
        }
        if (fuel < 5) {
            throw new IllegalArgumentException("need more fuel!");
        }
    }
}
