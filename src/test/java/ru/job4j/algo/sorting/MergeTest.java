package ru.job4j.algo.sorting;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class MergeTest {

    @ParameterizedTest
    @MethodSource("sortArgs")
    void checkSortedAlgo(int[] input, int[] expected) {
        assertThat(Merge.mergesort(input)).containsExactly(expected);
    }

    private static Stream<Arguments> sortArgs() {
        return Stream.of(
                Arguments.of(
                        new int[]{},
                        new int[]{}
                ),
                Arguments.of(
                        new int[]{5},
                        new int[]{5}
                ),
                Arguments.of(
                        new int[]{2, 1},
                        new int[]{1, 2}
                ),
                Arguments.of(
                        new int[]{1, 2, 3, 4},
                        new int[]{1, 2, 3, 4}
                ),
                Arguments.of(
                        new int[]{4, 3, 2, 1},
                        new int[]{1, 2, 3, 4}
                ),
                Arguments.of(
                        new int[]{10, 4, 6, 4, 8, -13, 2, 3},
                        new int[]{-13, 2, 3, 4, 4, 6, 8, 10}
                ),
                Arguments.of(
                        new int[]{7, 7, 7, 7},
                        new int[]{7, 7, 7, 7}
                ),
                Arguments.of(
                        new int[]{3, -1, 0, -5, 8, 2},
                        new int[]{-5, -1, 0, 2, 3, 8}
                ),
                Arguments.of(
                        new int[]{9, 1, 8, 2, 7},
                        new int[]{1, 2, 7, 8, 9}
                ),
                Arguments.of(
                        new int[]{12, 5, -3, 19, 0, 7, 7, -10, 4, 2, 15},
                        new int[]{-10, -3, 0, 2, 4, 5, 7, 7, 12, 15, 19}
                )
        );
    }
}