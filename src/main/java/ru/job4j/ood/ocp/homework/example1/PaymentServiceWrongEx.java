package ru.job4j.ood.ocp.homework.example1;

import org.slf4j.Logger;

public class PaymentServiceWrongEx {
    private final Logger logger;

    private int balance;

    private final String paymentMethod;

    public PaymentServiceWrongEx(int balance, String paymentMethod) {
        this.balance = balance;
        this.paymentMethod = paymentMethod;

        logger = switch (paymentMethod) {
            case "cash" -> pickSlj();
            case "credit card" -> pickLogBack();
            default -> pickDefaultLogger();
        };
    }

    private Logger pickDefaultLogger() {
        System.out.println("picking smth else as logger");
        return null;
    }

    private Logger pickLogBack() {
        System.out.println("picking Logback as example");
        return null;
    }

    private Logger pickSlj() {
        System.out.println("picking Sl4j as example");
        return null;
    }

    private void makePayment() {
        logger.info("making payment now");
        checkingBalance();
        transferFunds();
    }

    private void checkingBalance() {
        logger.info("checking balance has started");
        if (balance < 0) {
            logger.error("balance is negative");
        }
    }

    private void transferFunds() {
        logger.info("funds transfer has started");
    }
}
