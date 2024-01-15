package ru.job4j.iterator;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

public class NullableCyclicIterator<T> implements Iterator<T> {

    private List<T> data;
    private int index;

    public NullableCyclicIterator(List<T> data) {
        this.data = data;
    }

    @Override
    public boolean hasNext() {
        if (index == data.size()) {
            index = 0;
        }
        return !data.isEmpty() && index >= 0;
    }

    @Override
    public T next() {
        if (!hasNext()) {
            throw new NoSuchElementException("collection has no elements");
        }
        return data.get(index++);
    }
}