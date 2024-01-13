package ru.job4j.assertj;

import org.assertj.core.api.Condition;
import org.assertj.core.data.Index;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.entry;

class SimpleConvertTest {

    @Test
    void checkArray() {
        SimpleConvert simpleConvert = new SimpleConvert();
        String[] array = simpleConvert.toArray("first", "second", "three", "four", "five");
        assertThat(array).hasSize(5)
                .contains("second")
                .contains("first", Index.atIndex(0))
                .containsAnyOf("zero", "second", "six")
                .doesNotContain("first", Index.atIndex(1));
    }

    @Test
    void checkList() {
        SimpleConvert simpleConvert = new SimpleConvert();
        String[] input = new String[]{"first", "second", "three", "four", "five"};
        List<String> convertedList = simpleConvert.toList(input);
        assertThat(convertedList).isNotNull()
                .isNotEmpty()
                .hasSize(5)
                .contains("three", "five")
                .containsExactly("first", "second", "three", "four", "five")
                .startsWith("first")
                .endsWith("five")
                .allSatisfy(
                        elem -> assertThat(elem).isAlphabetic())
                .noneMatch(e -> e.length() < 1);
        assertThat(convertedList)
                .filteredOn(e -> e.startsWith("f"))
                .last()
                .isEqualTo("five");
        assertThat(convertedList)
                .filteredOnAssertions(e -> assertThat(e).isAlphabetic())
                .hasSize(5)
                .first()
                .isEqualTo("first");
    }

    @Test
    void checkSet() {
        SimpleConvert simpleConvert = new SimpleConvert();
        String[] input = new String[]{"first", "second", "three", "four", "five"};
        Set<String> convertedSet = simpleConvert.toSet(input);
        assertThat(convertedSet).isNotNull()
                .hasSize(5)
                .containsAnyOf("three")
                .doesNotContain("six")
                .contains("five", "second")
                .anySatisfy(
                        elem -> assertThat(elem)
                                .is(new Condition<String>(e -> e.length() > 5, "length should be > 5")))
                .anyMatch(elem -> elem.equals("second"));
        assertThat(convertedSet)
                .filteredOnAssertions(e -> assertThat(e).containsIgnoringCase("four"))
                .hasSize(1)
                .first()
                .isEqualTo("four");
    }

    @Test
    void checkMap() {
        SimpleConvert simpleConvert = new SimpleConvert();
        String[] input = new String[]{"first", "second", "three", "four", "five", "six"};
        Map<String, Integer> resultedMap = simpleConvert.toMap(input);
        Map<String, Integer> expectedMap = Map.of(
                "first", 0,
                "second", 1,
                "three", 2,
                "four", 3,
                "five", 4,
                "six", 5
        );
        assertThat(resultedMap).isEqualTo(expectedMap);
        assertThat(resultedMap).isNotNull()
                .hasSize(6)
                .containsAnyOf(entry("five", 4), entry("second", 1))
                .containsKeys(input)
                .containsValue(5);

    }
}