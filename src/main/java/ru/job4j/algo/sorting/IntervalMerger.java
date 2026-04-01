package ru.job4j.algo.sorting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class IntervalMerger {

    /**
     * сли начало текущего интервала меньше или равно концу прошлого, то они пересекаются и их надо сливать
     * если начало текущего интервала больше конца прошлого, то пересечения нет
     * currentStart <= previousEnd
     * currentStart > previousEnd
     * <p>
     * или... задача напрямую зависит от сортировки, причем сортировки начал.
     * потом - можно уже объединять интервалы
     * <p>
     * 1. Comparator.comparingInt(el -> el[0]) - comparingInt всего лишь возвращаает компаратор (new Comparator<Integ>)
     * int[] - каждый элемнт массива, а сам массив = интервал {1, 3}
     * ИЛИ для сортировки берутся элементы типа... el = [1,3]
     * el[0] - первый элемент массива (каждого), т.е. первый первого массива, второго и пр.
     * потом и сортируем по ним.
     * <p>
     * теперь про мердж.
     * за текущий элемент (стартовый) - мы берем 1 с начала, а сам массив - со следующего (2) элемента
     * возможны 2 варианта:
     * он будет объединяться в другой подмассив или оставаться на месте
     * А) если будет объединяться, тогда mergedEl = new int[]{currentStart, Math.max(currentEnd, nextEnd)};
     * т.е. мы после того как убедились nextStart <= currentEnd
     * тупо создаем новый массив из {старт, лучший конец} -- конец нужен, т.к. не всегда
     * [1, 10] + [2, 6] дадут как конец (или - второй участок - не гарантия того, что он шире)
     * <p>
     * Б) если проверка nextStart <= currentEnd НЕ прошла, значит и слияния не было
     * добавляем в результируюущю текущий mergedEl
     * (т.е. на 1 круге здесь быть только вариант "не объединили, значит добавим в резалт")
     * говорим что mergedEl - следующий (т.е. 2) элемент
     * МЕРДЖЕД - тот, который сейчас сравнивааем
     * <p>
     * в случае А) мерджед буде обновлен на рзультат мерджа, в случае Б) сохранен и обновлен на последний резалт
     * <p>
     * [1][3] [7][8] -- проверка не работает, [1][3] - добавляется в резалт, [7][8] - становится текущим мерджем
     * {1, 3}, {2, 6}, {8, 10} - проверка работает, создается {1, 6}
     * {1, 6} {8, 10} уже на следующем круге - проверка не работет, {1, 6} заносится в резалт
     * и скипается. {8, 10} - становится текущий мерджем
     * <p>
     * args.add(mergedEl); - надо, потмоу что можем скипнуть последний "неправильный" участок, если фолс
     *
     * @param intervals
     * @return
     */
    public int[][] merge(int[][] intervals) {
        if (intervals.length == 0) {
            return new int[][]{};
        }

        Arrays.sort(intervals, Comparator.comparingInt(el -> el[0]));

        int currentStart;
        int currentEnd;
        int nextStart;
        int nextEnd;
        int[] mergedEl = intervals[0];
        List<int[]> args = new ArrayList<>();
        for (int i = 1; i < intervals.length; i++) {
            currentStart = mergedEl[0];
            nextEnd = intervals[i][1];

            currentEnd = mergedEl[1];
            nextStart = intervals[i][0];

            if (nextStart <= currentEnd) {
                mergedEl = new int[]{currentStart, Math.max(currentEnd, nextEnd)};
            } else {
                args.add(mergedEl);
                mergedEl = intervals[i];
            }

        }
        args.add(mergedEl);

        return args.toArray(new int[0][0]);
    }

    public static void main(String[] args) {
        IntervalMerger intervalMerger = new IntervalMerger();
        int[][] merge = intervalMerger.merge(new int[][]{{1, 3}, {2, 6}, {8, 10}, {15, 18}});
        System.out.println("merged " + Arrays.deepToString(merge));

    }
}


/**
 * int[][] intervals = new int[][] {{1, 3}, {2, 6}, {8, 10}, {15, 18}};
 * int[][] expected = new int[][] {{1, 6}, {8, 10}, {15, 18}};
 */