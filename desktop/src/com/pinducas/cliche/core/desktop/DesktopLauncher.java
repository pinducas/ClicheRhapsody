package com.pinducas.cliche.core.desktop;  

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.pinducas.cliche.core.MyGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 1024;
		config.height = 576;
		config.useGL30 = false;
		config.title = "Cliche Stuff";
		new LwjglApplication(new MyGame(), config);
	}
}
