package ru.job4j.cashe;

import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.Map;

public abstract class AbstractCache<K, V> {

    private final Map<K, SoftReference<V>> cache = new HashMap<>();

    public final void put(K key, V value) {
        cache.put(key, new SoftReference<>(value));
    }

    /**
     * (V) cache.get(key) - не прокатывает.
     * по ТЗ "необходимо, чтобы можно было задать ключ получения объекта кеша, и, в случае если его нет в памяти,
     * задать поведение загрузки этого объекта в кеш"
     * <p> </p>
     * т.е. возможны 2 варианта сценария:
     * 1. Он есть по ключу в кеше (кеш = это мапа).
     * Значит, и в кеш такой файлик уже подгружен.
     * Тогда мы просто его получим как Строгн-референс
     * <p> </p>
     * 2. Его там (в кеше) нет, т.е. он не подгружен ИЛИ ЖЕ
     * кеш содержит ключ, НО референс уже затерт
     * <p> </p>
     * Для 2 частного варианта мотри метод get() с получением ссылки на объект из слебой ссылки:
     * Returns this reference object's referent.
     * If this reference object has been cleared, either by the program or by the garbage collector,
     * then this method returns null.
     * <p> </p>
     * "Если в кэше файла нет, кэш должен загрузить себе данные"
     * - получение происходит по имени файла и сперва нужно его загрузить
     * - загрузчиков может быть много, и они могут быть разными (у нас одна реализация)
     * - например DirFileCache получает кеш из директории (полного пути) и из файла
     * (т.е. если мы по ключу его не нашли - значит еще не подгрузили - и надо сперва это дело исправить)
     * <p> </p>
     * а т.к. у нас по прежнему основной метод - получить данные (вэлью) по ключу
     * - мы их и вернем (загрузчик подставляй какой хочешь, потому он и абстрактный, а классы наследующие нас
     * должны его будут реализовывать)
     * @param key
     * @return
     */

    public final V get(K key) {
        V value = null;
        if (!cache.containsKey(key) || cache.get(key).get() == null) {
            value = load(key);
            this.put(key, value);
        }
        return cache.get(key).get();
    }

    protected abstract V load(K key);
}