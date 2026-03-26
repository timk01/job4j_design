package ru.job4j.ood.icp.dfsalghorhytpm;

public class Main {
    public static void main(String[] args) {
        Example example = new Example("hello");
        System.out.println(example.getValue());

        ExampleBox box = new ExampleBox();
        box.add("one");
        box.add("two");
        box.add("three");

        for (String value : box) {
            System.out.println(value);
        }
    }
}

class Example {
    private final String value;

    public Example(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}

class ExampleBox implements Iterable<String> {
    private final java.util.List<String> values = new java.util.ArrayList<>();

    public void add(String value) {
        values.add(value);
    }

    @Override
    public java.util.Iterator<String> iterator() {
        return values.iterator();
    }
}