package ru.job4j.io;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.function.Predicate;

public class Search {
    private static void validateParams(String[] args) {
        if (args.length == 0) {
            System.out.println("No arguments provided");
        }
        if (args[0].startsWith("[.a-zA-Z0-9/]")) {
            throw new IllegalArgumentException("Provided folder \"" + args[0] + "\" is wrong");
        }
        if (!(".txt".equals(args[1]) || ".js".equals(args[1]))) {
            throw new IllegalArgumentException("Need to provide \".txt\" or \".js\" file extension.");
        }
    }

    public static void main(String[] args) throws IOException {
        validateParams(args);
        Path start = Paths.get(".");
        Predicate<Path> txtPredicate = path -> path.getFileName().toFile().toString().endsWith(args[1]);
        search(start, txtPredicate).forEach(System.out::println);
    }

    public static List<Path> search(Path root, Predicate<Path> condition) throws IOException {
        SearchFiles searcher = new SearchFiles(condition);
        Files.walkFileTree(root, searcher);
        return searcher.getPaths();
    }
}