package ru.job4j.map;

import java.util.*;

public class User {
    private String name;
    private int children;
    private Calendar birthday;

    public User(String name, int children, Calendar birthday) {
        this.name = name;
        this.children = children;
        this.birthday = birthday;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBirthday(Calendar birthday) {
        this.birthday = birthday;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        User user = (User) o;
        return children == user.children && Objects.equals(name, user.name) && Objects.equals(birthday, user.birthday);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, children, birthday);
    }

    public static void main(String[] args) {
        Map<User, Object> users = new HashMap<>(16);
        Calendar birthday = Calendar.getInstance(new Locale("En"));
        User user1 = new User("Ivan123", 3, birthday);
        int user1HashCode = user1.hashCode();
        int user1Hash = (user1HashCode) ^ (user1HashCode >>> 16);
        int bucket1 = user1Hash & 15;
        users.put(user1, new Object());
        User user2 = new User("Ivan123", 3, birthday);
        int user2HashCode = user2.hashCode();
        int user2Hash = (user2HashCode) ^ (user2HashCode >>> 16);
        int bucket2 = user2Hash & 15;
        System.out.printf("BEFORE for user user2: %n hashCode: %d, Hash: %d, bucket: %d",
                user2HashCode, user2Hash, bucket2);
        users.put(user2, new Object());
        System.out.println(user1);
        System.out.println(user2);
        System.out.printf("for user user1: %n hashCode: %d, Hash: %d, bucket: %d%n",
                user1HashCode, user1Hash, bucket1);
        System.out.printf("for user user2: %n hashCode: %d, Hash: %d, bucket: %d",
                user2HashCode, user2Hash, bucket2);
        System.out.println();
        users.entrySet().stream().forEach(System.out::println);
        birthday = Calendar.getInstance(new Locale("En"));
        user2.setBirthday(birthday);
        System.out.println("found user2? " + users.get(user2));
    }
}

