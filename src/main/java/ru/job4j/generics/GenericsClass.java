package ru.job4j.generics;

public class GenericsClass<K, V> {
    private K key;

    private V value;

    public GenericsClass(K key, V value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public String toString() {
        return "GenericsClass{"
                + "key=" + key
                + ", value=" + value
                + '}';
    }
}