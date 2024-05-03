package ru.job4j.pool;

public class StringPoolExample {
    public static void main(String[] args) {
        String string1 = new String("Hello"); 
        String string2 = new String("Hello"); 
        String string3 = "Hello"; 
        String string4 = "Hello"; 
        System.out.println(string1 == string2); 
        System.out.println(string3 == string4); 
        System.out.println(string1 == string3); 
        System.out.println(string2 == string4); 
    }
}