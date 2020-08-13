package com.advancejwars.Screens;

import com.advancejwars.CONSTANTS;
import com.advancejwars.Entities.Controller;
import com.advancejwars.Entities.GameData;
import com.advancejwars.Entities.Knight;
import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.IsometricTiledMapRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import java.util.ArrayList;

public class Level1 extends StageBasedScreen{
    private TiledMap map;
    private IsometricTiledMapRenderer renderer;
    private OrthographicCamera camera;
    private GameData data;
    private Controller controller;

    private final Sprite redKnight = new Sprite(new Texture("units/Knight_Red.png"));
    private final Sprite bluKnight = new Sprite(new Texture("units/Knight_Blue.png"));

    private final ArrayList<Knight> playerList = new ArrayList<Knight>(){
        {
            add(new Knight(new Vector2(9,8), redKnight));
            add(new Knight(new Vector2(8,9), redKnight));
            add(new Knight(new Vector2(7,8), redKnight));
            add(new Knight(new Vector2(6,7), redKnight));
            add(new Knight(new Vector2(8,4), redKnight));
        }
    };
    private final ArrayList<Knight> enemyList = new ArrayList<Knight>(){
        {
            add(new Knight(new Vector2(1,2), bluKnight));
            add(new Knight(new Vector2(3,4), bluKnight));
            add(new Knight(new Vector2(2,2), bluKnight));
            add(new Knight(new Vector2(1,3), bluKnight));
            add(new Knight(new Vector2(3,0), bluKnight));
        }
    };

    private final Skin skin = new Skin();
    Group pauseGroup;
    Table table;

    Sprite redTurn, blueTurn;

    @Override
    public void show() {
        camera = new OrthographicCamera();
        camera.translate(160,40);
        map = new TmxMapLoader().load("map/Test.tmx");
        renderer = new IsometricTiledMapRenderer(map);

        //data = new GameData(playerList, enemyList);
        data = new GameData();

        // Pause menu stuff
        skin.add("PlayBtn", new Texture("ui/Play_up.png"));
        skin.add("PlayBtn_d", new Texture("ui/Play_down.png"));
        skin.add("ExitBtn", new Texture("ui/Exit_up.png"));
        skin.add("ExitBtn_d", new Texture("ui/Exit_down.png"));


        // Create controller
        controller = new Controller(new Sprite(new Texture("map/Tiles/Controller.png")), map, data);
        Gdx.input.setInputProcessor(controller);

        // Turn stuff
        redTurn = new Sprite(new Texture("img/Banner_R.png"));
        redTurn.setPosition(300,75);
        blueTurn = new Sprite(new Texture("img/Banner_B.png"));
        blueTurn.setPosition(-20,75);

        camera.update();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);
        renderer.setView(camera);
        renderer.render();

        Batch batch = new SpriteBatch();
        batch.setProjectionMatrix(camera.combined);

        // Draw stuff in here
        batch.begin();
        controller.draw(batch);

        // Draw knights
        // TODO - animations ?
        // TODO - fix overlap - keeps drawing stuff on top so start at top and render down
        for (Knight k : data.getPlayerUnits()){ k.draw(batch); }
        for (Knight k : data.getEnemyUnits()){k.draw(batch); }

        // Draw turn banners
        if(controller.turn){
            redTurn.draw(batch);
        } else {
            blueTurn.draw(batch);
        }

        if (controller.checkVictory() > 0){
            if (controller.checkVictory() == 1){ // RED
                Gdx.graphics.setContinuousRendering(false);

                Sprite s = new Sprite(new Texture("ui/VICTORY.png"));
                s.setPosition(75,0);
                s.draw(batch);
            } else { // BLUE
                Gdx.graphics.setContinuousRendering(false);

                Sprite s = new Sprite(new Texture("ui/DEFEAT.png"));
                s.setPosition(75,0);
                s.draw(batch);
            }
        }


        // TODO - at some point optimize draw order somehow (fix overlaps)
        // https://www.geeksforgeeks.org/collections-sort-java-examples/ sorting might help

        batch.end();



        if(Gdx.input.isKeyPressed(Input.Keys.ESCAPE))
            System.out.println("Pausing");
            pause();
        /*
        if (Gdx.input.isKeyPressed(Input.Keys.Q))
            camera.zoom += 0.02;
        if (Gdx.input.isKeyPressed(Input.Keys.E))
            camera.zoom -= 0.02;
        */
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT))
            camera.translate(-1,0);
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT))
            camera.translate(1,0);
        if(Gdx.input.isKeyPressed(Input.Keys.UP))
            camera.translate(0,1);
        if(Gdx.input.isKeyPressed(Input.Keys.DOWN))
            camera.translate(0,-1);
        camera.zoom = MathUtils.clamp(camera.zoom, 0.1f, 0.5f);
        camera.position.x = MathUtils.clamp(camera.position.x, 0, 320);
        camera.position.y = MathUtils.clamp(camera.position.y, 0, 80);
        camera.update();
    }

    @Override
    public void resize(int width, int height) {
        camera.viewportHeight = height;
        camera.viewportWidth = width;
        camera.update();
    }

    @Override
    public void pause() {
        pauseGroup = new Group();

        table = new Table(skin);
        Button playBtn = new Button(skin.getDrawable("PlayBtn"), skin.getDrawable("PlayBtn_d"));
        Button exitBtn = new Button(skin.getDrawable("ExitBtn"), skin.getDrawable("ExitBtn_d"));
        playBtn.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) { resume(); }});
        exitBtn.addListener(new ClickListener(){

            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });
        table.add(playBtn).spaceBottom(0).row();
        table.add(new Image(new Texture("ui/Chains.png"))).spaceBottom(0).row();
        table.add(exitBtn).spaceBottom(15).row();
        pauseGroup.addActor(table);
    }

    @Override
    public void resume() {
        pauseGroup.remove();
    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {
        map.dispose();
        renderer.dispose();
        //stage.dispose();
    }
}
