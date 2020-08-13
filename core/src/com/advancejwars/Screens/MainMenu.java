package com.advancejwars.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.moveTo;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

public class MainMenu extends StageBasedScreen {
    private Table table;
    private Skin skin;

    @Override
    public void show() {
        //stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        skin = new Skin();
        skin.add("PlayBtn", new Texture("ui/Play_up.png"));
        skin.add("PlayBtn_d", new Texture("ui/Play_down.png"));
        skin.add("ExitBtn", new Texture("ui/Exit_up.png"));
        skin.add("ExitBtn_d", new Texture("ui/Exit_down.png"));
        skin.add("BG", new Texture("ui/BG.png"));
        table = new Table(skin);
        table.setBackground(skin.getDrawable("BG"));
        table.setFillParent(true);

        Button playBtn = new Button(skin.getDrawable("PlayBtn"), skin.getDrawable("PlayBtn_d"));
        Button exitBtn = new Button(skin.getDrawable("ExitBtn"), skin.getDrawable("ExitBtn_d"));

        playBtn.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) { ((Game) Gdx.app.getApplicationListener()).setScreen(new LevelMenu()); }});

        exitBtn.addListener(new ClickListener(){

            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });


        table.add(playBtn).spaceBottom(0).row();
        table.add(new Image(new Texture("ui/Chains.png"))).spaceBottom(0).row();
        table.add(exitBtn).spaceBottom(15).row();

        stage.addActor(table);
        stage.addAction(sequence(moveTo(0, stage.getHeight()), moveTo(0, 0, .3f))); // coming in from top animation
    }

    @Override
    public void dispose() {
        skin.dispose();
        stage.dispose();
    }
}
