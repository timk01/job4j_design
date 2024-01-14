package ru.job4j.iterator;

import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.*;

class MatrixIteratorTest  {

    @Test
    void when4El() {
        int[][] input = {
                {1}
        };
        MatrixIterator iterator = new MatrixIterator(input);
        assertThat(iterator.next()).isEqualTo(1);
    }

    @Test
    void whenFirstEmptyThenNext() {
        int[][] input = {
                {}, {1}
        };
        MatrixIterator iterator = new MatrixIterator(input);
        assertThat(iterator.next()).isEqualTo(1);
    }

    @Test
    void whenFirstEmptyThenHasNext() {
        int[][] input = {
                {}, {1}
        };
        MatrixIterator iterator = new MatrixIterator(input);
        assertThat(iterator.hasNext()).isTrue();
    }

    @Test
    void whenRowHasDiffSize() {
        int[][] input = {
                {1}, {2, 3}, {}, {}, {4}
        };
        MatrixIterator iterator = new MatrixIterator(input);
        assertThat(iterator.next()).isEqualTo(1);
        assertThat(iterator.next()).isEqualTo(2);
        assertThat(iterator.next()).isEqualTo(3);
        assertThat(iterator.next()).isEqualTo(4);
        assertThat(iterator.hasNext()).isFalse();
    }

    @Test
    void whenRowHasDiffSize2() {
        int[][] input = {
                {1}, {2, }, {}, {}, {4}, {6, 7, 8}
        };
        MatrixIterator iterator = new MatrixIterator(input);
        assertThat(iterator.next()).isEqualTo(1);
        assertThat(iterator.next()).isEqualTo(2);
        assertThat(iterator.next()).isEqualTo(4);
        assertThat(iterator.next()).isEqualTo(6);
        assertThat(iterator.next()).isEqualTo(7);
        assertThat(iterator.next()).isEqualTo(8);
        assertThat(iterator.hasNext()).isFalse();
    }

    @Test
    void whenRowHasDiffSize3() {
        int[][] input = {
                {1}, {2, 7, 9}, {}, {}, {4}, {6, }
        };
        MatrixIterator iterator = new MatrixIterator(input);
        assertThat(iterator.next()).isEqualTo(1);
        assertThat(iterator.next()).isEqualTo(2);
        assertThat(iterator.next()).isEqualTo(7);
        assertThat(iterator.next()).isEqualTo(9);
        assertThat(iterator.next()).isEqualTo(4);
        assertThat(iterator.next()).isEqualTo(6);
        assertThat(iterator.hasNext()).isFalse();
    }

    @Test
    void whenFewEmpty() {
        int[][] input = {
                {1}, {}, {}, {}, {2}
        };
        MatrixIterator iterator = new MatrixIterator(input);
        assertThat(iterator.next()).isEqualTo(1);
        assertThat(iterator.next()).isEqualTo(2);
        assertThat(iterator.hasNext()).isFalse();
    }

    @Test
    void whenEmpty() {
        int[][] input = {
                {}
        };
        MatrixIterator iterator = new MatrixIterator(input);
        assertThat(iterator.hasNext()).isFalse();
    }

    @Test
    void whenEmptyThenNext() {
        int[][] input = {
                {}
        };
        MatrixIterator iterator = new MatrixIterator(input);
        assertThatThrownBy(iterator::next)
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    void whenMultiHasNext() {
        int[][] input = {
                {}, {1}
        };
        MatrixIterator iterator = new MatrixIterator(input);
        assertThat(iterator.hasNext()).isTrue();
        assertThat(iterator.hasNext()).isTrue();
    }

    @Test
    void whenNoElements() {
        int[][] input = {
                {}, {}, {}
        };
        MatrixIterator iterator = new MatrixIterator(input);
        assertThat(iterator.hasNext()).isFalse();
    }
}