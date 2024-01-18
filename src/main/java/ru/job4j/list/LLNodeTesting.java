package ru.job4j.list;

import java.util.LinkedList;
import java.util.List;

public class LLNodeTesting {
    protected static List<String> linked = new LinkedList<>();

    public static void main(String[] args) {
        String first = "123";
        String second = "345";
        String third = "567";
        String[] arr = new String[]{first, second, third};
        LLNodeTesting llNodeTesting = new LLNodeTesting();
        llNodeTesting.addingElems(arr);
        llNodeTesting.printColl(linked);
    }

    public void addingElems(String... elems) {
        linked.add(elems[0]);
        linked.add(0, elems[1]);
        linked.add(1, elems[2]);
    }

    private void printColl(List<String> coll) {
        coll.forEach(System.out::println);
    }
}
