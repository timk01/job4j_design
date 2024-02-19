package ru.job4j.io.scanner;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * A as delimeter has no value here, i mean it doesn't cplit anything
 */

public class ScannerExample4 {
    public static void main(String[] args) throws FileNotFoundException {
        String expectedValue = "Hello world";
        FileInputStream inputStream
                = new FileInputStream("data/test.txt");

        Scanner scanner = new Scanner(inputStream);
        scanner.useDelimiter("A");

        String result = scanner.next();
        System.out.println(result);
        if (scanner.hasNext()) {
            System.out.println(scanner.next());
        }
        System.out.println(result.equals(expectedValue));

        scanner.close();
    }
}
