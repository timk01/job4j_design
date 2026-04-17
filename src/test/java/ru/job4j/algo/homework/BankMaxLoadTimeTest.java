package ru.job4j.algo.homework;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

class BankMaxLoadTimeTest {
    @Test
    void whenSimpleCaseThenCorrectMaxLoadTime() {
        List<int[]> visitTimes = Arrays.asList(
                new int[]{1, 5},
                new int[]{2, 6},
                new int[]{3, 8},
                new int[]{4, 7}
        );

        int[] result = BankMaxLoadTime.findMaxLoadTime(visitTimes);
        assertThat(result).containsExactly(4, 5);
    }

    @Test
    void whenOverlapCaseThenCorrectMaxLoadTime() {
        List<int[]> visitTimes = Arrays.asList(
                new int[]{1, 10},
                new int[]{2, 6},
                new int[]{5, 8},
                new int[]{7, 12}
        );

        int[] result = BankMaxLoadTime.findMaxLoadTime(visitTimes);
        assertThat(result).containsExactly(5, 6);
    }

    @Test
    void whenNoOverlapCaseThenCorrectMaxLoadTime() {
        List<int[]> visitTimes = Arrays.asList(
                new int[]{1, 2},
                new int[]{3, 4},
                new int[]{5, 6}
        );

        int[] result = BankMaxLoadTime.findMaxLoadTime(visitTimes);
        assertThat(result).containsExactly(1, 2);
    }

    @Test
    void whenComplexCaseThenCorrectMaxLoadTime() {
        List<int[]> visitTimes = Arrays.asList(
                new int[]{1, 3},
                new int[]{2, 4},
                new int[]{3, 5},
                new int[]{4, 6},
                new int[]{5, 7},
                new int[]{6, 8}
        );

        int[] result = BankMaxLoadTime.findMaxLoadTime(visitTimes);
        assertThat(result).containsExactly(3, 3);
    }
}