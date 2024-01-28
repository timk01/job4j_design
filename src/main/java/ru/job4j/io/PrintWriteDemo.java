package ru.job4j.io;

import java.io.PrintWriter;

class PrintWriteDemo {

    public static void main(String[] args) {
        PrintWriter pw = new PrintWriter(System.out, true);
        pw.println("Это строка");
        int i = -7;

        /**
         * used as alternative to sout
         */

        pw.println(i);
        double d = 4.5 - 7;
        pw.println(d);
    }
}