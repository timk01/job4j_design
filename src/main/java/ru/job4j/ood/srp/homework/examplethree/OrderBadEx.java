package ru.job4j.ood.srp.homework.examplethree;

public class OrderBadEx {
    private String productName;
    private int quantity;
    private double pricePerItem;
    private String customerEmail;

    public OrderBadEx(String productName, int quantity, double pricePerItem, String customerEmail) {
        this.productName = productName;
        this.quantity = quantity;
        this.pricePerItem = pricePerItem;
        this.customerEmail = customerEmail;
    }

    public String getProductName() {
        return productName;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getPricePerItem() {
        return pricePerItem;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public double calculateTotalPrice() {
        return quantity * pricePerItem;
    }

    public void sendOrder() {
        System.out.println("Order was sent to email: " + customerEmail);
    }
}