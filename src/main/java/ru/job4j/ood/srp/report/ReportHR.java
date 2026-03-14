package ru.job4j.ood.srp.report;

import ru.job4j.ood.srp.formatter.DateTimeParser;
import ru.job4j.ood.srp.model.Employee;
import ru.job4j.ood.srp.store.Store;

import java.util.*;
import java.util.function.Predicate;

public class ReportHR implements Report {
    private final Store store;
    private final DateTimeParser<Calendar> dateTimeParser;

    public ReportHR(Store store, DateTimeParser<Calendar> dateTimeParser) {
        this.store = store;
        this.dateTimeParser = dateTimeParser;
    }

    @Override
    public String generate(Predicate<Employee> filter) {
        StringBuilder text = new StringBuilder();
        text.append("Name; Salary").append(System.lineSeparator());
        List<Employee> filteredEmployees = new ArrayList<>(store.findBy(filter));
        Comparator<Employee> comparator = (o1, o2) -> Double.compare(o2.getSalary(), o1.getSalary());
        Collections.sort(filteredEmployees, comparator);
        for (Employee sortedEmployee : filteredEmployees) {
            text.append(sortedEmployee.getName()).append(" ")
                    .append(sortedEmployee.getSalary())
                    .append(System.lineSeparator());
        }
        return text.toString();
    }
}
