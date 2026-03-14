package ru.job4j.ood.srp.report;

import org.junit.jupiter.api.Test;
import ru.job4j.ood.srp.currency.Currency;
import ru.job4j.ood.srp.currency.CurrencyConverter;
import ru.job4j.ood.srp.currency.InMemoryCurrencyConverter;
import ru.job4j.ood.srp.formatter.DateTimeParser;
import ru.job4j.ood.srp.formatter.ReportDateTimeParser;
import ru.job4j.ood.srp.model.Employee;
import ru.job4j.ood.srp.store.MemoryStore;
import ru.job4j.ood.srp.store.Store;

import java.util.Calendar;

import static org.assertj.core.api.Assertions.assertThat;

class ReportAccountingTest {

    @Test
    void whenAccountingReportGenerated() {
        Store store = new MemoryStore();
        Calendar now = Calendar.getInstance();
        CurrencyConverter inMemoryCurrencyConverter = new InMemoryCurrencyConverter();
        Employee employee = new Employee("Ivan", now, now, 100);
        DateTimeParser<Calendar> dateTimeParser = new ReportDateTimeParser();
        store.add(employee);
        Currency source = Currency.RUB;
        Currency target = Currency.USD;
        Report accountReport = new ReportAccounting(store, dateTimeParser, inMemoryCurrencyConverter, source, target);
        StringBuilder expected = new StringBuilder()
                .append("Name; Hired; Fired; Salary")
                .append(System.lineSeparator())
                .append(employee.getName()).append(" ")
                .append(dateTimeParser.parse(employee.getHired())).append(" ")
                .append(dateTimeParser.parse(employee.getFired())).append(" ")
                .append(inMemoryCurrencyConverter.convert(source, employee.getSalary(), target))
                .append(System.lineSeparator());
        assertThat(accountReport.generate(thisEmployee -> true)).isEqualTo(expected.toString());
    }

}