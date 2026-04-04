package ru.job4j.algo.queueandstack;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class BracketsTest {

    private final Brackets brackets = new Brackets();

    @Test
    void givenValidBracketsWhenIsValidThenTrue() {
        assertThat(brackets.isValid("()")).isTrue();
        assertThat(brackets.isValid("()[]{}")).isTrue();
        assertThat(brackets.isValid("{[]}")).isTrue();
    }

    @Test
    void givenInvalidBracketsWhenIsValidThenFalse() {
        assertThat(brackets.isValid("(]")).isFalse();
        assertThat(brackets.isValid("([)]")).isFalse();
        assertThat(brackets.isValid("]")).isFalse();
        assertThat(brackets.isValid("{")).isFalse();
        assertThat(brackets.isValid("")).isTrue();
    }

    @Test
    void givenBracketsAreRepeatedThenFalse() {
        assertThat(brackets.isValid("]]")).isFalse();
        assertThat(brackets.isValid("]]]]")).isFalse();
        assertThat(brackets.isValid("()((")).isFalse();
        assertThat(brackets.isValid("{}{")).isFalse();
    }
}