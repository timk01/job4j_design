package ru.job4j.collection;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ListToMap {
    public static void main(String[] args) {
        Map<Integer, Integer> collect = Stream.of(1, 1, 2, 2).collect(
                Collectors.toMap(
                        e -> e,
                        e -> e * e,
                        (existing, replacement) -> existing
                ));
        System.out.println(collect.size());
        collect.entrySet().forEach((entry -> System.out.println(entry.getKey() + " " + entry.getValue())));
    }
}