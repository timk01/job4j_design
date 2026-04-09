package ru.job4j.newcoll.tree;

import ru.job4j.collection.ForwardLinked;

public class SimpleStack<T> {

    private ForwardLinked<T> linked = new ForwardLinked<T>();

    public T pop() {
        return linked.deleteFirst();
    }

    public void push(T value) {
        linked.addFirst(value);
    }

    public boolean isEmpty() {
        return linked.getSize() <= 0;
    }
}