package com.advancejwars.Entities;

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

    // testing purposes mainly
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

    public GameData(ArrayList<Knight> playerUnits, ArrayList<Knight> enemyUnits){
        this.playerUnits = playerUnits;
        this.enemyUnits = enemyUnits;
    }

    public ArrayList<Knight> getPlayerUnits() {
        return playerUnits;
    }

    public ArrayList<Knight> getEnemyUnits() {
        return enemyUnits;
    }

    public Knight findPlayer(Vector2 pos){
        for (Knight k:playerUnits){
            if (k.getPos() == pos){
                return k;
            }
        } return null;
    }

    public Knight findEnemy(Vector2 pos){
        for (Knight k:enemyUnits){
            if (k.getPos() == pos){
                return k;
            }
        } return null;
    }

    // TODO - do i really need these?
    public void setPlayerUnit(Vector2 newpos, int id) {
        this.playerUnits.get(id).setPos(newpos);
    }
    public void setEnemyUnit(Vector2 newpos, int id) {
        this.enemyUnits.get(id).setPos(newpos);
    }

    public void addPlayerUnit(Vector2 pos) {
        this.playerUnits.add(new Knight(pos, redKnight));
    }
    public void addEnemyUnit(Vector2 pos) {
        this.enemyUnits.add(new Knight(pos, bluKnight));
    }

    public void killPlayerUnit(int id){this.playerUnits.remove(id);}
    public void killEnemyUnit(int id){this.enemyUnits.remove(id); }
}
