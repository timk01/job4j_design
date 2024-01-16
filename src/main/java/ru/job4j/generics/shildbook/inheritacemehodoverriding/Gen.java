package ru.job4j.generics.shildbook.inheritacemehodoverriding;

class Gen<T> {

    T ob;

    Gen(T o) {
        ob = o;
    }

    T getOb() {
        System.out.print("Мметод getOb() из класса Gen: ");
        return ob;
    }

}

class Gen2<T> extends Gen<T> {

    Gen2(T o) {
        super(o);
    }

    T getOb() {
        System.out.print("Мметод getOb() из класса Gen2: ");
        return ob;
    }
}

class OverrideDemo {

    public static void main(String[] args) {

        Gen<Integer> iOb = new Gen<Integer>(88);

        Gen2<Integer> iOb2 = new Gen2<Integer>(99);

        Gen2<String> strOb2 = new Gen2<String>("Тест обощений");

        System.out.println(iOb.getOb());
        System.out.println(iOb2.getOb());
        System.out.println(strOb2.getOb());
    }
}