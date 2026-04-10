package ru.job4j.newcoll.tree.representation;

import java.util.ArrayList;
import java.util.List;

public class PrintTree {

    private PrintTree() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * 2 этапа:
     * 1. заполнение данными структуры дерева
     * 2. форматирование данных
     * <p>
     * List<List<String>> lines - это на самом деле лист листов подсписков дерева. 1 внутренний лист = 1 уровень дерева
     * List<VisualNode> level - текущий уровень (с чем сейчас работаем)
     * List<VisualNode> next - следующий (собирается по деткам предыдущего)
     * уже внутри: List<String> line - конкретный урвоень (текста)
     * <p>
     * 0. в старт кладем только рут (как и в бфт/дсф)
     * 1. while (nodeCount != 0) - пока вообще есть то перебирать (аналог изЕмпти)
     * - создал List<String> line - как раз конкретный уровень
     * прохожусь по конкретным НОДАМ теекущего уровня (цель - засунуть инфу из него в line-текстовый)
     * <p>
     * для каждой ноды:
     * - если она нулль, значит и в лайне она будет нулль, и ее потомки будут нулль (заглушки, для полной структуры)
     * - еелси нет (там что-то есть), извлекаем ее, кладем в ноду уже следующего уровня деток и крутим счетчик
     * <p>
     * в итоге после цикла внутреннего::
     * level = текущий уровень, который мы только что обработали (= взяли всю инфу)
     * next = следующий уровень, который мы собрали из детей (=собрали)
     * <p>
     * обработали текущий уровень → следующий стал текущим → старый текущий очистили и будем использовать как новый next
     * <p>
     * lines.add(line);
     * List<VisualNode> temp = level; //запомнили старый уровень
     * level = next; //зяли инфу из нового
     * next = temp; //положили в тмп старую
     * next.clear();
     * <p>
     * здесь ка вывалились: добавили всю инфу ИЗ ншего массива теекущего уровня в lines - лист листов
     * фатиктичеси обнулили некст, предварительно свапнув листы местами (некст уже не нужен, мы же работаем c level)
     * ((делаетсся чтобы не плодить новые списки))
     * <p>
     * 2 чатсь рисуеет текстом
     * lines уже содержит дерево по уровням; (к этому моменту у нас лист листов lines)
     * perPiece — это сколько ширины выделить на один “кусок” строки;
     * внешний цикл идет по уровням;
     * первый внутренний блок рисует соединительные линии между уровнями;
     * второй внутренний блок печатает сами значения узлов;
     * perPiece /= 2 — с каждым уровнем ширина между узлами уменьшается.
     *
     * @param root
     * @return
     */

    public static String getTreeDisplay(VisualNode root) {
        StringBuilder buffer = new StringBuilder();
        List<List<String>> lines = new ArrayList<>();
        List<VisualNode> level = new ArrayList<>();
        List<VisualNode> next = new ArrayList<>();
        level.add(root);
        int nodeCount = 1;
        int widest = 0;
        while (nodeCount != 0) {
            nodeCount = 0;
            List<String> line = new ArrayList<>();
            for (VisualNode node : level) {
                if (node == null) {
                    line.add(null);
                    next.add(null);
                    next.add(null);
                } else {
                    String key = node.getText();
                    line.add(key);
                    if (key.length() > widest) {
                        widest = key.length();
                    }
                    next.add(node.getLeft());
                    if (node.getLeft() != null) {
                        nodeCount++;
                    }
                    next.add(node.getRight());
                    if (node.getRight() != null) {
                        nodeCount++;
                    }
                }
            }
            if (widest % 2 == 1) {
                widest++;
            }
            lines.add(line);
            List<VisualNode> temp = level;
            level = next;
            next = temp;
            next.clear();
        }

        int perPiece = lines.get(lines.size() - 1).size() * (widest + 4);
        for (int i = 0; i < lines.size(); i++) {
            List<String> line = lines.get(i);
            int hpw = (int) Math.floor(perPiece / 2f) - 1;
            if (i > 0) {
                for (int j = 0; j < line.size(); j++) {
                    char symbol = ' ';
                    if (j % 2 == 1) {
                        if (line.get(j - 1) != null) {
                            symbol = (line.get(j) != null) ? '┴' : '╯';
                        } else if (line.get(j) != null) {
                            symbol = '╰';
                        }
                    }
                    buffer.append(symbol);
                    if (line.get(j) == null) {
                        buffer.append(" ".repeat(Math.max(0, perPiece - 1)));
                    } else {
                        buffer.append(String.valueOf(j % 2 == 0 ? " " : '─')
                                .repeat(Math.max(0, hpw)));
                        buffer.append(j % 2 == 0 ? '╭' : '╮');
                        buffer.append(String.valueOf(j % 2 == 0 ? '─' : " ")
                                .repeat(Math.max(0, hpw)));
                    }
                }
                buffer.append('\n');
            }
            for (String word : line) {
                if (word == null) {
                    word = "";
                }
                double space = perPiece / 2f - word.length() / 2f;
                buffer.append(" ".repeat(Math.max(0, (int) Math.ceil(space))))
                        .append(word)
                        .append(" ".repeat(Math.max(0, (int) Math.floor(space))));
            }
            buffer.append('\n');
            perPiece /= 2;
        }
        return buffer.toString();
    }
}