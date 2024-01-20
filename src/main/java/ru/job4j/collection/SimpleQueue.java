package ru.job4j.collection;

import java.util.NoSuchElementException;

public class SimpleQueue<T> {
    private final SimpleStack<T> input = new SimpleStack<>();
    private final SimpleStack<T> output = new SimpleStack<>();

    public T poll() {
        try {
            if (output.getSize() == 0) {
                while (input.getSize() != 0) {
                    output.push(input.pop());
                }
            }
            return output.pop();
        } catch (NoSuchElementException e) {
            throw new NoSuchElementException("Queue is empty");
        }
    }

    public void push(T value) {
        input.push(value);
    }
}