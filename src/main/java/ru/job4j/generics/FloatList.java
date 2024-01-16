package ru.job4j.generics;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class FloatList extends ArrayList<Float> {
    public static void main(String[] args) {
        ArrayList<Float> listOfNumbers = new FloatList();

        Class actual = listOfNumbers.getClass();
        System.out.println("actualClass is " + actual);
        Type genericSuperclass = actual.getGenericSuperclass();
        System.out.println("genericSuperclass " + genericSuperclass);
        ParameterizedType type = (ParameterizedType) actual.getGenericSuperclass();
        System.out.println(type);
        Type actualTypeArgument = type.getActualTypeArguments()[0];
        System.out.println(actualTypeArgument);
        Class parameter = (Class) type.getActualTypeArguments()[0];
        System.out.println(parameter);
    }
}