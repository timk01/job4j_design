package ru.job4j.ood.lcp.exampleone;

import java.time.LocalDateTime;

public class Card {
    private String owner;
    private String currency;
    private LocalDateTime opened;
    private long number;

    private double money;

    public Card(String owner, String currency, LocalDateTime opened, long number, double money) {
        this.owner = owner;
        this.currency = currency;
        this.opened = opened;
        this.number = number;
        this.money = money;
    }

    public String getOwner() {
        return owner;
    }

    public String getCurrency() {
        return currency;
    }

    public LocalDateTime getOpened() {
        return opened;
    }

    public long getNumber() {
        return number;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }
}
