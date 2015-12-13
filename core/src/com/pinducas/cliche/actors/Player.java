package com.pinducas.cliche.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class Player extends Actor {

	//PRIMITIVES
	private float walk_delta;
	public float [] position;
	
	//DISPOSABLE
	private Texture sheet;
	
	//NULLABLE
	public TextureRegion currentFrame;

	private Animation walk_animation;
	
	public Player(World world, int x, int y){
		CriaCorpo(world, x, y);
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
	
	
	
	
	private void CriaCorpo(World world, int x, int y){
		bodyDef = new BodyDef();  
	    bodyDef.type = BodyType.DynamicBody;  
	    bodyDef.position.set(x,y);
	    
	    body = world.createBody(bodyDef);
	    
	    shape = new PolygonShape();
	    //aqui o valor eh da metade do raio do quadrado. Como o seu sprite tem 32 pixels...
	    shape.setAsBox(16*4, 16*4);
	    
	    fixtureDef = new FixtureDef();  
	    fixtureDef.shape = shape;  
	    fixtureDef.density = 0.1f;  
	    fixtureDef.friction = 0.0f;  
	    fixtureDef.restitution = 0.3f;
	    
	    body.createFixture(fixtureDef);
	    body.setFixedRotation(true);	    
	    
	}
	
	@Override
	public void init(){
		position = new float[2];
		position[0] = this.body.getPosition().x;
		position[1] = this.body.getPosition().y;
		walk_delta = 0;
		facingRight = true;
	}
	
	@Override
	public void update(float delta){		
		int deltaX = 0;
		
		if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
			deltaX = 5;
			facingRight = true;
			walk_delta += delta;
		}
		else if(Gdx.input.isKeyPressed(Input.Keys.LEFT)){
			deltaX = -5;
			facingRight = false;
			walk_delta += delta;

		}else{
			walk_delta =  0;
		}
		
		
		movimenta(deltaX, 0);
		
		
		position[0] = this.getX();
		position[1] = this.getY();
	}
	
	@Override
	public void draw(SpriteBatch batch){
		currentFrame = walk_animation.getKeyFrame(walk_delta,true);
		
		if(currentFrame.isFlipX() == facingRight)currentFrame.flip(true, false);
		
		batch.draw(currentFrame,position[0]+32,position[1]+32,32,32,32,32,4,4,0);
		
	}
	
	@Override
	public void dispose(){
		body = null;
		fixtureDef = null;
		bodyDef = null;
		shape.dispose();
		walk_animation = null;
		currentFrame = null;
		sheet.dispose();
	}
	
}
