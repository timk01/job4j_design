package ru.job4j.ood.lcp.foodkeper.store.strategy;

import ru.job4j.ood.lcp.foodkeper.food.Food;
import ru.job4j.ood.lcp.foodkeper.store.DataProvider;
import ru.job4j.ood.lcp.foodkeper.store.ShopDataProvider;

import static ru.job4j.ood.lcp.foodkeper.store.ExpirationCalculator.countExpiration;

public class TrashStrategy implements Strategy {
    private static final double TRASH_FOOD_EXPIRATION = 1.0;

    private final DataProvider dataProvider;

    public TrashStrategy(DataProvider dataProvider) {
        this.dataProvider = dataProvider;
    }

    @Override
    public boolean accept(Food food) {
        return countExpiration(food, dataProvider.getCurrentDate()) >= TRASH_FOOD_EXPIRATION;
    }
}
