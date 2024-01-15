package ru.job4j.iterator;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class NotNotNullableCyclicIteratorTest {

    @Test
    void whenEmptyThenHasNextIsFalse() {
        NotNullableCyclicIterator<Integer> iterator = new NotNullableCyclicIterator<>(List.of());
        assertThat(iterator.hasNext()).isFalse();
    }

    @Test
    void whenEmptyAndNextThenThrow() {
        NotNullableCyclicIterator<Integer> iterator = new NotNullableCyclicIterator<>(List.of());
        assertThatThrownBy(iterator::next)
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    void whenNotEmptyThenHasNextIsTrue() {
        NotNullableCyclicIterator<Integer> iterator = new NotNullableCyclicIterator<>(List.of(1));
        assertThat(iterator.hasNext()).isTrue();
    }

    @Test
    void whenNotEmptyThenSomeHasNextIsTrue() {
        NotNullableCyclicIterator<Integer> iterator = new NotNullableCyclicIterator<>(List.of(1));
        iterator.hasNext();
        assertThat(iterator.hasNext()).isTrue();
    }

    @Test
    void whenOneElementThenNext() {
        NotNullableCyclicIterator<Integer> iterator = new NotNullableCyclicIterator<>(List.of(1));
        assertThat(iterator.hasNext()).isTrue();
        assertThat(iterator.next()).isEqualTo(1);
        assertThat(iterator.hasNext()).isTrue();
        assertThat(iterator.next()).isEqualTo(1);
        assertThat(iterator.hasNext()).isTrue();
        assertThat(iterator.next()).isEqualTo(1);
    }

    @Test
    void whenNotEmptyThenNext() {
        NotNullableCyclicIterator<Integer> iterator = new NotNullableCyclicIterator<>(List.of(1, 2, 3));
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
        NotNullableCyclicIterator<Integer> iterator = new NotNullableCyclicIterator<Integer>(Arrays.asList(1, null));
        assertThat(iterator.next()).isEqualTo(1);
        assertThat(iterator.hasNext()).isFalse();
        assertThatThrownBy(iterator::next)
                .isInstanceOf(NoSuchElementException.class);
    }
}