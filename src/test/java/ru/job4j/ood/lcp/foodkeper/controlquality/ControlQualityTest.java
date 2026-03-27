package ru.job4j.ood.lcp.foodkeper.controlquality;

import org.junit.jupiter.api.Test;
import ru.job4j.ood.lcp.foodkeper.food.*;
import ru.job4j.ood.lcp.foodkeper.store.*;
import ru.job4j.ood.lcp.foodkeper.store.discount.ShopDiscounter;
import ru.job4j.ood.lcp.foodkeper.store.strategy.ShopStrategy;
import ru.job4j.ood.lcp.foodkeper.store.strategy.TrashStrategy;
import ru.job4j.ood.lcp.foodkeper.store.strategy.WareHouseStrategy;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.job4j.ood.lcp.foodkeper.TestFoodDateHelper.*;

class ControlQualityTest {

    @Test
    void distributionChecker() {
        List<Food> foodList = createFoodList();
        DataProvider dataProvider = new FixedDateProvider(getReferenceDate());
        WareHouse wareHouse = new WareHouse();
        Shop shop = new Shop(new ShopDiscounter(dataProvider));
        Trash trash = new Trash();
        StoreAndStrategy wareHouseStrategy = new StoreAndStrategy(wareHouse, new WareHouseStrategy(dataProvider));
        StoreAndStrategy shopStrategy = new StoreAndStrategy(shop, new ShopStrategy(dataProvider));
        StoreAndStrategy trashStrategy = new StoreAndStrategy(trash, new TrashStrategy(dataProvider));
        List<StoreAndStrategy> strategies = List.of(
                wareHouseStrategy,
                shopStrategy,
                trashStrategy
        );

        ControlQuality controlQuality = new ControlQuality(strategies);
        controlQuality.distribute(foodList);

        assertThat(wareHouse.findAll()).hasSize(1);
        assertThat(shop.findAll()).hasSize(2);
        assertThat(trash.findAll()).hasSize(1);

        assertThat(wareHouse.findByName("white bread")).hasSize(1);
        assertThat(shop.findByName("mackerel")).hasSize(1);
        assertThat(shop.findByName("farmer eggs")).hasSize(1);
        assertThat(trash.findByName("vacuum tanderloin")).hasSize(1);

        double mackerelPriceAfter = foodList.get(1).getPrice();
        assertThat(shop.findByName("mackerel").get(0).getPrice()).isEqualTo(mackerelPriceAfter);
        double eggsPriceAfter = foodList.get(2).getPrice();
        assertThat(shop.findByName("farmer eggs").get(0).getPrice()).isEqualTo(eggsPriceAfter);
    }

    @Test
    void resortChecker() {
        List<Food> foodList = createFoodList();

        WareHouse wareHouse = new WareHouse();
        Shop shop = new Shop(new ShopDiscounter(new FixedDateProvider(getReferenceDate())));
        Trash trash = new Trash();

        DataProvider initialDateProvider = new FixedDateProvider(getReferenceDate());
        List<StoreAndStrategy> initialStrategies = createStrategies(
                wareHouse, shop, trash, initialDateProvider
        );

        ControlQuality initialControlQuality = new ControlQuality(initialStrategies);
        initialControlQuality.distribute(foodList);

        assertThat(wareHouse.findAll()).hasSize(1);
        assertThat(shop.findAll()).hasSize(2);
        assertThat(trash.findAll()).hasSize(1);

        assertThat(wareHouse.findByName("white bread")).hasSize(1);
        assertThat(shop.findByName("mackerel")).hasSize(1);
        assertThat(shop.findByName("farmer eggs")).hasSize(1);
        assertThat(trash.findByName("vacuum tanderloin")).hasSize(1);

        DataProvider resortDateProvider = new FixedDateProvider(LocalDate.of(2026, 4, 22));
        List<StoreAndStrategy> resortStrategies = createStrategies(
                wareHouse, shop, trash, resortDateProvider
        );

        ControlQuality resortControlQuality = new ControlQuality(resortStrategies);
        resortControlQuality.resort();

        assertThat(wareHouse.findAll()).isEmpty();
        assertThat(shop.findAll()).hasSize(2);
        assertThat(trash.findAll()).hasSize(2);

        assertThat(wareHouse.findByName("white bread")).isEmpty();
        assertThat(shop.findByName("white bread")).hasSize(1);

        assertThat(shop.findByName("mackerel")).hasSize(1);

        assertThat(shop.findByName("farmer eggs")).isEmpty();
        assertThat(trash.findByName("farmer eggs")).hasSize(1);

        assertThat(trash.findByName("vacuum tanderloin")).hasSize(1);
    }

    private static List<Food> createFoodList() {
        Food whiteBread = new Bread(
                "white bread",
                50.0,
                0,
                toCalendar(getReferenceDate().minusDays(10)),
                toCalendar(getReferenceDate().plusDays(90))
        );

        Food smokedMackerel = new Fish(
                "mackerel",
                300.0,
                0,
                toCalendar(getReferenceDate().minusDays(40)),
                toCalendar(getReferenceDate().plusDays(60))
        );

        Food farmerEggs = new Eggs(
                "farmer eggs",
                50.0,
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

        return List.of(
                whiteBread,
                smokedMackerel,
                farmerEggs,
                vacuumTanderloin
        );
    }

    private static List<StoreAndStrategy> createStrategies(
            WareHouse wareHouse,
            Shop shop,
            Trash trash,
            DataProvider dataProvider
    ) {
        return List.of(
                new StoreAndStrategy(wareHouse, new WareHouseStrategy(dataProvider)),
                new StoreAndStrategy(shop, new ShopStrategy(dataProvider)),
                new StoreAndStrategy(trash, new TrashStrategy(dataProvider))
        );
    }

    static class FixedDateProvider implements DataProvider {
        private final LocalDate currentDate;

        FixedDateProvider(LocalDate currentDate) {
            this.currentDate = currentDate;
        }

        @Override
        public LocalDate getCurrentDate() {
            return currentDate;
        }
    }
}