package ru.job4j.list;

import java.util.*;

public class ListUsage {
    public static void main(String[] args) {
        List<String> result = new ArrayList<>();
        result.add("one");
        result.add("two");
        result.add("three");
        result.add(1, "four");

        List<String> list = new ArrayList<>();
        list.add("four");
        list.add("five");
        result.addAll(list);

        List<String> list2 = new ArrayList<>();
        list2.add("four");
        list2.add("five");

        result.addAll(2, list2);

        for (String string : result) {
            System.out.println("Текущий элемент: " + string);
        }

        System.out.println();
        for (int i = 0; i < result.size(); i++) {
            System.out.println("Текущий элемент: " + result.get(i));
        }

        System.out.println();
        Iterator<String> iterator = result.iterator();
        while (iterator.hasNext()) {
            System.out.println("Текущий элемент: " + iterator.next());
        }

        System.out.println();
        ListIterator<String> listIterator = result.listIterator();
        while (listIterator.hasNext()) {
            System.out.println("Текущий элемент: " + listIterator.next());
        }

        System.out.println();
        System.out.println("listIterator, indexed");
        ListIterator<String> indexedListIterator = result.listIterator(2);
        while (indexedListIterator.hasNext()) {
            System.out.println("Текущий элемент: " + indexedListIterator.next());
        }

        System.out.println();
        result.set(1, "two and second");

        System.out.println();
        result.replaceAll(String::toUpperCase);
        result.forEach(System.out::println);

        System.out.println();
        result.remove(1);
        result.replaceAll(
                elem -> {
                    if (elem.equalsIgnoreCase("five")) {
                        elem = elem.toLowerCase();
                    }
                    return elem;
                });
        System.out.println("after FIVE replacement");
        result.forEach(System.out::println);

        System.out.println();
        result.remove("five");
        System.out.println("after five removal");
        result.forEach(System.out::println);

        System.out.println();
        System.out.println(result.removeAll(List.of("FOUR")));
        result = result.stream().map(String::toLowerCase).toList();
        result.forEach(System.out::println);
        System.out.println("BEWARE: result here after stream .toList() is actually... unmodifable list o_O");

        System.out.println();
        List<String> modifableResult = new ArrayList<>(result);
        modifableResult.retainAll(Arrays.asList("five", "zero", "one"));
        modifableResult.forEach(System.out::println);

        System.out.println();
        modifableResult.removeIf(string -> string.startsWith("o"));
        modifableResult.forEach(System.out::println);

        System.out.println("BEWARE: .remove(obj), .contains(obj), .indexOf(), .lastIndexOf(obj)");
        System.out.println("ALL go through all array... so, they are SLOW and ineffective");
        boolean check = modifableResult.contains("two");
        System.out.println("Список содержит элемент: " + check);

        System.out.println();
        int index = modifableResult.indexOf("five");
        System.out.println("Индекс элемента в списке: " + index);

        System.out.println();
        modifableResult.add("five");
        index = modifableResult.lastIndexOf("one");
        System.out.println("Индекс элемента в списке: " + index);
        index = modifableResult.lastIndexOf("five");
        System.out.println("Индекс элемента в списке: " + index);

        System.out.println();
        System.out.println(modifableResult.size());

        System.out.println();
        System.out.println(modifableResult.subList(1, 2));

        System.out.println();
        modifableResult.addAll(List.of("three", "one"));
        modifableResult.sort(Comparator.naturalOrder());
        modifableResult.forEach(System.out::println);

        System.out.println();
        modifableResult.sort(Comparator.comparing(String::length));
        modifableResult.forEach(System.out::println);

    }
}