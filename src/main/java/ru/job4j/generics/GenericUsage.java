package ru.job4j.generics;

import java.util.*;

public class GenericUsage {
    public void printResult(Collection<?> collection) {
        for (Iterator<?> iterator = collection.iterator(); iterator.hasNext();) {
            Object next = iterator.next();
            System.out.println("Текущий элемент: " + next);
        }
    }

    public void printInfo(Collection<? extends Person> collection) {
        for (Iterator<? extends Person> iterator = collection.iterator(); iterator.hasNext();) {
            Person next = iterator.next();
            System.out.println(next);
        }
    }

    public void printNumberInfo(Collection<? extends Number> collection) {
        for (Iterator<? extends Number> iterator = collection.iterator(); iterator.hasNext();) {
            Number number = iterator.next();
            System.out.println(number);
        }
    }

    public void addAll(List<? super Integer> list) {
        for (int i = 1; i <= 5; i++) {
            list.add(i);
        }
        list.forEach(System.out::println);
/*        for (Object line : list) {
            System.out.println("Текущий элемент: " + line);
        }*/
    }

    public static void main(String[] args) {
        List<Integer> list = List.of(1, 2, 3, 4, 5);
        new GenericUsage().printResult(list);

        List<Person> per = List.of(new Person("name", 21, new Date(913716000000L)));
        new GenericUsage().printInfo(per);
        List<Programmer> pro = List.of(new Programmer("name123", 23, new Date(913716000000L)));
        new GenericUsage().printInfo(pro);

        System.out.println();
        List<NumberChild> numberList = new ArrayList<>();
        numberList.add(new NumberChild());
        new GenericUsage().printNumberInfo(numberList);


        System.out.println();
        List<Number> aNumberList = new ArrayList<>();
        Number number = 1;
        aNumberList.add(number);
        new GenericUsage().addAll(new ArrayList<Integer>());
        new GenericUsage().addAll(new ArrayList<Number>());
        new GenericUsage().addAll(new ArrayList<Object>());
    }
}