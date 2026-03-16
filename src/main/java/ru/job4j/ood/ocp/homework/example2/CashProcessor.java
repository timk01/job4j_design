package ru.job4j.ood.ocp.homework.example2;

public class CashProcessor implements PaymentProcessor {
    @Override
    public void pay() {
        System.out.println("we pay with cash");
    }
}
