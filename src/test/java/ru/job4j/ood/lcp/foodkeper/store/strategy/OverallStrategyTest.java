package ru.job4j.ood.lcp.foodkeper.store.strategy;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.job4j.ood.lcp.foodkeper.food.Bread;
import ru.job4j.ood.lcp.foodkeper.food.Food;

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
                toCalendar(getReferenceDate().minusDays(10)),
                toCalendar(getReferenceDate().plusDays(30))
        );
        Food food2 = new Bread(
                "brown bread",
                50.0,
                0,
                toCalendar(getReferenceDate().minusDays(20)),
                toCalendar(getReferenceDate().minusDays(10))
        );
        Food food3 = new Bread(
                "black bread",
                50.0,
                0,
                toCalendar(getReferenceDate().minusDays(40)),
                toCalendar(getReferenceDate().plusDays(1))
        );

        return Stream.of(
                Arguments.of(new WareHouseStrategy(), food1, false),
                Arguments.of(new ShopStrategy(), food1, true),
                Arguments.of(new TrashStrategy(), food1, false),

                Arguments.of(new WareHouseStrategy(), food2, false),
                Arguments.of(new ShopStrategy(), food2, false),
                Arguments.of(new TrashStrategy(), food2, true),

                Arguments.of(new WareHouseStrategy(), food3, false),
                Arguments.of(new ShopStrategy(), food3, true),
                Arguments.of(new TrashStrategy(), food3, false)
        );
    }
}