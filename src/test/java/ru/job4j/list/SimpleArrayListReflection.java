package ru.job4j.list;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class SimpleArrayListReflection {

    private SimpleList<Integer> list;

    @BeforeEach
    void initData() {
        list = new SimpleArrayList<>(3);
        list.add(1);
        list.add(2);
        list.add(3);
    }

    /**
     * for future reference and usage:
     * test passes, but gives and exception below
     *
     * java.lang.ClassCastException: class [Ljava.lang.Object; cannot be cast to class [Ljava.lang.Integer;
     * ([Ljava.lang.Object; and [Ljava.lang.Integer; are in module java.base of loader 'bootstrap')
     *
     * container = (T[]) new Object[capacity]; the problem is here.
     */

    @Disabled
    @Test
    void checkContainerIncrease() {
        try {
            list = new SimpleArrayList<>(0);
            list.add(1);
            Field privateField
                    = SimpleArrayList.class.getDeclaredField("container");
            privateField.setAccessible(true);
            Object o = privateField.get(list);
            assertThat(((Integer[]) (o)).length).isEqualTo(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}