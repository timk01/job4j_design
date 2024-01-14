package ru.job4j.iterator;

import org.junit.jupiter.api.Test;
import ru.job4j.iterator.ArrayIt;

import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.*;

class ArrayItTest {

    @Test
    void whenMultiCallHasNextThenTrue() {
        ArrayIt iterator = new ArrayIt(
                new int[] {1, 2, 3}
        );
        boolean result = iterator.hasNext();
        assertThat(result).isTrue();
        assertThat(iterator.hasNext()).isTrue();
    }

    @Test
    public void whenNextFromEmpty() {
        ArrayIt iterator = new ArrayIt(
                new int[] {}
        );
        assertThatThrownBy(iterator::next).isInstanceOf(NoSuchElementException.class);
    }

    @Test
    void whenReadSequence() {
        ArrayIt iterator = new ArrayIt(
                new int[] {1, 2, 3}
        );
        assertThat(iterator.next()).isEqualTo(1);
        assertThat(iterator.next()).isEqualTo(2);
        assertThat(iterator.next()).isEqualTo(3);
    }
}