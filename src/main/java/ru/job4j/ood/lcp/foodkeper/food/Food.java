package ru.job4j.ood.lcp.foodkeper.food;

import java.util.GregorianCalendar;
import java.util.Objects;

/**
 * не используй ты черт, всякое говно типа Date, Calendar, GregorianCalendar
 * используй: LocalDate, LocalDateTime
 */

public abstract class Food {
    private int id;
    private final String name;
    private double price;
    private final double discount;
    private final GregorianCalendar createDate;
    private final GregorianCalendar expiryDate;

    public Food(String name, double price, double discount, GregorianCalendar createDate, GregorianCalendar expiryDate) {
        this.name = name;
        this.price = price;
        this.discount = discount;
        this.createDate = createDate;
        this.expiryDate = expiryDate;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public double getDiscount() {
        return discount;
    }

    public GregorianCalendar getCreateDate() {
        return createDate;
    }

    public GregorianCalendar getExpiryDate() {
        return expiryDate;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Food food = (Food) o;
        return id == food.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Food{"
                + "id=" + id
                + ", name='" + name + '\''
                + ", price=" + price
                + ", discount=" + discount
                + ", createDate=" + createDate
                + ", expiryDate=" + expiryDate
                + '}';
    }
}
