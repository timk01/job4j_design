package ru.job4j.iterator;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

public class NotNullableCyclicIterator<T> implements Iterator<T> {

    private List<T> data;
    private int index;

    public NotNullableCyclicIterator(List<T> data) {
        this.data = data;
    }

    @Override
    public boolean hasNext() {
        if (index == data.size()) {
            index = 0;
        }
        return !data.isEmpty() && index >= 0 && data.get(index) != null;
    }

    @Override
    public T next() {
        if (!hasNext()) {
            throw new NoSuchElementException("collection is empty or has null elements");
        }
        return data.get(index++);
    }
}