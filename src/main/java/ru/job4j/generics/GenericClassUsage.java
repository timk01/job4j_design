package ru.job4j.generics;

public class GenericClassUsage {
    public static void main(String[] args) {
        GenericsClass<String, String> first = new GenericsClass<>("First key", "First value");
        System.out.println("Вывод в консоль: " + first);

        GenericsClass<Integer, String> second = new GenericsClass<>(12345, "Second value");
        System.out.println("Вывод в консоль: " + second);
    }
}
