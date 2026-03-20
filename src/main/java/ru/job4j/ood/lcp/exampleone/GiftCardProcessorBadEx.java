package ru.job4j.ood.lcp.exampleone;

public class GiftCardProcessorBadEx extends PaymentProcessorBadEx {

    public GiftCardProcessorBadEx(Card card) {
        super(card);
    }

    @Override
    void refund(long sum) throws IllegalAccessException {
        throw new IllegalAccessException("cannot refund money for gifrcard!");
    }
}
