package ru.job4j.ood.icp.device;

public class Server implements DeviceSadExample {
    @Override
    public void in(String data) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void calculate() {
        System.out.println("Do some work!");
    }

    @Override
    public void output() {
        throw new UnsupportedOperationException();
    }
}
