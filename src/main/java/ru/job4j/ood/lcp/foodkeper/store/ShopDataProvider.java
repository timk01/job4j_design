package ru.job4j.ood.lcp.foodkeper.store;

import java.time.LocalDate;

public class ShopDataProvider implements DataProvider {
    public ShopDataProvider() {

    }

    @Override
    public LocalDate getCurrentDate() {
        return LocalDate.now();
    }
}
