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
    ArrayList<Vector2> unitList;


    public Controller(Sprite sprite, TiledMap map, Vector2 pos, ArrayList<Vector2> unitList){
        super(sprite);
        this.map = map;
        this.pos = pos;
        this.layer = (TiledMapTileLayer) map.getLayers().get("Tilemap");
        this.unitList = unitList;
    }

    @Override
    public void draw(Batch batch) {
        super.draw(batch);
        this.setPosition(pos.x*(CONSTANTS.TILEW/2)+pos.y*(CONSTANTS.TILEW/2),pos.y*(CONSTANTS.TILEH/2)-pos.x*(CONSTANTS.TILEH/2)+7);
        // +7 for tile height (brown bits)
    }

    private void update(float delta) {
        // TODO implement movement animations
    }

    // Checks if a tile is null (empty) for boundaries of map
    private boolean checkNull(float x, float y){
        //System.out.println("Checking X "+ x +" Y "+ y);
        return (layer.getCell((int) x,(int) y) != null);
    }

    private void interact(float x, float y){
        for (Vector2 pos : unitList){
            if (pos.x == x && pos.y == y){
                System.out.println("Unit found");
                // select unit and do something
                break;
            }
        }
        // else no unit selected must be something else

        int tmp = layer.getCell((int) x,(int) y).getTile().getId();
        if (tmp == 0) { // Red barracks
            // build units
        } else if (tmp == 2){ // Red castle
            // surrender option or something?
        } else {
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
        }
        // Interact with environment / units here - probably E

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
