package com.pinducas.cliche.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.pinducas.cliche.actors.Player;
import com.pinducas.cliche.core.MyGame;

public class TestStage extends Stage implements Screen{
		
	private Player player;
	
	public TestStage(MyGame game){
		camera = new OrthographicCamera();
		camera.setToOrtho(false,800,600);
		
		this.game = game;
		
		batch = new SpriteBatch();
		
		initController();
		
		player = new Player();
		
		init();
	}
	
	public void init(){
		//This method will start all stage variables and will be called when the player restarts a stage
		disposed = false;
		player.position[0] = 100;
		player.position[1] = 100;
	}
	
	@Override
	public void update(float delta){
		//Do the update here
		if(gamepad == null)keyboardControls();
		else gamepadControls();
		
		player.update(delta);
		
		camera.update();
	}
	
	@Override
	public void draw(){
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		//Draw here
		
		player.draw(batch);
		
		batch.end();
	}
	
	private void keyboardControls(){
		if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)){
			dispose();
			Gdx.app.exit();
			disposed = true;
		}
	}
	
	private void gamepadControls(){
		if(gamepad.getButton(8)){
			dispose();
			Gdx.app.exit();
			disposed = true;
		}
	}
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		update(delta);
		//This statement makes sure no disposed object is drawn
		if(disposed)return;
		draw();
	}

	@Override
	public void dispose() {
		batch.dispose();
	}
	
	
	//Fim de classe
	@Override
	public void resize(int width, int height) {}
	@Override
	public void pause() {}
	@Override
	public void resume() {}
	@Override
	public void hide() {}	
	@Override
	public void show() {}
	
}
