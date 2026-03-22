package ru.job4j.ood.lcp.foodkeper.store;

import org.junit.jupiter.api.Test;
import ru.job4j.ood.lcp.foodkeper.food.Bread;
import ru.job4j.ood.lcp.foodkeper.food.Food;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class TrashTest {
    @Test
    public void whenAddNewItemThenTrashHasSameItem() {
        Store trash = new Trash();
        Food whiteBread = new Bread(
                "white bread",
                50.0,
                0,
                new GregorianCalendar(2023, Calendar.JUNE, 8, 17, 41),
                new GregorianCalendar(2023, Calendar.JUNE, 10, 17, 41)
        );
        trash.add(whiteBread);
        Food result = trash.findById(whiteBread.getId());
        assertThat(result.getName()).isEqualTo(whiteBread.getName());
    }

    @Test
    public void whenTestFindById() {
        Store trash = new Trash();
        Food whiteBread = new Bread(
                "white bread",
                50.0,
                0,
                new GregorianCalendar(2023, Calendar.JUNE, 8, 17, 41),
                new GregorianCalendar(2023, Calendar.JUNE, 10, 17, 41)
        );
        Food bread = trash.add(whiteBread);
        Food result = trash.findById(bread.getId());
        assertThat(result.getName()).isEqualTo(bread.getName());
    }

    @Test
    public void whenTestFindAll() {
        Store trash = new Trash();
        Food whiteBread = new Bread(
                "white bread",
                50.0,
                0,
                new GregorianCalendar(2023, Calendar.JUNE, 8, 17, 41),
                new GregorianCalendar(2023, Calendar.JUNE, 10, 17, 41)
        );
        Food brownBread = new Bread(
                "brown bread",
                50.0,
                0,
                new GregorianCalendar(2023, Calendar.JUNE, 8, 17, 41),
                new GregorianCalendar(2023, Calendar.JUNE, 10, 17, 41)
        );
        trash.add(whiteBread);
        trash.add(brownBread);
        Food result = trash.findAll().get(0);
        assertThat(result.getName()).isEqualTo(whiteBread.getName());
    }

    @Test
    public void whenTestFindByNameCheckArrayLength() {
        Store trash = new Trash();
        Food whiteBread = new Bread(
                "white bread",
                50.0,
                0,
                new GregorianCalendar(2023, Calendar.JUNE, 8, 17, 41),
                new GregorianCalendar(2023, Calendar.JUNE, 10, 17, 41)
        );
        Food brownBread = new Bread(
                "brown bread",
                50.0,
                0,
                new GregorianCalendar(2023, Calendar.JUNE, 8, 17, 41),
                new GregorianCalendar(2023, Calendar.JUNE, 10, 17, 41)
        );
        trash.add(whiteBread);
        trash.add(brownBread);
        trash.add(new Bread(null, 0.0, 0.0, null, null));
        trash.add(new Bread(
                "white bread",
                50.0,
                0,
                new GregorianCalendar(2023, Calendar.JUNE, 8, 17, 41),
                new GregorianCalendar(2023, Calendar.JUNE, 10, 17, 41)
        ));
        trash.add(new Bread(
                "brown bread",
                50.0,
                0,
                new GregorianCalendar(2023, Calendar.JUNE, 8, 17, 41),
                new GregorianCalendar(2023, Calendar.JUNE, 10, 17, 41)
        ));
        trash.add(new Bread(
                "white bread",
                50.0,
                0,
                new GregorianCalendar(2023, Calendar.JUNE, 8, 17, 41),
                new GregorianCalendar(2023, Calendar.JUNE, 10, 17, 41)
        ));
        List<Food> result = trash.findByName(whiteBread.getName());
        assertThat(result).hasSize(3);
    }

    @Test
    public void whenTestFindByNameCheckSecondItemName() {
        Store trash = new Trash();
        Food whiteBread = new Bread(
                "white bread",
                50.0,
                0,
                new GregorianCalendar(2023, Calendar.JUNE, 8, 17, 41),
                new GregorianCalendar(2023, Calendar.JUNE, 10, 17, 41)
        );
        Food brownBread = new Bread(
                "brown bread",
                50.0,
                0,
                new GregorianCalendar(2023, Calendar.JUNE, 8, 17, 41),
                new GregorianCalendar(2023, Calendar.JUNE, 10, 17, 41)
        );
        trash.add(whiteBread);
        trash.add(brownBread);
        trash.add(new Bread(
                "white bread",
                50.0,
                0,
                new GregorianCalendar(2023, Calendar.JUNE, 8, 17, 41),
                new GregorianCalendar(2023, Calendar.JUNE, 10, 17, 41)
        ));
        trash.add(new Bread(
                "brown bread",
                50.0,
                0,
                new GregorianCalendar(2023, Calendar.JUNE, 8, 17, 41),
                new GregorianCalendar(2023, Calendar.JUNE, 10, 17, 41)
        ));
        trash.add(new Bread(
                "white bread",
                50.0,
                0,
                new GregorianCalendar(2023, Calendar.JUNE, 8, 17, 41),
                new GregorianCalendar(2023, Calendar.JUNE, 10, 17, 41)
        ));
        trash.add(new Bread(null, 0.0, 0.0, null, null));
        List<Food> result = trash.findByName(brownBread.getName());
        assertThat(result.get(1).getName()).isEqualTo(brownBread.getName());
    }

    @Test
    public void whenDeleteItemIsSuccessful() {
        Store trash = new Trash();
        Food whiteBread = new Bread(
                "white bread",
                50.0,
                0,
                new GregorianCalendar(2023, Calendar.JUNE, 8, 17, 41),
                new GregorianCalendar(2023, Calendar.JUNE, 10, 17, 41)
        );
        trash.add(whiteBread);
        int id = whiteBread.getId();
        trash.delete(id);
        assertThat(trash.findById(id)).isNull();
    }

    @Test
    public void whenDeleteItemIsNotSuccessful() {
        Store trash = new Trash();
        Food whiteBread = new Bread(
                "white bread",
                50.0,
                0,
                new GregorianCalendar(2023, Calendar.JUNE, 8, 17, 41),
                new GregorianCalendar(2023, Calendar.JUNE, 10, 17, 41)
        );
        trash.add(whiteBread);
        trash.delete(1000);
        assertThat(trash.findById(whiteBread.getId()).getName()).isEqualTo("white bread");
    }
}