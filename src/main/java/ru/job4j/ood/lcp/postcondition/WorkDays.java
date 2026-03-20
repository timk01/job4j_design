package ru.job4j.ood.lcp.postcondition;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class WorkDays implements Iterable<Integer> {
    private Map<LocalDate, Integer> workDays = new HashMap<>();

    void add(LocalDate date, int hours) {
        workDays.put(date, hours);
    }
    @Override
    public Iterator<Integer> iterator() {
        return workDays.values().iterator();
    }
}
