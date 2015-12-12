package com.pinducas.cliche.core;

import com.badlogic.gdx.Game;
import com.pinducas.cliche.screens.TestStage;

public class MyGame extends Game {
	
	@Override
	public void create () {
		setScreen(new TestStage());		
	}

	@Override
	public void render () {
		super.render();
	}
}
