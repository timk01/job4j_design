package ru.job4j.newcoll.binarytree;

import ru.job4j.newcoll.tree.representation.PrintTree;
import ru.job4j.newcoll.tree.representation.VisualNode;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AvlTree<T extends Comparable<T>> {

    public Node root;

    public boolean contains(T value) {
        boolean result = true;
        if (Objects.isNull(root)) {
            result = false;
        } else {
            Node node = find(root, value);
            if (Objects.isNull(node)) {
                result = false;
            }
        }
        return result;
    }

    private Node find(Node node, T key) {
        int result = key.compareTo(node.key);

        if (result < 0) {
            Node left = node.left;
            if (Objects.isNull(left)) {
                return null;
            } else {
                return find(left, key);
            }
        }

        if (result > 0) {
            Node right = node.right;
            if (Objects.isNull(right)) {
                return null;
            } else {
                return find(right, key);
            }
        }

        return node;
    }

    public boolean insert(T value) {
        boolean result = false;
        if (Objects.nonNull(value) && !contains(value)) {
            root = insert(root, value);
            result = true;
        }
        return result;
    }

    private Node insert(Node node, T value) {
        Node result = new Node(value);
        if (Objects.nonNull(node)) {
            int comparisonResult = value.compareTo(node.key);
            if (comparisonResult < 0) {
                node.left = insert(node.left, value);
            } else {
                node.right = insert(node.right, value);
            }
            updateHeight(node);
            result = balance(node);
        }
        return result;
    }

    public boolean remove(T element) {
        boolean result = false;
        if (Objects.nonNull(element) && Objects.nonNull(root) && contains(element)) {
            root = remove(root, element);
            result = true;
        }
        return result;
    }

    private Node remove(Node node, T element) {
        if (node == null) {
            return null;
        }
        int comparisonResult = element.compareTo(node.key);
        if (comparisonResult < 0) {
            node.left = remove(node.left, element);
        } else if (comparisonResult > 0) {
            node.right = remove(node.right, element);
        } else {
            if (node.left == null) {
                return node.right;
            } else if (node.right == null) {
                return node.left;
            } else {
                if (node.left.height > node.right.height) {
                    T heir = maximum(node.left).key;
                    node.key = heir;
                    node.left = remove(node.left, heir);
                } else {
                    T heir = minimum(node.right).key;
                    node.key = heir;
                    node.right = remove(node.right, heir);
                }
            }
        }
        updateHeight(node);
        return balance(node);
    }

    /**
     * вызывается где ?
     * где структура поддерева могла измениться:
     * <p>
     * после insert(...), когда рекурсия поднимается вверх;
     * после remove(...), когда рекурсия поднимается вверх;
     * после поворотов, потому что у узлов меняются дети, а значит и высоты/балансы.
     * <p>
     * 1 + max(-1, -1) = 0 - это если лист (т.е. нода без потомков)
     * Пустое поддерево = -1 всегда, когда конкретного ребёнка нет
     * <p>
     * leftNodeHeight т.е. высота левой поддерева = по-умолчанию -1 если ее нет (т.е. пустое поддерево)
     * т.е. слева нет узл - значит -1, или же берем уже вычисленную высоту левого поддереева.
     * <p>
     * node.height - или высота ТЕКУЩЕЙ ноды это как раз 1 + максимум из высот
     * <p>
     * то же самое и для правой ноды дальше высчитывается высота текущеей ноды -
     * и баланс = разница ВЫСОТ правого и левого поддеревьев
     * +2 → слишком тяжело вправо
     * -2 → слишком тяжело влево
     * <p>
     * и главное. если НЕ -1, 0 или 1 - значит перекос и значит надо делать повороты (ребаланс)
     * ИЛИ: balanceFactor < -1 || balanceFactor > 1.
     *
     * @param node
     */
    private void updateHeight(Node node) {
        int leftNodeHeight = Objects.isNull(node.left) ? -1 : node.left.height;
        int rightNodeHeight = Objects.isNull(node.right) ? -1 : node.right.height;
        node.height = 1 + Math.max(leftNodeHeight, rightNodeHeight);
        node.balanceFactor = rightNodeHeight - leftNodeHeight;
    }

    /**
     * а balance как раз смотрит балнсФактор (см. метод updateHeight), если меньше -1 ИЛИ больше 1
     * то нужна перебалансировка.
     * ИЛИ: если узел уже перекошен, тогда смотрим баланс тяжёлого ребёнка
     * и по нему выбираем, это LL / LR / RR / RL
     * <p>
     * и сам метод balance ызывается после апдейта состояния нод. т.е. добавили/удалили - посичтали баланс.
     * кривой - сразу же в методах вызываешь ребаланс, чтобы дерево сохрааняло структуру
     *
     * @param node
     * @return
     */

    private Node balance(Node node) {
        Node result = node;
        if (node.balanceFactor < -1) {
            if (node.left.balanceFactor > 0) {
                result = leftRightCase(node);
            } else {
                result = rightRotation(node);
            }
        } else if (node.balanceFactor > 1) {
            if (node.right.balanceFactor >= 0) {
                result = leftRotation(node);
            } else {
                result = rightLeftCase(node);
            }
        }
        return result;
    }

    private Node leftRightCase(Node node) {
        node.left = leftRotation(node.left);
        return rightRotation(node);
    }

    private Node rightLeftCase(Node node) {
        node.right = rightRotation(node.right);
        return leftRotation(node);
    }

    /**
     * Поднимется 30. Опускается 10. поддерево 20 раньше было левым поддеревом 30,
     * а после поворота стало правым поддеревом 10
     * **** 10
     * **** /----\
     * **** 5----30
     * ********* /--\
     * *********20--50
     * ************ /--\
     * ************* 40---60
     * <p>
     * исходно:
     * у 10.left = 5
     * у 10.right = 30
     * у 30.left = 20
     * у 30.right = 50
     * <p>
     * мы НЕ СВАПАЕМ их БУКВАЛЬНО на примере 10 и 30. (10 - ЛОКАЛЬНЫЙ ккорень поддерева, что нужно поменять)
     * а) записать в темп-Родителя правую - нод.правая - от 10 это = (30),
     * б) Левое поддерево 30 отдаём вправо 10: 10.right = 20 (20 больше 10 и меньше 30)
     * в) 10 делаем левым ребёнком 30: 30.left = 10
     * <p>
     * далее: пересчитать высоту узла нод и узла и ньюПарент
     * вернуть ньюпарент (те. приъодила нода, возвращаем ньюпарент - ибо перебалансировка)
     *
     * @param node
     * @return
     */

    private Node leftRotation(Node node) {
        Node newParent = node.right;
        node.right = newParent.left;
        newParent.left = node;
        updateHeight(node);
        updateHeight(newParent);
        return newParent;
    }

    /**
     * <p>
     * * **** 50
     * * *** /----\
     * ***** 30
     * ***** /--\
     * ***10--40
     * рут - 50 - 30 - 10 - 40 (перекос влева) -- поменять местам 50 и 30, со сдвигом потомкков если нуужно
     * 30 = родитель, 50.лефт = 40, 30.райт = 50
     * <p>
     *
     * @param node
     * @return
     */

    private Node rightRotation(Node node) {
        Node newParent = node.left;
        node.left = newParent.right;
        newParent.right = node;
        updateHeight(node);
        updateHeight(newParent);
        return newParent;
    }

    public T minimum() {
        return Objects.nonNull(root) ? minimum(root).key : null;
    }

    private Node minimum(Node node) {
        if (node != null && node.left != null) {
            return minimum(node.left);
        }
        return node;
    }

    public T maximum() {
        return Objects.nonNull(root) ? maximum(root).key : null;
    }

    private Node maximum(Node node) {
        if (node != null && node.right != null) {
            return maximum(node.right);
        }
        return node;
    }

    public List<T> inSymmetricalOrder() {
        List<T> result = new ArrayList<>();
        Node node = root;
        return inSymmetricalOrder(node, result);
    }

    private List<T> inSymmetricalOrder(Node localRoot, List<T> list) {
        if (localRoot != null) {
            inSymmetricalOrder(localRoot.left, list);
            list.add(localRoot.key);
            inSymmetricalOrder(localRoot.right, list);
        }
        return list;
    }

    public List<T> inPreOrder() {
        List<T> result = new ArrayList<>();
        Node node = root;
        return inPreOrder(node, result);
    }

    private List<T> inPreOrder(Node localRoot, List<T> list) {
        if (localRoot != null) {
            list.add(localRoot.key);
            inPreOrder(localRoot.left, list);
            inPreOrder(localRoot.right, list);
        }

        return list;
    }

    public List<T> inPostOrder() {
        List<T> result = new ArrayList<>();
        Node node = root;
        return inPostOrder(node, result);
    }

    private List<T> inPostOrder(Node localRoot, List<T> list) {
        if (localRoot != null) {
            inPostOrder(localRoot.left, list);
            inPostOrder(localRoot.right, list);
            list.add(localRoot.key);
        }

        return list;
    }

    public void clear() {
        Node node = root;
        clear(node);
        root = null;
    }

    private void clear(Node first) {
        if (first != null) {
            clear(first.left);
            clear(first.right);
            first.key = null;
            first.left = null;
            first.right = null;
        }
    }

    @Override
    public String toString() {
        return PrintTree.getTreeDisplay(root);
    }

    private class Node implements VisualNode {
        private int balanceFactor;
        private T key;
        private int height;
        private Node left;
        private Node right;

        public Node(T key) {
            this.key = key;
        }

        @Override
        public VisualNode getLeft() {
            return left;
        }

        @Override
        public VisualNode getRight() {
            return right;
        }

        @Override
        public String getText() {
            return String.valueOf(key);
        }
    }
}