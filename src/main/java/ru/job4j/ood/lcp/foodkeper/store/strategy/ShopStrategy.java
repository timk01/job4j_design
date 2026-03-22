package ru.job4j.ood.lcp.foodkeper.store.strategy;

import ru.job4j.ood.lcp.foodkeper.food.Food;

import static ru.job4j.ood.lcp.foodkeper.store.ExpirationCalculator.countExpiration;

public class ShopStrategy implements Strategy {

    private static final double SHOP_EXPIRATION_LOWER_THRESHOLD = 0.25;
    private static final double SHOP_EXPIRATION_HIGHER_THRESHOLD = 1.0;

    @Override
    public boolean accept(Food food) {
        double expiration = countExpiration(food);
        return expiration >= SHOP_EXPIRATION_LOWER_THRESHOLD && expiration < SHOP_EXPIRATION_HIGHER_THRESHOLD;
    }
}
