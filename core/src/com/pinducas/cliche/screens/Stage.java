package com.pinducas.cliche.screens;

import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.World;
import com.pinducas.cliche.core.MyGame;

public class Stage {
	
	protected final float BOX_TIME_STEP = 1/60.f;
	protected final int BOX_VELOCITY_ITERATIONS = 6;
	protected final int BOX_POSITION_ITERATIONS = 2;
	
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
		while(acumulator >= BOX_TIME_STEP){
		      world.step(BOX_TIME_STEP,BOX_VELOCITY_ITERATIONS,BOX_POSITION_ITERATIONS);
		      acumulator -= BOX_TIME_STEP;
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
}