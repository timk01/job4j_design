package ru.job4j.generics.shildbook;

public class StatsWithWildCard<T extends Number> {
    T[] nums;

    StatsWithWildCard(T[] o) {
        nums = o;
    }

    double average() {
        double sum = 0.0;
        for (int i = 0; i < nums.length; i++) {
            sum += nums[i].doubleValue();
        }
        return sum / nums.length;
    }

    boolean sameAvg(Stats<?> ob) {
        if (average() == ob.average()) {
            return true;
        }
        return false;
    }
}

class WildCardDemo {

    public static void main(String[] args) {

        Integer[] iNums = {1, 2, 3, 4, 5};
        StatsWithWildCard<Integer> iOb = new StatsWithWildCard<Integer>(iNums);
        double v = iOb.average();
        System.out.println("Среднее знеачение iOb равно " + v);

        Double[] dNums = {1.1, 2.2, 3.3, 4.4, 5.5};
        Stats<Double> dOb = new Stats<Double>(dNums);
        double w = dOb.average();
        System.out.println("Среднее знеачение dOb равно " + w);

        Float[] fNums = {1.1F, 2.2F, 3.3F, 4.4F, 5.5F};
        Stats<Double> fOb = new Stats<Double>(dNums);
        double x = fOb.average();
        System.out.println("Среднее знеачение fOb равно " + x);

        System.out.print("Средние значения iOb и dOb ");
        if (iOb.sameAvg(dOb)) {
            System.out.println("равны.");
        } else {
            System.out.println("не равны.");
        }

        System.out.print("Средние значения iOb и fOb ");
        if (iOb.sameAvg(fOb)) {
            System.out.println("равны.");
        } else {
            System.out.println("не равны.");
        }
    }
}
