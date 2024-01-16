package ru.job4j.iterator;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

public class CyclicIterator<T> implements Iterator<T> {

    /** data allows null elements (they are pefrectly valid),
     * see whenListContainsNullElements in test class
     * @param <T>
     */

    private List<T> data;
    private int index;

    public CyclicIterator(List<T> data) {
        this.data = data;
    }

    /**
     * in fact, we return true only if array has any elements (and only false if it's empty)
     * @return true/false see above when and why
     */

    @Override
    public boolean hasNext() {
        if (index == data.size()) {
            index = 0;
        }
        return !data.isEmpty();
    }

    @Override
    public T next() {
        if (!hasNext()) {
            throw new NoSuchElementException("collection has no elements");
        }
        return data.get(index++);
    }
}