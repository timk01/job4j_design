package ru.job4j.gc.ref;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.List;

/**
 * Soft reference objects, which are cleared at the discretion of the garbage collector in response
 * to memory demand. Soft references are most often used to implement memory-sensitive caches.
 *
 * Suppose that the garbage collector determines at a certain point in time that an object is softly reachable.
 * At that time it may choose to clear atomically all soft references to that object and all soft references
 * to any other softly-reachable objects from which that object is reachable through a chain of strong references.
 *
 * At the same time or at some later time it will enqueue those newly-cleared soft references that are registered
 * with reference queues.
 *
 * !!! All soft references to softly-reachable objects are guaranteed to have been cleared before the virtual machine
 * throws an OutOfMemoryError.
 */

public class SoftDemo {

    /**
     * Going from strongest to weakest, the different levels of reachability reflect the life cycle of an object.
     * They are operationally defined as follows:
     * <p></p>
     * An object is strongly reachable if it can be reached by some thread without traversing any reference objects.
     * A newly-created object is strongly reachable by the thread that created it.
     * <p></p>
     * An object is softly reachable if it is not strongly reachable but can be reached by traversing a soft reference.
     * <p></p>
     * An object is weakly reachable if it is neither strongly nor softly reachable
     * but can be reached by traversing a weak reference.
     * When the weak references to a weakly-reachable object are cleared, the object becomes eligible for finalization.
     * <p></p>
     * An object is phantom reachable if it is neither strongly, softly, nor weakly reachable,
     * it has been finalized, and some phantom reference refers to it.
     * <p></p>
     * Finally, an object is unreachable, and therefore eligible for reclamation,
     * when it is not reachable in any of the above ways.
     * @param args
     */

    public static void main(String[] args) {
        example2();
    }

    /**
     * !!! Объекты, на которые ссылаются безопасные ссылки, удаляются, только если JVM не хватает памяти,
     * т.е. они могут пережить более одной сборки мусора.
     * <p></p>
     * Данный тип ссылок подходит для реализации кэша - такой структуры данных,
     * при которой часть данных запоминается, а потом часто переиспользуется.
     * <p></p>
     * Например, можно запоминать данные из файлов или тяжелых запросов.
     * <p></p>
     * !!!!! При нехватке памяти JVM может удалить объекты по этим ссылкам, если на них нет сильных ссылок.
     * <p></p>
     * Есть контракт для данного типа ссылок: GC гарантировано удалит из кучи все объекты,
     * доступные только по soft-ссылке, перед тем, как бросит OutOfMemoryError
     * <p></p>
     * В первом методе, несмотря на то, что мы за'null'уляем сильную ссылку, на объект остается безопасная ссылка,
     * а это значит, что объект будет удален !только! при нехватке памяти.
     */

    private static void example1() {
        Object strong = new Object();
        SoftReference<Object> soft = new SoftReference<>(strong);
        strong = null;
        System.out.println(soft.get());
    }

    /**
     * Во втором методе мы создаем много объектов, но на них ссылается безопасная ссылка.
     * При создании большого количества объектов при малом heap мы увидим, что объекты начнут удаляться,
     * т.к. перестанет хватать памяти.
     * (а это не в наших интересах)
     */

    private static void example2() {
        List<SoftReference<Object>> objects = new ArrayList<>();
        for (int i = 0; i < 100_000_000; i++) {
            objects.add(new SoftReference<Object>(new Object() {
                String value = String.valueOf(System.currentTimeMillis());

                @Override
                protected void finalize() throws Throwable {
                    System.out.println("Object removed!");
                }
            }));
        }
        System.gc();
        int liveObject = 0;
        for (SoftReference<Object> ref : objects) {
            Object object = ref.get();
            if (object != null) {
                liveObject++;
            }
        }
        System.out.println(liveObject);
    }

    /**
     * Корректным использованием безопасных ссылок является сначала получение сильной ссылки на данные,
     * а потом работа с сильной ссылкой (вместо работы с ссылкой напрямую, что неверно - см. unsafe())
     * <p></p>
     * Это гарантирует, что в интервалах получения сильной ссылки из безопасной GC не затрет объект.
     * Что может быть легко видно если размеры памяти небольшие.
     * Это касается не только локальных переменных, но и возвращаемых значений и аргументов.
     */

    private static void unsafe() {
        List<SoftReference<Object>> someData = new ArrayList<>();
        if (someData.get(0).get() != null) {
            System.out.println("do some logic");
        } else {
            System.out.println("do some other logic");
        }
        someData.get(0).get();
    }

    private static void safe() {
        List<SoftReference<Object>> someData = new ArrayList<>();
        Object strong = someData.get(0).get();
        if (strong != null) {
            System.out.println("do some logic");
        } else {
            System.out.println("do some other logic");
        }
    }
}