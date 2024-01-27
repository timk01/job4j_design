package ru.job4j.tree;

import java.util.Comparator;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

public class Main {
    public static void main(String[] args) {
        TreeMap<String, User> users = new TreeMap<>(Comparator.naturalOrder());

        users.put("Арбузов", new User("Арбузов"));
        users.put("Иванов", new User("Иванов"));
        users.put("Петров", new User("Петров"));
        users.put("Сидоров", new User("Сидоров"));
        users.put("Федоров", new User("Федоров"));

        SortedMap<String, User> usersSubset = users.subMap("Арбузов", "Петров");

        for (Map.Entry<String, User> entry : usersSubset.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }
}

class User {
    private String name;

    public User(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "User{"
                +
                "name='" + name + '\''
                +
                '}';
    }
}