package ru.job4j.list;

import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

class LLNodeTestingTest {
    @Test
    void whenAddingTwoElementsIsOK() {
        List<String> linked = new LinkedList<>();
        String first = "123";
        String second = "345";
        String third = "567";
        LLNodeTesting llNodeTesting = new LLNodeTesting();
        String[] arr = new String[]{first, second, third};
        llNodeTesting.addingElems(arr);
    }
}