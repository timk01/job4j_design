package ru.job4j.ood.lcp.foodkeper.store.strategy;

import ru.job4j.ood.lcp.foodkeper.food.Food;

import static ru.job4j.ood.lcp.foodkeper.store.ExpirationCalculator.countExpiration;

public class TrashStrategy implements Strategy {
    private static final double TRASH_FOOD_EXPIRATION = 1.0;

    @Override
    public boolean accept(Food food) {
        return countExpiration(food) >= TRASH_FOOD_EXPIRATION;
    }
}
