package ru.job4j.newcoll.tree.representation;

public class ExampleTree {
    private Node root = new Node(1,
            new Node(2,
                    new Node(4,
                            new Node(8, new Node(16), new Node(17)),
                            new Node(9, new Node(18), new Node(19))),
                    new Node(5,
                            new Node(10, new Node(20), new Node(21)),
                            new Node(11, new Node(22), new Node(23))
                    )
            ),
            new Node(3,
                    new Node(6,
                            new Node(12, new Node(24), new Node(25)),
                            new Node(13, new Node(26), new Node(27))),
                    new Node(7,
                            new Node(14, new Node(28), new Node(29)),
                            new Node(15, new Node(30), new Node(31)))
            )
    );

    /**
     * VisualNode - утилита отвязана от конкретной реализации узла.
     * просто контракт для печати (и все)
     * а здесь Node - просто очередное представление как можно оформить ноду, ни больше, ни меньше
     * а статик и внутренний - потому что здесь нужно иное представление дерева чем у нас обычно (у нас велью + лист)
     * ((все вмест что-то типа адаптера))
     */

    private static class Node implements VisualNode {
        private int key;
        private Node left;
        private Node right;

        public Node(int key) {
            this.key = key;
        }

        public Node(int key, Node left, Node right) {
            this.key = key;
            this.left = left;
            this.right = right;
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

    @Override
    public String toString() {
        return PrintTree.getTreeDisplay(root);
    }
}