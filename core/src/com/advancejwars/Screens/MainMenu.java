package com.advancejwars.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

public class MainMenu implements Screen {

    private Stage stage;
    private Table table;
    private Skin skin;

    @Override
    public void show() {
        stage = new Stage();
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
            public void clicked(InputEvent event, float x, float y) {
                ((Game) Gdx.app.getApplicationListener()).setScreen(new LevelMenu());
            }
        });

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
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {
        skin.dispose();
        stage.dispose();
    }
}