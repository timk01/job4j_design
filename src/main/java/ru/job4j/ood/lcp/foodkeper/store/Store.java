package ru.job4j.ood.lcp.foodkeper.store;

import ru.job4j.ood.lcp.foodkeper.food.Food;

import java.util.List;

public interface Store {
    Food add(Food food);

    void delete(int id);

    List<Food> findAll();

    List<Food> findByName(String name);

    Food findById(int id);
}
