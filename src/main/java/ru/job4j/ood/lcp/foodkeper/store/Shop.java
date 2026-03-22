package ru.job4j.ood.lcp.foodkeper.store;

import ru.job4j.ood.lcp.foodkeper.food.Food;
import ru.job4j.ood.lcp.foodkeper.store.discount.DiscountPolicy;

public class Shop extends AbstractStore {
    private final DiscountPolicy discountPolicy;

    public Shop(DiscountPolicy discountPolicy) {
        this.discountPolicy = discountPolicy;
    }

    public void prepareFoodForStorage(Food food) {
        discountPolicy.makeDiscount(food);
    }
}
