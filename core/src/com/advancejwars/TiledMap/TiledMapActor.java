package com.advancejwars.TiledMap;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

import java.util.ArrayList;

public class TiledMapActor extends Actor {
    private TiledMap tiledMap;
    private TiledMapTileLayer tiledLayer;
    private TiledMapTileLayer mapLayer;
    private TiledMapTileLayer.Cell cell;
    private Vector2 position;

    public TiledMapActor(TiledMap tiledMap, TiledMapTileLayer tiledLayer, TiledMapTileLayer.Cell cell, Vector2 position) {
        this.tiledMap = tiledMap;
        this.tiledLayer = tiledLayer;
        this.cell = cell;
        this.position = position;
    }

    public Vector2 getPosition() { return position; }
    public TiledMapTileLayer.Cell getCell() {
        return cell;
    }

    //public TiledMapTileLayer.Cell getCellTileMap() { return mapLayer.getCell(); }

}