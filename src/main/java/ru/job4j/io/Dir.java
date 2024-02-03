package ru.job4j.io;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class Dir {

    /**
     * usage: Dir.printFiles(file.listFiles());
     * ((don't forget to change to C:\projects in prog args as args[0]))
     * @param files
     */

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

    public static void main(String[] args) throws IOException {
        if (args.length == 0) {
            throw new IllegalArgumentException("Root folder is null. "
                    + "Usage java -jar dir.jar ROOT_FOLDER.");
        }
        File file = new File(args[0]);
        if (!file.exists()) {
            throw new IllegalArgumentException(String.format("Not exist %s",
                    file.getAbsoluteFile()));
        }
        if (!file.isDirectory()) {
            throw new IllegalArgumentException(String.format("Not directory %s",
                    file.getAbsoluteFile()));
        }
        System.out.println(String.format("size : %s%n", file.getTotalSpace()));
        for (File subFile : Objects.requireNonNull(file.listFiles())) {
            System.out.printf("Name: %s, Size: %s%n", subFile.getName(), subFile.length());
        }
    }
}