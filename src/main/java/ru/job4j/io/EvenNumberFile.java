package ru.job4j.io;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;

public class EvenNumberFile {

    private static void printNumbersOddity(String[] strNumbers) {
        Arrays.stream(strNumbers)
                .mapToInt(Integer::parseInt)
                .forEach(
                        numb ->
                                System.out.printf("number %d is odd ? %b%s",
                                        numb, numb % 2 == 0, System.lineSeparator())
                );
    }

    public static void main(String[] args) {
        try (FileInputStream fileInputStream = new FileInputStream("data/even.txt")) {
            int read;
            StringBuilder text = new StringBuilder();
            while ((read = fileInputStream.read()) != -1) {
                text.append((char) read);
            }
            printNumbersOddity(text.toString().split(System.lineSeparator()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}