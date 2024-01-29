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
            String line;
            while ((line = buff.readLine()) != null) {
                String[] splitStr = line.split(" ");
                if (splitStr[splitStr.length - 2].equals("404")) {
                    strings.add(line);
                }
            }
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
            data.forEach(printWriter::println);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new LogFilter("data/log.txt").saveTo("data/404.txt");
    }
}