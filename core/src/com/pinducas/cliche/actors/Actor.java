package com.pinducas.cliche.actors;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.pinducas.cliche.tools.GameObject;

public class Actor extends GameObject{
	
	public boolean facingRight;	
	
	//NULLABLE
	public TextureRegion currentFrame;
	public Body body;	
	
	//DISPOSABLE
	
	protected void translate(float x, float y){
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
