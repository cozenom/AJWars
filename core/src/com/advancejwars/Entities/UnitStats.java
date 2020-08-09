package com.advancejwars.Entities;

public class UnitStats {
    public final String type;

    public final int visionRange;
    public final int maxHealth;
    public final int attack;
    public final int movement;
    public final int range;


    public UnitStats(
            String type,
            int visionRange,
            int maxHealth,
            int attack,
            int movement,
            int range) {
        this.type = type;
        this.visionRange = visionRange;
        this.maxHealth = maxHealth;
        this.attack = attack;
        this.movement = movement;
        this.range = range;
    }
}
