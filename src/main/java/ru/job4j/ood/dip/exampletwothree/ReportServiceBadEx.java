package ru.job4j.ood.dip.exampletwothree;

import ru.job4j.ood.srp.report.Report;

public class ReportServiceBadEx {
    private final PdfPrinter printer = new PdfPrinter();

    public void print(Report report) {
        printer.print(report);
    }
}