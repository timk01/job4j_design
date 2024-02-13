package ru.job4j.io;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UsageLog4j {

    private static final Logger LOG = LoggerFactory.getLogger(UsageLog4j.class.getName());

    public static void main(String[] args) {
        String name = "Timur K.";
        int age = 38;
        char sex = 'M';
        boolean jobless = true;
        boolean single = true;
        float tasksAvg = 2.19F;
        byte daysStudy = (byte) 182;
        long tries = 100_500_000_000_000L;
        double successTaskRate = 0.356789;
        LOG.debug("User info "
                + "name: {}, age: {}, sex: {}, jobless: {}, single: {},"
                       + " tasksAvg: {}, daysStudy: {}, tries: {}, successTaskRate: {}",
                name,
                age,
                sex,
                jobless,
                single,
                tasksAvg,
                daysStudy,
                tries,
                successTaskRate);
    }
}