package com.advancejwars.Entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

public class Knight extends Actor implements Unit{
    private Texture texture;
    private Vector2 pos;
    private Sprite sprite;
    private final int sizeX;
    private final int sizeY;
    private boolean focus;


    public Knight(Vector2 pos, int team){
        this.pos = pos;
        if (team == 0){ this.texture = new Texture(Gdx.files.internal("assets/units/Knight.png")); }
        else if (team == 1){this.texture = new Texture(Gdx.files.internal("assets/units/Knight_Blu.png"));}
        this.sprite = new Sprite(this.texture);
        sizeX = this.texture.getWidth();
        sizeY = this.texture.getHeight();


    }


    @Override
    public boolean move(Vector2 pos) {
        this.pos.x += pos.x;
        this.pos.y += pos.y;
        return false;
    }

    @Override
    public boolean attack(Vector2 pos) {
        return false;
    }

    @Override
    public boolean receiveFocus() {
        focus = true;
        return false;
    }

    @Override
    public boolean loseFocus() {
        focus = false;
        return false;
    }

    @Override
    public String info() {
        return null;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(this.sprite, this.pos.x*16+this.pos.y*16+12, this.pos.y*8-this.pos.x*8+12);
        //this.setPosition(pos.x*(WIDTH/2)+pos.y*(WIDTH/2)+12,pos.y*(HEIGHT/2)-pos.x*(HEIGHT/2)+12);

        super.draw(batch, parentAlpha);
    }
}
