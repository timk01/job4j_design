package ru.job4j.algo.homework;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static ru.job4j.algo.homework.BankMaxLoadTime.EventType.ARRIVAL;
import static ru.job4j.algo.homework.BankMaxLoadTime.EventType.DEPARTURE;

public class BankMaxLoadTime {

    /**
     * для чего и что нужно. первое: не сказано, что отрезки могут быть в порядке возрастания
     * это в целом предполагается, но не обязательно.
     * чтобы исключить веероятность такого случая (а мы пойдем слева-направо), мы их пихаем как ивенты в лист
     * и сортируем (ивенты под капотом сортируются как по времени прибытия, а если оно равно - эррайвл в приоритете)
     * <p>
     * ВАЖНО: в целом возможно состояние когда 1 человек уже пришел, а 2 еще не вышел. в одно и то же время.
     * тогда на временной кривой прибытие будет раньше чем отбытие.
     * <p>
     * далее. у нас есть лист ивентов. в целом нас забудет только то, что если к нм пришли (сравнение по типу)
     * это -1, если ушли, -2. то есть currentClientsQuantity - меняется. а нам нужен максимум.
     * когда его ловить ? только тогда, когда текущее колиество больше максимума
     * (например, на каком-то участке (б этом позже)) мы выявили что текущее клиентов 4 - а был макс 3
     * (причем не обязательно строго до этого) - тогда нам стандартно надо обновить максимум:
     * maxClientsQuantity = Math.max(currentClientsQuantity, maxClientsQuantity)
     * <p>
     * а теперь ВАЖНО: нам же ндо вернуть отрезки когда и кто там был,
     * при этом возможны варианты что человек в 3 часа пришел, а второй - в это же время выходит.
     * у нас по условию за входом - приоритет.
     * а в кривой времени мы берем первую цифру и вторую (именно время, тк-то двигаемс по индексам)
     * maxLoadStartTime = events.get(i).time; - первая
     * maxLoadStartTime = events.get(i + 1).time; - вторая.
     * т.е. если человек вошел и вышел в разное время (и мы при этом обновили максимум), будет нпример 1, (1, 2)
     * а вот если количество тел обновилось, но при этом время прихода и ухода совпадают,
     * может и быть совпадение времени: нпример 2, (3, 3)
     * <p>
     * для перекрытия времени - см. тесткейс 2, для одновременного прихода/ухода- тест 3
     *
     * @param visitTimes
     * @return
     */

    public static int[] findMaxLoadTime(List<int[]> visitTimes) {
        List<Event> events = new ArrayList<>();
        for (int[] visitTime : visitTimes) {
            events.add(new Event(visitTime[0], ARRIVAL));
            events.add(new Event(visitTime[1], DEPARTURE));
        }

        Collections.sort(events);

        int currentClientsQuantity = 0;
        int maxClientsQuantity = 0;
        int maxLoadStartTime = 0;
        int maxLoadEndTime = 0;
        for (int i = 0; i < events.size() - 1; i++) {
            if (events.get(i).type == ARRIVAL) {
                currentClientsQuantity++;
            } else {
                currentClientsQuantity--;
            }

            if (currentClientsQuantity > maxClientsQuantity) {
                maxClientsQuantity = Math.max(currentClientsQuantity, maxClientsQuantity);
                maxLoadStartTime = events.get(i).time;
                maxLoadEndTime = events.get(i + 1).time;
            }
        }

        return new int[]{maxLoadStartTime, maxLoadEndTime};
    }

    static class Event implements Comparable<Event> {
        int time;
        EventType type;

        Event(int time, EventType type) {
            this.time = time;
            this.type = type;
        }

        @Override
        public int compareTo(Event other) {
            if (this.time == other.time) {
                return this.type == ARRIVAL ? -1 : 1;
            }
            return Integer.compare(this.time, other.time);
        }

    }

    enum EventType {
        ARRIVAL, DEPARTURE
    }

    public static void main(String[] args) {
        List<int[]> visitTimes = Arrays.asList(
                new int[]{1, 5},
                new int[]{2, 6},
                new int[]{3, 8},
                new int[]{4, 7}
        );
        BankMaxLoadTime.findMaxLoadTime(visitTimes);
    }
}