package ru.job4j.newcoll.tree;

import ru.job4j.newcoll.tree.representation.PrintTree;
import ru.job4j.newcoll.tree.representation.VisualNode;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * тут важный момент в классе. он насследует
 * Тут кстати есть тема, ускользающая сперва от понимания при сравнении (или я возможно такой... тугой),
 * а как будто весьма полезная -
 * а именно внмиание на компарабл и отсутствие переопределения в нем compareTo() как такового.
 * Т.е.... КАК сравниваем ? А потому что Т.
 * <p>
 * А Т - может же быть не только Интеджер или Стринг (где компарабл реализован), а еще и скажем, Хьюман.
 * И вот ели Хьюман не будет это компейрТу
 * переопределять, выхватим ошибку.
 * Т.е. мы задаем контракт, что тип Т обязан либо иметь оный из-под коробки, либо реализовывать его сам.
 *
 * @param <T>
 */

public class BinarySearchTree<T extends Comparable<T>> {

    private Node root;

    public boolean put(T key) {
        boolean result;
        if (Objects.isNull(root)) {
            root = new Node(key);
            result = true;
        } else {
            result = put(root, key);
        }
        return result;
    }

    /**
     * получается так: выше добавили ТОЛЬКО рут, - и то если он нулль
     * <p>
     * в методе служебном:
     * нам передают ноду (узел) текущую и ключ-ввелью.
     * теперь мы сравниваем ее значениее с кеем, если = 0, просто фолс (значит нам передали кей и он уже есть)
     * елси меньше нуля - возможны 2 варианта:
     * 1. ноды слева нет (т.е. слева она = нуль) - добавляем - и просто вываливаемся (условие выхода из рекурсии)
     * 2. нода слева есть - значит нам надо продолжать искать.
     * <p>
     * ВАЖНО: КАК искать ?
     * мы УЖЕ убедились что нужное место точно будет левее и что там что-то есть
     * (т.е. в место куда мы хотели засунуть новую ноду - занято).
     * значит, нам надо продолжать искать незанятое место, но слева: вызывать метод поиска ноды ЕЩЕ раз.
     * и до тех пор, пока не найдем свободное место.
     * <p>
     * у нас леваая нода стала... текущей, или тем узлом, по которому ведем дальнейший поиск.
     * передаем это все рекурсивно в наш служебный поиск - фактически до тех пор,
     * пока не найдем место где ноды (слева/справа нет). нашли это место ? воткнули ноду, вывалились с тру
     * <p>
     * c правой нодой абсолютно такая же логика.
     * <p>
     * ВАЖНО: return put(left, key); - именно ретурн
     * пример:
     * binarySearchTree.put(2);
     * binarySearchTree.put(1);
     * binarySearchTree.put(3);
     * binarySearchTree.put(0);
     * на этапе 0: в дереве уже есть 1(левый) - 2 (рут) - 3(правый)
     * метод put - передаем рут. смотрим. меньше (т.е. 0 и 2), провалиаемся - а левый - занят уже.
     * вызываем put(left, key) И передаем его результат СРАЗУ. т.е. put(1,0)**
     * у 1 нет левого. вызывается:
     * node.left = new Node(key);
     * return true;
     * <p>
     * добвляем, передаем наверх в ** тру. в элсе - вернулся тру, завершили стек.
     * <p>
     * в итоге наверх мы передадим фолс только если нам передали кей, который уже есть
     *
     * @param node
     * @param key
     * @return
     */

    private boolean put(Node node, T key) {
        int result = key.compareTo(node.key);

        if (result < 0) {
            Node left = node.left;
            if (Objects.isNull(left)) {
                node.left = new Node(key);
                return true;
            } else {
                return put(left, key);
            }
        }

        if (result > 0) {
            Node right = node.right;
            if (Objects.isNull(right)) {
                node.right = new Node(key);
                return true;
            } else {
                return put(right, key);
            }
        }

        return false;
    }

    /**
     * алгоритм (основа) - в целом такой же. дьявол в деталях...
     * <p>
     * в служебном методе мы ищем ноду по одному пути. если нашли = она не нулль = тру. если не нашли = фолс.
     * так вот под капотом теперь если слева после сравнения (2, 1), есть ветка;
     * т.е. что-то есть и эта левая нода = нулль (т.е. null - 2 - 3),
     * а сравниваем напрмиер с кеем = 1, просто сразу вернем нулль - потому что у нас дерево упорядоченное сразу
     * <p>
     * ситуация когда нашли 0 в (0 - 1 - 2 - 3) (0 тут крайняя левая):
     * сравнили 0 с 2 (рут), левая - слева есть 1 ? да, есть, провалились в рекурсию
     * 1 сраниваем с 0, также меньше, посмотрели - тоже слева, снвоа провалились...
     * 0 с 0 сравнили - веренули ноду. вернулись наверх, пробросили ноду из return find(right, key);
     * оттуда - наверх... -- вернули ноду в конце-концов
     * <p>
     * ситуаация когда НЕ нашли 5 в (0 - 1 - 2 - 3)
     * - такая же. на конечном этапе сравнили 3 и 5. -- будет самая права нода. а еесть такая ? нет. вернули нулль.
     * нулль пробросили наверх.
     *
     * @param key
     * @return
     */

    public boolean contains(T key) {
        boolean result = true;
        if (Objects.isNull(root)) {
            result = false;
        } else {
            Node node = find(root, key);
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

    public boolean remove(T key) {
        /* Метод будет реализован в следующих уроках */
        return false;
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
        //TODO реализуйте метод
        return null;
    }

    private List<T> inPreOrder(Node localRoot, List<T> list) {
        //TODO реализуйте метод
        return null;
    }

    public List<T> inPostOrder() {
        //TODO реализуйте метод
        return null;
    }

    private List<T> inPostOrder(Node localRoot, List<T> list) {
        //TODO реализуйте метод
        return null;
    }

    public T minimum() {
        return Objects.nonNull(root) ? minimum(root).key : null;
    }

    private Node minimum(Node node) {
        //TODO реализуйте метод
        return null;
    }

    public T maximum() {
        return Objects.nonNull(root) ? maximum(root).key : null;
    }

    private Node maximum(Node node) {
        //TODO реализуйте метод
        return null;
    }

    @Override
    public String toString() {
        return PrintTree.getTreeDisplay(root);
    }

    private class Node implements VisualNode {
        private T key;
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
            return key.toString();
        }
    }

    public static void main(String[] args) {
        BinarySearchTree<Integer> binarySearchTree = new BinarySearchTree<>();
        binarySearchTree.put(2);
        binarySearchTree.put(1);
        binarySearchTree.put(3);
        binarySearchTree.put(0);

        binarySearchTree.contains(0);
        binarySearchTree.contains(5);

        System.out.println(binarySearchTree);
    }
}