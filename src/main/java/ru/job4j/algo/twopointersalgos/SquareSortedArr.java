package ru.job4j.algo.twopointersalgos;

import java.util.Arrays;

/**
 * https://leetcode.com/problems/squares-of-a-sorted-array/description/
 * (хотя кмк перегнал массив - возвел все в квадрат, положил поэлементно в темр, отсортировал темп - проще)
 * Решение этой задачи с использованием алгоритма двух указателей довольно простое.
 * Основная идея заключается в том, что мы будем двигаться по массиву с двух сторон: с начала и с конца,
 * возводя элементы в квадрат и помещая их в результирующий массив.
 * даже и без Arrays.sort(copy) - потому что O(n log(n))
 * и да. тут ВАЖНО идти с конца (или начала), потому что массив уже отсортирован (т.е. доп сортировка не нужна!)
 */

public class SquareSortedArr {
    public static int[] squareSortedArray(int[] arr) {
        int arrLength = arr.length;
        int[] copy = new int[arrLength];
        int left = 0;
        int right = arrLength - 1;
        int index = arrLength - 1;

        while (left <= right) {
            if (Math.abs(arr[left]) > Math.abs(arr[right])) {
                copy[index] = arr[left] * arr[left];
                left++;
            } else {
                copy[index] = arr[right] * arr[right];
                right--;
            }
            index--;
        }
        return copy;
    }

    public static int[] squareSortedArraySimpleLogN(int[] arr) {
        int[] result = new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            result[i] = arr[i] * arr[i];
        }
        Arrays.sort(result);
        return result;
    }

    public static void main(String[] args) {
        int[] nums = {-8, -4, -2, 0, 2, 3, 5};
        System.out.println(Arrays.toString(squareSortedArray(nums)));
    }
}
