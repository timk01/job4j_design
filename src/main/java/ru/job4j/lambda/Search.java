package ru.job4j.lambda;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.regex.Pattern;

public class Search {

    static List<File> findBy(Predicate<File> predicate, List<File> files) {
        List<File> result = new ArrayList<>();
        for (File file : files) {
            if (predicate.test(file)) {
                result.add(file);
            }
        }
        return result;
    }

    static List<File> findByMaskWithPredicate(List<File> files, String mask) {
        Predicate<File> predicate = new Predicate<File>() {
            @Override
            public boolean test(File file) {
                return Pattern.matches(mask, file.getName());
            }
        };
        return findBy(predicate, files);
    }

    static List<File> findByMaskWithLambda(List<File> files, String mask) {
        return findBy(file -> Pattern.matches(mask, file.getName()), files);
    }

    static List<File> findByName(List<File> files, String name) {
        return findBy(file -> file.getName().equals(name), files);
    }

    static List<File> findByExt(List<File> files, String extension) {
        return findBy(file -> file.getName().endsWith(extension), files);
    }
}