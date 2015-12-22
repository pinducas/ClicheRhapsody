package com.pinducas.cliche.screens;

import javax.swing.JOptionPane;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
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

import box2dLight.PointLight;
import box2dLight.RayHandler;

public class TestStage extends Stage implements Screen{
	
	private Player player;
	
	private PointLight[] lights;
	
	private float stageLight;
	
	private boolean showBoxes;
	private boolean showLight;
	
	private boolean showInstructions;
	
	public TestStage(MyGame game){
		this.game = game;
		camera = new OrthographicCamera();
		world = new World(new Vector2(0, -10), true);
		b2dRenderer = new Box2DDebugRenderer();	
		batch = new SpriteBatch();
	
		initController();
		
		player = new Player(world,gamepad, 200 , 200);
		
		map = new Map(world,player, camera,"Maps/ponte.map");
		
		
		stageLight = 0.1f;
		rayHandler = new RayHandler(world);
		rayHandler.setAmbientLight(0.1f, 0.1f, 0.1f, stageLight);
		rayHandler.setShadows(true);
		
		lights = new PointLight[4];
		lights[0] = new PointLight(rayHandler,80,new Color(0.1f,0.1f,0.5f,0.7f),60*Const.pixelToMeter,
				250*Const.pixelToMeter,270*Const.pixelToMeter);
		lights[1] = new PointLight(rayHandler,80,new Color(0.8f,0.4f,0.1f,1f),400*Const.pixelToMeter,
				250*Const.pixelToMeter,270*Const.pixelToMeter);
		lights[2] = new PointLight(rayHandler,80,new Color(0.8f,0.4f,0.1f,1f),400*Const.pixelToMeter,
				835*Const.pixelToMeter,470*Const.pixelToMeter);
		lights[3] = new PointLight(rayHandler,80,new Color(0.8f,0.4f,0.1f,1f),400*Const.pixelToMeter,
				1415*Const.pixelToMeter,470*Const.pixelToMeter);
		
		
		lights[0].attachToBody(player.body);
		
		init();
	}
	
	public void init(){
		super.init();
		camera.setToOrtho(false,1280 * Const.pixelToMeter,720 * Const.pixelToMeter);
		showBoxes = false;
		showLight = true;
		showInstructions = false;
	}
	
	@Override
	public void update(float delta){
		worldAndHandlerStep(delta);
		
		map.update(delta);
		
		player.update(delta);
		
		if(player.getX()-camera.position.x > 5){
				camera.translate(player.getX()-camera.position.x - 5,0);
			}
		else if(player.getX() - camera.position.x < -5 && camera.position.x > 10){
			camera.translate(5 + player.getX()-camera.position.x,0);
		}
		
		
		if(Gdx.input.isKeyJustPressed(Input.Keys.F1)){
			showBoxes = !showBoxes;
		}
		if(Gdx.input.isKeyJustPressed(Input.Keys.F2)){
			showLight = !showLight;
		}
		
		if(player.getX() > 42 && !showInstructions){
			JOptionPane.showMessageDialog(null, "Press F1 and F2 to understand how it works. All but the lights made with the editor. Next step with the editor is create a Control z function and add the lights tool!");
			showInstructions = true;
		}

		
		if(player.getX() > 26.5f && stageLight < 1){
			stageLight += delta*0.5f;
			if(stageLight > 1)stageLight = 1;
			rayHandler.setAmbientLight(new Color(stageLight*0.05f,0,stageLight*0.02f,stageLight));
		}
		
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
		
		if(showBoxes)b2dRenderer.render(world, camera.combined);

		rayHandler.setCombinedMatrix(camera);
		if(showLight)rayHandler.render();
		
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

		Gdx.gl.glClearColor(0.2f, 0.6f, 0.7f, 1);
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
