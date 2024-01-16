package ru.job4j.generics.shildbook;

public class Gen<T> {
    private T obj;

    public Gen(T obj) {
        this.obj = obj;
    }

    public T getObj() {
        return obj;
    }

    public void showType() {
        System.out.println("type is " + obj.getClass().getName());
    }
}

class GenDemo {
    public static void main(String[] args) {
        Gen<Integer> genEx = new Gen<>(88);
        System.out.println(genEx.getObj());
        genEx.showType();
        Gen<String> strOb = new Gen<String>("Тест обобщений");
        System.out.println(strOb.getObj());
    }
}
