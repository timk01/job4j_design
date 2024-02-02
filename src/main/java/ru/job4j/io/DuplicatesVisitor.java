package ru.job4j.io;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DuplicatesVisitor extends SimpleFileVisitor<Path> {

    private Map<FileProperty, List<Path>> map = new HashMap<>();

    @Override
    public FileVisitResult visitFile(Path file,
                                     BasicFileAttributes attributes) throws IOException {
        FileProperty fileProperty =
                new FileProperty(file.toFile().length(), file.toFile().getName());

        map.computeIfAbsent(
                        fileProperty,
                        k -> new ArrayList<>()
                )
                .add(file);

        return super.visitFile(file, attributes);
    }

    public void printDuplicates() {
        map.entrySet()
                .stream()
                .filter(entry -> entry.getValue().size() > 1)
                .forEach(entry ->
                        System.out.println(
                                entry.getKey().getName()
                                        + " "
                                        + entry.getKey().getSize()
                                        + System.lineSeparator()
                                        + entry.getValue()
                        ));
    }
}