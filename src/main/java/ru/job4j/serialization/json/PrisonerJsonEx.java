package ru.job4j.serialization.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class PrisonerJsonEx {
    public static void main(String[] args) {
        final Prisoner prisoner = new Prisoner(
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

        final Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String prisonerJs = gson.toJson(prisoner);
        System.out.println(prisonerJs);

        Prisoner deserializedPrisoner = gson.fromJson(prisonerJs, Prisoner.class);
        System.out.println(deserializedPrisoner);

        System.out.println(deserializedPrisoner.equals(prisoner));
    }
}
