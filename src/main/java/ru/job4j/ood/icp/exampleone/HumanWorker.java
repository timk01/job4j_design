package ru.job4j.ood.icp.exampleone;

public class HumanWorker implements WorkerBadEx {
    @Override
    public void work() {
        System.out.println("human working");
    }

    @Override
    public void eat() {
        System.out.println("human eating");
    }

    @Override
    public void sleep() {
        System.out.println("human sleeping");
    }
}
