package ru.job4j.ood.lcp.foodkeper.store;

import java.time.LocalDate;

public class FixedDateProvider implements DataProvider {
    private final LocalDate currentDate;

    public FixedDateProvider(LocalDate currentDate) {
        this.currentDate = currentDate;
    }

    @Override
    public LocalDate getCurrentDate() {
        return currentDate;
    }
}