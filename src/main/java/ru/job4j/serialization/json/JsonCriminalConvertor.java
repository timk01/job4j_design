package ru.job4j.serialization.json;

import org.json.JSONArray;
import org.json.JSONObject;

public class JsonCriminalConvertor {
    public static void main(String[] args) {
        Crime crime = new Crime("theft", 5, true);
        JSONObject crimeJsonObj = new JSONObject(crime);
        System.out.println(crimeJsonObj);

        JSONArray articlesJsonArr = new JSONArray(new float[]{100, 200, 300});

        Prisoner prisoner = new Prisoner(
                false,
                12348898789L,
                "Ivanov Ivan Ivanovich",
                new Crime(
                        "arson",
                        15,
                        true
                ),
                new float[]{167.2F, 109F}
        );
        JSONObject johnDoe = new JSONObject();
        johnDoe.put("sex", prisoner.getSex());
        johnDoe.put("number", prisoner.getPrisonerNumber());
        johnDoe.put("FIO", "John Doe");
        johnDoe.put("crime", crime);
        johnDoe.put("randomArcticles", articlesJsonArr);

        System.out.println(new JSONObject(prisoner).toString());
        System.out.println(johnDoe);
    }
}
