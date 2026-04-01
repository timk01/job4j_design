package ru.job4j.ood.lcp.foodkeper.store.discount;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.job4j.ood.lcp.foodkeper.food.Bread;
import ru.job4j.ood.lcp.foodkeper.food.Food;
import ru.job4j.ood.lcp.foodkeper.store.DataProvider;
import ru.job4j.ood.lcp.foodkeper.store.ShopDataProvider;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.job4j.ood.lcp.foodkeper.TestFoodDateHelper.getReferenceDate;
import static ru.job4j.ood.lcp.foodkeper.TestFoodDateHelper.toCalendar;

class ShopDiscounterTest {
    @ParameterizedTest
    @MethodSource("discountData")
    void whenStrategyDataIsProvidedThenCalculatedResultIsCorrect(DiscountPolicy policy,
                                                                 Food food,
                                                                 double price) {
        policy.makeDiscount(food);
        assertThat(food.getPrice()).isEqualTo(price);
    }

    static Stream<Arguments> discountData() {
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
                toCalendar(getReferenceDate().minusDays(40)),
                toCalendar(getReferenceDate().plusDays(10))
        );

        DataProvider fixedDataProvider = () -> getReferenceDate();

        return Stream.of(
                Arguments.of(new ShopDiscounter(fixedDataProvider), food1, 50),
                Arguments.of(new ShopDiscounter(fixedDataProvider), food2, 40)
        );
    }
}