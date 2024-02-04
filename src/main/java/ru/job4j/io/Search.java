package ru.job4j.io;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.function.Predicate;

public class Search {
    public static void validateParams(String[] args) {
        if (args.length == 0) {
            System.out.println("No arguments provided");
        }
        Path path = Path.of(args[0]);
        if (!Files.exists(path)) {
            throw new IllegalArgumentException(String.format("Not exist %s",
                    path.toFile().getAbsoluteFile()));
        }
        if (!Files.isDirectory(path)) {
            throw new IllegalArgumentException(String.format("Not directory %s",
                    path.toFile().getAbsoluteFile()));
        }
        if (!args[1].matches("[.].+")) {
            throw new IllegalArgumentException("Need to provide correct file extension.");
        }
    }

    public static void main(String[] args) throws IOException, URISyntaxException {
        validateParams(args);
        Path start = Paths.get(args[0]);
        Predicate<Path> txtPredicate = path -> path.getFileName().toFile().toString().endsWith(args[1]);
        search(start, txtPredicate).forEach(System.out::println);
    }

    public static List<Path> search(Path root, Predicate<Path> condition) throws IOException {
        SearchFiles searcher = new SearchFiles(condition);
        Files.walkFileTree(root, searcher);
        return searcher.getPaths();
    }
}