package ru.job4j.ood.ocp.homework.example3;

import static ru.job4j.ood.ocp.homework.example3.EntityType.*;

public abstract class StaticEntityWrongEx extends Entity {
    public void interact() {
        EntityType herbivore = HERBIVORE;
        matches(herbivore);
    }

    public Entity matches(EntityType entityType) {
        return switch (entityType) {
            case ROCK -> new Rock();
            case GRASS -> new Grass(5);
            case TREE -> new Tree();
            default -> throw new IllegalArgumentException("wrong type while matching entity: " + this);
        };
    }
}
