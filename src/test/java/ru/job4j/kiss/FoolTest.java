package ru.job4j.kiss;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.*;

class FoolTest {

    @ParameterizedTest
    @MethodSource("badMoves")
    void whenPlayerInputWrongThenResetToOne(int playerNumber, String input) {
        assertThat(Fool.nextNumberAfterPlayer(playerNumber, input)).isEqualTo(1);
    }

    private static Stream<Arguments> badMoves() {
        return Stream.of(
                Arguments.of(2, "Fizz"),
                Arguments.of(3, "3"),
                Arguments.of(5, "FizzBuzz"),
                Arguments.of(15, "Fizz")
        );
    }
}