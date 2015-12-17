package com.pinducas.cliche.screens;

import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.pinducas.cliche.core.MyGame;
import com.pinducas.cliche.map.Map;
import com.pinducas.cliche.tools.Const;

public class Stage {
	
	//PRIMITIVES
	public boolean disposed;
	protected float acumulator;
	
	//NULLABLE
	protected MyGame game;
	protected OrthographicCamera camera;
	protected Controller gamepad;
		
	//DISPOSABLE
	protected World world;
	protected SpriteBatch batch;
	protected Box2DDebugRenderer b2dRenderer;
	protected Map map;
	
	public void initController(){
		Controller c = null;
		if(Controllers.getControllers().size != 0){
			c = Controllers.getControllers().first();		
		}
		gamepad = c;
	}
	
	public void worldStep(float delta){
		//LIBGDX doc says this is good for slower machines
		float frameTime = Math.min(delta, 0.25f);
		acumulator += frameTime;
		while(acumulator >= Const.BOX_TIME_STEP){
		      world.step(Const.BOX_TIME_STEP,Const.BOX_VELOCITY_ITERATIONS,Const.BOX_POSITION_ITERATIONS);
		      acumulator -= Const.BOX_TIME_STEP;
		}
	}
	
	public void init(){
		disposed = false;
		acumulator = 0;
	
	}
	
	public void update(float delta){
		
	}
	
	public void draw(){
	
	}
	
	public void dispose(){
		disposed = true;
		game = null;
		camera = null;
		gamepad = null;
		batch.dispose();
		map.dispose();
		b2dRenderer.dispose();
		world.dispose();
		
	}
	
}