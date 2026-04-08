package ru.job4j.newcoll.tree;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

public class TreeUtils<T> {

    /**
     * Метод выполняет обход дерева и считает количество узлов
     *
     * @param root корневой узел дерева
     * @return количество узлов
     * @throws IllegalArgumentException если root является null
     */
    public int countNode(Node<T> root) {
        AtomicInteger counter = new AtomicInteger();
        bfs(root, node -> counter.getAndIncrement(), "count");

        return counter.get();
    }

    /**
     * Метод выполняет обход дерева и возвращает коллекцию ключей узлов дерева
     *
     * @param root корневой узел
     * @return коллекция с ключами, реализующая интерфейс Iterable<E>
     * @throws IllegalArgumentException если root является null
     */
    public Iterable<T> findAll(Node<T> root) {
        List<T> nodes = new ArrayList<>();
        bfs(root, node -> nodes.add(node.getValue()), "find");

        return nodes;
    }

    /**
     * bfs(root, node -> nodes.add(node.getValue()), "find");
     * общий метод, чтобы не повторять функционльность каждый раз на оба метода
     * action.accept(curr); - вызывается как только элемент достали из очерди
     * (здесь - либо крутит счетчик, либо извлекает велью из ноды и добавляет в лист значений нод)
     * ((раньше подобное добавление было в цикле после пуша ребенка,
     * но т.к. мы так или иначе его возьмем при queue.poll(); - разницы глобальной нет:
     * действие перенесено из момента добавления ребенка в очередь в момент извлечения его из очереди))
     *
     * @param root
     * @param action
     * @param msg
     */

    private void bfs(Node<T> root, Consumer<Node<T>> action, String msg) {
        if (root == null) {
            throw new IllegalArgumentException("cannot " + msg + " Nodes if root is null");
        }

        SimpleQueue<Node<T>> queue = new SimpleQueue<>();
        queue.push(root);

        while (!queue.isEmpty()) {
            Node<T> curr = queue.poll();
            action.accept(curr);
            List<Node<T>> children = curr.getChildren();
            for (Node<T> child : children) {
                queue.push(child);
            }
        }
    }
}