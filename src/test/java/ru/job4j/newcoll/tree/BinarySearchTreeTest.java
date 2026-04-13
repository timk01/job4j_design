package ru.job4j.newcoll.tree;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class BinarySearchTreeTest {
    @Test
    void whenAddToEmptyTreeThenListContainsOneElement() {
        BinarySearchTree<String> tree = new BinarySearchTree<>();
        assertThat(tree.put("first")).isTrue();
        assertThat(tree.inSymmetricalOrder()).hasSize(1)
                .containsExactly("first");
    }

    @Test
    void whenAddTwoToEmptyTreeThenListContainsTwoElement() {
        BinarySearchTree<String> tree = new BinarySearchTree<>();
        assertThat(tree.put("first")).isTrue();
        assertThat(tree.put("second")).isTrue();
        assertThat(tree.inSymmetricalOrder()).hasSize(2)
                .containsExactly("first", "second");
    }

    @Test
    void whenAddElementThenContainElementOk() {
        BinarySearchTree<String> tree = new BinarySearchTree<>();
        tree.put("first");
        tree.put("second");
        tree.put("three");
        assertThat(tree.contains("second")).isTrue();
        assertThat(tree.contains("four")).isFalse();
    }

    @Test
    void whenAddMaximumNotEndThenOk() {
        BinarySearchTree<Integer> tree = new BinarySearchTree<>();
        for (int element : new int[]{4, 2, 6, 1, 3, 5, 8, 7}) {
            tree.put(element);
        }
        assertThat(tree.maximum()).isEqualTo(8);
    }

    @Test
    void whenAddMaximumIsEndThenOk() {
        BinarySearchTree<Integer> tree = new BinarySearchTree<>();
        for (int element : new int[]{4, 2, 6, 1, 3, 5, 7}) {
            tree.put(element);
        }
        assertThat(tree.maximum()).isEqualTo(7);
    }

    @Test
    void whenAddMinimumIsEndThenOk() {
        BinarySearchTree<Integer> tree = new BinarySearchTree<>();
        for (int element : new int[]{4, 2, 6, 3, 5, 7, 1}) {
            tree.put(element);
        }
        assertThat(tree.minimum()).isEqualTo(1);
    }

    @Test
    void whenAddMinimumIsNotEndThenOk() {
        BinarySearchTree<Integer> tree = new BinarySearchTree<>();
        for (int element : new int[]{4, 2, 6, 3, 5, 7}) {
            tree.put(element);
        }
        assertThat(tree.minimum()).isEqualTo(2);
    }

    @Test
    void whenSymmetricalOrderThenOk() {
        BinarySearchTree<Integer> tree = new BinarySearchTree<>();
        for (int element : new int[]{4, 2, 6, 3, 5, 7, 1}) {
            tree.put(element);
        }
        assertThat(tree.inSymmetricalOrder()).hasSize(7)
                .containsExactly(1, 2, 3, 4, 5, 6, 7);
    }

    @Test
    void whenPreOrderThenOk() {
        BinarySearchTree<Integer> tree = new BinarySearchTree<>();
        for (int element : new int[]{4, 2, 6, 3, 5, 7, 1}) {
            tree.put(element);
        }
        assertThat(tree.inPreOrder()).hasSize(7)
                .containsExactly(4, 2, 1, 3, 6, 5, 7);
    }

    @Test
    void whenPostOrderThenOk() {
        BinarySearchTree<Integer> tree = new BinarySearchTree<>();
        for (int element : new int[]{4, 2, 6, 3, 5, 7, 1}) {
            tree.put(element);
        }
        assertThat(tree.inPostOrder()).hasSize(7)
                .containsExactly(1, 3, 2, 5, 7, 6, 4);
    }

    @ParameterizedTest
    @MethodSource("treeRemovalBasicCases")
    void whenRemoveCommonCases(Integer key, boolean expectedResult, List<Integer> expectedOrder) {
        BinarySearchTree<Integer> tree = new BinarySearchTree<>();
        for (int element : new int[]{4, 2, 6, 3, 5, 7, 1, 0, 8}) {
            tree.put(element);
        }

        assertThat(tree.remove(key)).isEqualTo(expectedResult);
        assertThat(tree.inSymmetricalOrder()).isEqualTo(expectedOrder);

        if (expectedResult) {
            assertThat(tree.contains(key)).isFalse();
        }
    }

    static Stream<Arguments> treeRemovalBasicCases() {
        return Stream.of(
                Arguments.of(10, false, List.of(0, 1, 2, 3, 4, 5, 6, 7, 8)),
                Arguments.of(0, true, List.of(1, 2, 3, 4, 5, 6, 7, 8)),
                Arguments.of(1, true, List.of(0, 2, 3, 4, 5, 6, 7, 8)),
                Arguments.of(7, true, List.of(0, 1, 2, 3, 4, 5, 6, 8)),
                Arguments.of(2, true, List.of(0, 1, 3, 4, 5, 6, 7, 8))
        );
    }

    @Test
    void whenNoRootThenRootRemovalIsFalse() {
        BinarySearchTree<Integer> tree = new BinarySearchTree<>();
        assertThat(tree.remove(4)).isEqualTo(false);
    }

    @Test
    void whenRootIsSoloThenRootRemovalIsTrue() {
        BinarySearchTree<Integer> tree = new BinarySearchTree<>();
        for (int element : new int[]{4}) {
            tree.put(element);
        }
        assertThat(tree.remove(4)).isTrue();
        assertThat(tree.inSymmetricalOrder()).isEmpty();
        assertThat(tree.contains(4)).isFalse();
    }

    @Test
    void whenRootHasChildrenThenRemovalTrue() {
        BinarySearchTree<Integer> tree = new BinarySearchTree<>();
        for (int element : new int[]{4, 2, 6, 3, 5, 7, 1, 0, 8}) {
            tree.put(element);
        }
        assertThat(tree.remove(4)).isTrue();
        assertThat(tree.contains(4)).isFalse();
        assertThat(tree.inSymmetricalOrder()).isEqualTo(List.of(0, 1, 2, 3, 5, 6, 7, 8));
    }
}