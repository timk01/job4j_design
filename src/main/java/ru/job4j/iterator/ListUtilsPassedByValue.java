package ru.job4j.iterator;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ListUtilsPassedByValue {

    public static <T> void addBefore(List<T> list, int index, T value) {
        Objects.checkIndex(index, list.size());
        list.listIterator(index).add(value);
    }

    public static <T> void addAfter(List<T> list, int index, T value) {
        Objects.checkIndex(index, list.size());
        list.listIterator(index + 1).add(value);
    }

    public static <T> void removeIf(List<T> list, Predicate<T> filter) {
        ListIterator<T> iterator = list.listIterator();
        while (iterator.hasNext()) {
            if (filter.test(iterator.next())) {
                iterator.remove();
            }
        }
    }

    public static <T> void replaceIf(List<T> list, Predicate<T> filter, T value) {
        ListIterator<T> iterator = list.listIterator();
        while (iterator.hasNext()) {
            if (filter.test(iterator.next())) {
                iterator.set(value);
            }
        }
    }

    /**
     * see collents in TG from 22.01.24. Passed by value
     * @param list
     * @param elements
     * @param <T>
     */

    public static <T> void removeAll(List<T> list, List<T> elements) {
        List<T> collect = list.stream().filter(element -> !elements.contains(element)).collect(Collectors.toList());
        list.clear();
        list.addAll(collect);
    }

    public static void main(String[] args) {
        ArrayList<Integer> integers2 = new ArrayList<>(Arrays.asList(1, 3, 3, 9, 5, 10, 12));
        ArrayList<Integer> integers = new ArrayList<>(Arrays.asList(3, 5));
        removeAll(integers2, integers);
        System.out.println(integers2);
    }
}