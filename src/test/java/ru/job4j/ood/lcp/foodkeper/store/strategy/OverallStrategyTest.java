package ru.job4j.ood.lcp.foodkeper.store.strategy;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.job4j.ood.lcp.foodkeper.food.Bread;
import ru.job4j.ood.lcp.foodkeper.food.Food;
import ru.job4j.ood.lcp.foodkeper.store.ShopDataProvider;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.job4j.ood.lcp.foodkeper.TestFoodDateHelper.getReferenceDate;
import static ru.job4j.ood.lcp.foodkeper.TestFoodDateHelper.toCalendar;

class OverallStrategyTest {

    @ParameterizedTest
    @MethodSource("strategyData")
    void whenStrategyDataIsProvidedThenCalculatedResultIsCorrect(Strategy strategy, Food food, boolean result) {
        assertThat(strategy.accept(food)).isEqualTo(result);
    }

    static Stream<Arguments> strategyData() {
        Food food1 = new Bread(
                "white bread",
                50.0,
                0,
                toCalendar(getReferenceDate().minusDays(40)),
                toCalendar(getReferenceDate().plusDays(60))
        );
        Food food2 = new Bread(
                "brown bread",
                50.0,
                0,
                toCalendar(getReferenceDate().minusDays(110)),
                toCalendar(getReferenceDate().minusDays(10))
        );
        Food food3 = new Bread(
                "black bread",
                50.0,
                0,
                toCalendar(getReferenceDate().minusDays(80)),
                toCalendar(getReferenceDate().plusDays(20))
        );

        ShopDataProvider shopDataProvider = new ShopDataProvider();
        return Stream.of(
                Arguments.of(new WareHouseStrategy(shopDataProvider), food1, false),
                Arguments.of(new ShopStrategy(shopDataProvider), food1, true),
                Arguments.of(new TrashStrategy(shopDataProvider), food1, false),

                Arguments.of(new WareHouseStrategy(shopDataProvider), food2, false),
                Arguments.of(new ShopStrategy(shopDataProvider), food2, false),
                Arguments.of(new TrashStrategy(shopDataProvider), food2, true),

                Arguments.of(new WareHouseStrategy(shopDataProvider), food3, false),
                Arguments.of(new ShopStrategy(shopDataProvider), food3, true),
                Arguments.of(new TrashStrategy(shopDataProvider), food3, false)
        );
    }
}