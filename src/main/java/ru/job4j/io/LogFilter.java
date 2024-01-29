package ru.job4j.io;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class LogFilter {
    private final String file;

    public LogFilter(String file) {
        this.file = file;
    }

    public List<String> filter() {
        List<String> strings = new ArrayList<>();
        try (BufferedReader buff = new BufferedReader(new FileReader(file))) {
            strings = buff.lines()
                    .filter(s -> s.contains(" 404 "))
                    .toList();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return strings;
    }

    public void saveTo(String out) {
        var data = filter();
        try (PrintWriter printWriter =
                     new PrintWriter(
                             new BufferedOutputStream(
                                     new FileOutputStream(out)))) {
            data.forEach(str ->
                    printWriter.printf("%s%s", str, System.lineSeparator()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new LogFilter("data/log.txt").saveTo("data/404.txt");
    }
}