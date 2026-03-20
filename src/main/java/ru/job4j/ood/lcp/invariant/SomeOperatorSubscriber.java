package ru.job4j.ood.lcp.invariant;

class SomeOperatorSubscriber extends Subscriber {

    public SomeOperatorSubscriber(PhoneNumber phoneNumber) {
        super(phoneNumber);
    }

    @Override
    public void setPhoneNumber(PhoneNumber phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}