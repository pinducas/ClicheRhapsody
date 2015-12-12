package com.pinducas.cliche.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Player extends Actor {

	//PRIMITIVES
	public float [] position;
	private float walk_delta;
	
	//DISPOSABLE
	private Texture sheet;
	
	//NULLABLE
	public TextureRegion currentFrame;

	private Animation walk_animation;
	
	public Player(){
		sheet = new Texture(Gdx.files.internal("Teste/Sheet.png"));
		
		TextureRegion [] walk_region = new TextureRegion[4];
		walk_region[0] = new TextureRegion(sheet,0,0,32,32);
		walk_region[1] = new TextureRegion(sheet,32,0,32,32);
		walk_region[2] = new TextureRegion(sheet,64,0,32,32);
		walk_region[3] = new TextureRegion(sheet,96,0,32,32);
		
		walk_animation = new Animation(0.2f,walk_region);
		
		currentFrame = walk_animation.getKeyFrame(walk_delta,true);
		
		init();
	}
	
	@Override
	public void init(){
		position = new float[2];
		position[0] = 1000;
		position[1] = 100;
		walk_delta = 0;
		facingRight = true;
	}
	
	@Override
	public void update(float delta){
	
		walk_delta += delta;
		
		if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
			position[0] += delta * 200;
			facingRight = true;
		}
		else if(Gdx.input.isKeyPressed(Input.Keys.LEFT)){
			position[0] -= delta * 200;
			facingRight = false;
		}
		
	}
	
	@Override
	public void draw(SpriteBatch batch){
		currentFrame = walk_animation.getKeyFrame(walk_delta,true);
		
		if(currentFrame.isFlipX() == facingRight)currentFrame.flip(true, false);
		
		batch.draw(currentFrame,position[0],position[1],16,0,32,32,4,4,0);
		
	}
	
	@Override
	public void dispose(){
		walk_animation = null;
		currentFrame = null;
		sheet.dispose();
	}
	
}
