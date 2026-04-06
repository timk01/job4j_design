package ru.job4j.algo.sliding.window;

import java.util.*;

public class Main {

    /**
     * 1. подготовка
     * а) отсортировали по возрастанию знаачений: добавляем старт и енд в трисет,
     * пихаем сет в лист ибо задолбаемся без индексов (это лист ranges)
     * б) запоминаем начало и конец будущего интервала (старт, енд)
     * + создаем Map<Interval, Integer> intervalIntegerMap = new LinkedHashMap<>(); для хранения интервал + его частота
     * <p>
     * 2. основное.
     * а) БАЗА. нам надо пройтись по всем интервалам (то пришли с наружи)
     * + по всему листу что мы создали с отсортированными значениями
     * и посмотреть каждый раз: есть ли вхождение в интервал нашего range.
     * если есть и было - крутим счетчик, если нет - ставим 1 (классический merge на каунтеры)
     * в результате будет хемапа из результатов + сколько их раз.
     * б) по условию задачи нам надо вернуть первый максимально встречающийся по количеству раз
     * минимальный отрезок - второе нам гарантирует разбиение на соседние куски с верным рзбиением границ
     * (это уже а) из подготтовки)
     * (т.е. если 1-2 и 2-3 встретились оба по 3 раза, и выше их нет - т.е. в трех отрезках, вернуть 1-2)
     * <p>
     * а вот по максимуму: ищем максимум по вельюс через стримы, преобразовывая в инт.
     * или выкидывем -1 с ранним возвратом
     * <p>
     * как нашли максимум, идем уже по ентрисету, нам надо отфильтровать только те ентри (т.е. гет у ключа)
     * велью которых равны максимуму, из стрима ентриез преобразовать (мап) в ентри как объект и вернуть первый.
     * здесь сорт не нужен, т.к 1) создали трисет (лист н его основе), 2) перебираем ЛХМ
     * <p>
     * ВАЖНО: здесь в ЛХМ важен порядок добавления элементов в ОРИГИНАЛЬНЫЙ лист
     * (внутренний цикл: по ranges слева направо_
     * <p>
     * ну и возвращаем интервал, преобразуя его в массив interval с началом или концом или -1, -1
     *
     * @param intervals
     * @return
     */

    public static int[] findMaxOverlapIntervalBruteForce(List<Interval> intervals) {
        if (intervals.isEmpty()) {
            return new int[]{-1, -1};
        }

        Set<Integer> sortedPoints = new TreeSet<>();

        for (Interval interval : intervals) {
            sortedPoints.add(interval.start);
            sortedPoints.add(interval.end);
        }

        List<Integer> ranges = new ArrayList<>(sortedPoints);

        int start;
        int end;
        Map<Interval, Integer> intervalIntegerMap = new LinkedHashMap<>();
        Interval curr;
        for (Interval interval : intervals) {
            for (int i = 1; i < ranges.size(); i++) {
                start = ranges.get(i - 1);
                end = ranges.get(i);
                curr = new Interval(start, end);
                if (start >= interval.start && end <= interval.end) {
                    intervalIntegerMap.merge(
                            curr,
                            1,
                            (oldValue, newValue) -> oldValue + 1);
                }
            }
        }

        int maximum = intervalIntegerMap.values()
                .stream().mapToInt(i -> i)
                .max().orElse(-1);

        if (maximum == -1) {
            return new int[]{-1, -1};
        }

        Optional<Interval> first = intervalIntegerMap.entrySet()
                .stream()
                .filter(entry -> (entry.getValue() == maximum))
                .map(Map.Entry::getKey)
                .findFirst();

        return first.map(interval -> new int[]{interval.start, interval.end})
                .orElseGet(() -> new int[]{-1, -1});
    }

    /**
     * к моменту когда провалились в цикл мы:
     * 1) отсортировали по старту, если еще нужно (обычно - да)
     * 3) проинициализаровали: активные интервалы (которые внутри сортируются по концу), максимум переесечений (будет
     * нужно для подсчета), максимальное начало и минимальный конец - потому что по ним сравнивать и будем
     * 2) добавили в активные интервлы самый пеервый элемент
     * <p>
     * while (!activeIntervals.isEmpty() && activeIntervals.peek().end <= intervals.get(i).start) {
     * activeIntervals.poll();
     * }
     * здесь мы проверяем что в интервалах кто-то есть И сравниваем с текущим началом все остальные концы.
     * или, мы можем выкинуть все интервалы если они не пересекаются с текущим
     * [1,4] [2,6] [3,5] [7,8]
     * к моменту когда добрались до начала 7, мы начинаем удалять с верха всех мртвых (потенциально, вообще всех)
     * <p>
     * A) добавляем в очередь очередной интервал activeIntervals.add(intervals.get(i));
     * Б) говорим, что текущий = старту очередного и концу лучшего
     * current = new Interval(
     * intervals.get(i).start,
     * activeIntervals.peek().end
     * );
     * <p>
     * ЕСЛИ ЖЕ: сайз массива (например 3) больше оверлапа (оверлап = пересечения)
     * тогда максимум пересечений = сайзу и меняем счетчики: обновляем maxStart и minEnd на текущие
     * if (activeIntervals.size() > maxOverlap) {
     * maxOverlap = activeIntervals.size();
     * maxStart = current.start;
     * minEnd = current.end;
     * }
     * <p>
     * полный пример:
     * 0) [1,4], [2,6], [3,5], [7,8]
     * живые: [1,4]
     * максимум перекрытий пока = 1 (дефолт при добавлении 1 жлемнта)
     * лучший учсток пока = [1,4]
     * 1) у верхнего в очереди конец 4
     * 4 <= 2? ((ИЛИ: в очереди - ужее осортирвоанной по приоритету ендов- с конца барется МИНИМАЛЬНЫЙ)
     * нет. значит никого удалять.
     * добавляем в интервалы (живых = 2)
     * говорим что новыей каррент = новое начало, старый конец (2, 4)
     * сравнивем с максимумом пересечений. было 1 - стало 2. обновляем все поинты: пересечений 2, начало 2, конец 4
     * 2)[3, 5]
     * 3 <= 4 ? нет, не умер. никто.
     * добавляем в интервалы (живых = 3)
     * говорим что новый каррент = 3, 4
     * т.к. персечений возросло = 3, обновляем - пересечений 3, начало = 3, конец = 4
     * 3) [7,8]
     * 4 <= 7 → [1,4] умер, удаляем
     * 5 <= 7 → [3,5] умер, удаляем
     * 6 <= 7 → [2,6] умер, удаляем
     * в очереди - никого
     * activeIntervals.add(intervals.get(i)); - добавляем элемент (в очерееди 1 элемент тееперь, вызывать пик не опасно)
     * смотрим. оверлап есть ? (к этому моменту берется дефолтынй оверлап = 1. логика добавили (под капотом) = 1)
     * 1 > 3 ? ничего не крутим (Т.Е. maxStart, minEnd НЕ изменяются)
     *
     * @param intervals
     * @return
     */

    public static int[] findMaxOverlapInterval(List<Interval> intervals) {
        if (intervals.isEmpty()) {
            return new int[]{-1, -1};
        }

        var activeIntervals = new PriorityQueue<Interval>(Comparator.comparingInt(i -> i.end));

        intervals.sort(Comparator.comparingInt(i -> i.start));

        Interval current = intervals.get(0);
        int maxOverlap = 1;
        int maxStart = current.start;
        int minEnd = current.end;

        activeIntervals.add(intervals.get(0));

        for (int i = 1; i < intervals.size(); i++) {
            while (!activeIntervals.isEmpty() && activeIntervals.peek().end <= intervals.get(i).start) {
                activeIntervals.poll();
            }

            activeIntervals.add(intervals.get(i));

            current = new Interval(
                    intervals.get(i).start,
                    activeIntervals.peek().end
            );

            if (activeIntervals.size() > maxOverlap) {
                maxOverlap = activeIntervals.size();
                maxStart = current.start;
                minEnd = current.end;
            }
        }

        return new int[]{
                maxStart, minEnd
        };
    }

    static class Interval {
        int start;
        int end;

        Interval(int start, int end) {
            this.start = start;
            this.end = end;
        }

        @Override
        public String toString() {
            return String.format("[%s, %s]", start, end);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Interval interval = (Interval) o;
            return start == interval.start && end == interval.end;
        }

        @Override
        public int hashCode() {
            return Objects.hash(start, end);
        }
    }

    public static void main(String[] args) {
        List<Interval> intervals = new ArrayList<>();
        intervals.add(new Interval(1, 4));
        intervals.add(new Interval(2, 6));
        intervals.add(new Interval(3, 5));
        intervals.add(new Interval(7, 8));

        int[] result = findMaxOverlapInterval(intervals);

        System.out.println("Interval that overlaps the maximum number of intervals: [" + result[0] + ", " + result[1] + "]");
    }
}