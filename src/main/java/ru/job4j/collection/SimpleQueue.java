package ru.job4j.collection;

import java.util.NoSuchElementException;

public class SimpleQueue<T> {
    private final SimpleStack<T> input = new SimpleStack<>();
    private final SimpleStack<T> output = new SimpleStack<>();
    private int counterIn;
    private int counterOut;

    public T poll() {
        if (counterOut == 0) {
            while (counterIn > 0) {
                output.push(input.pop());
                counterIn--;
                counterOut++;
            }
        }
        if (--counterOut < 0) {
            throw new NoSuchElementException("Queue is empty");
        }
        return output.pop();
    }

    public void push(T value) {
        input.push(value);
        counterIn++;
    }
}