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
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

// TODO - Make a level class to import and reduce clutter here

public class Level1 extends StageBasedScreen implements InputProcessor{
    private TiledMap map;
    private IsometricTiledMapRenderer renderer;
    private OrthographicCamera camera;
    private GameData data;
    private Controller controller;

    // Units to spawn
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
    HashMap<Knight, Float> renderOrder;

    private InputMultiplexer multiplexer;

    private final Skin skin = new Skin();
    Table table;
    boolean paused = false;

    Sprite redTurn, blueTurn;

    @Override
    public void show() {
        camera = new OrthographicCamera();
        camera.translate(160,40);
        map = new TmxMapLoader().load("map/Test.tmx");
        renderer = new IsometricTiledMapRenderer(map);


        data = new GameData(playerList, enemyList);
        //data = new GameData(); //-for debugging
        renderOrder = data.updateRenderOrder();

        // Create controller
        controller = new Controller(new Sprite(new Texture("map/Tiles/Controller.png")), map, data);

        // Multiplexer can have multiple inputs
        multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(this.stage);
        multiplexer.addProcessor(this);
        multiplexer.addProcessor(controller);

        Gdx.input.setInputProcessor(multiplexer);

        // Turn stuff
        redTurn = new Sprite(new Texture("img/Banner_R.png"));
        redTurn.setPosition(299,75);
        blueTurn = new Sprite(new Texture("img/Banner_B.png"));
        blueTurn.setPosition(-18,75);

        // TODO - change to continue
        skin.add("Back", new Texture("ui/Back_up.png"));
        skin.add("Back_d", new Texture("ui/Back_down.png"));
        skin.add("ExitBtn", new Texture("ui/Exit_up.png"));
        skin.add("ExitBtn_d", new Texture("ui/Exit_down.png"));

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

        renderOrder = data.updateRenderOrder();


        // Draw stuff in here
        batch.begin();
        controller.draw(batch);

        // Draw knights
        for (Map.Entry mapElement : renderOrder.entrySet()) {
            Knight k = (Knight) mapElement.getKey();
            k.draw(batch);
        }

        //for (Knight k : data.getPlayerUnits()){ k.draw(batch); }
        //for (Knight k : data.getEnemyUnits()){ k.draw(batch); }

        // Check victory
        if (controller.checkVictory() > 0){
            if (controller.checkVictory() == 1){ // RED
                Sprite s = new Sprite(new Texture("ui/VICTORY.png"));
                s.setPosition(75,0);
                s.draw(batch);
            } else { // BLUE
                Sprite s = new Sprite(new Texture("ui/DEFEAT.png"));
                s.setPosition(75,0);
                s.draw(batch);
            }
        }

        // Draw turn banners
        if(controller.turn && controller.checkVictory() == 0){
            redTurn.draw(batch);
        }
        else if (!controller.turn && controller.checkVictory() == 0){
            blueTurn.draw(batch);
        }
        else {
            redTurn.draw(batch);
            blueTurn.draw(batch);
        }

        if (paused){
            this.stage.addActor(table);
            this.stage.draw();
        }

        batch.end();

        /* debugging purposes
        if (Gdx.input.isKeyPressed(Input.Keys.Q))
            camera.zoom += 0.02;
        if (Gdx.input.isKeyPressed(Input.Keys.E))
            camera.zoom -= 0.02;

        if(Gdx.input.isKeyPressed(Input.Keys.LEFT))
            camera.translate(-1,0);
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT))
            camera.translate(1,0);
        if(Gdx.input.isKeyPressed(Input.Keys.UP))
            camera.translate(0,1);
        if(Gdx.input.isKeyPressed(Input.Keys.DOWN))
            camera.translate(0,-1);

        camera.position.x = MathUtils.clamp(camera.position.x, 0, 320);
        camera.position.y = MathUtils.clamp(camera.position.y, 0, 80);
        */
        //camera.zoom = MathUtils.clamp(camera.zoom, 0.1f, 0.5f);
        camera.zoom = 0.5f;
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
        System.out.println("Pausing");
        paused = true;

        multiplexer.removeProcessor(controller);
        table = new Table(skin);
        Button resumeBtn = new Button(skin.getDrawable("Back"), skin.getDrawable("Back_d"));
        Button exitBtn = new Button(skin.getDrawable("ExitBtn"), skin.getDrawable("ExitBtn_d"));

        resumeBtn.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) { resume(); }});
        exitBtn.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) { ((Game) Gdx.app.getApplicationListener()).setScreen(new MainMenu()); }});

        table.add(resumeBtn).spaceBottom(15).row();
        table.add(exitBtn).spaceBottom(15).row();
        table.setPosition(CONSTANTS.WIDTH/2,CONSTANTS.HEIGHT/2);
    }

    @Override
    public void resume() {
        System.out.println("Resuming");

        table.clear();
        paused = false;
        multiplexer.addProcessor(controller);
    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {
        map.dispose();
        renderer.dispose();
        stage.dispose();
    }


    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.ESCAPE && !paused) {
            Gdx.graphics.setContinuousRendering(false);
            pause();
        } else if ((keycode == Input.Keys.ESCAPE && paused)){
            Gdx.graphics.setContinuousRendering(true);
            resume();
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
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
