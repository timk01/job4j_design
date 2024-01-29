package ru.job4j.io;

import java.io.*;
import java.nio.CharBuffer;

public class ByteArrayStream {

    public static void main(String[] args) {
        byte[] bytes = new byte[]{'J', 'a', 'v', 'a', (byte) '\u25B6'};
        ByteArrayInputStream stream = new ByteArrayInputStream(bytes);
        int data;
        while ((data = stream.read()) != -1) {
            System.out.print((char) data);
        }

        System.out.println();
        String str = "123456789";
        byte[] bytes1 = str.getBytes();
        ByteArrayInputStream byteStream2 = new ByteArrayInputStream(bytes1, 3, 4);
        int data1;
        while ((data1 = byteStream2.read()) != -1) {
            System.out.print((char) data1);
        }

        System.out.println();
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] bytes2 = "Message\u25B6".getBytes();
        outStream.writeBytes(bytes2);

        System.out.println(outStream);
        try (FileOutputStream fileOutput = new FileOutputStream("data/message.txt")) {
            outStream.writeTo(fileOutput);
        } catch (IOException e) {
            e.printStackTrace();
        }

        StringBuilder stringBuilder = new StringBuilder();
        char[] chars
                = new char[]{'\u25B6', '\u65e5'};
        try (CharArrayReader reader = new CharArrayReader(chars);
             FileOutputStream fileOutput = new FileOutputStream("data/message2.txt")) {
            fileOutput.write(reader.read());
            System.out.println(stringBuilder);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}