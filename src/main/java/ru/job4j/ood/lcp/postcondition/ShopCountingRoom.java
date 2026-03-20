package ru.job4j.ood.lcp.postcondition;

public class ShopCountingRoom extends CountingRoom {
    public ShopCountingRoom(int normHours, int normPerHours) {
        super(normHours, normPerHours);
    }

    @Override
    public int pay(WorkDays workDays) {
        int factHours = 0;
        for (Integer hoursPerDay : workDays) {
            factHours += hoursPerDay;
        }
        return factHours * payPerHour;
    }
}
