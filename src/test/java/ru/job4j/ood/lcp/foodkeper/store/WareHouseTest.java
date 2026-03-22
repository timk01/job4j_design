package ru.job4j.ood.lcp.foodkeper.store;

import org.junit.jupiter.api.Test;
import ru.job4j.ood.lcp.foodkeper.food.Bread;
import ru.job4j.ood.lcp.foodkeper.food.Food;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class WareHouseTest {
    @Test
    public void whenAddNewItemThenWareHouseHasSameItem() {
        Store wareHouse = new WareHouse();
        Food whiteBread = new Bread(
                "white bread",
                50.0,
                0,
                new GregorianCalendar(2023, Calendar.JUNE, 8, 17, 41),
                new GregorianCalendar(2023, Calendar.JUNE, 10, 17, 41)
        );
        wareHouse.add(whiteBread);
        Food result = wareHouse.findById(whiteBread.getId());
        assertThat(result.getName()).isEqualTo(whiteBread.getName());
    }

    @Test
    public void whenTestFindById() {
        Store wareHouse = new WareHouse();
        Food whiteBread = new Bread(
                "white bread",
                50.0,
                0,
                new GregorianCalendar(2023, Calendar.JUNE, 8, 17, 41),
                new GregorianCalendar(2023, Calendar.JUNE, 10, 17, 41)
        );
        Food bread = wareHouse.add(whiteBread);
        Food result = wareHouse.findById(bread.getId());
        assertThat(result.getName()).isEqualTo(bread.getName());
    }

    @Test
    public void whenTestFindAll() {
        Store wareHouse = new WareHouse();
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
        wareHouse.add(whiteBread);
        wareHouse.add(brownBread);
        Food result = wareHouse.findAll().get(0);
        assertThat(result.getName()).isEqualTo(whiteBread.getName());
    }

    @Test
    public void whenTestFindByNameCheckArrayLength() {
        Store wareHouse = new WareHouse();
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
        wareHouse.add(whiteBread);
        wareHouse.add(brownBread);
        wareHouse.add(new Bread(null, 0.0, 0.0, null, null));
        wareHouse.add(new Bread(
                "white bread",
                50.0,
                0,
                new GregorianCalendar(2023, Calendar.JUNE, 8, 17, 41),
                new GregorianCalendar(2023, Calendar.JUNE, 10, 17, 41)
        ));
        wareHouse.add(new Bread(
                "brown bread",
                50.0,
                0,
                new GregorianCalendar(2023, Calendar.JUNE, 8, 17, 41),
                new GregorianCalendar(2023, Calendar.JUNE, 10, 17, 41)
        ));
        wareHouse.add(new Bread(
                "white bread",
                50.0,
                0,
                new GregorianCalendar(2023, Calendar.JUNE, 8, 17, 41),
                new GregorianCalendar(2023, Calendar.JUNE, 10, 17, 41)
        ));
        List<Food> result = wareHouse.findByName(whiteBread.getName());
        assertThat(result).hasSize(3);
    }

    @Test
    public void whenTestFindByNameCheckSecondItemName() {
        Store wareHouse = new WareHouse();
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
        wareHouse.add(whiteBread);
        wareHouse.add(brownBread);
        wareHouse.add(new Bread(
                "white bread",
                50.0,
                0,
                new GregorianCalendar(2023, Calendar.JUNE, 8, 17, 41),
                new GregorianCalendar(2023, Calendar.JUNE, 10, 17, 41)
        ));
        wareHouse.add(new Bread(
                "brown bread",
                50.0,
                0,
                new GregorianCalendar(2023, Calendar.JUNE, 8, 17, 41),
                new GregorianCalendar(2023, Calendar.JUNE, 10, 17, 41)
        ));
        wareHouse.add(new Bread(
                "white bread",
                50.0,
                0,
                new GregorianCalendar(2023, Calendar.JUNE, 8, 17, 41),
                new GregorianCalendar(2023, Calendar.JUNE, 10, 17, 41)
        ));
        wareHouse.add(new Bread(null, 0.0, 0.0, null, null));
        List<Food> result = wareHouse.findByName(brownBread.getName());
        assertThat(result.get(1).getName()).isEqualTo(brownBread.getName());
    }

    @Test
    public void whenDeleteItemIsSuccessful() {
        Store wareHouse = new WareHouse();
        Food whiteBread = new Bread(
                "white bread",
                50.0,
                0,
                new GregorianCalendar(2023, Calendar.JUNE, 8, 17, 41),
                new GregorianCalendar(2023, Calendar.JUNE, 10, 17, 41)
        );
        wareHouse.add(whiteBread);
        int id = whiteBread.getId();
        wareHouse.delete(id);
        assertThat(wareHouse.findById(id)).isNull();
    }

    @Test
    public void whenDeleteItemIsNotSuccessful() {
        Store wareHouse = new WareHouse();
        Food whiteBread = new Bread(
                "white bread",
                50.0,
                0,
                new GregorianCalendar(2023, Calendar.JUNE, 8, 17, 41),
                new GregorianCalendar(2023, Calendar.JUNE, 10, 17, 41)
        );
        wareHouse.add(whiteBread);
        wareHouse.delete(1000);
        assertThat(wareHouse.findById(whiteBread.getId()).getName()).isEqualTo("white bread");
    }
}