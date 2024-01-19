package ru.job4j.collection;

import ru.job4j.generics.Node;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

public class ForwardLinked<T> implements Iterable<T> {
    private int size;
    private int modCount;
    private Node<T> head;

    public void add(T value) {
        Node<T> current = head;
        if (head == null) {
            head = new Node<>(value);
        } else {
            while (current.next != null) {
                current = current.next;
            }
            current.next = new Node<>(value);
        }
        size++;
        modCount++;
    }

    public T get(int index) {
        Objects.checkIndex(index, size);
        Node<T> lastNode = head;
        for (int i = 0; i < index; i++) {
            lastNode = lastNode.next;
        }
        return lastNode.data;
    }

    public T deleteFirst() {
        if (head == null) {
            throw new NoSuchElementException("list is empty");
        }
        T elem = head.data;
        head.data = null;
        if (head.next != null) {
            Node<T> temp = head.next;
            head.next = null;
            head = temp;
        }
        size--;
        modCount++;
        return elem;
    }

    private static class Node<T> {
        private T data;
        private Node<T> next;

        Node(T element) {
            this.data = element;
            this.next = null;
        }
    }

    @Override
    public Iterator<T> iterator() {
        return new Itr();
    }

    private class Itr implements Iterator<T> {
        private final int expectedModCount = modCount;
        private ForwardLinked.Node<T> current = head;

        @Override
        public boolean hasNext() {
            if (expectedModCount != modCount) {
                throw new ConcurrentModificationException("can't change data structure during iteration");
            }
            return current != null;
        }

        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException("no elements anymore");
            }
            T data = current.data;
            current = current.next;
            return data;
        }
    }
}