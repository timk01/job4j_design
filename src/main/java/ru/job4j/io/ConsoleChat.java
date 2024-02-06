package ru.job4j.io;

import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.random.RandomGenerator;
import java.util.stream.Stream;

public class ConsoleChat {
    private static final String OUT = "закончить";
    private static final String STOP = "стоп";
    private static final String CONTINUE = "продолжить";
    private final String path;
    private final String botAnswers;
    private final List<String> botPhrases = new ArrayList<>();
    private final List<String> log = new ArrayList<>();

    public ConsoleChat(String path, String botAnswers) {
        this.path = path;
        this.botAnswers = botAnswers;
    }

    private String getBotAnswer() {
        return botPhrases.get(
                RandomGenerator.getDefault().nextInt(0, botPhrases.size() - 1));
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введи слово-фразу: ");
        String phrase;

        readPhrases();
        String botAnswer;
        boolean flag = true;
        boolean botStopper = false;

        while (flag) {
            phrase = scanner.nextLine();
            log.add(phrase);
            switch (phrase) {
                case OUT:
                    flag = false;
                    System.out.println("Заканчиваю работу");
                    saveLog(this.log);
                    break;
                case STOP:
                    botStopper = true;
                    break;
                case CONTINUE:
                    botStopper = false;
                default:
                    if (!botStopper) {
                        botAnswer = getBotAnswer();
                        System.out.println(botAnswer);
                        log.add(botAnswer);
                    }
            }
        }
    }

    private List<String> readPhrases() {
        try (BufferedReader reader =
                     new BufferedReader(
                             new FileReader(
                                     botAnswers, Charset.forName("WINDOWS-1251")))) {
            reader.lines()
                    .flatMap(string -> Stream.of(string.split(" ")))
                    .forEach(botPhrases::add);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return botPhrases;
    }

    private void saveLog(List<String> log) {
        try (PrintWriter writer =
                     new PrintWriter(new FileWriter(path, Charset.forName("WINDOWS-1251"), false))) {
            log.forEach(writer::println);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ConsoleChat consoleChat =
                new ConsoleChat("data/botUserLog.txt", "data/randomAnswers.txt");
        consoleChat.run();
    }
}