package ru.job4j.generics;

import java.util.*;

public class RawUsage {
    public static void main(String[] args) {
        List list = new ArrayList();
        list.add("first");
        list.add("second");
        list.add("third");
        list.add(new Person("name", 21, new Date(913716000000L)));
        System.out.println("Количество элементов в списке: " + list.size());
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i) instanceof String) {
                String line = (String) list.get(i);
                System.out.println("Текущий элемент: " + line);
            }
            if (list.get(i) instanceof Person) {
                Person person = (Person) list.get(i);
                System.out.println(person);
            }
        }
    }
}
