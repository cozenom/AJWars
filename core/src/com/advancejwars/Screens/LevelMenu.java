package com.advancejwars.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

public class LevelMenu extends StageBasedScreen {

    //private Stage stage;
    private Table table;
    private Skin skin;

    @Override
    public void show() {
        //stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        skin = new Skin();
        skin.add("BG", new Texture("ui/BG.png"));
        skin.add("ExitBtn", new Texture("ui/Exit_up.png"));
        skin.add("ExitBtn_d", new Texture("ui/Exit_down.png"));

        skin.add("Level1Btn", new Texture("ui/level_1_up.png"));
        skin.add("Level1Btn_d", new Texture("ui/level_1_down.png"));

        skin.add("Level2Btn", new Texture("ui/level_2_up.png"));
        skin.add("Level2Btn_d", new Texture("ui/level_2_down.png"));

        skin.add("Level3Btn", new Texture("ui/level_3_up.png"));
        skin.add("Level3Btn_d", new Texture("ui/level_3_down.png"));


        skin.add("Back", new Texture("ui/Back_up.png"));
        skin.add("Back_d", new Texture("ui/Back_down.png"));

        // Buttons
        Button lvl1 = new Button(skin.getDrawable("Level1Btn"), skin.getDrawable("Level1Btn_d"));
        Button lvl2 = new Button(skin.getDrawable("Level2Btn"), skin.getDrawable("Level2Btn_d"));
        Button lvl3 = new Button(skin.getDrawable("Level3Btn"), skin.getDrawable("Level3Btn_d"));

        Button back = new Button(skin.getDrawable("Back"), skin.getDrawable("Back_d"));
        Button exitBtn = new Button(skin.getDrawable("ExitBtn"), skin.getDrawable("ExitBtn_d"));

        // Button listeners
        lvl1.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Game) Gdx.app.getApplicationListener()).setScreen(new Level1());
            }
        });
        lvl2.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                //((Game) Gdx.app.getApplicationListener()).setScreen(new Level2());
            }
        });
        lvl2.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                //((Game) Gdx.app.getApplicationListener()).setScreen(new Level3());
            }
        });
        back.addListener(new ClickListener() {

            @Override
            public void clicked(InputEvent event, float x, float y) {
                stage.addAction(sequence(moveTo(0, -stage.getHeight(), .3f), run(new Runnable() {
                    @Override
                    public void run() {
                        ((Game) Gdx.app.getApplicationListener()).setScreen(new MainMenu());
                    }

                })));
            }
        });
        exitBtn.addListener(new ClickListener(){

            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });

        
        // Table for button layout --
        // https://github.com/libgdx/libgdx/wiki/Table#cell-properties
        table = new Table(skin);
        table.setBackground(skin.getDrawable("BG"));
        table.setFillParent(true);

        table.add(lvl1).pad(8).uniformX().padBottom(100);
        table.add(lvl2).pad(8).uniformX().padBottom(100);
        table.add(lvl3).pad(8).uniformX().padBottom(100).row();

        table.add(back).pad(8).uniformX();
        table.add();
        table.add(exitBtn).pad(8).bottom().right();

        stage.addActor(table);
        stage.addAction(sequence(moveTo(0, stage.getHeight()), moveTo(0, 0, .3f))); // coming in from top animation
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);

        stage.act(delta);
        stage.draw();
    }


    @Override
    public void dispose() {
        skin.dispose();
        stage.dispose();
    }
}
