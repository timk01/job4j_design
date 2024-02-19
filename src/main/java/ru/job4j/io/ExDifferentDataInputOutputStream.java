package ru.job4j.io;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * surprizingly (or not) it will be "end of file"
 * since double is 8 bytes, int is 4
 * compare with writing double, reading int - here we have corrupted data
 */

public class ExDifferentDataInputOutputStream {
    public static void main(String[] args) throws IOException {
        Path path = Path.of("123.txt");
        Files.createFile(path);
        try (DataInputStream in = new DataInputStream(new FileInputStream(String.valueOf(path)));
             DataOutputStream out = new DataOutputStream(new FileOutputStream(String.valueOf(path)))) {
            out.writeDouble(3.0);
            int i = in.readInt();
            System.out.println(i);
        } catch (EOFException e) {
            System.out.println("end of file");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
