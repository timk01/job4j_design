package ru.job4j.iterator;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Здесь подготовительный, одномерный итератор. Внимание - НЕ коллекция. Задача - тупо по нему пройтись...
 *
 * @param <T>
 */

public class FlatMap2<T> implements Iterator<T> {
    private final Iterator<T> data;
    private Iterator<T> cursor = Collections.emptyIterator();

    public FlatMap2(Iterator<T> data) {
        this.data = data;
    }

    @Override
    public boolean hasNext() {
        boolean result = false;
        while (data.hasNext()) {
            result = true;
            break;
        }
        return result;
    }

    @Override
    public T next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        return data.next();
    }

    public static void main(String[] args) {
        Iterator<Integer> data =
                List.of(1, 2, 3).iterator();
        FlatMap2 flat = new FlatMap2(data);
        flat.next();
        flat.next();
        flat.next();
        if (flat.hasNext()) {
            flat.next();
        }
    }
}