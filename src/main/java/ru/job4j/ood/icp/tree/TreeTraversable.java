package ru.job4j.ood.icp.tree;

import java.util.Iterator;

/**
 * ключевая мысль.
 * GraphTraversable - вообще про граф
 * preOrder и postOrder — общедревесные вещи;
 * inOrder — обычно история про бинарное дерево.
 * Сравнис вариантом ккогда все в 1 интерфейсе
 * @param <T>
 */

interface TreeTraversable<T> {
    Iterator<T> preOrder();
    Iterator<T> inOrder();
    Iterator<T> postOrder();
}

