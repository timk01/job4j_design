package ru.job4j.ood.srp.report;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Задача - если там было конвертнуть Календарь в Стринг (сериализация) и наоборот,
 * ТУТ вместо стринги мы работаем с JsonElement
 * Сериализация:
 * взяли время (Дату), конвертнули в стрингу, упаковали в JsonPrimitive
 * - это по-сути примитив жсона, в который упакована строка
 * Десериализация:
 * абслютно обратный процесс: в стрингу, потом в календарь и - парсим
 */

public class CalendarAdapterJson implements JsonSerializer<Calendar>, JsonDeserializer<Calendar> {

    private static final SimpleDateFormat FORMATTER = new SimpleDateFormat("dd:MM:yyyy HH:mm");

    @Override
    public Calendar deserialize(JsonElement jsonElement,
                                Type type,
                                JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        if (jsonElement == null) {
            return null;
        }
        try {
            String str = jsonElement.getAsString();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(FORMATTER.parse(str));
            return calendar;
        } catch (Exception e) {
            throw new JsonParseException(e);
        }
    }

    @Override
    public JsonElement serialize(Calendar calendar,
                                 Type type,
                                 JsonSerializationContext jsonSerializationContext) {
        if (calendar == null) {
            return null;
        }
        return new JsonPrimitive(FORMATTER.format(calendar.getTime()));
    }
}
