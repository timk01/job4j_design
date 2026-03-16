package ru.job4j.ood.ocp.homework.example2;

public class CreditCardPaymentProcessorWrongExImpl extends PaymentProcessorWrongEx {
    public void payWithCreditCard() {
        super.pay();
        System.out.println("and with creditCard too");
    }
}
