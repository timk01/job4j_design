package ru.job4j.collection;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

public class SimpleLinkedList<E> implements SimpleLinked<E> {
    private int size;
    private int modCount;
    private Node<E> head;

    @Override
    public void add(E value) {
        Node<E> current = head;
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

    @Override
    public E get(int index) {
        Objects.checkIndex(index, size);
        Node<E> lastNode = head;
        for (int i = 0; i < index; i++) {
            lastNode = lastNode.next;
        }
        return lastNode.data;
    }

    private static class Node<E> {
        private final E data;
        private Node<E> next;

        Node(E element) {
            this.data = element;
            this.next = null;
        }
    }

    @Override
    public Iterator<E> iterator() {
        return new Itr();
    }

    private class Itr implements Iterator<E> {
        private final int expectedModCount = modCount;
        private SimpleLinkedList.Node<E> current = head;

        @Override
        public boolean hasNext() {
            if (expectedModCount != modCount) {
                throw new ConcurrentModificationException("can't change data structure during iteration");
            }
            return current != null;
        }

        @Override
        public E next() {
            if (!hasNext()) {
                throw new NoSuchElementException("no elements anymore");
            }
            E data = current.data;
            current = current.next;
            return data;
        }
    }

    public static void main(String[] args) {
         SimpleLinked<Integer> list = new SimpleLinkedList<>();
         list.add(1);
         list.add(1);
    }
}