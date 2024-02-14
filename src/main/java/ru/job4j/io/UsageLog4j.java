package ru.job4j.io;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ALL < TRACE < DEBUG < INFO < WARN < ERROR
 * trick is, slf4j has no fatal
 *
 * here for example, root = trace (low)
 * debug.txt has = debug (kinda middle), wgile error has = error (top)
 *
 * so in result, in debug.txt we will have
 * debug, info, warn, error (trace is excluded since FileAppender Threshhold = debug)
 * while error will have only... error
 *
 * WHILE if you set Root = DEBUG (or all, whatever)
 * and set Debug threshold to... TRACE
 * Debug will have
 * debug, info, warn, error
 * since it's restricted by root now
 */

/**
 * Why Sl4j ?
 * to avoid situation when teams need to several -log-libraries
 * for example, team A uses LogBack, team B uses Log4j (or logger, whatever)
 * Without Sl4j, team A (and team B, in fact) needs to configure both LogBack and Log4j to use each other loggers
 * while Sl4j can provide unite interface to both -- kinda like facade pattern
 * (and we need only to add in to amven for downloading via LoggerFactory - see below)
 */

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
        short maxShortToRemember = Short.MAX_VALUE;
        LOG.debug("User info name: {}, age: {}, sex: {}, jobless: {}, single: {}",
                name,
                age,
                sex,
                jobless,
                single);
        LOG.debug("tasksAvg: {}, daysStudy: {}, tries: {}, successTaskRate: {}, maxShortToRemember: {}",
                tasksAvg,
                daysStudy,
                tries,
                successTaskRate,
                maxShortToRemember);
    }
}