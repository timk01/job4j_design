package ru.job4j.map;

public class BitRepresentation {
    public static void main(String[] args) {
        System.out.println(binary(30));
    }

    public static String binary(int number) {
        StringBuilder buffer = new StringBuilder();
        for (int i = 0; i < 32; i++) {
            buffer.append(number % 2 == 0 ? 0 : 1);
            buffer.append((i + 1) % 8 == 0 ? " " : "");
            number /= 2;
        }
        return buffer.reverse().toString();
    }
}
