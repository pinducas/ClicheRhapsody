package com.pinducas.cliche.actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Player extends Actor {

	public float [] position;
	
	//DISPOSABLE
	private Texture sheet;
	
	//NULLABLE
	
	public Player(){
		
	}
	
	@Override
	public void init(){
		
		position = new float[2];
		position[0] = 1000;
		position[1] = 100;
	}
	
	@Override
	public void update(float delta){
		
	}
	
	@Override
	public void draw(SpriteBatch batch){
		
	}
	
	@Override
	public void dispose(){
		sheet.dispose();
	}
	
}
