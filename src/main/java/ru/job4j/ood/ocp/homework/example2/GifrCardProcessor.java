package ru.job4j.ood.ocp.homework.example2;

public class GifrCardProcessor implements PaymentProcessor {
    @Override
    public void pay() {
        System.out.println("pay with giftcard");
    }
}
