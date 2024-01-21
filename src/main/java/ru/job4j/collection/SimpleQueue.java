package ru.job4j.collection;

import java.util.NoSuchElementException;

public class SimpleQueue<T> {
    private final SimpleStack<T> input = new SimpleStack<>();
    private final SimpleStack<T> output = new SimpleStack<>();
    private int inputCount;
    private int outputCount;

    public T poll() {
        if (inputCount == 0 && outputCount == 0) {
            throw new NoSuchElementException("Queue is empty");
        }

        if (outputCount == 0) {
            while (inputCount > 0) {
                output.push(input.pop());
                inputCount--;
                outputCount++;
            }
        }
        outputCount--;

        return output.pop();
    }

    public void push(T value) {
        input.push(value);
        inputCount++;
    }
}