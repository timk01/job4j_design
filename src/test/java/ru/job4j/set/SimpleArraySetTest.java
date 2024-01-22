package ru.job4j.set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.job4j.list.SimpleArrayList;
import ru.job4j.list.SimpleList;

import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.*;

class SimpleArraySetTest {

    private SimpleSet<Integer> set;

    @BeforeEach
    void initData() {
        set = new SimpleArraySet<>();
    }

    @Test
    void whenAddNonNull() {
        assertThat(set.add(1)).isTrue();
        assertThat(set.contains(1)).isTrue();
        assertThat(set.add(1)).isFalse();
    }

    @Test
    void whenAddSomeElementsNonNull() {
        assertThat(set.contains(1)).isFalse();
        assertThat(set.add(1)).isTrue();
        assertThat(set.contains(1)).isTrue();
        assertThat(set.add(1)).isFalse();
        assertThat(set.contains(2)).isFalse();
        assertThat(set.add(2)).isTrue();
        assertThat(set.contains(2)).isTrue();
        assertThat(set.add(2)).isFalse();
        assertThat(set.contains(3)).isFalse();
        assertThat(set.add(3)).isTrue();
        assertThat(set.contains(3)).isTrue();
        assertThat(set.add(3)).isFalse();
    }

    @Test
    void whenAddNull() {
        assertThat(set.add(null)).isTrue();
        assertThat(set.contains(null)).isTrue();
        assertThat(set.add(null)).isFalse();
    }

    @Test
    void nullElementIsFound() {
        set.add(null);
        assertThat(set.contains(null)).isTrue();
    }

    @Test
    void nothingIsFoundInEmptySet() {
        assertThat(set.contains(null)).isFalse();
        assertThat(set.contains(1)).isFalse();
    }

    @Test
    void valueIsFound() {
        set.add(1);
        assertThat(set.contains(1)).isTrue();
        set.add(1);
        assertThat(set.contains(1)).isTrue();
        set.add(2);
        assertThat(set.contains(2)).isTrue();
    }

    @Test
    void whenGetIteratorFromEmptySetThenHasNextReturnFalse() {
        Iterator<Integer> iterator = set.iterator();
        assertThat(iterator).isNotNull();
        assertThat(iterator.hasNext()).isFalse();
    }

    @Test
    void whenGetIteratorFromEmptySetThenNextThrowException() {
        Iterator<Integer> iterator = set.iterator();
        assertThat(iterator).isNotNull();
        assertThatThrownBy(iterator::next)
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    void whenGetIteratorTwiceThenStartAlwaysFromBeginning() {
        set.add(1);
        set.add(1);
        Iterator<Integer> iterator = set.iterator();
        assertThat(iterator.next()).isEqualTo(1);
        assertThatThrownBy(iterator::next)
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    void whenCheckIterator() {
        set.add(1);
        Iterator<Integer> iterator = set.iterator();
        assertThat(iterator.hasNext()).isTrue();
        assertThat(iterator.next()).isEqualTo(1);
        assertThat(iterator.hasNext()).isFalse();
        assertThatThrownBy(iterator::next)
                .isInstanceOf(NoSuchElementException.class);
    }

}