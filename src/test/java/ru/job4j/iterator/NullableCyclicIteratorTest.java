package ru.job4j.iterator;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.*;

class NullableCyclicIteratorTest {

    @Test
    void whenEmptyThenHasNextIsFalse() {
        NullableCyclicIterator<Integer> iterator = new NullableCyclicIterator<>(List.of());
        assertThat(iterator.hasNext()).isFalse();
    }

    @Test
    void whenEmptyAndNextThenThrow() {
        NullableCyclicIterator<Integer> iterator = new NullableCyclicIterator<>(List.of());
        assertThatThrownBy(iterator::next)
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    void whenNotEmptyThenHasNextIsTrue() {
        NullableCyclicIterator<Integer> iterator = new NullableCyclicIterator<>(List.of(1));
        assertThat(iterator.hasNext()).isTrue();
    }

    @Test
    void whenNotEmptyThenSomeHasNextIsTrue() {
        NullableCyclicIterator<Integer> iterator = new NullableCyclicIterator<>(List.of(1));
        iterator.hasNext();
        assertThat(iterator.hasNext()).isTrue();
    }

    @Test
    void whenOneElementThenNext() {
        NullableCyclicIterator<Integer> iterator = new NullableCyclicIterator<>(List.of(1));
        assertThat(iterator.hasNext()).isTrue();
        assertThat(iterator.next()).isEqualTo(1);
        assertThat(iterator.hasNext()).isTrue();
        assertThat(iterator.next()).isEqualTo(1);
        assertThat(iterator.hasNext()).isTrue();
        assertThat(iterator.next()).isEqualTo(1);
    }

    @Test
    void whenNotEmptyThenNext() {
        NullableCyclicIterator<Integer> iterator = new NullableCyclicIterator<>(List.of(1, 2, 3));
        assertThat(iterator.next()).isEqualTo(1);
        assertThat(iterator.hasNext()).isTrue();
        assertThat(iterator.next()).isEqualTo(2);
        assertThat(iterator.hasNext()).isTrue();
        assertThat(iterator.next()).isEqualTo(3);
        assertThat(iterator.hasNext()).isTrue();
        assertThat(iterator.next()).isEqualTo(1);
        assertThat(iterator.hasNext()).isTrue();
        assertThat(iterator.next()).isEqualTo(2);
        assertThat(iterator.hasNext()).isTrue();
        assertThat(iterator.next()).isEqualTo(3);
        assertThat(iterator.hasNext()).isTrue();
        assertThat(iterator.next()).isEqualTo(1);
        assertThat(iterator.hasNext()).isTrue();
    }

    @Test
    void whenListContainsNullElements() {
        NullableCyclicIterator<Integer> iterator = new NullableCyclicIterator<Integer>(Arrays.asList(1, null, 2, null, null, 3));
        assertThat(iterator.next()).isEqualTo(1);
        assertThat(iterator.hasNext()).isTrue();
        assertThat(iterator.next()).isEqualTo(null);
        assertThat(iterator.hasNext()).isTrue();
        assertThat(iterator.next()).isEqualTo(2);
        assertThat(iterator.hasNext()).isTrue();
        assertThat(iterator.next()).isEqualTo(null);
        assertThat(iterator.hasNext()).isTrue();
        assertThat(iterator.next()).isEqualTo(null);
        assertThat(iterator.hasNext()).isTrue();
        assertThat(iterator.next()).isEqualTo(3);
        assertThat(iterator.hasNext()).isTrue();
        assertThat(iterator.next()).isEqualTo(1);
        assertThat(iterator.hasNext()).isTrue();
    }
}