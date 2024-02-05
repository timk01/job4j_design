package ru.regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * ^(\+)?([ 0-9]){10,16}$
 *
 * +39 999 999 99599
 *
 * сначала + с экскейпом,
 * потом ? - означает 0 или 1 "попаданий" на (ПРОБЕЛ и цифры от 0 до 9) ((т.е. 2 символа макс))
 * т.е. варианты:
 * 0 символов, 1 символ или пробел например (или цифра), 2 символа (пробел и цифра)
 * {x,} - это х раз и более
 * {10,16} - касается предыдущей группы и повторов - их может быть от 10 до 16 штук
 */

public class SimplePhoneValidation {
    public static void main(String[] args) {
        Pattern pattern = Pattern.compile("^(\\+)?([ 0-9]){10,16}$");
        String text = "+39 999 999 99599";
        Matcher matcher = pattern.matcher(text);
        while (matcher.find()) {
            System.out.println("Найдено совпадение: " + matcher.group());
        }
    }

}
