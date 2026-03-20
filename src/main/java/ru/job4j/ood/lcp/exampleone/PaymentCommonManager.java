package ru.job4j.ood.lcp.exampleone;

public class PaymentCommonManager implements PaymentProcessor, RefundableProcessor {
    private Card card;
    @Override
    public void pay(long expences) {
        card.setMoney(card.getMoney() - expences);
    }

    @Override
    public void refund(long expences) {
        card.setMoney(card.getMoney() + expences);
    }
}
