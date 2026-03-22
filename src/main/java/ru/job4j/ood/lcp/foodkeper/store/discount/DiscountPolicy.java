package ru.job4j.ood.lcp.foodkeper.store.discount;

import ru.job4j.ood.lcp.foodkeper.food.Food;

public interface DiscountPolicy {
    void makeDiscount(Food food);
}
