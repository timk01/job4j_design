package ru.job4j.algo.sorting;

import java.util.Arrays;

/**
 * https://leetcode.com/problems/maximum-gap/description/
 * Нам необходимо найти самое большое количество пропущенных чисел между двумя другими числами,
 * которые идут последовательно.
 * <p>
 * Given an integer array nums, return the maximum difference between two successive elements in its sorted form.
 * If the array contains less than two elements, return 0.
 * <p>
 * Example 1:
 * <p>
 * Input: nums = [3,6,9,1]
 * Output: 3
 * Explanation: The sorted form of the array is [1,3,6,9], either (3,6) or (6,9) has the maximum difference 3.
 * <p>
 * Example 2:
 * <p>
 * Input: nums = [10]
 * Output: 0
 * Explanation: The array contains less than 2 elements, therefore return 0.
 * <p>
 * пример 3. [3, 6, 10, 1 ---> 4
 */

public class MaximumGap {

    /**
     * решение в лоб за NLogN
     * <p>
     * 1. сортируем (
     * 2. последовательно находим разницу между ними (2 числа), выводим макс или 0 если не нашли
     * (т.е. nums[i] - nums[i - 1] - разница, потом вычисляешь максимум из числа до и разницей: max(int a, int b)
     *
     * @param nums
     * @return
     */

    public int maximumGapBruteForce(int[] nums) {
        Arrays.sort(nums);
        int maxGap = 0;
        for (int i = 1; i < nums.length; i++) {
            maxGap = Math.max(maxGap, nums[i] - nums[i - 1]);
        }
        return maxGap;
    }

    /**
     * о да. идея. засунуть все в корзины и считать за линейку.
     * чем-то похоже на... хешмапу и их рвномерного распредления,
     * потому и скорость становится О(1) + скорость от цикла О(эн) вот и все
     * <p>
     * 4 шага.
     * 1. н-найти мин и макс текущего массива и посчитать разницу между ними
     * 2. используя разницу и длину оригинального массива, посчитать размер и колиество бакетов
     * 3. пройти по оригинальному массиву, посмотреть на какой бакет "падает" наше число и положить в бакет его
     * (помним то в бакете могут быть еще числа)
     * 4. пройтись по готовым бакетам и посмотреть разницу между минимальный элементом текущего бкета и
     * максимльным элементом предыдущего (логика близка к оригинальной)
     * ((шаг 4 - расписан ниже))
     * <p>
     * 1 - понятно. или стримами, или просто циклом
     * 2 - мысля. равномерное распрделение. bucketSize - заависит от сайза маассива, а  bucketQuantity - от сайза
     * 3. самое сложное нйти индекс. bucketIndex = (num - min.getAsInt()) / bucketSize;
     * на самом деле не сложно - у нс есть минимум и есть число + мы знаем что каждый бакет будет содержать
     * ренджи с мин. и до макс... мы просто тупо считаем разницу между аболютным минимумом и текущим числом
     * и говорим "этот бакет будет бакетом по этому индексу"
     * <p>
     * ну а потом ндао просто обновить (если используется впервые) мин, макс и рыбильник
     * а в общем слчучаае - посчитать локально в массиве макс/мин
     * т.е. к концу цикла мы получим заполненные бакеты с: мин, макс, заполнен ли он...
     * <p>
     * на 4. мы их перебирем (см. ниже)
     *
     * @param nums
     * @return
     */

    public int maximumGapCuteOne(int[] nums) {
        if (nums == null || nums.length < 2) {
            return 0;
        }

        int max = Arrays.stream(nums).max().orElse(0);
        int min = Arrays.stream(nums).min().orElse(0);

        int difference = max - min;
        if (difference == 0) {
            return 0;
        }

        int bucketSize = (int) Math.ceil((double) difference / (nums.length - 1));

        int bucketQuantity = difference / bucketSize + 1;

        Bucket[] buckets = new Bucket[bucketQuantity];
        for (int i = 0; i < bucketQuantity; i++) {
            buckets[i] = new Bucket();
        }

        int bucketIndex;
        int currentMin = 0;
        int currentMax = 0;
        for (int num : nums) {
            bucketIndex = (num - min) / bucketSize;
            Bucket currBucket = buckets[bucketIndex];

            if (!currBucket.isBucketUsed) {
                currBucket.min = num;
                currBucket.max = num;
                currBucket.isBucketUsed = true;
            } else {
                currBucket.min = Math.min(num, currBucket.min);
                currBucket.max = Math.max(num, currBucket.max);
            }

        }

        /**
         * хрень, максимально похожая на два поинтра. к этому моменту у тебя есть заполненные корзины
         * что нужно сделать концептуально: сравнить минимум текущей корзины и максимум предыдущей,
         * если пустая - скипать целиком
         *
         * соответственно есть: максимум - то число, которое от нас требуют в задаче
         * и - внимание prevOfMaxNotUsedBucket - ровно то, о чм он говорит:
         * предыдущий максимальный элемент не пустой корзины
         *
         * в начале если ноа не пуста - тупо заполняем его
         * (помним, что любая корзина - скип)
         * следущая не пуста корзина уже будет работать: берем у нее минимум, находим разницу с максимумом предыдущей.
         * считаем финальный максимум.
         * вышли - обновляем prevOfMaxNotUsedBucket (потому что у текущий не пустой корзины всегда есть максимум)
         */

        int maximum = 0;
        Integer prevOfMaxNotUsedBucket = null;
        for (Bucket bucket : buckets) {
            if (!bucket.isBucketUsed) {
                continue;
            }
            if (prevOfMaxNotUsedBucket != null) {
                int gap = bucket.min - prevOfMaxNotUsedBucket;
                maximum = Math.max(maximum, gap);
            }
            prevOfMaxNotUsedBucket = bucket.max;
        }

        return maximum;
    }

    static class Bucket {
        private int min;
        private int max;
        private boolean isBucketUsed;

        public Bucket() {
        }
    }

    public static void main(String[] args) {
        MaximumGap maximumGap = new MaximumGap();
        System.out.println(maximumGap.maximumGapBruteForce(new int[]{3, 6, 10, 1}));
    }
}
