package ru.job4j.ood.lcp.foodkeper.controlquality;

import org.junit.jupiter.api.Test;
import ru.job4j.ood.lcp.foodkeper.food.*;
import ru.job4j.ood.lcp.foodkeper.store.Shop;
import ru.job4j.ood.lcp.foodkeper.store.Trash;
import ru.job4j.ood.lcp.foodkeper.store.WareHouse;
import ru.job4j.ood.lcp.foodkeper.store.discount.ShopDiscounter;
import ru.job4j.ood.lcp.foodkeper.store.strategy.ShopStrategy;
import ru.job4j.ood.lcp.foodkeper.store.strategy.TrashStrategy;
import ru.job4j.ood.lcp.foodkeper.store.strategy.WareHouseStrategy;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.job4j.ood.lcp.foodkeper.TestFoodDateHelper.*;

class ControlQualityTest {

    @Test
    void overallControlQualityTest() {
        Food whiteBread = new Bread(
                "white bread",
                50.0,
                0,
                toCalendar(getReferenceDate().minusDays(10)),
                toCalendar(getReferenceDate().plusDays(90))
        );

        double mackerelPriceBefore = 300;
        Food smokedMackerel = new Fish(
                "mackerel",
                mackerelPriceBefore,
                0,
                toCalendar(getReferenceDate().minusDays(40)),
                toCalendar(getReferenceDate().plusDays(60))
        );

        double eggsPriceBefore = 50;
        Food farmerEggs = new Eggs(
                "farmer eggs",
                eggsPriceBefore,
                0,
                toCalendar(getReferenceDate().minusDays(80)),
                toCalendar(getReferenceDate().plusDays(20))
        );

        Food vacuumTanderloin = new Meat(
                "vacuum tanderloin",
                50.0,
                0,
                toCalendar(getReferenceDate().minusDays(110)),
                toCalendar(getReferenceDate().minusDays(10))
        );

        List<Food> foodList = List.of(
                whiteBread,
                smokedMackerel,
                farmerEggs,
                vacuumTanderloin
        );

        WareHouse wareHouse = new WareHouse();
        Shop shop = new Shop(new ShopDiscounter());
        Trash trash = new Trash();
        StoreAndStrategy wareHouseStrategy = new StoreAndStrategy(wareHouse, new WareHouseStrategy());
        StoreAndStrategy shopStrategy = new StoreAndStrategy(shop, new ShopStrategy());
        StoreAndStrategy trashStrategy = new StoreAndStrategy(trash, new TrashStrategy());
        List<StoreAndStrategy> strategies = List.of(
                wareHouseStrategy,
                shopStrategy,
                trashStrategy
        );

        ControlQuality controlQuality = new ControlQuality(foodList, strategies);
        controlQuality.putFoodInStore();

        assertThat(wareHouse.findAll()).hasSize(1);
        assertThat(shop.findAll()).hasSize(2);
        assertThat(trash.findAll()).hasSize(1);

        assertThat(wareHouse.findByName("white bread")).hasSize(1);
        assertThat(shop.findByName("mackerel")).hasSize(1);
        assertThat(shop.findByName("farmer eggs")).hasSize(1);
        assertThat(trash.findByName("vacuum tanderloin")).hasSize(1);

        double mackerelPriceAfter = smokedMackerel.getPrice();
        assertThat(shop.findByName("mackerel").get(0).getPrice()).isEqualTo(mackerelPriceAfter);
        double eggsPriceAfter = farmerEggs.getPrice();
        assertThat(shop.findByName("farmer eggs").get(0).getPrice()).isEqualTo(eggsPriceAfter);
    }
}