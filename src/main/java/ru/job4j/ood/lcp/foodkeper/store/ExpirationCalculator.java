package ru.job4j.ood.lcp.foodkeper.store;

import ru.job4j.ood.lcp.foodkeper.food.Food;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.GregorianCalendar;

/**
 * считаем разницу с тем когда была произведена И тем когда закончится = общий срок годности
 * считаем разницу между сейчас И датой производства = сколько упустили времени
 * общий % второго на первое, тем большу времени упустили - тем хуже
 * (если в принципе НЕПОЛОЖИТЕЛЬНОЕ число - заранее продукт произведен задним числом (ошибка) или 0 срок годности)
 * 4 варианта.
 * прошло времени совсем немного с момента "нау" (3/30)
 * прошло всемя, но все еще ок - основной сценарий (9/30)
 * прошло всемя и прилично, но все еще ок со скидкой - основной сценарий (25/30)
 * прошло много времени и срок истек (31/30) - срок 30 дней, а везли... 31
 *
 * created ----10 days---- now ----30 days---- expiry (10/40)
 */

public final class ExpirationCalculator {
    private static final double FOOD_MAX_EXPIRATION = 1.0;

    private ExpirationCalculator() {
    }

    public static double countExpiration(Food food) {
        GregorianCalendar createDate = food.getCreateDate();
        GregorianCalendar expiryDate = food.getExpiryDate();
        LocalDate created = createDate.toZonedDateTime().toLocalDate();
        LocalDate expired = expiryDate.toZonedDateTime().toLocalDate();
        long totalShelfLife  = ChronoUnit.DAYS.between(created, expired);
        LocalDate now = LocalDate.now();
        long daysWasted = ChronoUnit.DAYS.between(created, now);
        if (totalShelfLife  <= 0) {
            return FOOD_MAX_EXPIRATION;
        }
        return (double) daysWasted / totalShelfLife;
    }
}
