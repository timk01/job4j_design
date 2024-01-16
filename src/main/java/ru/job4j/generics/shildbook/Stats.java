package ru.job4j.generics.shildbook;

public class Stats<T extends Number> {
    T[] nums;

    Stats(T[] o) {
        nums = o;
    }

    double average() {
        double sum = 0.0;
        for (int i = 0; i < nums.length; i++) {
            sum += nums[i].doubleValue();
        }
        return sum / nums.length;
    }
}

class BoundsDemo {

    public static void main(String[] args) {

        Integer[] iNums = {1, 2, 3, 4, 5};
        Stats<Integer> iOb = new Stats<Integer>(iNums);
        double v = iOb.average();
        System.out.println("Среднее значение iOb равно " + v);

        Double[] dNums = {1.1, 2.2, 3.3, 4.4, 5.5};
        Stats<Double> dOb = new Stats<Double>(dNums);
        double w = dOb.average();
        System.out.println("Среднее значение dOb равно " + w);
    }
}