package ru.job4j.collection;

public class SimpleStack<T> {

    private ForwardLinked<T> linked = new ForwardLinked<T>();

    public T pop() {
        return linked.deleteFirst();
    }

    public void push(T value) {
        linked.addToStart(value);
    }

    public void addToEnd(T value) {
        linked.add(value);
    }

    public T getLastElem() {
        return linked.deleteLast();
    }
}