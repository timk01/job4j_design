package ru.job4j.generics.shildbook.inheritance2;

class NonGen {

    int num;

    NonGen(int i) {
        num = i;
    }

    int getNum() {
        return num;
    }
}

class Gen<T> extends NonGen {

    T ob;

    Gen(T o, int i) {
        super(i);
        ob = o;
    }

    T getOb() {
        return ob;
    }

}

class HierDemo2 {

    public static void main(String[] args) {

        Gen<String> w = new Gen<String>("Добро пожаловать", 47);

        System.out.print(w.getOb() + " ");
        System.out.println(w.getNum());
    }
}