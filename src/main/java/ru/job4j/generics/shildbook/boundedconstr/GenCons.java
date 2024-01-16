package ru.job4j.generics.shildbook.boundedconstr;

class GenCons {

    private double val;

    <T extends Number> GenCons(T arg) {
        val = arg.byteValue();
    }

    void showVal() {
        System.out.println("val: " + val);
    }
}

class GenConsDemo {

    public static void main(String[] args) {

        GenCons test1 = new GenCons(100);
        GenCons test2 = new GenCons(123.5F);

        test1.showVal();
        test2.showVal();
    }
}