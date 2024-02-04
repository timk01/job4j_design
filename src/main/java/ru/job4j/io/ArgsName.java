package ru.job4j.io;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class ArgsName {

    private final Map<String, String> values = new HashMap<>();
    private int argsSplitCounter = 1;

    public String get(String key) {
        if (!values.containsKey(key)) {
            throw new IllegalArgumentException(
                    String.format("This key: '%s' is missing", key));
        }
        return values.get(key);
    }

    private void preliminaryArgChecker(String arg) {
        if (!arg.contains("=")) {
            throw new IllegalArgumentException(
                    String.format("Error: This argument '%s' does not contain an equal sign", arg));
        }
        if (!arg.startsWith("-")) {
            throw new IllegalArgumentException(
                    String.format("Error: This argument '%s' does not start with a '-' character", arg));
        }
        if (arg.startsWith("-=")) {
            throw new IllegalArgumentException(
                    String.format("Error: This argument '%s' does not contain a key", arg));
        }
        int i = arg.indexOf("=");
        if (arg.substring(i + 1).isEmpty()) {
            throw new IllegalArgumentException(
                    String.format("Error: This argument '%s' does not contain a value",
                            arg));
        }
    }

    private void checkFirstSplittedArg(String[] splitted, String firstArg) {
        if (!splitted[0].startsWith(firstArg)) {
            throw new IllegalArgumentException(
                    String.format("Error: First argument should be '%s'",
                            firstArg));
        }
    }

    private void argumentsChecker(String[] splitted) {
        String firstArg;
        switch (argsSplitCounter) {
            case 1 -> {
                firstArg = "-d";
                checkFirstSplittedArg(splitted, firstArg);
                Path initDirectory = Path.of(splitted[1]);
                if (!Files.exists(initDirectory) && !Files.isDirectory(initDirectory)) {
                    throw new IllegalArgumentException(
                            String.format("Error: File '%s' doesn't exist or file isn't directory",
                                    initDirectory.toAbsolutePath()));
                }
            }
            case 2 -> {
                firstArg = "-e";
                checkFirstSplittedArg(splitted, firstArg);
                if (!splitted[1].matches("[.].+")) {
                    throw new IllegalArgumentException("Need to provide correct file extension.");
                }
            }
            case 3 -> {
                firstArg = "-o";
                checkFirstSplittedArg(splitted, firstArg);
                if (!splitted[1].endsWith(".zip")) {
                    throw new IllegalArgumentException("Need to provide correct file extension.");
                }
            }
            default ->
                throw new IllegalArgumentException("Wrong number of args");
        }
    }

    private void parse(String[] args) {
        for (String arg : args) {
            preliminaryArgChecker(arg);
            String[] splitted = arg.split("=", 2);
            argumentsChecker(splitted);
            argsSplitCounter++;
            for (int i = 0; i < splitted.length; i++) {
                values.put(splitted[0].substring(1), splitted[1]);
            }
        }
    }

    public static ArgsName of(String[] args) {
        if (args.length == 0) {
            throw new IllegalArgumentException("Arguments not passed to program");
        }
        ArgsName names = new ArgsName();
        names.parse(args);
        return names;
    }

    public static void main(String[] args) {
        ArgsName threeParams = ArgsName.of(
                new String[]{args[0], args[1], args[2]}
        );
        System.out.println(threeParams.get("d"));
    }
}