package ru.job4j.ood.srp.report;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ru.job4j.ood.srp.model.Employee;
import ru.job4j.ood.srp.store.Store;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.function.Predicate;

public class JsonReportEngine implements Report {
    private final Store store;

    public JsonReportEngine(Store store) {
        this.store = store;
    }

    @Override
    public String generate(Predicate<Employee> filter) {
        List<Employee> employees = store.findBy(filter);
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Calendar.class, new CalendarAdapterJson());
        gsonBuilder.registerTypeAdapter(GregorianCalendar.class, new CalendarAdapterJson());
        Gson gson = gsonBuilder.setPrettyPrinting().create();
        return gson.toJson(new ArrayList<>(employees));
    }
}
