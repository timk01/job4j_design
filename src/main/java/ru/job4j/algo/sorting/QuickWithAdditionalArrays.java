package ru.job4j.algo.sorting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Главная идея: собрать кусок из кусков XX + P + YY и склеить обратно,
 * переопределив элементы в исходном массиве (его куске) или после или сразу.
 * <p>
 * По времени: (так же как и классик квиксорт)
 * <p>
 * один partition — O(n)
 * весь алгоритм: в среднем O(n log n), в худшем O(n^2)
 * <p>
 * По памяти:
 * <p>
 * требует дополнительную память O(n)
 * то есть по памяти хуже классического quicksort
 */

public class QuickWithAdditionalArrays {
    private int[] sequence;

    public QuickWithAdditionalArrays(int[] sequence) {
        this.sequence = sequence;
    }

    public void quickSort() {
        quickSort(0, sequence.length - 1);
    }

    private void quickSort(int start, int end) {
        if (start >= end) {
            return;
        }
        int h = breakPartition(start, end);
        quickSort(start, h - 1);
        quickSort(h + 1, end);
    }

    /**
     * какая здесь логика: взять левую часть, правую (пустые массивы). старт = начало, и пивотиднекс = нуль
     * бегаем по массиву с +1 позиции и проверяем, какой элемент относительно пивота: <= или >
     * заносим в левую и правую часть.
     * т.к. пивот переместится, запоминаем его индекс относительно смещения (его и будем возращать)
     * собираем весь кусок как XXPYY
     * этот кусок нужно обратно запихнуть в массив.
     * здесь хитрость: у нас сам входящий кусок будет сужаться! т.е. если вначале это был весь массив, после 1 разбиения
     * это 2 половинки, левая и правая. вот нам надо вернуть, например, правую (ОТ старта и до конца - куск)
     * НО - сунуть всю часть ндо на оригинальный массив!
     * <p>
     * пример. дан {7, 2, 9, 4, 3, 8, 1, 6};
     * после стал [1, 2, 6, 4, 3, 7, 8, 9] с пивотом = 5
     * надо это у нас на 1 этапе стал, заменили оригинальный массив
     * а потом пришел кусок 8, 9
     * передавать мы будем 2 индекса и после - заменять их же, т.е. старт будет = 6, енд = 7 (в оригинальном массиве)
     * когда мы соберем кусок, он будет например из 2 индесов,
     * т.е. мне в оригинальный массив надо будет вернуть этот кусок (а кусок размером в 2)
     * ИТОГО: мы двигаемся по куску с 0 и до его конца и кладем элементы в оригинальный массив, начиная от (старта + ай)
     * <p>
     * пока (ай = старт (0) И сайз < длины куска (2) -- растет в конце цикла ай)
     * sequence[6 + 0] = brokenSequencePart[0];
     *
     * @param start
     * @param end
     * @return
     */

    private int breakPartition(int start, int end) {
        int pivot = sequence[start];
        List<Integer> left = new ArrayList<>();
        List<Integer> right = new ArrayList<>();
        int pivotIndex = start;

        for (int i = start + 1; i <= end; i++) {
            if (sequence[i] <= pivot) {
                left.add(sequence[i]);
            } else if (sequence[i] > pivot) {
                right.add(sequence[i]);
            }
        }

        pivotIndex += left.size();

        left.add(pivot);
        left.addAll(right);

        int[] brokenSequencePart = left.stream()
                .mapToInt(Integer::intValue)
                .toArray();


        for (int i = 0; i < brokenSequencePart.length; i++) {
            sequence[start + i] = brokenSequencePart[i];
        }

        return pivotIndex;
    }

    public static void main(String[] args) {
        int[] array = {7, 2, 9, 4, 3, 8, 1, 6};
        QuickWithAdditionalArrays quickWithAdditionalArrays = new QuickWithAdditionalArrays(array);
        quickWithAdditionalArrays.quickSort();
        System.out.println(Arrays.toString(array));
    }
}
