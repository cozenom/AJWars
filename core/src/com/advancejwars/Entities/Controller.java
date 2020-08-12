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

public class Controller extends Sprite implements InputProcessor {
    TiledMap map;
    // Layer to check for collision
    TiledMapTileLayer layer;
    // Vector for position
    Vector2 pos;
    GameData data;
    //Sprite sprite = new Sprite(new Texture("map/Tiles/Controller.png"));
    Vector2 tmpPos;
    int currID;
    boolean turn;

    public Controller(Sprite sprite, TiledMap map, GameData data){
        super(sprite);
        this.map = map;
        this.pos = new Vector2(4,4);
        this.layer = (TiledMapTileLayer) map.getLayers().get(0);
        this.data = data;
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

    private void changeTurn(){
        // Turn: true = red turn, false = blue turn
        // Change turn first THEN change unit states
        turn = !turn;
        if (turn){ // red turn - make all RED knights IDLE
            for (Knight k : data.getPlayerUnits()){
                k.state = Knight.State.IDLE;
            }
        } else { // blue turn - make all BLUE knights IDLE
            for (Knight k : data.getEnemyUnits()){
                k.state = Knight.State.IDLE;
            }
        }
        // TODO - Overlay
    }

    private boolean checkTurn(ArrayList<Knight> list){
        for (Knight k : list){
            if (k.state != Knight.State.DONE){
                return false;
            }
        }
        return true;
    }

    private void interact(float x, float y){
        // TODO - maybe put this in an if statement
        // https://stackoverflow.com/questions/29420656/how-to-add-a-pop-up-menu-in-libgdx
        // https://github.com/libgdx/libgdx/wiki/Table
        for (Knight knight : data.getPlayerUnits()){
            if (knight.pos.x == this.pos.x && knight.pos.y == this.pos.y){
                currID = data.getPlayerUnits().indexOf(knight);
                if (data.getPlayerUnits().get(currID).state == Knight.State.IDLE ) {
                    data.getPlayerUnits().get(currID).state = Knight.State.SELECTED;
                    tmpPos = new Vector2(this.pos);
                }
                if (data.getPlayerUnits().get(currID).state == Knight.State.SELECTED ) {
                    // TODO pt1 - fix this shit

                    //moved = (int) (Math.abs(tmpPos.x - this.pos.x) + Math.abs(tmpPos.y - this.pos.y));
                    //System.out.println("moved = "+moved);
                    data.getPlayerUnits().get(currID).setPos(this.pos);
                    /*
                    if (data.getPlayerUnits().get(currID).move(this.pos, tmpPos)){
                        data.getPlayerUnits().get(currID).setPos(new Vector2(this.pos));
                    }*/
                }
                // select unit and do something
                break;
            }
        }
        for (Knight knight : data.getEnemyUnits()){
            if (knight.pos.x == this.pos.x && knight.pos.y == this.pos.y){
                System.out.println("Enemy unit found");
                // select unit and do something
                break;
            }
        }

        // else no unit selected must be something else
        // ID's seem to start at 1 instead of 0 :. the -1
        int cellID = layer.getCell((int) x,(int) y).getTile().getId()-1;
        if (cellID == 0) { // Red barracks
            data.addPlayerUnits(new Vector2(this.pos.x, this.pos.y));
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
        System.out.println("dropping");
        data.getPlayerUnits().get(ID).setPos(new Vector2(this.pos.x, this.pos.y));
        data.getPlayerUnits().get(ID).state = Knight.State.DONE;
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
        // TODO pt2 - terrain traversible
        // TODO pt3 - stacking knights - can probably be done together
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
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

        return true;
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
