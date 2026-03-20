package ru.job4j.ood.srp.report;

import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.GregorianCalendar;

import static org.assertj.core.api.Assertions.*;

class CalendarAdapterXmlTest {

    private final CalendarAdapterXml adapter = new CalendarAdapterXml();

    @Test
    void whenMarshalThenStringMatchesPattern() throws Exception {
        Calendar calendar = new GregorianCalendar(2023, Calendar.JUNE, 8, 17, 41);
        String result = adapter.marshal(calendar);
        assertThat(result).isEqualTo("08:06:2023 17:41");
    }

    @Test
    void whenUnmarshalThenCalendarMatchesOriginal() throws Exception {
        String input = "08:06:2023 17:41";
        Calendar calendar = adapter.unmarshal(input);

        assertThat(calendar.get(Calendar.YEAR)).isEqualTo(2023);
        assertThat(calendar.get(Calendar.MONTH)).isEqualTo(Calendar.JUNE);
        assertThat(calendar.get(Calendar.DAY_OF_MONTH)).isEqualTo(8);
        assertThat(calendar.get(Calendar.HOUR_OF_DAY)).isEqualTo(17);
        assertThat(calendar.get(Calendar.MINUTE)).isEqualTo(41);
    }

    @Test
    void whenMarshalAndUnmarshalThenValuePreserved() throws Exception {
        Calendar original = new GregorianCalendar(2023, Calendar.JUNE, 8, 17, 41);
        String marshalled = adapter.marshal(original);
        Calendar unmarshalled = adapter.unmarshal(marshalled);

        assertThat(unmarshalled.getTimeInMillis())
                .isEqualTo(original.getTimeInMillis());
    }
}