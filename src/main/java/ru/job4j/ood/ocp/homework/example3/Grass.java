package ru.job4j.ood.ocp.homework.example3;

public class Grass extends StaticEntityWrongEx {
    private final int nutrition;

    public Grass(int nutrition) {
        this.nutrition = nutrition;
    }

    public int getNutrition() {
        return nutrition;
    }
}
