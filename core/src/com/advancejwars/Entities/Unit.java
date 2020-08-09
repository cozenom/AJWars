package com.advancejwars.Entities;

import com.badlogic.gdx.math.Vector2;

public interface Unit {
    // Unit movement
    public boolean move(Vector2 pos);

    // Unit attack
    public boolean attack(Vector2 pos);

    // Unit receiving focus
    public boolean receiveFocus();

    // Unit losing focus
    public boolean loseFocus();

    // Debugging info
    public String info();

}
