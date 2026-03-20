package ru.job4j.ood.lcp.exampleone;

public class GiftCardManager implements PaymentProcessor {

    private Card card;
    @Override
    public void pay(long expences) {
        card.setMoney(card.getMoney() - expences);
    }
}
