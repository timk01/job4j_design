package ru.job4j.list;

import java.util.*;

public class SimpleArrayList<T> implements SimpleList<T> {

    private T[] container;
    private int size;
    private int modCount;

    public SimpleArrayList(int capacity) {
        container = (T[]) new Object[capacity];
    }

    @Override
    public void add(T value) {
        modCount++;
        increaseContainerSize();
        container[size] = value;
        size++;
    }

    private T[] increaseContainerSize() {
        if (container.length == 0) {
            container = Arrays.copyOf(container, 1);
        }
        if (container.length == size) {
            container = Arrays.copyOf(container, container.length * 2);
        }
        return container;
    }

    private void indexChecker(int index) {
        Objects.checkIndex(index, size);
    }

    @Override
    public T set(int index, T newValue) {
        indexChecker(index);
        T oldValue = container[index];
        container[index] = newValue;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        modCount++;
        indexChecker(index);
        T elem = container[index];
        System.arraycopy(
                container,
                index + 1,
                container,
                index,
                container.length - index - 1
        );
        container[container.length - 1] = null;
        size--;
        return elem;
    }

    @Override
    public T get(int index) {
        indexChecker(index);
        return container[index];
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private int cursor;
            private int expectedModCount = modCount;

            @Override
            public boolean hasNext() {
                checkModification();
                return cursor < size;
            }

            @Override
            public T next() {
                if (!hasNext()) {
                    throw new NoSuchElementException("no elements anymore");
                }
                return container[cursor++];
            }

            private void checkModification() {
                if (expectedModCount != modCount) {
                    throw new ConcurrentModificationException("can't change array during iteration");
                }
            }
        };
    }
}