package ru.job4j.algo.sorting;

import java.util.Arrays;

/**
 * концептуально:
 * int[] lesserElements = new int[sequence.length - 1];
 * int[] biggerElements = new int[sequence.length - 1];
 * <p>
 * for (int i = 1; i < sequence.length; i++) {
 * if (sequence[i] <= pivot) {
 * lesserElements[i - 1] = sequence[i];
 * } else {
 * biggerElements[i - 1] = sequence[i];
 * }
 * }
 * System.out.println("lesserElements " + Arrays.toString(lesserElements));
 * System.out.println("biggerElements " + Arrays.toString(biggerElements));
 * <p>
 * т.е. собрать массив из кусков массива, в конце добавить 1 кусок мньших + пивот + 2 кусок больших
 * - не такая плохая идея. хотя, она требует доп. завтрт памяти, это да
 * <p>
 * merge sort: разделил → отсортировал части → слил;
 * quick sort: pivot → partition → рекурсия слева и справа.
 */
public class Quick {
    public static void quickSort(int[] sequence) {
        quickSort(sequence, 0, sequence.length - 1);
    }

    /**
     * пока start >= end - стандартное условие, когда надо перестать двигать
     * во-первых мы сразу находим элемент партиции и пляшем от него
     * - а чтобы его вернуть - надо предварительно подвигать breakPartition, т.е. элементы внутри всего массива
     * потом этот массив будет урезаться в куски-разделители:
     * breakPartition возвращает индеекс - разделитель, от него и будут рекурсивно уже двигаться остальные quickSort
     * <p>
     * нам буквально надо сделть ХХХХPYYYY - где Х - элементы меньше, Р - рзделитель, У - элементы больше
     * <p>
     * как оно работает ?
     * [7, 2, 6, 4, 3, 1, 8, 9]
     * взяли 7 как опорник, нужно добиться [1, 2, 6, 4, 3, 7, 8, 9] в конце -- см breakPartition
     * <p>
     * отдали h = 5; - разбили на 2 куска. - семерка уже на своем месте (и никуда не денется)
     * [1, 2, 6, 4, 3] [8, 9]
     * работаем с [1, 2, 6, 4, 3] - вызвали для него разбиение... вышло [1] [2, 6, 4, 3] ---> [2] [6, 4, 3]
     * 1 - встает сразу, 2 - тоже, 6 - уходит в конец, из 4-3 выходит 3-4
     *
     * В quick sort нет отдельного этапа “собрали куски обратно”.
     *
     * @param sequence
     * @param start
     * @param end
     */

    private static void quickSort(int[] sequence, int start, int end) {
        if (start >= end) {
            return;
        }
        int h = breakPartition(sequence, start, end);
        quickSort(sequence, start, h - 1);
        quickSort(sequence, h + 1, end);
    }

    /**
     * ВАЖНО: суть:: прямо два указателя. один с начала ползет, второй с конца. сраниваем с поинтером каждый и меняем индексы
     * (левый указатель ищет первый элемент слева, который больше pivot,
     * правый указатель ищет первый элемент справа, который меньше или равен pivot)
     * именно НЕПОДХОДЯЩИЕ элементы - если по индексам все ок
     * (а это возможно только когда левый левее правого) - свапаем
     * происходит все безобразие (условие выхода из цикла) пока левый индекс <= правого - т.е. когда дошел до него
     * в конце нам надо досвапать пойнтер (который свдинулся) с первым элементом
     *
     * @param sequence
     * @param start
     * @param end
     * @return
     */

    private static int breakPartition(int[] sequence, int start, int end) {
        int pivot = sequence[start];
        int leftIndex = start + 1;
        int rightIndex = end;

        while (leftIndex <= rightIndex) {

            /**
             * сортируемый участок СУЖАЕТСЯ, потому не до всего массива, а до границ УЧАСТКА
             * при этом логика обратна = мы будем сдвигать индекс пока условие равно и искать таким базом НАИБОЛЬШИЙ эл.
             * (ищем не минимальный, а максимальный!)
             */
            while (leftIndex <= end && sequence[leftIndex] <= pivot) {
                leftIndex++;
            }

            /**
             * start + 1 == старт уже занят пивотом (ищем индекс минимального элемента по-сути)
             */

            while (rightIndex >= start + 1 && sequence[rightIndex] > pivot) {
                rightIndex--;
            }

            /**
             * а когда нашли оба (при этом левый максимальный меньше правого минимального) - свпаем
             * со сдвигом индексов, т.к. уже не имеет смысла стартовать от них
             */
            if (leftIndex < rightIndex) {
                swap(sequence, leftIndex, rightIndex);
                leftIndex++;
                rightIndex--;
            }
        }

        /**
         * как раз финльный момент, когда после сортировки ВСЕГО участка, нам надо поставить указатель ровно
         * в то место, где сейчас стоит ПРАВЫЙ укаатель (возвращать-то будем Н, а это начало участка - 1 элемент)
         */
        swap(sequence, start, rightIndex);

        return rightIndex;
    }

    private static void swap(int[] array, int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    public static void main(String[] args) {
        int[] array = {7, 2, 9, 4, 3, 8, 1, 6};
        quickSort(array);
        System.out.println(Arrays.toString(array));
    }
}