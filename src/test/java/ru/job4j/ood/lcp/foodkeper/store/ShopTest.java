package ru.job4j.ood.lcp.foodkeper.store;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.job4j.ood.lcp.foodkeper.food.Bread;
import ru.job4j.ood.lcp.foodkeper.food.Food;
import ru.job4j.ood.lcp.foodkeper.store.discount.DiscountPolicy;
import ru.job4j.ood.lcp.foodkeper.store.discount.ShopDiscounter;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.withPrecision;

class ShopTest {
    private static final double DEFAULT_PRECISION = 0.0001;

    private static DiscountPolicy shopDiscounter;

    @BeforeAll
    static void prepareDiscounter() {
        shopDiscounter = new ShopDiscounter();
    }

    @Test
    public void whenAddNewItemThenShopHasSameItem() {
        LocalDate now = LocalDate.now();
        Food whiteBread = new Bread(
                "white bread",
                50.0,
                0,
                nowToGregCalendar(now.minusDays(40)),
                nowToGregCalendar(now.plusDays(10))
        );
        Store shop = new Shop(shopDiscounter);
        shop.add(whiteBread);
        Food result = shop.findById(whiteBread.getId());
        assertThat(result.getName()).isEqualTo(whiteBread.getName());
    }

    @Test
    public void whenAddItemWithBadExpirationThenItemHasDiscountInShop() {
        LocalDate now = LocalDate.now();
        double initialPrice = 50;
        Food whiteBread = new Bread(
                "white bread",
                initialPrice,
                0,
                nowToGregCalendar(now.minusDays(40)),
                nowToGregCalendar(now.plusDays(10))
        );
        double finalPrice = 40;
        Store shop = new Shop(shopDiscounter);
        shop.add(whiteBread);
        Food result = shop.findById(whiteBread.getId());
        assertThat(result.getPrice()).isEqualTo(finalPrice, withPrecision(DEFAULT_PRECISION));
    }

    @Test
    public void whenAddItemWithDecentExpirationThenItemHasNoDiscountInShop() {
        LocalDate now = LocalDate.now();
        double initialPrice = 50;
        Food whiteBread = new Bread(
                "white bread",
                initialPrice,
                0,
                nowToGregCalendar(now.minusDays(10)),
                nowToGregCalendar(now.plusDays(30))
        );
        double finalPrice = 50;
        Store shop = new Shop(shopDiscounter);
        shop.add(whiteBread);
        Food result = shop.findById(whiteBread.getId());
        assertThat(result.getPrice()).isEqualTo(finalPrice, withPrecision(DEFAULT_PRECISION));
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

    static GregorianCalendar nowToGregCalendar(LocalDate date) {
        ZonedDateTime dateTime = date.atStartOfDay().atZone(ZoneId.systemDefault());
        return GregorianCalendar.from(dateTime);
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