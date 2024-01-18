package ru.job4j.iterator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class IteratorModExc {
    public static void main(String[] args) {
        List<Integer> integerList = new ArrayList<>();
        integerList.addAll(List.of(1, 2, 3));
        Iterator<Integer> iterator = integerList.iterator();
        System.out.println(iterator.hasNext());
        System.out.println(iterator.next());
        integerList.add(1);
        System.out.println(integerList);
        System.out.println(iterator.hasNext());

        /**
         * till THIS:
         * iterator.next();
         */
    }
}
