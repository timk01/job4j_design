package ru.job4j.ood.lcp.foodkeper.store;

import ru.job4j.ood.lcp.foodkeper.food.Food;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class AbstractStore implements Store {
    protected final List<Food> foodList = new ArrayList<>();
    private int uniqueFoodStoreId = 1;

    @Override
    public Food add(Food food) {
        int index = indexOf(food.getId());
        boolean isFoodFound = index != -1;
        if (!isFoodFound) {
            prepareFoodForStorage(food);
            food.setId(uniqueFoodStoreId++);
            foodList.add(food);
            return food;
        }
        return food;
    }

    private int indexOf(int id) {
        int rsl = -1;
        for (int index = 0; index < foodList.size(); index++) {
            if (foodList.get(index).getId() == id) {
                rsl = index;
                break;
            }
        }
        return rsl;
    }

    public void prepareFoodForStorage(Food food) {

    }

    @Override
    public void delete(int id) {
        int index = indexOf(id);
        boolean isFoodFound = index != -1;
        if (isFoodFound) {
            foodList.remove(index);
        }
    }

    @Override
    public List<Food> findAll() {
        return List.copyOf(foodList);
    }

    @Override
    public Food findById(int id) {
        int index = indexOf(id);
        return index != -1 ? foodList.get(index) : null;
    }

    @Override
    public List<Food> findByName(String name) {
        List<Food> foundFood = new ArrayList<>();
        for (Food food : foodList) {
            if (Objects.equals(name, food.getName())) {
                foundFood.add(food);
            }
        }
        return foundFood;
    }
}
