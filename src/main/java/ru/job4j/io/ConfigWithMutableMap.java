package ru.job4j.io;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;
import java.util.stream.Collectors;

public class ConfigWithMutableMap {

    private final String path;
    private Map<String, String> values = new HashMap<String, String>();

    public ConfigWithMutableMap(final String path) {
        this.path = path;
    }

    private String[] checkSplit(String[] split) {
        if (split.length != 2 || split[0].length() == 0 || split[1].length() == 0) {
            throw new IllegalArgumentException(
                    "must have '=', key/value part can't be empty");
        }
        return split;
    }

    public void load() {
        try (BufferedReader reader = new BufferedReader(new FileReader(this.path))) {
            values = reader.lines()
                    .filter(str -> !str.isEmpty() && !str.startsWith("#"))
                    .map(str -> checkSplit(str.split("=", 2)))
                    .collect(Collectors.toMap(strings -> strings[0], strings -> strings[1]));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String value(String key) {
        return values.get(key);
    }

    @Override
    public String toString() {
        StringJoiner output = new StringJoiner(System.lineSeparator());
        try (BufferedReader reader = new BufferedReader(new FileReader(this.path))) {
            reader.lines().forEach(output::add);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return output.toString();
    }
}