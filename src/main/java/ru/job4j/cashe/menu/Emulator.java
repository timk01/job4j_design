package ru.job4j.cashe.menu;

import ru.job4j.cashe.AbstractCache;
import ru.job4j.cashe.DirFileCache;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Stream;

public class Emulator {

    private static List<String> files;

    public static final int SET_CASH_DIRECTORY = 1;
    public static final int LOAD_FILE_TO_CASH = 2;
    public static final int SHOW_FILE_CONTENT = 3;

    public static final String SELECT = "Выберите меню";
    public static final String EXIT = "Конец работы";

    public static final String MENU = """
                Введите 1 для указания кэшируемой директории (начинать нужно отсюда!).
                Введите 2, чтобы загрузить содержимое файла в кэш.
                Введите 3, получить содержимое файла из кэша.
                Введите любое другое число для выхода.
            """;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        try {
            start(scanner);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<String> listFilesUsingFileWalk(String dir, int depth) throws IOException {
        try (Stream<Path> stream = Files.walk(Paths.get(dir), depth)) {
            return stream
                    .filter(file -> !Files.isDirectory(file))
                    .map(Path::getFileName)
                    .map(Path::toString)
                    .toList();
        }
    }

    public static void start(Scanner scanner) throws IOException {
        boolean run = true;
        String path = null;
        String file = null;
        AbstractCache<String, String> cache = null;
        while (run) {
            System.out.println(MENU);
            System.out.println(SELECT);
            int userChoice = Integer.parseInt(scanner.nextLine());
            System.out.printf("%s%s%n", "Вы выбрали: ", userChoice);
            if (SET_CASH_DIRECTORY == userChoice) {
                System.out.printf("%s%n", "Сперва нужно набрать путь к директории: ");
                path = scanner.nextLine();
                System.out.printf("%s%s%s%n", "Вы выбрали: \"", path, "\"");
                cache = new DirFileCache(path);
                files = listFilesUsingFileWalk(path, 1);
                if (!files.isEmpty()) {
                    System.out.println("Файлы в директории:");
                    files.forEach(System.out::println);
                } else {
                    System.out.println("Директория пуста!");
                }
            } else if (LOAD_FILE_TO_CASH == userChoice) {
                if (cache != null) {
                    System.out.printf("%s%n", "Нужно ввести название файла: ");
                    file = scanner.nextLine();
                    System.out.printf("%s%s%s%n", "Вы выбрали: \"", file, "\"");
                    if (files.contains(file)) {
                        cache.get(file);
                        System.out.println("Файл загружен в кеш");
                    } else {
                        System.out.println("Нет такого файла!");
                    }
                } else {
                    System.out.println("Нужно сперва указать путь до директории!");
                }
            } else if (SHOW_FILE_CONTENT == userChoice) {
                if (cache != null) {
                    System.out.printf("%s%n", "Нужно ввести название файла: ");
                    file = scanner.nextLine();
                    System.out.printf("%s%s%s%n", "Вы выбрали: \"", file, "\"");
                    if (files.contains(file)) {
                        String s = cache.get(file);
                        System.out.println("Контент кэша:");
                        System.out.println(s);
                    } else {
                        System.out.println("Нет такого файла!");
                    }

                } else {
                    System.out.println("Нужно сперва указать путь до директории!");
                }
            } else {
                run = false;
                System.out.println(EXIT);
            }
        }
    }
}
