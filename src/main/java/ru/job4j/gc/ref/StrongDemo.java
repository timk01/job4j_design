package ru.job4j.gc.ref;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class StrongDemo {

    /**
     * Сильная ссылка является "обычной" ссылкой, которую мы с вами привыкли создавать.
     * При данном типе ссылок объекты удаляются только если
     * на них нет сильной ссылки или они находятся в составе объекта на который нет ссылки.
     * Т.е. см. пример 2 - внутренние объекты нормально удалятся, но не сразу.
     * В 3 примере видимо не удалятся совсем, т.к. например при -Xmx4m -Xms4m
     * оно будет сразу выбивать ошибку
     * (впрочем и при дефолтных значениях у меня выбило - что видно по скрину)
     * <p> </p>
     * В первом методе, мы создаем объект (как анонимный) и далее их за'null'яем.
     * Вызываем сборщик мусора и ждем некоторое время. Объекты удаляются, т.к. ссылки на них null
     * <p> </p>
     * Во втором методе, мы создаем объекты вместе с вложенными.
     * !!!!! Удаляя внешние объекты как в примере выше удаляются и вложенные, не смотря на то что они не null.
     * <p> </p>
     * Проблема данного типа ссылок является то,
     * что если в программе есть неиспользуемые ссылки на созданные объекты, то они не будут удалены,
     * что приведет к утечке памяти, что в свою очередь может привести к ошибке OutOfMemoryException
     * - ситуации когда программе не хватает выделенной памяти
     * (в данном случае мы зануляем ссылки у внешнего объекта, но не ссылки у внутреннего - они-то и остаются)
     * @param args
     * @throws InterruptedException
     */

    public static void main(String[] args) throws InterruptedException {
        example3();
    }

    private static void example1() throws InterruptedException {
        Object[] objects = new Object[100];
        for (int i = 0; i < 100; i++) {
            objects[i] = new Object() {
                @Override
                protected void finalize() throws Throwable {
                    System.out.println("Object removed!");
                }
            };
        }
        for (int i = 0; i < 100; i++) {
            objects[i] = null;
        }
        System.gc();
        TimeUnit.SECONDS.sleep(5);
    }

    private static void example2() throws InterruptedException {
        Object[] objects = new Object[100];
        for (int i = 0; i < 100; i++) {
            Object object = new Object() {
                Object innerObject = new Object() {
                    @Override
                    protected void finalize() throws Throwable {
                        System.out.println("Remove inner object!");
                    }
                };
            };
            objects[i] = object;
        }
        for (int i = 0; i < 100; i++) {
            objects[i] = null;
        }
        System.gc();
        TimeUnit.SECONDS.sleep(5);
    }

    private static void example3() {
        List<String> strings = new ArrayList<>();
        while (true) {
            strings.add(String.valueOf(System.currentTimeMillis()));
        }
    }
}