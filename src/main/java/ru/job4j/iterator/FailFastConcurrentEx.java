package ru.job4j.iterator;

import java.util.*;

public class FailFastConcurrentEx {
    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5));

        /**
         * works fine.
         */

        for (Iterator<Integer> iterator = list.iterator(); iterator.hasNext();) {
            Integer next = iterator.next();
            if (next.equals(5)) {
                iterator.remove();
            }
        }
        System.out.println(list);

        System.out.println();
        Iterator<Integer> iterator = list.iterator();
        while (iterator.hasNext()) {
            int element = iterator.next();
            if (element == 2) {
                iterator.remove();
            }
        }
        System.out.println(list);

        /**
         * fails! use listIterator
         */

        /**
        List<Integer> list2 = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5));
        ListIterator<Integer> iterator2 = list2.listIterator();
        while (iterator2.hasNext()) {
            Integer value = iterator2.next();
            if (value <= 3) {
                list2.add(value * value);
            }
            iterator2.next();
        }
         */

        List<Integer> list3 = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5));
        ListIterator<Integer> iterator3 = list3.listIterator();
        while (iterator3.hasNext()) {
            Integer value = iterator3.next();
            if (value <= 3) {
                iterator3.add(value * value);
            }
        }
        list3.forEach(System.out::println);
    }
}
