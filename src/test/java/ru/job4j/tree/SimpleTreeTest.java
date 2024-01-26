package ru.job4j.tree;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

public class SimpleTreeTest {

    @Test
    void when6ElFindLastThen6() {
        Tree<Integer> tree = new SimpleTree<>(1);
        tree.add(1, 2);
        tree.add(1, 3);
        tree.add(1, 4);
        tree.add(4, 5);
        tree.add(5, 6);
        assertThat(tree.findBy(6)).isPresent();
    }

    @Test
    void whenElFindNotExistThenOptionEmpty() {
        Tree<Integer> tree = new SimpleTree<>(1);
        tree.add(1, 2);
        assertThat(tree.findBy(7)).isEmpty();
    }

    @Test
    void whenChildExistOnLeafThenNotAdd() {
        Tree<Integer> tree = new SimpleTree<>(1);
        tree.add(1, 2);
        tree.add(1, 3);
        tree.add(1, 4);
        tree.add(4, 5);
        tree.add(5, 6);
        assertThat(tree.add(2, 6)).isFalse();
    }

    @Test
    void whenChildDoesNoExistOnLeafThenAdd() {
        Tree<Integer> tree = new SimpleTree<>(1);
        tree.add(1, 2);
        tree.add(1, 3);
        tree.add(1, 4);
        tree.add(4, 5);
        tree.add(5, 6);
        assertThat(tree.add(2, 7)).isTrue();
    }

    @Test
    void whenTreeIsBigTestProperLogic() {
        Tree<Integer> tree = new SimpleTree<>(1);
        tree.add(1, 2);
        tree.add(1, 3);
        tree.add(1, 4);
        tree.add(4, 5);
        tree.add(4, 15);
        tree.add(2, 6);
        tree.add(2, 7);
        tree.add(2, 8);
        assertThat(tree.findBy(6)).isPresent();
    }

    @Test
    void isBinaryFalseOnSoloRoot() {
        SimpleTree<Integer> tree = new SimpleTree<>(1);
        assertThat(tree.isBinary()).isTrue();
    }

    @Test
    void isBinaryFalseOnRootWithLotsOfChildren() {
        SimpleTree<Integer> tree = new SimpleTree<>(1);
        tree.add(1, 2);
        tree.add(1, 3);
        tree.add(1, 4);
        tree.add(1, 5);
        assertThat(tree.isBinary()).isFalse();
    }

    @Test
    void isBinaryTrueOnRootWithExactTwoChildren() {
        SimpleTree<Integer> tree = new SimpleTree<>(1);
        tree.add(1, 2);
        tree.add(1, 3);
        assertThat(tree.isBinary()).isTrue();
    }

    @Test
    void isBinaryTrueWithBigStructure() {
        SimpleTree<Integer> tree = new SimpleTree<>(1);
        tree.add(1, 2);
        tree.add(1, 3);
        tree.add(2, 4);
        tree.add(3, 5);
        tree.add(3, 6);
        tree.add(4, 7);
        assertThat(tree.isBinary()).isTrue();
    }

    @Test
    void isBinaryFalseWithBigStructure() {
        SimpleTree<Integer> tree = new SimpleTree<>(1);
        tree.add(1, 2);
        tree.add(1, 3);
        tree.add(2, 4);
        tree.add(3, 5);
        tree.add(3, 6);
        tree.add(4, 7);
        tree.add(4, 8);
        tree.add(4, 9);
        assertThat(tree.isBinary()).isFalse();
    }
}