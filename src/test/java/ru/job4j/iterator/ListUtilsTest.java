package ru.job4j.iterator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static ru.job4j.iterator.ListUtils.*;

class ListUtilsTest {

    private List<Integer> input;
    private List<Integer> deletedFrom;
    private List<Integer> deletedBy;

    @BeforeEach
    void setUp() {
        input = new ArrayList<>(Arrays.asList(1, 3));
        deletedFrom = new ArrayList<>(Arrays.asList(1, 3, 3, 9, 5, 10, 12));
        deletedBy = new ArrayList<>(Arrays.asList(3, 5));
    }

    @Test
    void whenAddBefore() {
        ListUtils.addBefore(input, 1, 2);
        assertThat(input).hasSize(3).containsSequence(1, 2, 3);
    }

    @Test
    void whenAddBeforeWithInvalidIndex() {
        assertThatThrownBy(() -> ListUtils.addBefore(input, 2, 2))
                .isInstanceOf(IndexOutOfBoundsException.class);
    }

    @Test
    void whenAddAfter() {
        ListUtils.addAfter(input, 0, 2);
        assertThat(input).hasSize(3).containsSequence(1, 2, 3);
    }

    @Test
    void whenAddAfterWithEndIndexOK() {
        ListUtils.addAfter(input, 1, 2);
        assertThat(input).hasSize(3).containsSequence(1, 3, 2);
    }

    @Test
    void whenAddAfterWithInvalidIndex() {
        assertThatThrownBy(() -> ListUtils.addAfter(input, 2, 2))
                .isInstanceOf(IndexOutOfBoundsException.class);
    }

    @Test
    void removeIfWithEmptyListOK() {
        input = new ArrayList<>();
        assertThat(input).isEmpty();
        removeIf(input, x -> x >= 2);
        assertThat(input).isEmpty();
    }

    @Test
    void removeIfWithFilledListOK() {
        removeIf(input, x -> x >= 2);
        assertThat(input).containsExactly(1);
    }

    @Test
    void removeIfWithFilledListWithCorrectBooleanOK() {
        removeIf(input, x -> true);
        assertThat(input).isEmpty();
    }

    @Test
    void removeIfWithFilledListWithFalseBooleanOK() {
        removeIf(input, x -> x >= 5);
        assertThat(input).containsExactly(1, 3);
    }

    @Test
    void replaceIfWithEmptyListOK() {
        input = new ArrayList<>();
        assertThat(input).isEmpty();
        replaceIf(input, x -> x >= 2, 5);
        assertThat(input).isEmpty();
    }

    @Test
    void replaceIfWithFilledListOK() {
        replaceIf(input, x -> x >= 2, 5);
        assertThat(input).containsExactly(1, 5);
    }

    @Test
    void replaceIfWithFilledListWithBooleanOK() {
        replaceIf(input, x -> true, 5);
        assertThat(input).containsSequence(5, 5);
    }

    @Test
    void replaceIfWithFilledListWithFalseBooleanOK() {
        removeIf(input, x -> x >= 5);
        assertThat(input).containsExactly(1, 3);
    }

    @Test
    void removeAllEmptyParamsReturnsEmpty() {
        deletedFrom = new ArrayList<>();
        deletedBy = new ArrayList<>();
        removeAll(deletedFrom, deletedBy);
        assertThat(deletedFrom).isEmpty();
    }

    @Test
    void removeAllEmptyInitCollectionParamsReturnsEmpty() {
        deletedFrom = new ArrayList<>();
        removeAll(deletedFrom, deletedBy);
        assertThat(deletedFrom).isEmpty();
    }

    @Test
    void removeAllOK() {
        removeAll(deletedFrom, deletedBy);
        assertThat(deletedFrom).containsSequence(1, 9, 10, 12);
    }

    @Test
    void removeAllFilledInitWithZeroCollectionRetainOriginalOne() {
        deletedBy = new ArrayList<>();
        removeAll(deletedFrom, deletedBy);
        assertThat(deletedFrom).containsExactly(1, 3, 3, 9, 5, 10, 12);
    }
}