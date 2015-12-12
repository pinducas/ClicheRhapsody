package com.pinducas.cliche.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.pinducas.cliche.actors.Player;
import com.pinducas.cliche.core.MyGame;

public class TestStage extends Stage implements Screen{
		
	private World world;
	private Box2DDebugRenderer brender;
	private Player player;
	
	public TestStage(MyGame game){
		camera = new OrthographicCamera();
		camera.setToOrtho(false,800,600);
		world = new World(new Vector2(0, 0), true);
		brender = new Box2DDebugRenderer();		
		
		
		
		
		this.game = game;
		
		batch = new SpriteBatch();
		
		initController();
		
		player = new Player(world, 300 , 300);
		
		init();
	}
	
	public void init(){
		//This method will start all stage variables and will be called when the player restarts a stage
		disposed = false;
	}
	
	@Override
	public void update(float delta){
		world.step(Gdx.graphics.getDeltaTime(), 6, 2);
		
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
		brender.render(world, camera.combined);

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
		brender.render(world, camera.combined);
		
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
