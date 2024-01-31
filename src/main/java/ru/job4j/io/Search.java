package ru.job4j.io;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.function.Predicate;

public class Search {
    public static void main(String[] args) throws IOException {
        Path start = Paths.get(".");
        Predicate<Path> txtPredicate = path -> path.getFileName().toFile().toString().endsWith(".txt");
        search(start, txtPredicate);

        SearchFiles searchFiles = new SearchFiles(txtPredicate);
        searchFiles.visitFileBasic(start);
        System.out.println(searchFiles.compareTwoWalkers());
    }

    public static List<Path> search(Path root, Predicate<Path> condition) throws IOException {
        SearchFiles searcher = new SearchFiles(condition);
        Files.walkFileTree(root, searcher);
        return searcher.getPaths();
    }
}