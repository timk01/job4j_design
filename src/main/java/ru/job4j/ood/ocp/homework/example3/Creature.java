package ru.job4j.ood.ocp.homework.example3;

public abstract class Creature extends Entity {
    private static final int STEPS_LIMITER = 2;

    private final int speed;
    private int hp;
    private final int maxHp;

    public Creature(int speed, int hp, int maxHp) {
        this.speed = speed;
        this.hp = hp;
        this.maxHp = maxHp;
    }

    public int getSpeed() {
        return speed;
    }

    public int getHp() {
        return hp;
    }


    public void heal(int healAmount) {
        hp += healAmount;
        if (hp >= maxHp) {
            hp = maxHp;
        }
    }

    public void takeDamage(int damageAmount) {
        hp -= damageAmount;
    }

    public boolean isDead() {
        return hp <= 0;
    }
}