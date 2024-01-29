package ru.job4j.io;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * ??? StringJoiner, as idea to gather String from flatmap ?
 * List<String> stringList = buff.lines()
 * .map(str -> str.split(" "))
 * .filter(string -> string[string.length - 2].equals("404"))
 * .flatMap(Arrays::stream)
 * .toList();
 */

public class LogFiltersmth {
    private final String file;

    public LogFiltersmth(String file) {
        this.file = file;
    }

    public List<String> filter() {
        List<String> strings = new ArrayList<>();
        try (BufferedReader buff = new BufferedReader(new FileReader(file))) {
            strings = buff.lines()
                    .filter(string -> {
                        String[] splitStr = string.split(" ");
                        return splitStr[splitStr.length - 2].equals("404");
                    })
                    .map(str -> str.concat(System.lineSeparator()))
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
            data.forEach(printWriter::print);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new LogFiltersmth("data/log.txt").saveTo("data/404.txt");
    }
}