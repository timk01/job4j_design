package ru.job4j.iterator;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * В классе нужно реализовать два метода: next и hasNext.
 * <p>
 * Метод next должен последовательно вернуть числа из вложенных итераторов.
 * <p>
 * В этом задании нельзя копировать элементы во временный список.
 * <p>
 * Ниже приведен код, как делать нельзя.
 * <p>
 * java
 * private List<Integer> temp = new ArrayList<>();
 * <p>
 * public FlatMap(Iterator<Iterator<Integer>> data) {
 * this.data = data;
 * while (data.hasNext()) {
 * Iterator<Integer> inner = data.next();
 * while (inner.hasNext()) {
 * temp.add(inner.next());
 * }
 * }
 * }
 *
 * @param <T>
 */

public class FlatMap<T> implements Iterator<T> {
    private final Iterator<Iterator<T>> data;
    private Iterator<T> cursor = Collections.emptyIterator();

    public FlatMap(Iterator<Iterator<T>> data) {
        this.data = data;
    }

    @Override
    public boolean hasNext() {
        while (data.hasNext() && !cursor.hasNext()) {
            cursor = data.next();
        }
        return cursor.hasNext();
    }

    @Override
    public T next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        return cursor.next();
    }

    public static void main(String[] args) {
        Iterator<Iterator<Integer>> data = List.of(
                List.of(1, 2, 3).iterator(),
                List.of(4, 5, 6).iterator(),
                List.of(7, 8, 9).iterator()
        ).iterator();
        FlatMap flat = new FlatMap(data);
        while (flat.hasNext()) {
            flat.next();
        }
    }
}