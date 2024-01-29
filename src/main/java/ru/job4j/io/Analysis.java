package ru.job4j.io;

import java.io.*;

public class Analysis {
    public void unavailable(String source, String target) {
        boolean status = true;
        try (BufferedReader bufferedReader =
                     new BufferedReader(new FileReader(source));
             PrintWriter printWriter =
                     new PrintWriter(
                             new BufferedOutputStream(
                                     new FileOutputStream(target)))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] split = line.split(" ");
                if (status && (line.startsWith("400") || line.startsWith("500"))) {
                    printWriter.printf("%s%s", split[1], ";");
                    status = false;
                } else if (!status && (line.startsWith("200") || line.startsWith("300"))) {
                    printWriter.printf("%s%s", split[1], System.lineSeparator());
                    status = true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Analysis analysis = new Analysis();
        analysis.unavailable("data/server2.log", "data/target2.csv");
    }
}