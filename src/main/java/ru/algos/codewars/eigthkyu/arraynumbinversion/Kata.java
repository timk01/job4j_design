package ru.algos.codewars.eigthkyu.arraynumbinversion;

/**
 * Не нужны проверки типа
 * if (array.length == 0) {
 *       return new int[]{};
 * }
 * + также првоерка на нулль элемент
 */
public class Kata {
  public static int[] invert(int[] array) {
    for (int i = 0; i < array.length; i++) {
      array[i] = -array[i];
    }
    return array;
  }
}