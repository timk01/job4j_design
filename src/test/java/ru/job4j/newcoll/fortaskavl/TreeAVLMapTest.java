package ru.job4j.newcoll.fortaskavl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class TreeAVLMapTest {
    @Test
    void whenEmptyTree() {
        TreeAVLMap<Integer, String> tree = new TreeAVLMap<>();
        assertThat(tree.values()).isEmpty();
        assertThat(tree.keySet()).isEmpty();
    }

    @Test
    void whenAddOneElemThenOk() {
        TreeAVLMap<Integer, String> tree = new TreeAVLMap<>();
        assertThat(tree.put(1, "11")).isTrue();
        assertThat(tree.values()).hasSize(1)
                .containsExactly("11");
        assertThat(tree.keySet()).hasSize(1)
                .containsExactly(1);
    }

    @Test
    void whenAddSevenElemThenOk() {
        TreeAVLMap<Integer, String> tree = new TreeAVLMap<>();
        assertThat(tree.put(1, "11")).isTrue();
        assertThat(tree.put(2, "22")).isTrue();
        assertThat(tree.put(3, "33")).isTrue();
        assertThat(tree.put(4, "44")).isTrue();
        assertThat(tree.put(5, "55")).isTrue();
        assertThat(tree.put(6, "66")).isTrue();
        assertThat(tree.put(7, "77")).isTrue();
        assertThat(tree.values()).hasSize(7)
                .containsExactly("11", "22", "33", "44", "55", "66", "77");
        assertThat(tree.keySet()).hasSize(7)
                .containsExactly(1, 2, 3, 4, 5, 6, 7);
    }

    @Test
    void whenUpdateValueThenOk() {
        TreeAVLMap<Integer, String> tree = new TreeAVLMap<>();
        tree.put(1, "11");
        tree.put(2, "22");
        tree.put(3, "33");
        tree.put(4, "44");
        tree.put(5, "55");
        tree.put(6, "66");
        tree.put(7, "77");
        assertThat(tree.put(5, "555")).isTrue();
        assertThat(tree.values()).hasSize(7)
                .containsExactly("11", "22", "33", "44", "555", "66", "77");
        assertThat(tree.keySet()).hasSize(7)
                .containsExactly(1, 2, 3, 4, 5, 6, 7);
    }

    @Test
    void whenDeleteKeyThenOk() {
        TreeAVLMap<Integer, String> tree = new TreeAVLMap<>();
        tree.put(1, "11");
        tree.put(2, "22");
        tree.put(3, "33");
        tree.put(4, "44");
        tree.put(5, "55");
        tree.put(6, "66");
        tree.put(7, "77");
        assertThat(tree.remove(5)).isTrue();
        assertThat(tree.remove(0)).isFalse();
        assertThat(tree.values()).hasSize(6)
                .containsExactly("11", "22", "33", "44", "66", "77");
        assertThat(tree.keySet()).hasSize(6)
                .containsExactly(1, 2, 3, 4, 6, 7);
    }

    @Test
    void whenGetKeyThenOk() {
        TreeAVLMap<Integer, String> tree = new TreeAVLMap<>();
        tree.put(1, "11");
        tree.put(2, "22");
        tree.put(3, "33");
        tree.put(4, "44");
        tree.put(5, "55");
        tree.put(6, "66");
        tree.put(7, "77");
        assertThat(tree.get(5)).isEqualTo("55");
        assertThat(tree.get(0)).isNull();
    }

    @ParameterizedTest
    @MethodSource("treeAVLMapInsertionBasicCases")
    void whenAVLMapInsertionCommonCases(List<Map.Entry<Integer, Integer>> in, List<Integer> expectedPreOrder) {
        TreeAVLMap<Integer, Integer> tree = new TreeAVLMap<>();
        for (Map.Entry<Integer, Integer> entry : in) {
            tree.put(entry.getKey(), entry.getValue());
        }

        assertThat(tree.inPreOrder()).isEqualTo(expectedPreOrder);
    }

    static Stream<Arguments> treeAVLMapInsertionBasicCases() {
        List<Integer> expectedPreOrder = List.of(2, 1, 3);
        return Stream.of(
                Arguments.of(
                        List.of(Map.entry(3, 33), Map.entry(2, 22), Map.entry(1, 11)),
                        expectedPreOrder
                ),
                Arguments.of(
                        List.of(Map.entry(1, 11), Map.entry(2, 22), Map.entry(3, 33)),
                        expectedPreOrder
                ),
                Arguments.of(
                        List.of(Map.entry(3, 33), Map.entry(1, 11), Map.entry(2, 22)),
                        expectedPreOrder
                ),
                Arguments.of(
                        List.of(Map.entry(1, 11), Map.entry(3, 33), Map.entry(2, 22)),
                        expectedPreOrder
                )
        );
    }

    @ParameterizedTest
    @MethodSource("treeAVLMapRemovalRebalanceCases")
    void whenAVLMapRemovalRequiresRebalance(
            List<Map.Entry<Integer, Integer>> before,
            Integer keyToRemove,
            List<Integer> expectedPreOrder
    ) {
        TreeAVLMap<Integer, Integer> tree = new TreeAVLMap<>();
        for (Map.Entry<Integer, Integer> entry : before) {
            tree.put(entry.getKey(), entry.getValue());
        }

        assertThat(tree.remove(keyToRemove)).isTrue();
        assertThat(tree.get(keyToRemove)).isNull();
        assertThat(tree.inPreOrder()).isEqualTo(expectedPreOrder);
    }

    static Stream<Arguments> treeAVLMapRemovalRebalanceCases() {
        return Stream.of(
                Arguments.of(
                        List.of(
                                Map.entry(4, 44), Map.entry(2, 22), Map.entry(5, 55),
                                Map.entry(1, 11), Map.entry(3, 33)
                        ),
                        5,
                        List.of(2, 1, 4, 3)
                ),
                Arguments.of(
                        List.of(
                                Map.entry(2, 22), Map.entry(1, 11), Map.entry(4, 44),
                                Map.entry(3, 33), Map.entry(5, 55)
                        ),
                        1,
                        List.of(4, 2, 3, 5)
                ),
                Arguments.of(
                        List.of(
                                Map.entry(5, 55), Map.entry(2, 22), Map.entry(6, 66),
                                Map.entry(1, 11), Map.entry(4, 44), Map.entry(7, 77),
                                Map.entry(3, 33)
                        ),
                        7,
                        List.of(4, 2, 1, 3, 5, 6)
                ),
                Arguments.of(
                        List.of(
                                Map.entry(2, 22), Map.entry(1, 11), Map.entry(5, 55),
                                Map.entry(0, 0), Map.entry(3, 33), Map.entry(6, 66),
                                Map.entry(4, 44)
                        ),
                        0,
                        List.of(3, 2, 1, 5, 4, 6)
                )
        );
    }

    @Test
    void whenAVLMapNullInsertionWhenFalse() {
        TreeAVLMap<Integer, Integer> tree = new TreeAVLMap<>();
        assertThat(tree.put(null, 11)).isFalse();
        assertThat(tree.put(1, null)).isFalse();
    }

    @Test
    void whenKeySetAfterRebalanceThenOrderedByKey() {
        TreeAVLMap<Integer, String> tree = new TreeAVLMap<>();
        tree.put(3, "33");
        tree.put(1, "11");
        tree.put(2, "22");
        tree.put(5, "55");
        tree.put(4, "44");

        assertThat(tree.keySet())
                .containsExactly(1, 2, 3, 4, 5);
    }

    @Test
    void whenValuesAfterUpdateAndRemoveThenOrderedByKey() {
        TreeAVLMap<Integer, String> tree = new TreeAVLMap<>();
        tree.put(3, "33");
        tree.put(1, "11");
        tree.put(2, "22");
        tree.put(5, "55");
        tree.put(4, "44");

        tree.put(2, "222");
        tree.remove(5);

        assertThat(tree.values())
                .containsExactly("11", "222", "33", "44");
    }
}