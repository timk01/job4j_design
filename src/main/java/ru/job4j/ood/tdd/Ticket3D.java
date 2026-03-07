package ru.job4j.ood.tdd;

public class Ticket3D implements Ticket {

    @Override
    public int hashCode() {
        return Ticket3D.class.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return obj instanceof Ticket3D;
    }
}
