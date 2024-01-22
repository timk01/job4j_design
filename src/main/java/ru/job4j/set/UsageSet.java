package ru.job4j.set;

import java.util.*;
import java.util.stream.Collectors;

public class UsageSet {
    public static void main(String[] args) {
        Set<String> strings = new HashSet<>();
        strings.add("one");
        strings.add("two");
        strings.add("three");
        strings.add("two");
        strings.add("null");
        for (String string : strings) {
            System.out.println("Текущий элемент: " + string);
        }

        strings.addAll(List.of("one", "four", "five"));

        System.out.println();
        Iterator<String> stringIterator = strings.iterator();
        while (stringIterator.hasNext()) {
            String nextStr = stringIterator.next();
            System.out.println(nextStr);
        }

        System.out.println();
        Set<String> orderedLinkedStringSet = new LinkedHashSet<>(strings);
        orderedLinkedStringSet.forEach(System.out::println);
        System.out.println();

        Iterator<String> iterator = orderedLinkedStringSet.iterator();
        while (iterator.hasNext()) {
            String next = iterator.next();
            if (next.equals("four")) {
                iterator.remove();
                orderedLinkedStringSet.add(next + " new");
                break;
            }
        }
        orderedLinkedStringSet.forEach(System.out::println);

        System.out.println();
        orderedLinkedStringSet.remove("five");
        orderedLinkedStringSet.add("five new");
        orderedLinkedStringSet.forEach(System.out::println);

        System.out.println();
        orderedLinkedStringSet.removeAll(Set.of("null", "one"));
        orderedLinkedStringSet.forEach(System.out::println);

        System.out.println();
        orderedLinkedStringSet.retainAll(Set.of("two", "five new"));
        orderedLinkedStringSet.forEach(System.out::println);

        System.out.println();
        orderedLinkedStringSet.addAll(List.of("five", "five", "six", "seven"));
        orderedLinkedStringSet.forEach(System.out::println);

        System.out.println();
        orderedLinkedStringSet.removeIf(s -> s.startsWith("f"));
        orderedLinkedStringSet.forEach(System.out::println);

        System.out.println();
        Set<String> changedLHS = orderedLinkedStringSet.stream()
                .filter(s -> s.startsWith("s"))
                .collect(Collectors.toSet());
        System.out.println("changedLHS");
        changedLHS.forEach(System.out::println);
        orderedLinkedStringSet = changedLHS;
        System.out.println("supposed to change, and it does, since in ONE method ");
        orderedLinkedStringSet.forEach(System.out::println);


    }
}