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
import com.pinducas.cliche.map.Map;
import com.pinducas.cliche.tools.Const;

public class TestStage extends Stage implements Screen{
	
	private Player player;
	
	public TestStage(MyGame game){
		this.game = game;
		camera = new OrthographicCamera();
		world = new World(new Vector2(0, -10), true);
		b2dRenderer = new Box2DDebugRenderer();	
		batch = new SpriteBatch();
		
		initController();
		
		player = new Player(world,gamepad, 200 , 200);
		
		map = new Map(world,player, camera,"Maps/testmap.map");
		
		init();
	}
	
	public void init(){
		super.init();
		camera.setToOrtho(false,1280 * Const.pixelToMeter,720 * Const.pixelToMeter);

	}
	
	@Override
	public void update(float delta){
		worldStep(delta);
		
		map.update(delta);
		
		player.update(delta);
		
		camera.update();
		
		if(gamepad == null)keyboardControls();
		else gamepadControls();
	}
	
	@Override
	public void draw(){
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
				
		map.draw(batch);
		
		player.draw(batch);
		
		batch.end();
		b2dRenderer.render(world, camera.combined);

	}
	
	private void keyboardControls(){
		if(Gdx.input.isKeyJustPressed(Input.Keys.F4)){
			dispose();
			Gdx.app.exit();
			disposed = true;
		}
	}
	
	private void gamepadControls(){
		if(gamepad.getAxis(0) > 0.2f)camera.translate(new Vector2(0.1f,0));
		if(gamepad.getAxis(0) < -0.2f)camera.translate(new Vector2(-0.1f,0));
		if(gamepad.getAxis(1) > 0.2f)camera.translate(new Vector2(0,-0.1f));
		if(gamepad.getAxis(1) < -0.2f)camera.translate(new Vector2(0f,0.1f));
		if(gamepad.getButton(8)){
			dispose();
			Gdx.app.exit();
		}
	
	}
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(1f, 1f, 1f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		Gdx.graphics.setTitle(""+Gdx.graphics.getFramesPerSecond());
		
		update(delta);
		if(disposed)return;
		draw();
	}

	@Override
	public void dispose() {
		super.dispose();
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
