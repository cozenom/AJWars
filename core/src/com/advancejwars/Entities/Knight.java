package com.advancejwars.Entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Knight extends Actor {
    private Texture texture;
    private Vector2 pos;
    private Sprite sprite;
    //private final int sizeX;
    //private final int sizeY;
    private UnitStats stats;
    public State state;
    public int currentHP;


    public Knight(Vector2 pos, int team){
        this.pos = pos;
        if (team == 0){ this.texture = new Texture(Gdx.files.internal("assets/units/Knight_Red.png")); }
        else if (team == 1){this.texture = new Texture(Gdx.files.internal("assets/units/Knight_Blue.png"));}
        this.sprite = new Sprite(this.texture);
        //sizeX = this.texture.getWidth();
        //sizeY = this.texture.getHeight();
        this.stats = new UnitStats("Knight", 2, 100, 50, 3, 1, 1);
        this.currentHP = this.stats.maxHealth;
        this.state = State.IDLE;
    }

    public void fight(Knight opponent) {
        opponent.currentHP -= getStats().attack;
    }

    public UnitStats getStats() {
        return this.stats;
    }

    public void move(Vector2 newpos){
        if ((newpos.x - this.pos.x) + (newpos.y - this.pos.y) < stats.movement){
            this.pos = newpos;
            // TODO - implement this properly
        }
        // TODO - if enemy unit is on tile - fight
    }

    public enum State {
        IDLE,
        SELECTED,
        DONE
    }
}
