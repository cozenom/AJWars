package com.advancejwars;

import com.advancejwars.Screens.Level1;
import com.advancejwars.Screens.Splash;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;

public class AdvanceJavaWars extends Game {


	private Game game;

	@Override
	public void create () {
		game = this;
		//setScreen(new Splash());
		((Game) Gdx.app.getApplicationListener()).setScreen(new Level1());
	}


	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
		super.dispose();
	}

	public void MyScreen(Game game) {
		this.game = game;
	}
}
