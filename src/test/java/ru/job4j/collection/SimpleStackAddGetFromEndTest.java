package ru.job4j.collection;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.*;

class SimpleStackAddGetFromEndTest {

    private SimpleStackAddGetFromEnd<Integer> stack;

    @BeforeEach
    void init() {
        stack = new SimpleStackAddGetFromEnd<>();
        stack.addToEnd(1);
        stack.addToEnd(2);
    }

    @Test
    void whenAddSeveralThenRetrieve() {
        stack.addToEnd(3);
        stack.addToEnd(4);
        assertThat(stack.getLastElem()).isEqualTo(4);
    }


    @Test
    void whenAddAddThenRetrieveAndPush() {
        stack.getLastElem();
        stack.addToEnd(3);
        assertThat(stack.getLastElem()).isEqualTo(3);
    }


    @Test
    void whenAddAddThenDoubleRetrieve() {
        assertThat(stack.getLastElem()).isEqualTo(2);
        assertThat(stack.getLastElem()).isEqualTo(1);
        assertThatThrownBy(stack::getLastElem)
                .isInstanceOf(NoSuchElementException.class);
    }

}