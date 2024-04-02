package ru.job4j.jdbc.sql_trainer;

import java.util.HashMap;

public class ProductManager {
    public static void main(String[] args) {
        HashMap<Product, Integer> products = new HashMap<>();

        Product product1 = new Product("Apple", 1.99);
        Product product2 = new Product("Banana", 0.99);
        Product product3 = new Product("Apple", 1.99);

        products.put(product1, 10);
        products.put(product2, 15);
        products.put(product3, 20);

        for (Product product : products.keySet()) {
            System.out.println("Товар: " + product.getName() + ", количество: " + products.get(product));
        }
    }
}