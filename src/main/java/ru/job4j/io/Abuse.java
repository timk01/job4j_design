package ru.job4j.io;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

public class Abuse {

    public static void drop(String source, String target, List<String> words) throws IOException {
        try (BufferedReader input = new BufferedReader(new FileReader(source));
             PrintWriter output = new PrintWriter(new BufferedOutputStream(new FileOutputStream(target)))) {
            input.lines()
                    .flatMap(line -> Stream.of(line.split("\\s+")))
                    .filter(word -> !words.contains(word))
                    .map(word -> word + " ")
                    .forEach(output::print);
        }
    }

    public static void main(String[] args) throws FileNotFoundException {
        Path path
                = Paths.get("C:\\Users\\Тимур");
        File source = path.resolve("source.txt").toFile();
        try (PrintWriter output = new PrintWriter(source)) {
            output.println("hello foolish dude");
            output.println("java job4j php");
        }
    }
}