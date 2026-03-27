package ru.job4j.ood.lcp.foodkeper.store.discount;

import ru.job4j.ood.lcp.foodkeper.food.Food;
import ru.job4j.ood.lcp.foodkeper.store.DataProvider;
import ru.job4j.ood.lcp.foodkeper.store.ShopDataProvider;

import static ru.job4j.ood.lcp.foodkeper.store.ExpirationCalculator.countExpiration;

public class ShopDiscounter implements DiscountPolicy {
    private static final double EXPIRATION_LOWER_THRESHOLD = 0.75;
    private static final double EXPIRATION_HIGHER_THRESHOLD = 1.0;
    private static final double DISCOUNT = 0.8;

    private final DataProvider dataProvider;

    public ShopDiscounter(DataProvider dataProvider) {
        this.dataProvider = dataProvider;
    }

    @Override
    public void makeDiscount(Food food) {
        double expiration = countExpiration(food, dataProvider.getCurrentDate());
        if (expiration >= EXPIRATION_LOWER_THRESHOLD && expiration < EXPIRATION_HIGHER_THRESHOLD) {
            food.setPrice(food.getPrice() * DISCOUNT);
        }
    }
}
