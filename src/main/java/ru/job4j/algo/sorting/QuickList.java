package ru.job4j.algo.sorting;

import java.util.Comparator;
import java.util.List;

public class QuickList {

    public static <T> void quickSort(List<T> sequence, Comparator<T> comparator) {
        quickSort(sequence, 0, sequence.size() - 1, comparator);
    }

    private static <T> void quickSort(List<T> sequence, int start, int end, Comparator<T> comparator) {
        if (start >= end) {
            return;
        }
        int h = breakPartition(sequence, start, end, comparator);
        quickSort(sequence, start, h - 1, comparator);
        quickSort(sequence, h + 1, end, comparator);
    }

    /**
     * !!! главое что мы передаем компаратор для сравнения объектов (а для сравнения чисел - им итак всего хватает)
     * пример сравнения чисел: если первое меньше или равно второму, вернет меньше или равно.
     * <p>
     * внешний цикл отвечает за жизнь двухуказательной схемы целиком;
     * - это прям классическая (пока один не дойдет до второго, тут правда с перекрытием):
     * пока указатели еще не встретились и не пересеклись, процесс поиска и возможных перестановок продолжается.
     * <p>
     * внутренние циклы отвечают за поиск кандидатов;
     * (все то же самое как и в каноническом сорте: я ищу НЕПОДХОДЯЩИХ кандидатов, оставил только индексы)
     * <p>
     * if (leftIndex < rightIndex) отвечает за разрешение на обмен. буквльно. это две РАЗНЫЕ позиции для обмена
     * после внутренних сдвигов нашли ли мы еще две разные позиции,
     * которые стоят не на своих местах... МОЖНО ЛИ их обменять?
     * (обмен возможен только если они не в равных точках ((тогда это бесмысленно)) и пока левый левее правого)
     *
     * @param sequence
     * @param start
     * @param end
     * @param comparator
     * @param <T>
     * @return
     */

    private static <T> int breakPartition(List<T> sequence, int start, int end, Comparator<T> comparator) {
        T pivot = sequence.get(start);
        int leftIndex = start + 1;
        int rightIndex = end;

        while (leftIndex <= rightIndex) {
            while (leftIndex <= end && comparator.compare(sequence.get(leftIndex), pivot) <= 0) {
                leftIndex++;
            }

            while ((rightIndex >= start + 1) && comparator.compare(sequence.get(rightIndex), pivot) > 0) {
                rightIndex--;
            }

            if (leftIndex < rightIndex) {
                swap(sequence, leftIndex++, rightIndex--);
            }
        }
        swap(sequence, start, rightIndex);

        return rightIndex;
    }

    private static <T> void swap(List<T> array, int i, int j) {
        T temp = array.get(i);
        array.set(i, array.get(j));
        array.set(j, temp);
    }
}