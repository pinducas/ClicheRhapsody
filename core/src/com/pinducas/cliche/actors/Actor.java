package com.pinducas.cliche.actors;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;

public class Actor {
	
	public boolean facingRight;	
	
	//NULLABLE
	public TextureRegion currentFrame;
	protected Body body;	

	//DISPOSABLE
	
	protected void movimenta(float x, float y){
		body.setTransform(getX() + x, getY() + y, 0);
	}
	
	public float getX(){return body.getPosition().x;}
	public float getY(){return body.getPosition().y;}
	
	public void init(){
		
	}
	
	public void update(float delta){
		
	}
	
	public void draw(SpriteBatch batch){
		
	}
	
	public void dispose(){
		
	}
	
	
}
