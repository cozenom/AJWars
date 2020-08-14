package com.advancejwars.Entities;

import com.advancejwars.CONSTANTS;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
/*
** TODO - MOVEMENT RANGE
** TODO - more victory conditions
** TODO - better interact
** TODO - city
** TODO - resources + production
** TODO - more units
*/

public class Controller extends Sprite implements InputProcessor {
    TiledMap map;
    // Layer to check for collision
    TiledMapTileLayer layer;
    // Vector for position
    Vector2 pos;
    GameData data;
    Vector2 tmpPos;
    int currID;
    // Keep track of turns
    public boolean turn;

    public Controller(Sprite sprite, TiledMap map, GameData data){
        super(sprite);
        this.map = map;
        this.pos = new Vector2(4,4);
        this.layer = (TiledMapTileLayer) map.getLayers().get(0);
        this.data = data;
        // Red starts
        this.turn = true;
    }

    @Override
    public void draw(Batch batch) {
        super.draw(batch);
        this.setPosition(pos.x*(CONSTANTS.TILEW/2)+pos.y*(CONSTANTS.TILEW/2),
                pos.y*(CONSTANTS.TILEH/2)-pos.x*(CONSTANTS.TILEH/2)+7);
        // +7 for tile height (brown bits)
    }

    // Checks if a tile is null (empty) for boundaries of map
    private boolean checkNull(float x, float y){
        return (layer.getCell((int) x,(int) y) != null);
    }

    // Checks if a tile is traversable (not mountain or sea) for unit placement
    private boolean checkTraversable(Vector2 pos){
        return (layer.getCell((int) pos.x,(int) pos.y).getTile().getProperties().containsKey("traversable"));
    }

    // Checks if there is an enemy on this tile
    private int checkEnemy(Vector2 pos){
        if (turn){ // red turn - blue enemy
            for (Knight k : data.getEnemyUnits()){
                if (k.getPos().x == pos.x && k.getPos().y == pos.y){
                    return data.getEnemyUnits().indexOf(k);
                }
            }
        } else { // blue turn - red enemy
            for (Knight k : data.getPlayerUnits()){
                if (k.getPos().x == pos.x && k.getPos().y == pos.y){
                    return data.getPlayerUnits().indexOf(k);
                }
            }
        }
        return -1;
    }

    // Checks if there is an ally on this tile
    private boolean checkAlly(Vector2 pos){
        int count = 0;
        if (turn){ // red turn
            for (Knight k : data.getPlayerUnits()){
                if (k.getPos().x == pos.x && k.getPos().y == pos.y){
                    count++;
                }
            }
        } else { // blue turn - red enemy
            for (Knight k : data.getEnemyUnits()){
                if (k.getPos().x == pos.x && k.getPos().y == pos.y){
                    count++;
                }
            }
        }
        return count > 1;
    }

    private void changeTurn(){
        // Turn: true = red turn, false = blue turn
        // Change turn first THEN change unit states
        turn = !turn;
        System.out.println("Turn change "+turn);
        if (turn){ // red turn - make all RED knights IDLE
            for (Knight k : data.getPlayerUnits()){
                k.state = Knight.State.IDLE;
            }
        } else { // blue turn - make all BLUE knights IDLE
            for (Knight k : data.getEnemyUnits()){
                k.state = Knight.State.IDLE;
            }
        }
    }

    private boolean checkTurn(ArrayList<Knight> list){
        for (Knight k : list){
            if (k.state != Knight.State.DONE){
                return false;
            }
        }
        return true;
    }

    public int checkVictory(){
        /* 0 - No victory yet
        ** 1 - Red (player) victory
        ** 2 - Blue (enemy) victory  */
        if (data.getPlayerUnits().size() < 1){/*System.out.println("Blue Victory");*/ return 2; }
        if (data.getEnemyUnits().size() < 1){/*System.out.println("Red Victory");*/ return 1;}
        return 0;
        // TODO add more victory types
    }

    private void interact(float x, float y){
        // TODO - maybe put this in an if statement
        // https://stackoverflow.com/questions/29420656/how-to-add-a-pop-up-menu-in-libgdx
        // https://github.com/libgdx/libgdx/wiki/Table

        // Red (true) player turn
        for (Knight knight : data.getPlayerUnits()){
            if (knight.pos.x == this.pos.x && knight.pos.y == this.pos.y && turn){
                currID = data.getPlayerUnits().indexOf(knight);
                // If IDLE - set to SELECTED and move it
                if (data.getPlayerUnits().get(currID).state == Knight.State.IDLE ) {
                    data.getPlayerUnits().get(currID).state = Knight.State.SELECTED;
                    tmpPos = new Vector2(this.pos);
                }
                if (data.getPlayerUnits().get(currID).state == Knight.State.SELECTED ) {
                    data.getPlayerUnits().get(currID).setPos(this.pos);
                }
                break;
            }
        }

        // Blue (false) player turn
        for (Knight knight : data.getEnemyUnits()){
            if (knight.pos.x == this.pos.x && knight.pos.y == this.pos.y && !turn){
                currID = data.getEnemyUnits().indexOf(knight);
                // If IDLE - set to SELECTED and move it
                if (data.getEnemyUnits().get(currID).state == Knight.State.IDLE ) {
                    data.getEnemyUnits().get(currID).state = Knight.State.SELECTED;
                    tmpPos = new Vector2(this.pos);
                }
                if (data.getEnemyUnits().get(currID).state == Knight.State.SELECTED ) {
                    data.getEnemyUnits().get(currID).setPos(this.pos);
                }
            }
        }

        // else no unit selected must be something else
        // ID's seem to start at 1 instead of 0 :. the -1
        int cellID = layer.getCell((int) x,(int) y).getTile().getId()-1;
        if (cellID == 0) { // Red barracks
            // data.addPlayerUnit(new Vector2(this.pos.x, this.pos.y));
            // TODO - proper unit production
            System.out.println("Red Barracks");
            // build units menu
        } else if (cellID == 2){ // Red castle
            // surrender option or something?
            System.out.println("Red Castle");
        } else if (cellID == 3){ // Red castle
            // surrender option or something?
            System.out.println("Red City");
        } else {
            System.out.println(cellID);
            // just display tile stats or something
        }
    }

    public void drop(int ID){
        if (turn) {
            if (checkTraversable(this.pos) && !checkAlly(this.pos)) {
                data.getPlayerUnits().get(ID).setPos(new Vector2(this.pos.x, this.pos.y));
                data.getPlayerUnits().get(ID).state = Knight.State.DONE;
            } else {System.out.println("Invalid placement ");}
            if (checkEnemy(this.pos) != -1){ // True if there is an enemy on the tile
                data.killEnemyUnit(checkEnemy(this.pos));
            }
        }
        else{
            if (checkTraversable(this.pos) && !checkAlly(this.pos)) {
                data.getEnemyUnits().get(ID).setPos(new Vector2(this.pos.x, this.pos.y));
                data.getEnemyUnits().get(ID).state = Knight.State.DONE;
            } else {System.out.println("Invalid placement ");}
            if (checkEnemy(this.pos) != -1){ // True if there is an enemy on the tile
                data.killPlayerUnit(checkEnemy(this.pos));
            }
        }

        // Check if turn is done
        if (turn){
            if (checkTurn(data.getPlayerUnits())){
                changeTurn();
            }
        } else {
            if (checkTurn(data.getEnemyUnits())){
                changeTurn();
            }
        }
        checkVictory();
    }

    @Override
    public boolean keyDown(int keycode) {
        switch (keycode) {
            case Input.Keys.W:
                if(checkNull(pos.x, pos.y+1))
                    this.pos.y += 1;
                break;
            case Input.Keys.A:
                if(checkNull(pos.x-1, pos.y))
                    this.pos.x -= 1;
                break;
            case Input.Keys.S:
                if(checkNull(pos.x, pos.y-1))
                    this.pos.y -= 1;
                break;
            case Input.Keys.D:
                if(checkNull(pos.x+1, pos.y))
                    this.pos.x += 1;
                break;
            case Input.Keys.E:
                interact(pos.x, pos.y);
                break;
            case Input.Keys.Q:
                drop(currID);
                break;
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {


        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
