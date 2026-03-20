package ru.job4j.ood.lcp.precondition;

public class AutoTransport {
    protected int fuel;

    public AutoTransport(int fuel) {
        this.fuel = fuel;
    }

    public void move(float km) {
        if (km < 0) {
            throw new IllegalArgumentException("invalid distance!");
        }
        if (fuel < 0) {
            throw new IllegalArgumentException("need more fuel!");
        }
    }
}
