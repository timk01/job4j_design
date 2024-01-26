package ru.job4j.map;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

public class NonCollisionMap<K, V> implements SimpleMap<K, V> {

    private static final float LOAD_FACTOR = 0.75f;

    private int capacity = 8;

    private int count = 0;

    private int modCount = 0;

    private MapEntry<K, V>[] table = new MapEntry[capacity];

    @Override
    public boolean put(K key, V value) {
        if (count >= capacity * LOAD_FACTOR) {
            expand();
        }
        int index = indexFor(hash(Objects.hashCode(key)));
        boolean res = table[index] == null;
        if (res) {
            table[index] = new MapEntry<>(key, value);
            modCount++;
            count++;
        }
        return res;
    }

    private int hash(int hashCode) {
        return hashCode ^ (hashCode >>> 16);
    }

    private int indexFor(int hash) {
        return (capacity - 1) & hash;
    }

    private void expand() {
        capacity = capacity * 2;
        MapEntry<K, V>[] newTable = new MapEntry[capacity];
        for (MapEntry<K, V> entry : table) {
            if (entry != null) {
                newTable[indexFor(hash(Objects.hashCode(entry.key)))] =
                        new MapEntry<>(entry.key, entry.value);
            }
        }
        table = newTable;
    }

    private boolean compareTwoHashes(K key1, K key2) {
        int hash1 = key1 == null ? 0 : hash(key1.hashCode());
        int hash2 = key2 == null ? 0 : hash(key2.hashCode());
        return hash1 == hash2;
    }

    @Override
    public V get(K key) {
        MapEntry<K, V> currentEntry = table[indexFor(hash(Objects.hashCode(key)))];
        boolean res = currentEntry != null;
        if (res) {
            if (compareTwoHashes(key, currentEntry.key)
                    && Objects.equals(currentEntry.key, key)) {
                return currentEntry.value;
            }
        }
        return null;
    }

    @Override
    public boolean remove(K key) {
        int i = indexFor(hash(Objects.hashCode(key)));
        MapEntry<K, V> currentEntry = table[i];
        boolean res = currentEntry != null;
        boolean isDeleted = false;
        if (res) {
            if (compareTwoHashes(key, currentEntry.key)
                    && Objects.equals(currentEntry.key, key)) {
                table[i] = null;
                modCount++;
                count--;
                isDeleted = true;
            }
        }
        return isDeleted;
    }

    @Override
    public Iterator<K> iterator() {
        return new Iterator<K>() {
            private int index;
            private final int expectedModCount = modCount;

            @Override
            public boolean hasNext() {
                if (expectedModCount != modCount) {
                    throw new ConcurrentModificationException("can't change map during iteration");
                }
                while (index < table.length && table[index] == null) {
                    index++;
                }
                return index < table.length;
            }

            @Override
            public K next() {
                if (!hasNext()) {
                    throw new NoSuchElementException("has no not null elements");
                }
                return table[index++].key;
            }
        };
    }

    private static class MapEntry<K, V> {
        K key;
        V value;

        public MapEntry(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }
}