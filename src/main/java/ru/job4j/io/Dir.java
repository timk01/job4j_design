package ru.job4j.io;

import java.io.File;
import java.util.Objects;

public class Dir {

    public static void printFiles(File[] files) {
        for (File file : files) {
            if (file.isDirectory()) {
                printFiles(Objects.requireNonNull(file.listFiles()));
            } else {
                System.out.printf(
                        "fileName: %s, fileSize: %d%s",
                        file.getAbsoluteFile().getName(),
                        file.length(),
                        System.lineSeparator());
            }
        }
    }

    public static void main(String[] args) {
        if (args.length == 0) {
            throw new IllegalArgumentException("Root folder is null. Usage  ROOT_FOLDER.");
        }
        File file = new File(args[0]);
        if (!file.exists()) {
            throw new IllegalArgumentException(String.format("Not exist %s", file.getAbsoluteFile()));
        }
        if (!file.isDirectory()) {
            throw new IllegalArgumentException(String.format("Not directory %s", file.getAbsoluteFile()));
        }
        System.out.println(String.format("size : %s", file.getTotalSpace()));
        for (File subfile : file.listFiles()) {
            System.out.println(subfile.getName());
        }
        Dir.printFiles(file.listFiles());
    }
}