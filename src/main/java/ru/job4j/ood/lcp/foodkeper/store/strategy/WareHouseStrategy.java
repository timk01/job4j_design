package ru.job4j.ood.lcp.foodkeper.store.strategy;

import ru.job4j.ood.lcp.foodkeper.food.Food;
import ru.job4j.ood.lcp.foodkeper.store.DataProvider;
import ru.job4j.ood.lcp.foodkeper.store.ShopDataProvider;

import static ru.job4j.ood.lcp.foodkeper.store.ExpirationCalculator.countExpiration;

public class WareHouseStrategy implements Strategy {
    private static final double WAREHOUSE_FOOD_EXPIRATION = 0.25;

    private final DataProvider dataProvider;

    public WareHouseStrategy(DataProvider dataProvider) {
        this.dataProvider = dataProvider;
    }


    @Override
    public boolean accept(Food food) {
        return countExpiration(food, dataProvider.getCurrentDate()) < WAREHOUSE_FOOD_EXPIRATION;
    }
}

