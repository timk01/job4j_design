package ru.job4j.io;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class DataInputStreamReadStringEx {
    public static String readString(DataInputStream in) throws IOException {
        short length = in.readShort();
        if (length == 0) {
            return "";
        }

        byte[] bytes = new byte[length];
        System.out.println(length);

        System.out.println(in.available());

        in.readFully(bytes);

        return new String(bytes, "UTF-8");
    }

    public static void main(String[] args) throws IOException {
        String s = readString(new DataInputStream(new FileInputStream("data/dataOutput.txt")));
        System.out.println(s);
    }
}
