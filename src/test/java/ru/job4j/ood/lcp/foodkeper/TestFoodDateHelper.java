package ru.job4j.ood.lcp.foodkeper;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.GregorianCalendar;

public final class TestFoodDateHelper {
    private TestFoodDateHelper() {
    }

    private static final LocalDate REFERENCE_DATE = LocalDate.of(2026, 3, 22);

    public static GregorianCalendar toCalendar(LocalDate date) {
        return GregorianCalendar.from(date.atStartOfDay(ZoneId.systemDefault()));
    }

    public static LocalDate getReferenceDate() {
        return REFERENCE_DATE;
    }
}
