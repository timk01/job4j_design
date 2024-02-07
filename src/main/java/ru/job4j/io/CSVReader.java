package ru.job4j.io;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class CSVReader {

    private static List<String> firstCLIArgs = List.of("path", "delimiter", "out", "filter");
    private static int argsCounter = 4;
    private static final String FILE_EXTENSION = ".csv";
    private static String stdout = "stdout";
    private static String delimeter;
    private static List<String> result = new ArrayList<>();
    private static StringBuilder stringBuilder = new StringBuilder();

    private static void fillIndexes(String[] filteredArgsArr, String[] splitFirstStr, int[] indexes) {
        int j = 0;
        for (String value : filteredArgsArr) {
            for (int i = 0; i < splitFirstStr.length; i++) {
                if (value.equals(splitFirstStr[i])) {
                    stringBuilder.append(splitFirstStr[i]);
                    if (j != indexes.length - 1) {
                        stringBuilder.append(delimeter);
                    }
                    indexes[j++] = i;
                    if (j >= filteredArgsArr.length) {
                        stringBuilder.append(System.lineSeparator());
                        break;
                    }
                }
            }
        }
    }

    public static void handle(ArgsName argsName) {
        argumentsDetailedChecker(argsName);

        try (Scanner scanner =
                     new Scanner(
                             new FileInputStream(
                                     argsName.get("path")));
             PrintWriter writer =
                     new PrintWriter(
                             new FileWriter(argsName.get("out"), Charset.forName("WINDOWS-1251")), false)) {

            String filteredArgs = argsName.get(firstCLIArgs.get(argsCounter - 1));
            String[] filteredArgsArr = filteredArgs.split(",");

            String[] splitFirstStr = new String[argsCounter];
            if (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                splitFirstStr = line.split(delimeter);
            }

            int[] indexes = new int[filteredArgsArr.length];

            fillIndexes(filteredArgsArr, splitFirstStr, indexes);

            while (scanner.hasNextLine()) {
                String s = scanner.nextLine();
                String[] splitStr = s.split(delimeter);
                for (int i = 0; i < indexes.length; i++) {
                    stringBuilder.append(splitStr[indexes[i]]);
                    if (i < indexes.length - 1) {
                        stringBuilder.append(delimeter);
                    }
                }
                stringBuilder.append(System.lineSeparator());
            }
            result.add(stringBuilder.toString());

            if (stdout.equals(argsName.get(firstCLIArgs.get(2)))) {
                result.forEach(System.out::println);
            } else {
                result.forEach(writer::write);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void argsCountChecker(String[] args) {
        if (args.length != argsCounter) {
            throw new IllegalArgumentException(
                    String.format("Error: Have to pass exactly '%d' args!",
                            argsCounter));
        }
    }

    private static void checkFirstSplitArg(Map<String, String> preparedArgs) {
        for (String firstCLIArg : firstCLIArgs) {
            if (!preparedArgs.containsKey(firstCLIArg)) {
                throw new IllegalArgumentException(
                        String.format("Error: First argument should be '%s'",
                                firstCLIArg));
            }
        }
    }

    private static boolean isProperDelimeter(Path secondSplitArg) {
        return !("\\t".equals(secondSplitArg.toString())
                || " ".equals(secondSplitArg.toString())
                || "|".equals(secondSplitArg.toString())
                || ",".equals(secondSplitArg.toString())
                || ";".equals(secondSplitArg.toString()));
    }

    private static void argumentsDetailedChecker(ArgsName preparedArgs) {
        checkFirstSplitArg(preparedArgs.getValues());

        int argCounter = -1;
        Path secondSplitArg = Path.of(preparedArgs.get(firstCLIArgs.get(++argCounter)));
        if (!Files.exists(secondSplitArg) && !Files.isRegularFile(secondSplitArg)) {
            throw new IllegalArgumentException(
                    String.format("Error: File '%s' doesn't exist or file isn't a regular file",
                            secondSplitArg.toAbsolutePath()));
        }
        if (!secondSplitArg.toString().endsWith(FILE_EXTENSION)) {
            throw new IllegalArgumentException(
                    String.format("Error: File extension should be: '%s'",
                            FILE_EXTENSION));
        }

        secondSplitArg = Path.of(preparedArgs.get(firstCLIArgs.get(++argCounter)));
        delimeter = secondSplitArg.toString();
        if (isProperDelimeter(secondSplitArg)) {
            throw new IllegalArgumentException("Error: Need to provide correct file delimeter.");
        }

        secondSplitArg = Path.of(preparedArgs.get(firstCLIArgs.get(++argCounter)));
        if (!(secondSplitArg.toString().endsWith(stdout)
                || secondSplitArg.toString().endsWith(FILE_EXTENSION))) {
            throw new IllegalArgumentException(
                    String.format("Error: Need to provide output to '%s' or to '%s'",
                            stdout, secondSplitArg.toAbsolutePath()));
        }

        secondSplitArg = Path.of(preparedArgs.get(firstCLIArgs.get(++argCounter)));
        if (secondSplitArg.toString().length() < 1) {
            throw new IllegalArgumentException(
                    "Error: arguments must conform the ones in table");
        }
    }

    public static void main(String[] args) throws Exception {
        argsCountChecker(args);

        ArgsName concreteArgs = ArgsName.of(args);
        System.out.println(concreteArgs.hashCode());

        handle(concreteArgs);
    }
}