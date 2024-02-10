package ru.job4j.tasks;

public class Task54 {
    private static boolean even;
    private static int getNumber(int number) {
        if (even) {
        return number % 2 == 0 ? ++number : --number;
        } else {
            return number % 2 != 0 ? ++number : --number;
        }
    }

    private static String getX(int third, int second, int first) {
        return third == 0 ? (second + "" + first) : (third + "" + second + "" + first);
    }

    private static boolean isUnchangedNumber(int number) {
        return number == 0 || number == 9;
    }

    public static void transform(int number) {
        boolean sign = number < 0;
        number = Math.abs(number);
        int third = number / 100;
        int second = (number - third * 100) / 10;
        int first = number - third * 100 - second * 10;
        even = number % 2 == 0;
        if (even) {
            third = getNumber(third);
            second = getNumber(second);
            first = getNumber(first);
        } else {
            third = isUnchangedNumber(third) ? third : getNumber(third);
            second = isUnchangedNumber(second) ? second : getNumber(second);
            first = isUnchangedNumber(first) ? first : getNumber(first);
        }
        System.out.println(sign ? "-" + getX(third, second, first) : getX(third, second, first));
    }
}
