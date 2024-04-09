package ru.job4j.gc;

import java.util.StringJoiner;

public class Example {
    public static void main(String[] args) {
        int x = 0;
        String str = "abc";
        A a = new A(15);
        System.out.println(x);
        System.out.println(a);
        System.out.println(str);
        Example example = new Example();
        example.action(a, x, str);
        System.out.println(a);
        System.out.println(x);
        System.out.println(str);
    }

    public void action(A parameter, int x, String str) {
        parameter.setIntVal(20);
        x = 20;
        str = "bcd";
    }
}

class A {
    int intVal = 10;
    A(int intVal) {
        this.intVal = intVal;
    }

    public void setIntVal(int intVal) {
        this.intVal = intVal;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", A.class.getSimpleName() + "[", "]")
                .add("intVal=" + intVal)
                .toString();
    }
}