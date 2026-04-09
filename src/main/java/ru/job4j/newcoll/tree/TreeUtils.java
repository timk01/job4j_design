package ru.job4j.newcoll.tree;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
        bfs(root, node -> counter.getAndIncrement());

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
        bfs(root, node -> nodes.add(node.getValue()));

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
     */

    private void bfs(Node<T> root, Consumer<Node<T>> action) {
        checkRoot(root);

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

    /**
     * add, findByKey, divideByKey - это уже поиск в глубину!
     * <p>
     * Метод обходит дерево root и добавляет к узлу с ключом parent
     * новый узел с ключом child, при этом на момент добавления узел с ключом parent
     * уже должен существовать в дереве, а узла с ключом child в дереве быть не должно
     *
     * @param root   корень дерева
     * @param parent ключ узла-родителя
     * @param child  ключ узла-потомка
     * @return true если добавление произошло успешно и false в обратном случае.
     * @throws IllegalArgumentException если root является null
     */
    public boolean add(Node<T> root, T parent, T child) {
        checkRoot(root);

        Optional<Node<T>> parentResult = findByKey(root, parent);
        Optional<Node<T>> childResult = findByKey(root, child);

        if (parentResult.isEmpty() || childResult.isPresent()) {
            return false;
        }

        return parentResult.get().getChildren().add(new Node<>(child));
    }

    /**
     * Метод обходит дерево root и возвращает первый найденный узел с ключом key
     *
     * @param root корень дерева
     * @param key  ключ поиска
     * @return узел с ключом key, завернутый в объект типа Optional
     * @throws IllegalArgumentException если root является null
     */
    public Optional<Node<T>> findByKey(Node<T> root, T key) {
        checkRoot(root);

        SimpleStack<Node<T>> stack = new SimpleStack<>();
        stack.push(root);

        while (!stack.isEmpty()) {
            Node<T> poppedEl = stack.pop();
            if (poppedEl.getValue().equals(key)) {
                return Optional.of(poppedEl);
            }

            for (Node<T> child : poppedEl.getChildren()) {
                if (child.getValue().equals(key)) {
                    return Optional.of(child);
                }
            }

        }

        return Optional.empty();
    }

    /**
     * Метод обходит дерево root и возвращает первый найденный узел с ключом key,
     * при этом из дерева root удаляется все поддерево найденного узла
     *
     * @param root корень дерева
     * @param key  ключ поиска
     * @return узел с ключом key, завернутый в объект типа Optional
     * @throws IllegalArgumentException если root является null
     */
    public Optional<Node<T>> divideByKey(Node<T> root, T key) {
        checkRoot(root);

        Optional<Node<T>> foundNode = findByKey(root, key);

        if (foundNode.isEmpty()) {
            return Optional.empty();
        }

        if (foundNode.get().equals(root)) {
            return Optional.of(root);
        }

        SimpleStack<Node<T>> stack = new SimpleStack<>();
        stack.push(root);

        while (!stack.isEmpty()) {
            Node<T> poppedEl = stack.pop();
            List<Node<T>> children = poppedEl.getChildren();

            for (int i = 0; i < children.size(); i++) {
                if (children.get(i).equals(foundNode.get())) {
                    children.remove(i);
                    return foundNode;
                }
                stack.push(children.get(i));
            }
        }

        throw new IllegalStateException(
                "Node was found and is not the root, but its parent was not found in the tree"
        );
    }

    private <T> void checkRoot(Node<T> root) {
        if (root == null) {
            throw new IllegalArgumentException("root is null");
        }
    }
}