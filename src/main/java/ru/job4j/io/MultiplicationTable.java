package ru.job4j.io;

import java.util.stream.IntStream;

public class MultiplicationTable {
    public static void main(String[] args) {
        IntStream.range(1, 10)
                .forEach(j ->
                        IntStream.range(1, 10).forEach(
                        i -> {
                            String s = System.lineSeparator();
                            System.out.printf("%d * %d = %d%s", j, i, j * i, s);
                            if (i == 9) {
                                System.out.println();
                            }
                        }));
    }
}
