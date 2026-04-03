package ru.job4j.algo.sorting;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class QuickListTest {
    @Test
    void whenSortThenOk() {

        User user1 = new User(1, "11");
        User user2 = new User(1, "22");
        User user3 = new User(3, "33");
        User user4 = new User(-4, "44");
        User user5 = new User(4, "55");
        User user6 = new User(6, "66");
        Comparator<User> comparator = Comparator.comparingInt(User::getId);
        List<User> listUser = new ArrayList<>();
        listUser.add(user3);
        listUser.add(user5);
        listUser.add(user1);
        listUser.add(user4);
        listUser.add(user6);
        listUser.add(user2);
        QuickList.quickSort(listUser, comparator);
        List<Integer> result = listUser.stream().map(User::getId).toList();
        assertThat(result).containsExactly(-4, 1, 1, 3, 4, 6);
    }

    @Test
    void whenCompoundOrderingThenOk() {
        Comparator<Human> humanName = (human1, human2) -> human1.name().compareTo(human2.name());
        Comparator<Human> humanAge = (human1, human2) -> Integer.compare(human1.age(), human2.age());
        List<Human> humans = new ArrayList<>();
        humans.add(new Human(30, "Petr"));
        humans.add(new Human(25, "Anna"));
        humans.add(new Human(30, "Boris"));
        humans.add(new Human(25, "Zoe"));
        humans.add(new Human(40, "Ivan"));
        humans.add(new Human(30, "Petr"));
        QuickList.quickSort(humans, humanAge.thenComparing(humanName));
        List<Human> expected = List.of(
                new Human(25, "Anna"),
                new Human(25, "Zoe"),
                new Human(30, "Boris"),
                new Human(30, "Petr"),
                new Human(30, "Petr"),
                new Human(40, "Ivan")
        );
        assertThat(humans).containsExactlyElementsOf(expected);
    }

    @Test
    void whenListContainsOneElement() {
        List<Human> humans = new ArrayList<>();
        humans.add(new Human(30, "Petr"));
        QuickList.quickSort(humans, (human1, human2) -> Integer.compare(human1.age(), human2.age()));
        List<Human> expected = List.of(
                new Human(30, "Petr")
        );
        assertThat(humans).containsExactlyElementsOf(expected);
    }

    @Test
    void whenCompoundHumanTestThenOk() {
        Comparator<Integer> comparator = (a, b) -> b - a;
        List<Integer> list = new ArrayList<>();
        list.add(-2);
        list.add(-8);
        list.add(1);
        list.add(0);
        list.add(10);
        list.add(1);
        list.add(8);
        QuickList.quickSort(list, comparator);
        System.out.println(list);
        assertThat(list).containsExactly(10, 8, 1, 1, 0, -2, -8);
    }

    private static class User {
        private Integer id;
        private String name;

        public User(int id, String name) {
            this.id = id;
            this.name = name;
        }

        public int getId() {
            return id;
        }
    }

    public record Human(int age, String name) {
    }

}