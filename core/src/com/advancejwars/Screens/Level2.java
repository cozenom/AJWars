package com.advancejwars.Screens;

import com.advancejwars.AdvanceJavaWars;
import com.advancejwars.TiledMap.TiledMapStage;
import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.IsometricTiledMapRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class Level2 implements Screen{
    private TiledMap map;
    private IsometricTiledMapRenderer renderer;
    private OrthographicCamera camera;
    private TiledMapStage stage;

    @Override
    public void show() {
        camera = new OrthographicCamera();
        camera.translate(160,40);
        camera.update();
        map = new TmxMapLoader().load("map/Test.tmx");
        renderer = new IsometricTiledMapRenderer(map);

        Stage stage = new TiledMapStage(map);
        stage.getViewport().setCamera(camera);
        Gdx.input.setInputProcessor(stage);
        camera.update();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);
        renderer.setView(camera);
        renderer.render();
        camera.update();
        if (Gdx.input.isKeyPressed(Input.Keys.Q))
            camera.zoom += 0.02;
        if (Gdx.input.isKeyPressed(Input.Keys.E))
            camera.zoom -= 0.02;
        if(Gdx.input.isKeyPressed(Input.Keys.A))
            camera.translate(-1,0);
        if(Gdx.input.isKeyPressed(Input.Keys.D))
            camera.translate(1,0);
        if(Gdx.input.isKeyPressed(Input.Keys.W))
            camera.translate(0,1);
        if(Gdx.input.isKeyPressed(Input.Keys.S))
            camera.translate(0,-1);


        camera.zoom = MathUtils.clamp(camera.zoom, 0.1f, 0.5f);
        camera.position.x = MathUtils.clamp(camera.position.x, 0, 320);
        camera.position.y = MathUtils.clamp(camera.position.y, 0, 80);
    }

    @Override
    public void resize(int width, int height) {
        camera.viewportHeight = height;
        camera.viewportWidth = width;
        camera.update();
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
        map.dispose();
        renderer.dispose();
    }
}
