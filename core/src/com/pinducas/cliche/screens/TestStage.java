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
import com.pinducas.cliche.actors.Actor;
import com.pinducas.cliche.actors.Box;
import com.pinducas.cliche.actors.Circle;
import com.pinducas.cliche.actors.Player;
import com.pinducas.cliche.core.Constants;
import com.pinducas.cliche.core.MyGame;

public class TestStage extends Stage implements Screen{
	
	private Box2DDebugRenderer brender;
	private Player player;
	
	private Actor []shapes;
	
	public TestStage(MyGame game){
		camera = new OrthographicCamera();
		//Changed dimensions to meters
		
		world = new World(new Vector2(0, -10f), true);
		
		brender = new Box2DDebugRenderer();	
		batch = new SpriteBatch();
		
		this.game = game;	
		
		shapes = new Actor[2];
		shapes[0] = new Circle(world, 500, 200);
		shapes[1] = new Box(world, 400,-100,800,300);
		
		initController();
		
		//PASS IN PIXEL COORDINATES BECAUSE THE CLASS CONVERTS IT TO METERS INSIDE OF IT
		player = new Player(world,gamepad, 200 , 200);
		
		init();
	}
	
	public void init(){
		super.init();
		//This method will start all stage variables and will be called when the player restarts a stage
		camera.setToOrtho(false,800 * Constants.pixelToMeter,600 * Constants.pixelToMeter);

	}
	
	@Override
	public void update(float delta){
		worldStep(delta);
		
		//Do the update here		
		player.update(delta);
		
		camera.update();
		
		if(gamepad == null)keyboardControls();
		else gamepadControls();
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
		if(Gdx.input.isKeyJustPressed(Input.Keys.F4)){
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
		Gdx.gl.glClearColor(1f, 1f, 1f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		//There were two calls for this method
		//brender.render(world, camera.combined);
		
		//As i nulled and disposed some box2d elements lets leave this one here as well
		update(delta);
		//This statement makes sure no disposed object is drawn
		if(disposed)return;
		draw();
	}

	@Override
	public void dispose() {
		game = null;
		camera = null;
		gamepad = null;
		brender.dispose();
		world.dispose();
		batch.dispose();
		player.dispose();
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
