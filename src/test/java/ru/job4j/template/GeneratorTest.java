package ru.job4j.template;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.*;

@Disabled("tests without implementation on real class")
class GeneratorTest {

    private String template = "I am a ${name}, Who are ${subject}? ";

    private Map<String, String> generatorMap;

    private Generator generator;

    @BeforeEach
    void setUp() {
        generator = new Generator() {
            @Override
            public String produce(String template, Map<String, String> args) {
                throw new UnsupportedOperationException("isn't implemented yet");
            }
        };
        generatorMap = new HashMap<>();
        generatorMap.put("name", "Tim");
        generatorMap.put("subject", "you");
    }

    @Test
    void whenMapHasKeyAndValueThenOK() {
        String result = generator.produce(template, generatorMap);
        assertThat(result).isEqualTo("I am a Tim, Who are you? ");
    }

    @Test
    void whenTemplateHasKeyWhichMapHasNotThenException() {
        generatorMap.remove("name");
        assertThatThrownBy(() -> generator.produce(template, generatorMap))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void whenMapHasExtraKeysException() {
        generatorMap.put("age", "18");
        assertThatThrownBy(() -> generator.produce(template, generatorMap))
                .isInstanceOf(IllegalArgumentException.class);
    }
}