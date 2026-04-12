package ru.job4j.algo.hash;

import java.util.*;
import java.util.stream.Collectors;

public class FindAnagram {

    /**
     * Суть. Мы двигаемся по внешнему циклу как оригинальный набор символов,
     * а внутри будем проверять побуквенно - есть ли вхождение символа из внешней строки в строку внутреннюю
     * например а-б-ц-д -> а-ц
     * а (внешняя) входит во внутреннюю подстроку ? да, значит идем дальше.
     * б (внешняя) - входит во внутреннюю подстроку ? нет, значит этот участок точно не анаграмма (т.е. можно брейк)
     * <p>
     * теперь. поскольку символы могт встреаться более 1 раза, нам нужно как-то работть с подстрокой
     * (т.е. буквально изменять копию подстроки, вычленяя оттуда символ, если нашли таковой во внешней)
     * самое удобное - создать хешмапу: символ + количество в подсроке.
     * <p>
     * общаяя логика: идем по подстроке внутри по текущим символам "внешки", именно посимвольно,
     * если есть вхождение в хешмапу (а оно может быть болеее раза)
     * - обнуляем елси 1, и уменьшаем на 1 если "еще осталось".
     * в итоге, если это анаграмма, в копии хешмапы вообще н должно осаться элементов
     * <p>
     * в конце классическое фолс-тру, а перед этим в конце внешнего цикла проверка на мапу.
     * и если он пустая, кладем индекс первого символа, как и надо по задаче
     * <p>
     * нюансы:
     * 1. char c = original[j]; - копия создается по внешнем, т.к. во внутреннем каждый раз может меняться
     * 2. i <= original.length - sub.length - защита от исключения и чтобы не бегать дольше ем надо
     * 3. самое важное: int j = i - берем элемент подмассива (проверяем ж их!), но:
     * 4. int j = i - он будет равен элементу внешнему (индекс), а т.к. мы сдвигаем ай, двигаем джей,
     * соответвтенно и j < i + sub.length
     * 5. charOccurenceCopy.computeIfPresent - как работет. берет ключ и бифункцию
     * ей нужна лямбда. из 2 элементов - ключ + старое значение (то самое, что мы обычно полуем по ключу)
     * соответственно, логик проста: уменьшили старое вхождение элемент на 1 (он же есть в оригинальной стринге!)
     * если там больше у символа элементов нет - положили нуль, если есть - новое значение
     *
     * @param str
     * @param substr
     * @return
     */
    private static List<Integer> findAnagramsBruteForce(String str, String substr) {
        char[] original = str.toCharArray();
        char[] sub = substr.toCharArray();

        Map<Character, Integer> charOccurence = toHashMapOriginal(sub);

        Map<Character, Integer> charOccurenceCopy = new HashMap<>(charOccurence);
        boolean isAnagramFound = false;

        List<Integer> result = new ArrayList<>();
        for (int i = 0; i <= original.length - sub.length; i++) {
            charOccurenceCopy = new HashMap<>(charOccurence);
            for (int j = i; j < i + sub.length; j++) {
                char c = original[j];
                if (charOccurenceCopy.containsKey(c)) {
                    charOccurenceCopy.computeIfPresent(c,
                            (key, oldValue) -> {
                                int newValue = oldValue - 1;
                                return newValue == 0 ? null : newValue;
                            });
                } else {
                    break;
                }

            }
            if (charOccurenceCopy.isEmpty()) {
                isAnagramFound = true;
                result.add(i);
            }
        }
        return isAnagramFound ? result : Collections.emptyList();
    }

    private static Map<Character, Integer> toHashMapOriginal(char[] sub) {
        Map<Character, Integer> charOccurence = new HashMap<>();
        for (Character key : sub) {
            charOccurence.put(key, charOccurence.getOrDefault(key, 0) + 1);
        }
        return charOccurence;
    }

    /**
     * допущение здесь: в подстринге символы не повторяются
     * <p>
     * идея та же: мы взяли из подстринги - сделали сет, его же будем сравнивать с сетом из того куска стринги,
     * что мы проверили
     * ДАЛЕЕ - мы сравниваем уже не поисмвольно, а группы символов! (в этом и ключевое отличие от брудфорса)
     * cbaebabacd И abc
     * т.е. н 1 круге сравниваем cba и abc
     * на 2 круге: свдигаем "окно" на +1 (как начало, так и конец). т.е. начиная уже с (1, 4) - получааем bae
     * на 3... aeb
     * <p>
     * что нм нужно ? цикл. причем цикл сразу органичен i <= str.length() - windowSize (виндоусайз фиксирован)
     * теперь что самое пожаалуй важное. логика сдвига окна
     * String windowWeChecked = str.substring(0, windowSize + 0);
     * - на начальном этапе вычленяет (0, 3) - 0, 1 и 2 индексы. надо сдвигать на ай, потому то это ... цикл.
     * <p>
     * ну а substrSet.equals(toCharsSet(windowWeChecked) - будет рабоатть быстро ?
     * нет, не очень... операция поиска/вставки - это константа. но пройтись по всем элементам подстроки - это уже
     * линейная.
     * при этом, substr как размер НЕ фиксирован и приходит извне (т.е. мы не может сказать, то он "всегда 3")
     * т.е. это не констаанта. итого здесь - о(эм)
     * Хотя, спрведливости ради, при мленьком размере substr - это близко к линейной
     * <p>
     * плюс, еще стоит equals учесть - он сравнивает элементы одного сета с другим, то по-сути тоже перебор всего сета.
     * или это тоже линейная
     * или, финальная O(n * m) в-общем, если маленький сабстринг - ближе к линейной
     *
     * @param str
     * @param substr
     * @return
     */
    public static List<Integer> findAnagramsWithSet(String str, String substr) {
        List<Integer> result = new ArrayList<>();
        Set<Character> substrSet = toCharsSet(substr);
        int windowSize = substr.length();
        for (int i = 0; i <= str.length() - windowSize; i++) {
            String windowWeChecked = str.substring(i, windowSize + i);
            if (substrSet.equals(toCharsSet(windowWeChecked))) {
                result.add(i);
            }
        }
        return result;
    }

    private static Set<Character> toCharsSet(String str) {
        return str.chars()
                .mapToObj(ch -> (char) ch)
                .collect(Collectors.toSet());
    }

    /**
     * equals(): The simplest method.
     * It checks if both maps have the same size and identical key-value mappings, regardless of order.
     * @param str
     * @param substr
     * @return
     */

    public static List<Integer> findAnagramsWithHashMap(String str, String substr) {
        List<Integer> result = new ArrayList<>();
        Map<Character, Integer> subStrMap = toHashMap(substr);
        int windowSize = substr.length();
        for (int i = 0; i <= str.length() - windowSize; i++) {
            String windowWeChecked = str.substring(i, windowSize + i);
            if (subStrMap.equals(toHashMap(windowWeChecked))) {
                result.add(i);
            }
        }
        return result;
    }

    /**
     * как и выше, получаем стрим. интов. конвертим в чар.
     * собираем. куда ? в мапу.
     * тм есть несколько вариантов, нам нужно:
     * 1) вернуть чар, 2) вернуть значение (если нашли чар - его знаачение = 1), 3) взять старое значение и новое
     * (т.е. по факту 1+1)
     * <p>
     * valueMapper – a mapping function to produce values
     * mergeFunction – a merge function,
     * used to resolve collisions between values associated with the same key,
     * as supplied to Map.merge(Object, Object, BiFunction)
     * здесь: ch -> 1 -- значение если элемент ДО не встречался. (нью)
     * ОЛД - то значение, что уже было
     *
     * @param str
     * @return
     */

    private static Map<Character, Integer> toHashMap(String str) {
        Map<Character, Integer> charOccurence = new HashMap<>();
        charOccurence = str.chars()
                .mapToObj(ch -> (char) ch)
                .collect(Collectors.toMap(
                        ch -> ch,
                        ch -> 1,
                        (oldValue, newValue) -> oldValue + newValue
                ));
        return charOccurence;
    }

    public static void main(String[] args) {
        String s = "cbaebabacd";
        String p = "abc";
        List<Integer> anagramIndices = findAnagramsWithSet(s, p);
        System.out.println(anagramIndices);
    }
}