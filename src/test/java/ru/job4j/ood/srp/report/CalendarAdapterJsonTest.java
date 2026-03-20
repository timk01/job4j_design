package ru.job4j.ood.srp.report;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.GregorianCalendar;

import static org.assertj.core.api.Assertions.assertThat;

class CalendarAdapterJsonTest {

    private final Gson gson = new GsonBuilder()
            .registerTypeAdapter(Calendar.class, new CalendarAdapterJson())
            .registerTypeAdapter(GregorianCalendar.class, new CalendarAdapterJson())
            .create();

    @Test
    void whenSerializeThenStringMatchesPattern() {
        Calendar calendar = new GregorianCalendar(2023, Calendar.JUNE, 8, 17, 41);

        String json = gson.toJson(calendar);

        assertThat(json).isEqualTo("\"08:06:2023 17:41\"");
    }

    @Test
    void whenDeserializeThenCalendarMatchesOriginal() {
        String json = "\"08:06:2023 17:41\"";

        Calendar calendar = gson.fromJson(json, Calendar.class);

        assertThat(calendar.get(Calendar.YEAR)).isEqualTo(2023);
        assertThat(calendar.get(Calendar.MONTH)).isEqualTo(Calendar.JUNE);
        assertThat(calendar.get(Calendar.DAY_OF_MONTH)).isEqualTo(8);
        assertThat(calendar.get(Calendar.HOUR_OF_DAY)).isEqualTo(17);
        assertThat(calendar.get(Calendar.MINUTE)).isEqualTo(41);
    }

    @Test
    void whenSerializeAndDeserializeThenValuePreserved() {
        Calendar original = new GregorianCalendar(2023, Calendar.JUNE, 8, 17, 41);

        String json = gson.toJson(original);
        Calendar restored = gson.fromJson(json, Calendar.class);

        assertThat(restored.getTimeInMillis())
                .isEqualTo(original.getTimeInMillis());
    }
}