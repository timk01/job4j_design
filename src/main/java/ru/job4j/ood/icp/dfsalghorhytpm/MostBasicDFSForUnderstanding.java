package ru.job4j.ood.icp.dfsalghorhytpm;

import java.util.*;

public class MostBasicDFSForUnderstanding {

    /**
     * здесь, в целом, ни на что не влияет, комменты для понимания итерейбл, итератор и его реализации
     *
     * КОГДА нужен итерейбл ? когда есть класс, скажем Экземлп и по нему (потрохам) я хочу делать форич
     * если там просто пара полей - нахер итератор и не сдался.
     * но если там например ЛИСТ полей (см. Экземпл и экземлпБокс классы), я хочу по ним идти
     * - читай "перебирать потроха класса" - тогда да.
     *
     * а когда мы помещаем этот класс в например лист (т.е. там будут экземляры бокс)
     * , мы делаем итератор по... ЛИСТУ - и итератор у бокса НЕ нужен также.
     * (разумеется кроме варианта когда мы хотим залезть внутрь бокса и проитерироваться уже по его элеменатм)
     *
     *
     * Node — “что обходят” (буквально:хочу обходить Нод в фориче. как ? Спроси у итератора
     * (который возвращает уже НодИтератор)
     * NodeIterator — “чем обходят”
     */

    static class Node implements Iterable<Node> {
        String name;
        List<Node> children = new ArrayList<>();

        Node(String name) {
            this.name = name;
        }

        Node add(Node child) {
            children.add(child);
            return this;
        }

        /**
         * чтобы получить ПРЯМОЙ порядок (не С-Б, а Б - С), придется развораивать цикл с конца
         * @param stack
         * @return
         */

        static List<Node> path(Deque<Node> stack) {
            List<Node> result = new ArrayList<>();
            while (!stack.isEmpty()) {
                Node node = stack.poll();
                System.out.println("NODE after polling: " + node);
                result.add(node);
                for (int i = node.children.size() - 1; i >= 0; i--) {
                    Node current = node.children.get(i);
                    System.out.println("current children number " + i + " of NODE above: " + current);
                    stack.push(current);
                }
            }
            return result;
        }

        @Override
        public String toString() {
            return name;
        }

        int index;

        @Override
        public Iterator<Node> iterator() {
            return new NodeIterator();
        }

        static class NodeIterator implements Iterator<Node> {
            NodeIterator() {

            }
            @Override
            public boolean hasNext() {
                return true;
            }

            @Override
            public Node next() {
                return null;
            }
        }

    }

    public static void main(String[] args) {
        Node a = new Node("A");
        Node b = new Node("B");
        Node c = new Node("C");
        Node d = new Node("D");
        Node e = new Node("E");
        Node f = new Node("F");

        a.add(b).add(c);
        b.add(d).add(e);
        c.add(f);

        Deque<Node> stack = new LinkedList<>();
        stack.push(a);
        System.out.println("STACK starts here: ");
        System.out.println(stack);

        List<Node> result = Node.path(stack);

        for (Node node : result) {
            System.out.print(node.name + " ");
        }

        System.out.println("DFS from A:");
        System.out.println("A -> B -> D -> E -> C -> F");
    }
}
