package com.advancejwars;

import com.advancejwars.Screens.Splash;
import com.badlogic.gdx.Game;

public class AdvanceJavaWars extends Game {


	private Game game;

	@Override
	public void create () {
		game = this;
		setScreen(new Splash());
	}


	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
		super.dispose(); //rm?
	}

	public void MyScreen(Game game) {
		this.game = game;
	}
}
