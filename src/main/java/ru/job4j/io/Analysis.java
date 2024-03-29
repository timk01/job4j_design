package ru.job4j.io;

import java.io.*;

/**
 * it was like this (and honestly, it's more readable);
 * additionally it can be done via chars like char == '4' since responses are grouped by code at the ebginning
 *
 * if (status && (line.startsWith("400") || line.startsWith("500"))) {
 *                     printWriter.printf("%s%s", split[1], ";");
 *                     status = false;
 *                 } else if (!status && (line.startsWith("200") || line.startsWith("300"))) {
 *                     printWriter.printf("%s%s", split[1], System.lineSeparator());
 *                     status = true;
 *                 }
 **/

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
            String separator;

            while ((line = bufferedReader.readLine()) != null) {
                separator = status ? ";" : System.lineSeparator();
                if ((status && (line.startsWith("400") || line.startsWith("500"))
                        || (!status && (line.startsWith("200") || line.startsWith("300"))))) {
                    printWriter.printf("%s%s",  line.split(" ")[1], separator);
                    status = !status;
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