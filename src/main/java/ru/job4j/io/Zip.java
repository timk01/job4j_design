package ru.job4j.io;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Zip {

    private List<String> firstCLIArgs = List.of("d", "e", "o");

    private static void argsCountChecker(String[] args) {
        if (args.length != 3) {
            throw new IllegalArgumentException(
                    String.format("Error: Have to pass exactly '%s' args!",
                            args.length));
        }
    }

    private void checkFirstSplittedArg(Map<String, String> preparedArgs) {
        for (String firstCLIArg : firstCLIArgs) {
            if (!preparedArgs.containsKey(firstCLIArg)) {
                throw new IllegalArgumentException(
                        String.format("Error: First argument should be '%s'",
                                firstCLIArg));
            }
        }
    }

    private void argumentsZipDetailedChecker(ArgsName preparedArgs) {
        checkFirstSplittedArg(preparedArgs.getValues());

        int argCounter = -1;
        Path secondSplittedArg = Path.of(preparedArgs.get(firstCLIArgs.get(++argCounter)));
        if (!Files.exists(secondSplittedArg) && !Files.isDirectory(secondSplittedArg)) {
            throw new IllegalArgumentException(
                    String.format("Error: File '%s' doesn't exist or file isn't directory",
                            secondSplittedArg.toAbsolutePath()));
        }

        secondSplittedArg = Path.of(preparedArgs.get(firstCLIArgs.get(++argCounter)));
        if (!secondSplittedArg.toString().matches("[.].+")) {
            throw new IllegalArgumentException("Need to provide correct file extension.");
        }

        secondSplittedArg = Path.of(preparedArgs.get(firstCLIArgs.get(++argCounter)));
        if (!secondSplittedArg.toString().endsWith(".zip")) {
            throw new IllegalArgumentException("Need to provide .zip file extension.");
        }
    }

    public void packFiles(List<Path> sources, File target) {
        try (ZipOutputStream zip =
                     new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(target)))) {
            for (Path path : sources) {
                zip.putNextEntry(new ZipEntry(path.toString()));
                try (BufferedInputStream bufferedInputStream =
                             new BufferedInputStream(new FileInputStream(path.toFile()))) {
                        zip.write(bufferedInputStream.readAllBytes());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        Zip zip = new Zip();

        argsCountChecker(args);
        ArgsName threeParams = ArgsName.of(
                new String[]{args[0], args[1], args[2]}
        );

        zip.argumentsZipDetailedChecker(threeParams);

        String searchPath = threeParams.get("d");
        String excludeExtension = threeParams.get("e");
        String nameOfZippedProject = threeParams.get("o");

        Path start = Paths.get(searchPath);
        Predicate<Path> excludePredicateExt = path ->
                !path.getFileName().toFile().toString().endsWith(excludeExtension);
        List<Path> list = Search.search(start, excludePredicateExt);
        zip.packFiles(list, new File(nameOfZippedProject));
    }
}