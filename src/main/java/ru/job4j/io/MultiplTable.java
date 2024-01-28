package ru.job4j.io;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.stream.IntStream;

public class MultiplTable {
    public static void main(String[] args) {
        writeTableToFile();
    }

    private static void writeTableToFile() {
        try (FileOutputStream output = new FileOutputStream("data/multi_table.txt")) {
            IntStream.rangeClosed(1, 10)
                    .forEach(j ->
                            IntStream.rangeClosed(1, 10).forEach(
                                    i -> {
                                        int res = i * j;
                                        String gap = res < 10 ? "  " : " ";
                                        String stringRes = Integer.toString(res);
                                        try {
                                            output.write(stringRes.getBytes());
                                            output.write(gap.getBytes());
                                            if (i == 10) {
                                                output.write(System.lineSeparator().getBytes());
                                            }
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    }));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
