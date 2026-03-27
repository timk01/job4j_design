package ru.job4j.ood.dip.exampletwothree;

import ru.job4j.ood.srp.report.Report;

public class PdfPrinter {
    void print(Report report) {
        System.out.println(report.generate((employee) -> employee.getSalary() > 0));
    }
}
