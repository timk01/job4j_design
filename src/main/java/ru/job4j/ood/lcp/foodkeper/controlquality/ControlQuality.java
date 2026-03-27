package ru.job4j.ood.lcp.foodkeper.controlquality;

import ru.job4j.ood.lcp.foodkeper.food.Food;
import ru.job4j.ood.lcp.foodkeper.store.Store;
import ru.job4j.ood.lcp.foodkeper.store.strategy.Strategy;

import java.util.ArrayList;
import java.util.List;

public class ControlQuality {
    private final List<StoreAndStrategy> strategies;

    public ControlQuality(List<StoreAndStrategy> strategies) {
        this.strategies = strategies;
    }

    /**
     * first time disribution (initial one)
     * @param foodList
     */

    public void distribute(List<Food> foodList) {
        putFoodInStore(foodList);
    }

    /**
     * REdistribution
     */

    public void resort() {
        List<Food> overallFood = new ArrayList<>();
        for (StoreAndStrategy strategy : strategies) {
            Store store = strategy.store();
            List<Food> foods = store.findAll();
            overallFood.addAll(foods);
            store.clean();
        }

        putFoodInStore(overallFood);
    }

    private void putFoodInStore(List<Food> overallFood) {
        for (Food food : overallFood) {
            boolean isCurrentFoodAccepted = false;
            for (StoreAndStrategy strategy : strategies) {
                Store store = strategy.store();
                Strategy chosenStrategy = strategy.strategy();
                if (chosenStrategy.accept(food)) {
                    store.add(food);
                    isCurrentFoodAccepted = true;
                    break;
                }
            }
            wrongStrategies(isCurrentFoodAccepted);
        }
    }

    private static void wrongStrategies(boolean isCurrentFoodAccepted) {
        if (!isCurrentFoodAccepted) {
            throw new IllegalStateException("wrong chosen strategies for food acceptance!");
        }
    }
}
