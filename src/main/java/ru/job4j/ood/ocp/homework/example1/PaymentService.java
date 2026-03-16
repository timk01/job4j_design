package ru.job4j.ood.ocp.homework.example1;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PaymentService {
    private static final Logger LOGGER = LoggerFactory.getLogger(PaymentService.class);

    private int balance;

    public PaymentService(int balance) {
        this.balance = balance;
    }

    private void makePayment() {
        LOGGER.info("making payment now");
        checkingBalance();
        transferFunds();
    }

    private void checkingBalance() {
        LOGGER.info("checking balance has started");
        if (balance < 0) {
            LOGGER.error("balance is negative");
        }
    }

    private void transferFunds() {
        LOGGER.info("funds transfer has started");
    }
}
