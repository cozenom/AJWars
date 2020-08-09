package com.advancejwars.desktop;

import com.advancejwars.AdvanceJavaWars;
import com.advancejwars.CONSTANTS;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = CONSTANTS.WIDTH;
		config.height = CONSTANTS.HEIGHT;
		config.title = CONSTANTS.TITLE +" "+ CONSTANTS.VER;
		config.vSyncEnabled = true;
		config.useGL30 = false;
		new LwjglApplication(new AdvanceJavaWars(), config);
	}
}
