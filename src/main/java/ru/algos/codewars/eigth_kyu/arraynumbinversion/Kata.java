package ru.algos.codewars.eigth_kyu.arraynumbinversion;

import java.util.Arrays;
import java.util.stream.Stream;

/**
 * Не нужны проверки типа
 * if (array.length == 0) {
 *       return new int[]{};
 * }
 * + также првоерка на нулль элемент
 */
public class Kata {
  public static int[] invert(int[] array) {

/*    return Arrays.stream(array)
            .map(i -> -i)
            .toArray();
    */

    for (int i = 0; i < array.length; i++) {
      array[i] = -array[i];
    }
    return array;
  }
}