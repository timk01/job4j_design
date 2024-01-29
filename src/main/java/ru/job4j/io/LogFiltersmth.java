package ru.job4j.io;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *  List<String> stringList = buff.lines()
 *                     .map(str -> str.split(" "))
 *                     .filter(string -> string[string.length - 2].equals("404"))
 *                     .flatMap(Arrays::stream)
 *                     .toList();
 */

public class LogFiltersmth {
    private final String file;

    public LogFiltersmth(String file) {
        this.file = file;
    }

    public List<String> filter() {
        List<String> strings = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        try (BufferedReader buff = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = buff.readLine()) != null) {
                String[] splitStr = line.split(" ");
                if (splitStr[splitStr.length - 2].equals("404")) {
                    System.out.println("line: " + line);
                    strings.add(sb.append(line).append(System.lineSeparator()).toString());
                    sb.setLength(0);
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
            data.forEach(str ->
                    printWriter.printf("%s%s", str, System.lineSeparator()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new LogFiltersmth("data/log.txt").saveTo("data/404.txt");
    }
}