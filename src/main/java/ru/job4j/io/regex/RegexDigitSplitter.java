package ru.job4j.io.regex;

import java.util.Arrays;

/**
 * anything BUT numbers - exclude (can repeat more than 1 time)
 */

public class RegexDigitSplitter {
    public static void main(String[] args) {
        String string = "123+=-456:/789";
        String[] result = string.split("\\D+");
        System.out.println(Arrays.toString(result));
    }
}
