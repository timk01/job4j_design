package ru.job4j.generics.shildbook.restriction;

class Gen<T extends Number> {

    T ob;

    T[] vals;

    Gen(T o, T[] nums) {

        ob = o;
        vals = nums;
    }
}

class GenArrays {

    public static void main(String[] args) {

        Integer[] n = {1, 2, 3, 4, 5};

        Gen<Integer> iOb = new Gen<Integer>(50, n);

        Gen<?>[] gens = new Gen<?>[10];
    }
}