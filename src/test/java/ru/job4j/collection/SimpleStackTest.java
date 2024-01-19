package ru.job4j.collection;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.*;

class SimpleStackTest {
    private SimpleStack<Integer> stack;

    @BeforeEach
    void init() {
        stack = new SimpleStack<>();
        stack.push(1);
        stack.push(2);
    }

    @Test
    void whenPushThenPoll() {
        stack.push(3);
        stack.push(4);
        assertThat(stack.pop()).isEqualTo(4);
    }

    @Test
    void whenAddSeveralThenRetrieve() {
        stack = new SimpleStack<>();
        stack.addToEnd(1);
        stack.addToEnd(2);
        stack.addToEnd(3);
        stack.addToEnd(4);
        assertThat(stack.getLastElem()).isEqualTo(4);
    }

    @Test
    void whenPushPushThenPollAndPush() {
        stack.pop();
        stack.push(3);
        assertThat(stack.pop()).isEqualTo(3);
    }

    @Test
    void whenAddAddThenRetrieveAndPush() {
        stack = new SimpleStack<>();
        stack.addToEnd(1);
        stack.addToEnd(2);
        stack.getLastElem();
        stack.addToEnd(3);
        assertThat(stack.getLastElem()).isEqualTo(3);
    }

    @Test
    void whenPushPushThenDoublePoll() {
        stack.pop();
        assertThat(stack.pop()).isEqualTo(1);
        assertThatThrownBy(stack::pop)
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    void whenAddAddThenDoubleRetrieve() {
        stack = new SimpleStack<>();
        stack.addToEnd(1);
        stack.addToEnd(2);
        assertThat(stack.getLastElem()).isEqualTo(2);
        assertThat(stack.getLastElem()).isEqualTo(1);
        assertThatThrownBy(stack::pop)
                .isInstanceOf(NoSuchElementException.class);
    }
}