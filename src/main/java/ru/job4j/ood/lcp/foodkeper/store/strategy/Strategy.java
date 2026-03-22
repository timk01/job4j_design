package ru.job4j.ood.lcp.foodkeper.store.strategy;

import ru.job4j.ood.lcp.foodkeper.food.Food;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.GregorianCalendar;

/**
 * Стратегия сверху-вниз:
 * прошло менее - 25% = склад (хорошо), с 25-75 - шоп (ОК), с 75 до 1 - шоп с дискаунтом (плохо), более 1 (ужасно)
 */

public interface Strategy {
    boolean accept(Food food);
}
