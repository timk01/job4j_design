package ru.job4j.ood.srp.report;

import org.junit.jupiter.api.Test;
import ru.job4j.ood.srp.formatter.DateTimeParser;
import ru.job4j.ood.srp.formatter.ReportDateTimeParser;
import ru.job4j.ood.srp.model.Employee;
import ru.job4j.ood.srp.store.MemoryStore;
import ru.job4j.ood.srp.store.Store;

import java.util.Calendar;
import java.util.Date;

import static org.assertj.core.api.Assertions.*;

class ReportEngineTest {

    @Test
    void whenBasicReportGenerated() {
        Store store = new MemoryStore();
        Calendar now = Calendar.getInstance();
        Employee employee = new Employee("Ivan", now, now, 100);
        DateTimeParser<Calendar> dateTimeParser = new ReportDateTimeParser();
        store.add(employee);
        Report basicReport = new ReportEngine(store, dateTimeParser);
        StringBuilder expected = new StringBuilder()
                .append("Name; Hired; Fired; Salary")
                .append(System.lineSeparator())
                .append(employee.getName()).append(" ")
                .append(dateTimeParser.parse(employee.getHired())).append(" ")
                .append(dateTimeParser.parse(employee.getFired())).append(" ")
                .append(employee.getSalary())
                .append(System.lineSeparator());
        assertThat(basicReport.generate(thisEmployee -> true)).isEqualTo(expected.toString());
    }

}