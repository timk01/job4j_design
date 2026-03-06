package ru.job4j.kiss;

import java.util.Scanner;

import static ru.job4j.kiss.FizzBuzzLogic.convert;

public class Fool {

    public static void main(String[] args) {
        fizzBuzz();
    }

    public static void fizzBuzz() {
        System.out.println("Игра FizzBuzz.");
        var startAt = 1;
        var input = new Scanner(System.in);
        while (startAt < 100) {
            System.out.println(convert(startAt));
            startAt++;
            startAt = nextNumberAfterPlayer(startAt, input.nextLine());
        }
    }

    public static int nextNumberAfterPlayer(int currentNumber, String playerInput) {
        if (!FizzBuzzLogic.convert(currentNumber).equals(playerInput)) {
            System.out.println("Ошибка. Начинай снова.");
            return 1;
        }
        return currentNumber + 1;
    }
}