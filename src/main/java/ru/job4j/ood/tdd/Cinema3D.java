package ru.job4j.ood.tdd;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.function.Predicate;

public class Cinema3D implements Cinema {

    private List<Session> sessions = new ArrayList<>();

    @Override
    public Ticket buy(Account account, int row, int column, Calendar date) {
        if (row <= 0 || column <= 0) {
            throw new IllegalArgumentException("rown or columnn cannot be less or equal zero");
        }
        if (date == null) {
            throw new IllegalArgumentException("date cannot be null");
        }
        Calendar dateNow = Calendar.getInstance();
        dateNow.add(Calendar.DAY_OF_MONTH, -1);
        if (dateNow.compareTo(date) > 0) {
            throw new IllegalArgumentException("date cannot be in past");
        }
        if (account == null) {
            throw new IllegalArgumentException("account cannot be null");
        }
        return new Ticket3D();
    }

    @Override
    public List<Session> find(Predicate<Session> filter) {
        if (filter == null) {
            throw new IllegalArgumentException("filter cannot be null");
        }
        List<Session> foundSessions = new ArrayList<>();
        for (Session session : sessions) {
            if (filter.test(session)) {
                foundSessions.add(session);
            }
        }
        return foundSessions;
    }

    @Override
    public void add(Session session) {
        if (session == null) {
            throw new IllegalArgumentException("session cannot be null");
        }
        sessions.add(session);
    }
}
