package ru.job4j.list;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

public class ListIteratorLinkedListUsage {
    public static void main(String[] args) {
        List<Integer> list = new LinkedList<>();
        for (int i = 0; i < 100; i++) {
            list.add(i);
        }
        ListIterator<Integer> listIterator = list.listIterator(50);
        if (listIterator.hasNext()) {
            Integer next = listIterator.next();
            listIterator.add(500);
        }
        list.forEach(System.out::println);

    }
}
