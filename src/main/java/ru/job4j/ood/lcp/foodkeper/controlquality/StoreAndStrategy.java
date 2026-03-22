package ru.job4j.ood.lcp.foodkeper.controlquality;

import ru.job4j.ood.lcp.foodkeper.store.Store;
import ru.job4j.ood.lcp.foodkeper.store.strategy.Strategy;

/**
 * RIGHT order: warehouse->shop(without discount and with)->trash (from fresh food to trash)
 *
 * @param store
 * @param strategy
 */

public record StoreAndStrategy(Store store, Strategy strategy) {

}