package ru.algos.codewars.eigth_kyu.arraynumbinversion;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Tests")
class KataTests {
    private void runTest(int[] expected, int[] input) {
        assertArrayEquals(expected, Kata.invert(input), () -> String.format("Input: %s", Arrays.toString(input)));
    }

    @Test
    @DisplayName("Sample Tests")
    void sampleTests() {
        runTest(new int[]{-1, -2, -3, -4, -5}, new int[]{1, 2, 3, 4, 5});
        runTest(new int[]{-1, 2, -3, 4, -5}, new int[]{1, -2, 3, -4, 5});
        runTest(new int[]{1, 2, 3, 4, 5}, new int[]{-1, -2, -3, -4, -5});
        runTest(new int[]{}, new int[]{});
        runTest(new int[]{0}, new int[]{0});
    }
}