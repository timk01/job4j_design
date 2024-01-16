package ru.job4j.generics.shildbook.boundedinterface;

interface MinMax<T extends Comparable<T>> {
    T min();

    T max();
}

class MinMaxImpl<T extends Comparable<T>> implements MinMax<T> {

    T[] vals;

    MinMaxImpl(T[] o) {
        vals = o;
    }

    public T min() {
        T v = vals[0];

        for (int i = 1; i < vals.length; i++) {
            if (vals[i].compareTo(v) < 0) {
                v = vals[i];
            }
        }
        return v;
    }

    public T max() {
        T v = vals[0];

        for (int i = 1; i < vals.length; i++) {
            if (vals[i].compareTo(v) > 0) {
                v = vals[i];
            }
        }
        return v;
    }
}

class GetIfDemo {

    public static void main(String[] args) {

        Integer[] iNums = {3, 6, 2, 8, 6};
        Character[] chs = {'b', 'r', 'p', 'w'};

        MinMaxImpl<Integer> iOb = new MinMaxImpl<Integer>(iNums);
        MinMaxImpl<Character> cOb = new MinMaxImpl<Character>(chs);

        System.out.println("Максимальное значение массива iNums: " + iOb.max());
        System.out.println("Минимальное значение массива iNums: " + iOb.min());

        System.out.println("Максимальное значение массива chs: " + cOb.max());
        System.out.println("Минимальное значение массива chs: " + cOb.min());
    }
}