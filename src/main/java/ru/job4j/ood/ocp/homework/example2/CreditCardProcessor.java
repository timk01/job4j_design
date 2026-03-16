package ru.job4j.ood.ocp.homework.example2;

public class CreditCardProcessor implements PaymentProcessor {
    @Override
    public void pay() {
        System.out.println("do smth specific, pay with carrd");
    }
}
