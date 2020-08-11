package com.advancejwars.Entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

public class GameData {
    // Array for keeping track of player units
    private ArrayList<Knight> playerUnits;
    // Array for keeping track of enemy units
    private ArrayList<Knight> enemyUnits;

    private final Sprite redKnight = new Sprite(new Texture("units/Knight_Red.png"));
    private final Sprite bluKnight = new Sprite(new Texture("units/Knight_Blue.png"));

    public GameData() {
        this.playerUnits = new ArrayList<Knight>(){
            {
                add(new Knight(new Vector2(9,8), redKnight));
                add(new Knight(new Vector2(8,9), redKnight));
            }
        };
        this.enemyUnits = new ArrayList<Knight>(){
            {
                add(new Knight(new Vector2(1,2), bluKnight));
                add(new Knight(new Vector2(3,1), bluKnight));
            }
        };
    }

    public ArrayList<Knight> getPlayerUnits() {
        return playerUnits;
    }

    public ArrayList<Knight> getEnemyUnits() {
        return enemyUnits;
    }

    public void setPlayerUnit(Vector2 newpos, int id) {
        this.playerUnits.get(id).setPos(newpos);
    }

    public void setEnemyUnit(Vector2 newpos, int id) {
        this.enemyUnits.get(id).setPos(newpos);
    }

    public void addPlayerUnits(Vector2 pos) {
        this.playerUnits.add(new Knight(pos, redKnight));
    }

    public void addEnemyUnits(Vector2 pos) {
        this.enemyUnits.add(new Knight(pos, bluKnight));
    }
}
