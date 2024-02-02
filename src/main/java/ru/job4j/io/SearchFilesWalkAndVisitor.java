package ru.job4j.io;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class SearchFilesWalkAndVisitor extends SimpleFileVisitor<Path> {

    private final Predicate<Path> condition;
    private List<Path> paths = new ArrayList<>();
    private List<Path> paths2 = new ArrayList<>();

    public SearchFilesWalkAndVisitor(Predicate<Path> condition) {
        this.condition = condition;
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs)
            throws IOException {
        Objects.requireNonNull(file);
        Objects.requireNonNull(attrs);

        if (condition.test(file)) {
            paths.add(file);
        }

        return FileVisitResult.CONTINUE;
    }

    public void visitFileBasic(Path file) throws IOException {
        try (Stream<Path> stream = Files.walk(file)) {
            stream.filter(condition)
                    .forEach(paths2::add);
        }
    }

    public List<Path> getPaths() {
        return this.paths;
    }

    public boolean compareTwoWalkers() {
        this.paths.removeAll(paths2);
        return paths.isEmpty();
    }
}
