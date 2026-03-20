package ru.job4j.ood.srp.report;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * XmlAdapter<String, Calendar>
 * XML хранит String
 * Java хранит Calendar
 * marshal
 * Calendar -> String: из Calendar получаем дату-время --- форматируем в строку (по формату, да)
 * unmarshal
 * String -> Calendar: из строки парсим дату-время --- добавляем таймзону -- превращаем в календарь
 * !!! ДОЛЖЕН совпадать формат DateTimeFormatter.ofPattern("yyyy:MM:dd HH:mm")
 *
 */

public class CalendarAdapterXml extends XmlAdapter<String, Calendar> {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd:MM:yyyy HH:mm");

    @Override
    public Calendar unmarshal(String string) throws Exception {
        if (string == null) {
            return null;
        }
        LocalDateTime localDateTime = LocalDateTime.parse(string, FORMATTER);
        ZonedDateTime dateTime = localDateTime.atZone(ZoneId.systemDefault());
        return GregorianCalendar.from(dateTime);
    }

    @Override
    public String marshal(Calendar calendar) throws Exception {
        if (calendar == null) {
            return null;
        }
        ZonedDateTime dateTime = ZonedDateTime.ofInstant(calendar.toInstant(), ZoneId.systemDefault());
        return dateTime.format(FORMATTER);
    }
}
