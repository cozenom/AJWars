package com.advancejwars.Entities;

import com.advancejwars.CONSTANTS;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

public class Controller extends Sprite implements InputProcessor {
    TiledMap map;
    // Layer to check for collision
    TiledMapTileLayer layer;
    // Vector for position
    Vector2 pos;
    GameData data;

    public Controller(Sprite sprite, TiledMap map, GameData data){
        super(sprite);
        this.map = map;
        this.pos = new Vector2(4,4);
        this.layer = (TiledMapTileLayer) map.getLayers().get("Tilemap");
        this.data = data;
    }

    @Override
    public void draw(Batch batch) {
        super.draw(batch);
        this.setPosition(pos.x*(CONSTANTS.TILEW/2)+pos.y*(CONSTANTS.TILEW/2),pos.y*(CONSTANTS.TILEH/2)-pos.x*(CONSTANTS.TILEH/2)+7);
        // +7 for tile height (brown bits)
    }


    // Checks if a tile is null (empty) for boundaries of map
    private boolean checkNull(float x, float y){
        //System.out.println("Checking X "+ x +" Y "+ y);
        return (layer.getCell((int) x,(int) y) != null);
    }

    private void interact(float x, float y){
        // https://stackoverflow.com/questions/29420656/how-to-add-a-pop-up-menu-in-libgdx
        // https://github.com/libgdx/libgdx/wiki/Table
        for (Knight knight : data.getPlayerUnits()){
            if (knight.pos.x == this.pos.x && knight.pos.y == this.pos.y){
                int id = data.getPlayerUnits().indexOf(knight);
                if (data.getPlayerUnits().get(id).state != Knight.State.DONE)
                    data.getPlayerUnits().get(id).state = Knight.State.SELECTED;
                while (data.getPlayerUnits().get(id).state == Knight.State.SELECTED){
                    //MathUtils.clamp(this.pos.x, this.pos.x - knight.stats.movement, this.pos.x + knight.stats.movement);
                    //MathUtils.clamp(this.pos.y, this.pos.y - knight.stats.movement, this.pos.y + knight.stats.movement);
                    if (Gdx.input.isButtonPressed(Input.Keys.E)){
                        System.out.println("Pressed");
                        data.getPlayerUnits().get(data.getPlayerUnits().indexOf(knight)).setPos(this.pos);
                        data.getPlayerUnits().get(data.getPlayerUnits().indexOf(knight)).state = Knight.State.DONE;
                    }
                    // show movement tiles
                }
                System.out.println("Player unit found");
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
        int tmp = layer.getCell((int) x,(int) y).getTile().getId()-1;
        if (tmp == 0) { // Red barracks
            data.addPlayerUnits(new Vector2(this.pos.x, this.pos.y));
            System.out.println("Red Barracks");
            // build units menu
        } else if (tmp == 2){ // Red castle
            // surrender option or something?
            System.out.println("Red Castle");
        } else if (tmp == 3){ // Red castle
            // surrender option or something?
            System.out.println("Red City");
        } else {
            System.out.println(tmp);
            // just display tile stats or something
        }
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
