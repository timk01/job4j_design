package ru.job4j.iterator;

import java.util.List;
import java.util.stream.Collectors;

public class FlatMapViaStream {
    public static void main(String[] args) {
        List<List<Integer>> data = List.of(
                List.of(1, 2, 3),
                List.of(4, 5, 6),
                List.of(7, 8, 9)
        );
        List<Integer> flat =
                data.stream()
                        .flatMap(List::stream)
                        .collect(Collectors.toList());
        System.out.println(flat);
    }
}