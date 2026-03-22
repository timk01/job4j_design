package ru.job4j.ood.lcp.foodkeper.controlquality;

import ru.job4j.ood.lcp.foodkeper.food.Food;
import ru.job4j.ood.lcp.foodkeper.store.Store;
import ru.job4j.ood.lcp.foodkeper.store.strategy.Strategy;

import java.util.List;

public class ControlQuality {
    private final List<Food> foodList;
    private final List<StoreAndStrategy> strategies;

    public ControlQuality(List<Food> foodList, List<StoreAndStrategy> strategies) {
        this.foodList = foodList;
        this.strategies = strategies;
    }

    public void putFoodInStore() {
        for (Food food : foodList) {
            for (StoreAndStrategy strategy : strategies) {
                Store store = strategy.store();
                Strategy chosenStrategy = strategy.strategy();
                if (chosenStrategy.accept(food)) {
                    store.add(food);
                    break;
                }
            }
        }
    }
}
