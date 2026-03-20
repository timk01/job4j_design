package ru.job4j.ood.lcp.exampleone;

public class PaymentProcessorBadEx {
    private final Card card;

    public PaymentProcessorBadEx(Card card) {
        this.card = card;
    }

    protected Card getCard() {
        return card;
    }

    void paymentProcess(long expences) {
        card.setMoney(card.getMoney() - expences);
    }

    void refund(long sum) throws IllegalAccessException {
        card.setMoney(card.getMoney() + sum);
    }
}
