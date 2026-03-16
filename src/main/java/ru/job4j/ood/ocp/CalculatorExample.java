package ru.job4j.ood.ocp;

import java.util.function.BiFunction;
import java.util.function.Function;

public class CalculatorExample {

    private static class SimpleCalculator {
        public int sum(int a, int b) {
            return a + b;
        }
    }

    private static class AbstractCalculator<T> {
        public T calculate(BiFunction<T, T, T> function, T first, T second) {
            return function.apply(first, second);
        }
    }

    private static class AbstractCalculator2<T, R> {
        public R calculate(Function<T, R> function, T value) {
            return function.apply(value);
        }
    }

    public static void main(String[] args) {
        CalculatorExample.SimpleCalculator simpleCalculator = new CalculatorExample.SimpleCalculator();
        CalculatorExample.AbstractCalculator<Integer> abstractCalculator =
                new CalculatorExample.AbstractCalculator<>();

        Integer result1 = abstractCalculator.calculate((a, b) -> a + b, 10, 20);
        Integer result2 = abstractCalculator.calculate(simpleCalculator::sum, 5, 7);

        System.out.println(result1);
        System.out.println(result2);

        Integer result3 = abstractCalculator.calculate((a, b) -> a * b, 10, 20);

        CalculatorExample.AbstractCalculator2<String, String> upperCaseCalculator =
                new CalculatorExample.AbstractCalculator2<>();

        System.out.println(upperCaseCalculator.calculate((text) -> text.toUpperCase(), "value"));
    }
}