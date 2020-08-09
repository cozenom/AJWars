package com.advancejwars.TiledMap;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class TiledMapClickListener extends ClickListener {

    private TiledMapActor actor;

    public TiledMapClickListener(TiledMapActor actor) {
        this.actor = actor;
    }

    @Override
    public void clicked(InputEvent event, float x, float y) {
        // When unit clicked set

        System.out.println(actor.getCell().getTile().getId() +" "+ actor.getCell().getTile().getProperties().get("name") + " has been clicked.");
        System.out.println(actor.getX() +" , "+ actor.getY());
    }
}