package ru.job4j.ood.lcp.exampleone;

import ru.job4j.ood.lcp.exampleone.Card;

/**
 * по идее еще можно добавить функционал (базовый) в оба менеджера
 */

abstract class BasePaymentManager {
    protected Card card;

    protected void withdraw(long amount) {
        card.setMoney(card.getMoney() - amount);
    }
}