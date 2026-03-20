package ru.job4j.ood.lcp.postcondition;

import java.time.LocalDate;
import java.time.Month;

public class SecondRule {
    public static void main(String[] args) {

        WorkDays workDays = new WorkDays();
        workDays.add(LocalDate.of(2020, Month.DECEMBER, 1), 8);
        workDays.add(LocalDate.of(2020, Month.DECEMBER, 2), 6);
        workDays.add(LocalDate.of(2020, Month.DECEMBER, 3), 7);

        CountingRoom countingRoom = new ShopCountingRoom(3 * 8, 500);
        System.out.println(countingRoom.pay(workDays));
    }
}