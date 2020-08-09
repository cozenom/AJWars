package com.advancejwars.TiledMap;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Stage;

import java.util.ArrayList;

public class TiledMapStage extends Stage {
    // BIBLE - https://github.com/libgdx/libgdx/wiki/Tile-maps

    private TiledMap tiledMap;
    private TiledMapTileLayer mapLayer;
    private TiledMapTileLayer selectionLayer;
    private Boolean isActive;

    public TiledMapStage(TiledMap tiledMap) {
        this.tiledMap = tiledMap;
        this.mapLayer = (TiledMapTileLayer)tiledMap.getLayers().get("Tilemap");
        //this.selectionLayer = (TiledMapTileLayer)tiledMap.getLayers().get("Selection");
        createActorsForLayer(mapLayer);
    }


    public TiledMapTile getTile(int x, int y){
        // Translate to coordinates
        int adjustedX = (int) (x/mapLayer.getHeight())+mapLayer.getHeight()/2;
        int adjustedY = (int) (y/mapLayer.getWidth())+mapLayer.getWidth()/2;
        return mapLayer.getCell(adjustedX,adjustedY).getTile();
    }

    private void createActorsForLayer(TiledMapTileLayer tiledLayer) {
        for (int x = 0; x < tiledLayer.getWidth(); x++) {
            for (int y = 0; y < tiledLayer.getHeight(); y++) {
                // http://clintbellanger.net/articles/isometric_math/
                TiledMapTileLayer.Cell cell = tiledLayer.getCell(x, y);
                TiledMapActor actor = new TiledMapActor(tiledMap, tiledLayer, cell, new Vector2(x,y));
                actor.setBounds(
                        x * (16) + y * (16), // W = 32, H = 16
                        -x * (8) + y * (8) + 8, // +8 Z offset constant
                        tiledLayer.getTileWidth(),
                        tiledLayer.getTileHeight());
                addActor(actor);
                EventListener eventListener = new TiledMapClickListener(actor);
                actor.addListener(eventListener);
            }
        }
    }
}