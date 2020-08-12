package com.advancejwars.Entities;

import com.advancejwars.CONSTANTS;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

public class Knight extends Sprite implements Cloneable{
    public Vector2 pos;
    public UnitStats stats;
    public State state;
    public int currentHP;

    public Knight(Vector2 pos, Sprite sprite){
        super(sprite);
        this.pos = pos;

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

    public Vector2 getPos(){
        return this.pos;
    }

    public void setPos(Vector2 pos) {
        this.pos = pos;
    }

    public boolean move(Vector2 newpos, Vector2 oldpos){
        System.out.println(((newpos.x - this.pos.x) + (newpos.y - this.pos.y) < stats.movement));
        return (newpos.x - oldpos.x) + (newpos.y - oldpos.y) < stats.movement;
    }

    public enum State {
        IDLE,
        SELECTED,
        DONE
    }

    @Override
    public void draw(Batch batch) {
        super.draw(batch);
        this.setPosition(pos.x*(CONSTANTS.TILEW/2)+pos.y*(CONSTANTS.TILEW/2),pos.y*(CONSTANTS.TILEH/2)-pos.x*(CONSTANTS.TILEH/2)+12);
        // +7 for tile height (brown bits)
    }
}
