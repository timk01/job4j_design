package ru.job4j.io;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.PrintWriter;

public class ResultFile {
    public static void main(String[] args) {
        try (PrintWriter output = new PrintWriter(
                new BufferedOutputStream(
                        new FileOutputStream("data/result.txt")
                ))) {
            output.println("Hello, world!");
            output.printf("%s%n", "Some string");
            output.printf("%d%n", 10);
            output.printf("%f%n", 1.5f);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}