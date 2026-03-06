package ru.job4j.kiss;

import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import static org.assertj.core.api.Assertions.assertThat;

class FizzBuzzLogicTest {

    @ParameterizedTest
    @MethodSource("fizzBuzzTestData")
    void whenVariousInputsThenCorrectString(int input, String answer) {
        assertThat(FizzBuzzLogic.convert(input)).isEqualTo(answer);
    }

    private static Stream<Arguments> fizzBuzzTestData() {
        return Stream.of(
                Arguments.of(1, "1"),
                Arguments.of(2, "2"),
                Arguments.of(3, "Fizz"),
                Arguments.of(5, "Buzz"),
                Arguments.of(15, "FizzBuzz"),
                Arguments.of(30, "FizzBuzz"),
                Arguments.of(6, "Fizz"),
                Arguments.of(10, "Buzz")
        );
    }
}