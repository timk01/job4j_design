package ru.job4j.ood.lcp.foodkeper.store.strategy;

import ru.job4j.ood.lcp.foodkeper.food.Food;

import static ru.job4j.ood.lcp.foodkeper.store.ExpirationCalculator.countExpiration;

public class WareHouseStrategy implements Strategy {
    private static final double WAREHOUSE_FOOD_EXPIRATION = 0.25;

    @Override
    public boolean accept(Food food) {
        return countExpiration(food) < WAREHOUSE_FOOD_EXPIRATION;
    }

}

