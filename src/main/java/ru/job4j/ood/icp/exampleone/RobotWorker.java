package ru.job4j.ood.icp.exampleone;

public class RobotWorker implements WorkerBadEx {
    @Override
    public void work() {
        System.out.println("robot working");
    }

    @Override
    public void eat() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void sleep() {
        throw new UnsupportedOperationException();
    }
}
