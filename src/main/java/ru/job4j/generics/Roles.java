package ru.job4j.generics;

public enum Roles {
    OWNER("Владелец"),
    ADMINISTRATOR("Администратор магазина"),
    CASHIER("Кассир"),
    BUYER("Покупатель"),
    SELLER("Продавец"),
    DELIVERER("Доставщик");

    private String description;

    Roles(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
