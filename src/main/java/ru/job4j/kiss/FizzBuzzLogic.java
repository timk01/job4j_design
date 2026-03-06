package ru.job4j.kiss;

public class FizzBuzzLogic {
    public static String convert(int startAt) {
        String result;
        if (startAt % 3 == 0 && startAt % 5 == 0) {
            result = "FizzBuzz";
        } else if (startAt % 3 == 0) {
            result = "Fizz";
        } else if (startAt % 5 == 0) {
            result = "Buzz";
        } else {
            result = String.valueOf(startAt);
        }
        return result;
    }
}
