package ru.job4j.newcoll.binarytree;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class AvlTreeTest {

    @Test
    void whenAddToEmptyTreeThenListContainsOneElement() {
        AvlTree<String> tree = new AvlTree<>();
        assertThat(tree.insert("first")).isTrue();
        assertThat(tree.inSymmetricalOrder()).hasSize(1)
                .containsExactly("first");
    }

    @Test
    void whenAddTwoToEmptyTreeThenListContainsTwoElement() {
        AvlTree<String> tree = new AvlTree<>();
        assertThat(tree.insert("first")).isTrue();
        assertThat(tree.insert("second")).isTrue();
        assertThat(tree.inSymmetricalOrder()).hasSize(2)
                .containsExactly("first", "second");
    }

    @Test
    void whenAddElementThenContainElementOk() {
        AvlTree<String> tree = new AvlTree<>();
        tree.insert("first");
        tree.insert("second");
        tree.insert("three");
        assertThat(tree.contains("second")).isTrue();
        assertThat(tree.contains("four")).isFalse();
    }

    @Test
    void whenAddMaximumNotEndThenOk() {
        AvlTree<Integer> tree = new AvlTree<>();
        for (int element : new int[]{4, 2, 6, 1, 3, 5, 8, 7}) {
            tree.insert(element);
        }
        assertThat(tree.maximum()).isEqualTo(8);
    }

    @Test
    void whenAddMaximumIsEndThenOk() {
        AvlTree<Integer> tree = new AvlTree<>();
        for (int element : new int[]{4, 2, 6, 1, 3, 5, 7}) {
            tree.insert(element);
        }
        assertThat(tree.maximum()).isEqualTo(7);
    }

    @Test
    void whenAddMinimumIsEndThenOk() {
        AvlTree<Integer> tree = new AvlTree<>();
        for (int element : new int[]{4, 2, 6, 3, 5, 7, 1}) {
            tree.insert(element);
        }
        assertThat(tree.minimum()).isEqualTo(1);
    }

    @Test
    void whenAddMinimumIsNotEndThenOk() {
        AvlTree<Integer> tree = new AvlTree<>();
        for (int element : new int[]{4, 2, 6, 3, 5, 7}) {
            tree.insert(element);
        }
        assertThat(tree.minimum()).isEqualTo(2);
    }

    @Test
    void whenSymmetricalOrderThenOk() {
        AvlTree<Integer> tree = new AvlTree<>();
        for (int element : new int[]{4, 2, 6, 3, 5, 7, 1}) {
            tree.insert(element);
        }
        assertThat(tree.inSymmetricalOrder()).hasSize(7)
                .containsExactly(1, 2, 3, 4, 5, 6, 7);
    }

    @Test
    void whenPreOrderThenOk() {
        AvlTree<Integer> tree = new AvlTree<>();
        for (int element : new int[]{4, 2, 6, 3, 5, 7, 1}) {
            tree.insert(element);
        }
        assertThat(tree.inPreOrder()).hasSize(7)
                .containsExactly(4, 2, 1, 3, 6, 5, 7);
    }

    @Test
    void whenPostOrderThenOk() {
        AvlTree<Integer> tree = new AvlTree<>();
        for (int element : new int[]{4, 2, 6, 3, 5, 7, 1}) {
            tree.insert(element);
        }
        assertThat(tree.inPostOrder()).hasSize(7)
                .containsExactly(1, 3, 2, 5, 7, 6, 4);
    }

    @ParameterizedTest
    @MethodSource("treeRemovalNodeBasicCases")
    void whenRemoveNodeCommonCases(Integer key, boolean expectedResult, List<Integer> expectedOrder) {
        AvlTree<Integer> tree = new AvlTree<>();
        for (int element : new int[]{4, 2, 6, 3, 5, 7, 1, 0, 8}) {
            tree.insert(element);
        }

        assertThat(tree.remove(key)).isEqualTo(expectedResult);
        assertThat(tree.inSymmetricalOrder()).isEqualTo(expectedOrder);

        if (expectedResult) {
            assertThat(tree.contains(key)).isFalse();
        }
    }

    static Stream<Arguments> treeRemovalNodeBasicCases() {
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
        AvlTree<Integer> tree = new AvlTree<>();
        assertThat(tree.remove(4)).isEqualTo(false);
    }

    @Test
    void whenRootIsSoloThenRootRemovalIsTrue() {
        AvlTree<Integer> tree = new AvlTree<>();
        for (int element : new int[]{4}) {
            tree.insert(element);
        }
        assertThat(tree.remove(4)).isTrue();
        assertThat(tree.inSymmetricalOrder()).isEmpty();
        assertThat(tree.contains(4)).isFalse();
    }

    @Test
    void whenRootHasChildrenThenRemovalTrue() {
        AvlTree<Integer> tree = new AvlTree<>();
        for (int element : new int[]{4, 2, 6, 3, 5, 7, 1, 0, 8}) {
            tree.insert(element);
        }
        assertThat(tree.remove(4)).isTrue();
        assertThat(tree.contains(4)).isFalse();
        assertThat(tree.inSymmetricalOrder()).isEqualTo(List.of(0, 1, 2, 3, 5, 6, 7, 8));
    }

    @ParameterizedTest
    @MethodSource("treeClearTreeBasicCases")
    void whenClearTreeCommonCases(List<Integer> in, List<Integer> result) {
        AvlTree<Integer> tree = new AvlTree<>();
        for (int element : in) {
            tree.insert(element);
        }

        tree.clear();
        assertThat(tree.inSymmetricalOrder()).isEqualTo(result);
        assertThat(tree.maximum()).isNull();
        assertThat(tree.minimum()).isNull();
    }

    static Stream<Arguments> treeClearTreeBasicCases() {
        List<Integer> emptyList = List.of();
        return Stream.of(
                Arguments.of(List.of(4, 2, 6, 3, 5, 7, 1, 0, 8), emptyList),
                Arguments.of(List.of(2, 6, 3, 5, 7, 1, 0, 8), emptyList),
                Arguments.of(List.of(4, 2, 6, 3, 5, 7, 1, 0), emptyList),
                Arguments.of(List.of(2, 6, 3, 5, 7, 1, 0), emptyList),
                Arguments.of(List.of(1, 2, 3), emptyList),
                Arguments.of(List.of(1, 2), emptyList),
                Arguments.of(List.of(2, 3), emptyList)
        );
    }

    @Test
    void whenRootIsSoloThenClearContainsNothing() {
        AvlTree<Integer> tree = new AvlTree<>();
        for (int element : new int[]{4}) {
            tree.insert(element);
        }
        tree.clear();
        List<Integer> result = List.of();
        assertThat(tree.inSymmetricalOrder()).isEqualTo(result);
        assertThat(tree.maximum()).isNull();
        assertThat(tree.minimum()).isNull();
    }

    @Test
    void simmetricalOrderIsOk() {
        AvlTree<Integer> tree = new AvlTree<>();
        for (int i = 1; i < 8; i++) {
            tree.insert(i);
        }
        List<Integer> list = tree.inSymmetricalOrder();
        assertThat(list).containsExactly(1, 2, 3, 4, 5, 6, 7);
    }

    @ParameterizedTest
    @MethodSource("avtTreeInsertionBasicCases")
    void whenAvtInsertionCommonCases(List<Integer> in, List<Integer> result) {
        AvlTree<Integer> tree = new AvlTree<>();
        for (int element : in) {
            tree.insert(element);
        }

        assertThat(tree.inPreOrder()).isEqualTo(result);
    }

    static Stream<Arguments> avtTreeInsertionBasicCases() {
        List<Integer> result = List.of(2, 1, 3);
        return Stream.of(
                Arguments.of(List.of(3, 2, 1), result),
                Arguments.of(List.of(1, 2, 3), result),
                Arguments.of(List.of(3, 1, 2), result),
                Arguments.of(List.of(1, 3, 2), result)
        );
    }

    @Test
    void whenAvtDuplicateInsertionWhenFalse() {
        AvlTree<Integer> tree = new AvlTree<>();
        List<Integer> in = List.of(3, 2, 1);
        for (int element : in) {
            tree.insert(element);
        }

        boolean result = tree.insert(3);
        assertThat(result).isFalse();
    }

    @Test
    void whenAvtNullInsertionWhenFalse() {
        AvlTree<Integer> tree = new AvlTree<>();
        boolean result = tree.insert(null);
        assertThat(result).isFalse();
    }

    @ParameterizedTest
    @MethodSource("avlTreeRemovalRebalanceCases")
    void whenAvlRemovalRequiresRebalance(List<Integer> before, Integer keyToRemove, List<Integer> expectedPreOrder) {
        AvlTree<Integer> tree = new AvlTree<>();
        for (int element : before) {
            tree.insert(element);
        }

        assertThat(tree.remove(keyToRemove)).isTrue();
        assertThat(tree.contains(keyToRemove)).isFalse();
        assertThat(tree.inPreOrder()).isEqualTo(expectedPreOrder);
    }

    static Stream<Arguments> avlTreeRemovalRebalanceCases() {
        return Stream.of(
                Arguments.of(List.of(4, 2, 5, 1, 3), 5, List.of(2, 1, 4, 3)),
                Arguments.of(List.of(2, 1, 4, 3, 5), 1, List.of(4, 2, 3, 5)),
                Arguments.of(List.of(5, 2, 6, 1, 4, 7, 3), 7, List.of(4, 2, 1, 3, 5, 6)),
                Arguments.of(List.of(2, 1, 5, 0, 3, 6, 4), 0, List.of(3, 2, 1, 5, 4, 6))
        );
    }
}