package ru.job4j.generics.shildbook;

public class NonGen {
    Object ob;

    NonGen(Object o) {
        ob = o;
    }

    Object getOb() {
        return ob;
    }

    void showType() {
        System.out.println("Объект ob относится к типу " + ob.getClass().getName());
    }
}

class NonGetDemo {

    public static void main(String[] args) {

        NonGen iOb;

        iOb = new NonGen(88);

        System.out.println(iOb.getClass().getName());
        System.out.println(iOb.ob.getClass().getName());
        Object obj = new Integer(1);

        iOb.showType();

        int v = (Integer) iOb.getOb();
        System.out.println("Значение: " + v);
        System.out.println();

        NonGen strOb = new NonGen("Тест без обощений");

        strOb.showType();

        String str = (String) strOb.getOb();
        System.out.println("Значение: " + str);
    }
}