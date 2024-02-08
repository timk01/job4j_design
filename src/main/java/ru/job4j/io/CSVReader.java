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

    private static void writeDataToFile(ArgsName argsName, StringBuilder stringBuilder) {
        try (PrintWriter writer =
                     new PrintWriter(
                             new FileWriter(argsName.get("out"), Charset.forName("WINDOWS-1251")), true)) {
            writer.print(stringBuilder);
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

    public static void handle(ArgsName argsName) {
        argumentsDetailedChecker(argsName);

        try (Scanner scanner =
                     new Scanner(
                             new FileInputStream(
                                     argsName.get("path")))) {

            Map<String, Integer> firstStrMap = new HashMap<>();
            String[] splitFirstStr;
            if (scanner.hasNextLine()) {
                splitFirstStr = scanner.nextLine().split(delimeter);
                for (int i = 0; i < splitFirstStr.length; i++) {
                    firstStrMap.put(splitFirstStr[i], i);
                }
            }

            String[] filteredArgsArr = argsName.get(firstCLIArgs.get(argsCounter - 1)).split(",");
            StringBuilder stringBuilder = new StringBuilder();

            for (int i = 0; i < filteredArgsArr.length; i++) {
                stringBuilder.append(filteredArgsArr[i]);
                if (i != filteredArgsArr.length - 1) {
                    stringBuilder.append(delimeter);
                }
                if (i == filteredArgsArr.length - 1) {
                    stringBuilder.append(System.lineSeparator());
                    break;
                }
            }

            while (scanner.hasNextLine()) {
                String s = scanner.nextLine();
                String[] splitStr = s.split(delimeter);
                for (int i = 0; i < filteredArgsArr.length; i++) {
                    stringBuilder.append(splitStr[firstStrMap.get(filteredArgsArr[i])]);
                    if (i != filteredArgsArr.length - 1) {
                        stringBuilder.append(delimeter);
                    }
                }
                stringBuilder.append(System.lineSeparator());
            }

            if (stdout.equals(argsName.get(firstCLIArgs.get(2)))) {
                System.out.print(stringBuilder);
                Path file = Path.of(argsName.get("out"));
                file.toFile().deleteOnExit();
            } else {
                writeDataToFile(argsName, stringBuilder);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        argsCountChecker(args);

        handle(ArgsName.of(args));
    }
}