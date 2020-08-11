package com.advancejwars.Entities;

import com.advancejwars.CONSTANTS;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;


public class playerTest extends Sprite implements InputProcessor {
    TiledMap map;
    // Layer to check for collision
    TiledMapTileLayer layer;
    // Vector for position
    Vector2 pos;

    public State state;
    public UnitStats unitStats= new UnitStats("Knight", 2, 100, 30, 2, 1, 2);


    // TODO -- IMPLEMENT
    public enum State {
        IDLE,
        SELECTED,
        MOVING,
        DONE
    }


    public playerTest(Sprite sprite, TiledMap map, Vector2 posnew){
        super(sprite);
        this.pos = posnew;
        this.map = map;
        this.layer = (TiledMapTileLayer) map.getLayers().get("Tilemap");
        // TODO something here
    }


    /*
    TODO: WITH ANIMATION
    https://hg.sr.ht/~dermetfan/tiledmapgame/browse/TiledMapGame/src/net/dermetfan/tiledMapGame/entities/Player.java?rev=tip

    public Player(Animation still, Animation left, Animation right, TiledMapTileLayer collisionLayer) {
        super(still.getKeyFrame(0));
        this.still = still;
        this.left = left;
        this.right = right;
        this.collisionLayer = collisionLayer;
        setSize(collisionLayer.getWidth() / 3, collisionLayer.getHeight() * 1.25f);
    }
    */

    @Override
    public void draw(Batch batch) {
        update(Gdx.graphics.getDeltaTime());
        super.draw(batch);
        this.setPosition(pos.x*(CONSTANTS.TILEW /2)+pos.y*(CONSTANTS.TILEW/2)+12,pos.y*(CONSTANTS.TILEH/2)-pos.x*(CONSTANTS.TILEH/2)+12);
        //setScale(1);
    }

    private void update(float delta) {
        // TODO implement movement animations

    }

    private boolean checkTraversable(float x, float y){
        System.out.println("X "+ pos.x +" Y "+ pos.y +
                (layer.getCell((int) pos.x,(int) pos.y) != null &&
                        layer.getCell((int) pos.x,(int) pos.y).getTile().getProperties().containsKey("traversable")));

        return (layer.getCell((int) x,(int) y) != null && layer.getCell((int) x,(int) y).getTile().getProperties().containsKey("traversable"));
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        switch (keycode) {
            case Input.Keys.W:
                if (checkTraversable(this.pos.x, this.pos.y+1))
                    this.pos.y += 1;
                break;
            case Input.Keys.A:
                if (checkTraversable((int) pos.x-1, (int) pos.y))
                    this.pos.x -= 1;
                break;
            case Input.Keys.S:
                if (checkTraversable((int) pos.x, (int) pos.y-1))
                    this.pos.y -= 1;
                break;
            case Input.Keys.D:
                if (checkTraversable((int) pos.x+1, (int) pos.y))
                    this.pos.x += 1;
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
        Vector2 clickCoordinates = new Vector2(screenX,screenY);
        //this.pos = (clickCoordinates.x, clickCoordinates.y);
        System.out.println(pos);
        return true;
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
