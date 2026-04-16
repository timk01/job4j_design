package ru.job4j.newcoll.fortaskavl;

import java.util.*;

public class TreeAVLMap<T extends Comparable<T>, V> {

    private Node root;

    /**
     * Предполагаем, что и велью также как и кей НЕ могут быть равны нулю (и это фолс). При прочих равных - та же логика
     * <p>
     * в отличие от обычного АВЛТри, мы тут оперируем еще и с велью
     * и согласно предоставленным тестам, оно обновляется если кеи совпадают (сравнение все еще идет по ключам)
     *
     * @param key
     * @param value
     * @return
     */

    public boolean put(T key, V value) {
        if (Objects.isNull(key) || Objects.isNull(value)) {
            return false;
        }

        root = insert(root, key, value);

        return true;
    }

    private Node insert(Node node, T key, V value) {
        Node result = new Node(key, value);
        if (Objects.nonNull(node)) {
            int comparisonResult = key.compareTo(node.key);

            if (comparisonResult < 0) {
                node.left = insert(node.left, key, value);
            } else if (comparisonResult > 0) {
                node.right = insert(node.right, key, value);
            } else {
                node.value = value;
                return node;
            }
            updateHeight(node);
            result = balance(node);
        }
        return result;
    }

    /**
     * если рут = 0 или кей = нуль, сразу фолс
     * если ключа - нет (иначе и удалять смысла тоже нет) - фолс
     * иначе удаляем, сохраняя рут
     *
     * @param key
     * @return
     */

    public boolean remove(T key) {
        if (Objects.isNull(root) || Objects.isNull(key)) {
            return false;
        }

        if ((get(key) == null)) {
            return false;
        } else {
            root = remove(root, key);
        }

        return true;
    }

    /**
     * Для случая если А) лист или  Б) один наследник
     * нашли узел
     * вместо него вернули null или его единственного ребёнка
     * родитель подставил это в своё поле
     * дальше рекурсия пошла вверх пересчитывать высоты и баланс
     * <p>
     * Для В) 2 ребенка
     * взять наследника/предшественника
     * скопировать в текущий узел и key, и value
     * удалить старый узел-наследник из его прошлого места
     * <p>
     * А) лист
     * *****5
     * *** /-\
     * ** 3   7
     * <p>
     * допустим, (5, 3) - удаляем тройку, т.е. левый ЛИСТ (без потомков)
     * сравнили, компаризон меньше, зашли в node.left = remove(node.left, key);
     * тперь (3, 3) - компарисон = 0, node.left == null - да, вернули правую ноду. но у листа и правая = нулль
     * наверх врнулась нулль. т.е. node.left = null в результате.
     * перебалансировали, если нужно
     * <p>
     * Б) удаление узла с ОДНИМ ребенком (3) -- то же самое будет и с 1 левым ребенком
     * <p>
     * <p>
     * **** 5
     * ** /
     * * 3
     * **\
     * ** 4
     * <p>
     * remove(5, 3), у нас 3 меньше 5
     * идем влево, node.left = remove(node.left, 3);
     * снова вызывается remove(3, 3) - равно - смотрим: node.left == null - ДА, возвращается правый (4)
     * выходим наверх, 5.left = 4; (вернули же 4)... дерево стало 5---4 (тройку удалили, 4 перееехали на его место)
     * ребаланс, если нужно
     * <p>
     * В)узел с 2 детьми
     * ********** (5,"55")
     * ********  / -------\
     * ***** (3,"33")----(7,"77")
     * **************** /--------\
     * *************(6,"66")----(8,"88")
     * У узла (5,"55") два потомка, значит просто удалить нельзя.
     * Как и договаривались, ищем ближайшую замену, но из правого подузла (т.е. 6, 66)
     * remove(node=(5,"55"), key=5) - компарисон = 0, ни влева, ни вправа не идем, проваливаемся в третий элс
     * при этом ни лефт, ни райт не нулль -- потому у нас третий случай (как раз сложный)
     * <p>
     * здесь внутри: if (node.left.height > node.right.height) - фолс ((высота у одного больше))
     * heirNode = minimum(node.right) - смотрим минимальный из левых
     * стартуем с 7
     * идём максимально влево
     * приходим в 6
     * дальше: (5,"55") - перезаписываем на (6,"66") ((т.е. у нас 2 шестерки - где было и где стало))
     * node.right = remove(7, 6)
     * <p>
     * 6.compareTo(7) < 0 - идем влево, или node.left = remove(node.left, 6), ИЛИ ремув(6, 6)
     * доходим до него - это лист, т.е. вернули нулль, от семерки наследника удалили
     *
     * @param node
     * @param key
     * @return
     */

    private Node remove(Node node, T key) {
        if (node == null) {
            return null;
        }
        int comparisonResult = key.compareTo(node.key);
        if (comparisonResult < 0) {
            node.left = remove(node.left, key);
        } else if (comparisonResult > 0) {
            node.right = remove(node.right, key);
        } else {
            if (node.left == null) {
                return node.right;
            } else if (node.right == null) {
                return node.left;
            } else {
                if (node.left.height > node.right.height) {
                    Node heirNode = maximum(node.left);
                    node.key = heirNode.key;
                    node.value = heirNode.value;
                    node.left = remove(node.left, heirNode.key);
                } else {
                    Node heirNode = minimum(node.right);
                    node.key = heirNode.key;
                    node.value = heirNode.value;
                    node.right = remove(node.right, heirNode.key);
                }
            }
        }
        updateHeight(node);
        return balance(node);
    }

    private void updateHeight(Node node) {
        int leftNodeHeight = Objects.isNull(node.left) ? -1 : node.left.height;
        int rightNodeHeight = Objects.isNull(node.right) ? -1 : node.right.height;
        node.height = 1 + Math.max(leftNodeHeight, rightNodeHeight);
        node.balanceFactor = rightNodeHeight - leftNodeHeight;
    }

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

    private Node leftRotation(Node node) {
        Node newParent = node.right;
        node.right = newParent.left;
        newParent.left = node;
        updateHeight(node);
        updateHeight(newParent);
        return newParent;
    }

    private Node rightRotation(Node node) {
        Node newParent = node.left;
        node.left = newParent.right;
        newParent.right = node;
        updateHeight(node);
        updateHeight(newParent);
        return newParent;
    }

    private Node minimum(Node node) {
        if (node != null && node.left != null) {
            return minimum(node.left);
        }
        return node;
    }

    private Node maximum(Node node) {
        if (node != null && node.right != null) {
            return maximum(node.right);
        }
        return node;
    }

    /**
     * Both root and key cannot be null
     * <p>
     * Берем ноду по ключу (если она не нулль или рут) и возвращаем велью (тоже может быть не найдено)
     *
     * @param key
     * @return
     */

    public V get(T key) {
        if (Objects.isNull(root) || key == null) {
            return null;
        }
        Node node = findByKey(root, key);

        return node != null ? node.value : null;
    }

    private Node findByKey(Node node, T key) {
        int result = key.compareTo(node.key);

        if (result < 0) {
            Node left = node.left;
            if (Objects.isNull(left)) {
                return null;
            } else {
                return findByKey(left, key);
            }
        }

        if (result > 0) {
            Node right = node.right;
            if (Objects.isNull(right)) {
                return null;
            } else {
                return findByKey(right, key);
            }
        }

        return node;
    }

    public Set<T> keySet() {
        if (root == null) {
            return Set.of();
        }

        Set<T> orderedKeys = new LinkedHashSet<>();
        for (T elem : inSymmetricalOrder()) {
            orderedKeys.add(elem);
        }

        return orderedKeys;
    }

    /**
     * использует keySet() метод внутри, а по ключу (слубный get(key) получает значения
     * - это несколько костыльно, зато понятно
     *
     * @return
     */
    public Collection<V> values() {
        if (root == null) {
            return List.of();
        }

        Collection<V> orderedValues = new ArrayList<>();
        for (T key : keySet()) {
            orderedValues.add(get(key));
        }

        return orderedValues;
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

    /**
     * остаавлен скорее как часть служебной проверки
     *
     * @return
     */
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

    private class Node {
        private int balanceFactor;
        private T key;
        private V value;
        private int height;
        private Node left;
        private Node right;

        Node(T key, V value) {
            this.key = key;
            this.value = value;
        }
    }
}