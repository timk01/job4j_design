package ru.job4j.io;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ConfigTest {

    @Test
    void whenPairWithoutComment() {
        String path = "./data/pair_without_comment.properties";
        Config config = new Config(path);
        config.load();
        assertThat(config.value("name")).isEqualTo("Petr Arsentev");
    }

    @Test
    void whenPairWithComment() {
        String path = "data/pair_with_comment.properties";
        Config config = new Config(path);
        config.load();
        assertThat(config.value("name")).isEqualTo("Petr Arsentev");
    }

    @Test
    void whenPairWithEmptyString() {
        String path = "data/pair_with_empty_string.properties";
        Config config = new Config(path);
        config.load();
        assertThat(config.value("name")).isEqualTo("Petr Arsentev");
    }

    @Test
    void whenPairHasNoEquation() {
        String path = "data/pair_without_equation.properties";
        Config config = new Config(path);
        assertThatThrownBy(config::load)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("must have '=', key/value part can't be empty");
    }

    @Test
    void whenPairHasOnlyEquation() {
        String path = "data/pair_with_equation_only.properties";
        Config config = new Config(path);
        assertThatThrownBy(config::load)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("must have '=', key/value part can't be empty");
    }

    @Test
    void whenPairWithEmptyKey() {
        String path = "data/pair_with_empty_key.properties";
        Config config = new Config(path);
        assertThatThrownBy(config::load)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("must have '=', key/value part can't be empty");
    }

    @Test
    void whenPairWithEmptyValue() {
        String path = "data/pair_with_empty_value.properties";
        Config config = new Config(path);
        assertThatThrownBy(config::load)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("must have '=', key/value part can't be empty");
    }

    @Test
    void whenPairHasSeveralEquation() {
        String path = "data/pair_with_several_equation.properties";
        Config config = new Config(path);
        config.load();
        assertThat(config.value("name")).isEqualTo("Petr Arsentev=1");
    }

    @Test
    void whenPairHasSeveralEquation2() {
        String path = "data/pair_with_several_equation2.properties";
        Config config = new Config(path);
        config.load();
        assertThat(config.value("name")).isEqualTo("Petr Arsentev=");
    }

    @Test
    void whenValueIsFound() {
        String path = "data/pair_existed_value.properties";
        Config config = new Config(path);
        config.load();
        assertThat(config.value("hibernate.connection.username")).isEqualTo("postgres");
    }

    @Test
    void whenValueIsNotFound() {
        String path = "data/pair_existed_value.properties";
        Config config = new Config(path);
        config.load();
        assertThat(config.value("hibernate.connection.username2")).isNull();
    }
}