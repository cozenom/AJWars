package com.advancejwars.Entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

import java.util.*;

public class GameData {
    // Array for keeping track of player units
    private ArrayList<Knight> playerUnits;
    // Array for keeping track of enemy units
    private ArrayList<Knight> enemyUnits;

    private final Sprite redKnight = new Sprite(new Texture("units/Knight_Red.png"));
    private final Sprite bluKnight = new Sprite(new Texture("units/Knight_Blue.png"));

    private HashMap<Knight, Float> allUnits;


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


    public HashMap<Knight, Float> updateRenderOrder(){
        HashMap<Knight, Float> tmp = new HashMap<Knight, Float>();
        for (Knight k : playerUnits){
            tmp.put(k, k.pos.y);
        }
        for (Knight k : enemyUnits){
            tmp.put(k, k.pos.y);
        }

        allUnits = sortByY(tmp);
        //System.out.println(allUnits);
        return allUnits;
    }

    private static HashMap<Knight, Float> sortByY(HashMap<Knight, Float> hm) {
        // Create a list from elements of HashMap
        List<Map.Entry<Knight, Float>> list =
                new LinkedList<Map.Entry<Knight, Float> >(hm.entrySet());

        // Sort the list
        Collections.sort(list, new Comparator<Map.Entry<Knight, Float> >() {
            public int compare(Map.Entry<Knight, Float> o1,
                               Map.Entry<Knight, Float> o2)
            {
                return -1*(o1.getValue()).compareTo(o2.getValue());
            }
        });

        // put data from sorted list to hashmap
        HashMap<Knight, Float> temp = new LinkedHashMap<Knight, Float>();
        for (Map.Entry<Knight, Float> aa : list) {
            temp.put(aa.getKey(), aa.getValue());
        }
        return temp;
    }







    // Get units
    public ArrayList<Knight> getPlayerUnits() {
        return playerUnits;
    }

    public ArrayList<Knight> getEnemyUnits() {
        return enemyUnits;
    }

    public HashMap<Knight, Float> getAllUnits() {
        return allUnits;
    }

    // Find units
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

    // do i really need these?
    public void setPlayerUnit(Vector2 newpos, int id) {
        this.playerUnits.get(id).setPos(newpos);
    }
    public void setEnemyUnit(Vector2 newpos, int id) {
        this.enemyUnits.get(id).setPos(newpos);
    }

    // Spawn units - TODO
    public void addPlayerUnit(Vector2 pos) {
        this.playerUnits.add(new Knight(pos, redKnight));
    }
    public void addEnemyUnit(Vector2 pos) {
        this.enemyUnits.add(new Knight(pos, bluKnight));
    }

    // Kill units
    public void killPlayerUnit(int id){this.playerUnits.remove(id);}
    public void killEnemyUnit(int id){this.enemyUnits.remove(id); }
}
