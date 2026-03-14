package ru.job4j.ood.srp.report;

import org.junit.jupiter.api.Test;
import ru.job4j.ood.srp.formatter.DateTimeParser;
import ru.job4j.ood.srp.formatter.ReportDateTimeParser;
import ru.job4j.ood.srp.model.Employee;
import ru.job4j.ood.srp.store.MemoryStore;
import ru.job4j.ood.srp.store.Store;

import java.util.Calendar;

import static org.assertj.core.api.Assertions.*;

class ReportProgrammersTest {
    @Test
    void whenProgrammersReportGenerated() {
        Calendar now = Calendar.getInstance();
        Store store = new MemoryStore();
        Employee ivan = new Employee("ivan", now, now, 100);
        Employee marusya = new Employee("marusya", now, now, 200);
        Employee egor = new Employee("egor", now, now, 200);
        Employee petr = new Employee("petr", now, now, 150);
        DateTimeParser<Calendar> dateTimeParser = new ReportDateTimeParser();
        store.add(ivan);
        store.add(marusya);
        store.add(egor);
        store.add(petr);
        Report reportProgrammers = new ReportProgrammers(store, dateTimeParser);
        StringBuilder expected = new StringBuilder()
                .append("Name;Hired;Fired;Salary")
                .append(System.lineSeparator())
                .append(ivan.getName()).append(";")
                .append(dateTimeParser.parse(ivan.getHired())).append(";")
                .append(dateTimeParser.parse(ivan.getFired())).append(";")
                .append(ivan.getSalary())
                .append(System.lineSeparator())
                .append(marusya.getName()).append(";")
                .append(dateTimeParser.parse(marusya.getHired())).append(";")
                .append(dateTimeParser.parse(marusya.getFired())).append(";")
                .append(marusya.getSalary())
                .append(System.lineSeparator())
                .append(egor.getName()).append(";")
                .append(dateTimeParser.parse(egor.getHired())).append(";")
                .append(dateTimeParser.parse(egor.getFired())).append(";")
                .append(egor.getSalary())
                .append(System.lineSeparator())
                .append(petr.getName()).append(";")
                .append(dateTimeParser.parse(petr.getHired())).append(";")
                .append(dateTimeParser.parse(petr.getFired())).append(";")
                .append(petr.getSalary())
                .append(System.lineSeparator());
        assertThat(reportProgrammers.generate(thisEmployee -> true)).isEqualTo(expected.toString());
    }

}