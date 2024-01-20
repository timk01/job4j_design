package ru.job4j.collection;

public class SimpleStackAddGetFromEnd<T> {

    private ForwardLinkedCopy<T> linked = new ForwardLinkedCopy<T>();

    public void addToEnd(T value) {
        linked.add(value);
    }

    public T getLastElem() {
        return linked.deleteLast();
    }
}