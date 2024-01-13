package ru.job4j.assertj;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.*;

class NameLoadTest {
    @Test
    void mapIsEmptyWhileGettingIt() {
        NameLoad nameLoad = new NameLoad();
        assertThatThrownBy(nameLoad::getMap)
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("no data");
    }

    @Test
    void mapIsNOTEmptyWhileGettingIt() {
        NameLoad nameLoad = new NameLoad();
        String firstName = "key=value";
        String[] names = new String[] {firstName};
        nameLoad.parse(names);
        Map<String, String> result = nameLoad.getMap();
        assertThat(result).isNotEmpty()
                .containsOnly(entry("key", "value"));
    }

    @Test
    void mapIsEmptyWhenParse() {
        NameLoad nameLoad = new NameLoad();
        String[] emptyNames = new String[0];
        assertThatThrownBy(() -> nameLoad.parse(emptyNames))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("array is empty");
    }

    @Test
    void arrayDoNotContainSymbolWhenParse() {
        NameLoad nameLoad = new NameLoad();
        String firstName = "key value";
        String[] names = new String[] {firstName};
        assertThatThrownBy(() -> nameLoad.parse(names))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(firstName)
                .hasMessageContaining("does not contain the symbol '='");
    }

    @Test
    void symbolIsAtBeginningWhenParse() {
        NameLoad nameLoad = new NameLoad();
        String firstName = "=key value";
        String[] names = new String[] {firstName};
        assertThatThrownBy(() -> nameLoad.parse(names))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(firstName)
                .hasMessageContaining("does not contain a key");
    }

    @Test
    void symbolIsAtEndWhenParse() {
        NameLoad nameLoad = new NameLoad();
        String firstName = "key value=";
        String[] names = new String[] {firstName};
        assertThatThrownBy(() -> nameLoad.parse(names))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(firstName)
                .hasMessageContaining("does not contain a value");
    }

    @Test
    void keysAreDifferentWhenParse() {
        NameLoad nameLoad = new NameLoad();
        String firstName = "key=value";
        String secondName = "key2=value2";
        String[] names = new String[] {firstName, secondName};
        nameLoad.parse(names);
        Map<String, String> result = nameLoad.getMap();
        assertThat(result).isNotEmpty()
                .containsOnly(entry("key", "value"), entry("key2", "value2"));
    }

    @Test
    void keysAreEqualWhenParse() {
        NameLoad nameLoad = new NameLoad();
        String firstName = "key=value";
        String secondName = "key=value2";
        String[] names = new String[] {firstName, secondName};
        nameLoad.parse(names);
        Map<String, String> result = nameLoad.getMap();
        assertThat(result).isNotEmpty()
                .containsOnly(entry("key", "value+value2"));    }
}