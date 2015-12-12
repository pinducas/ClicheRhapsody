package com.pinducas.cliche.screens;

import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.pinducas.cliche.core.MyGame;

public class Stage {
	
	//PRIMITIVES
	public boolean disposed;
		
	//NULLABLE
	protected MyGame game;
	protected OrthographicCamera camera;
	protected Controller gamepad;
		
	//DISPOSABLE
	protected SpriteBatch batch;
	
	public void initController(){
		Controller c = null;
		if(Controllers.getControllers().size != 0){
			c = Controllers.getControllers().first();		
		}
		gamepad = c;
	}
	
	public void init(){
		
	}
	
	public void update(float delta){
		
	}
	
	public void draw(){
	
	}
}