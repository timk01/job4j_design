package ru.job4j.collection;

import java.util.NoSuchElementException;

public class SimpleStack<T> {

    private ForwardLinked<T> linked = new ForwardLinked<T>();

    public T pop() {
        try {
            return linked.deleteFirst();
        } catch (NoSuchElementException e) {
            throw new NoSuchElementException("Stack is empty");
        }
    }

    public void push(T value) {
        linked.addFirst(value);
    }

    public int getSize() {
        return linked.getSize();
    }
}