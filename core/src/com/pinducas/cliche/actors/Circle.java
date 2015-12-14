package com.pinducas.cliche.actors;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.pinducas.cliche.core.Constants;

public class Circle extends Actor{

	public Circle(World world,TextureRegion region,float x,float y){
		this.currentFrame = region;
		
		criaCorpo(world, x, y);
	}
	
	@Override 
	public void update(float delta) {

	}
	
	@Override
	public void draw(SpriteBatch batch){
		if(currentFrame != null){
			batch.draw(currentFrame,getX()- 16*Constants.pixelToMeter, getY()-28*Constants.pixelToMeter
				,16*Constants.pixelToMeter,16*Constants.pixelToMeter,32*Constants.pixelToMeter,32*Constants.pixelToMeter,3,3,
				(float)Math.toDegrees(body.getAngle()));
		}
	}
	
	private void criaCorpo(World world, float x, float y){
		BodyDef bodyDef = new BodyDef();  
	    bodyDef.type = BodyType.DynamicBody;  
	    bodyDef.position.set(x * Constants.pixelToMeter, y * Constants.pixelToMeter);
	    
	    body = world.createBody(bodyDef);
	    
	    CircleShape shape = new CircleShape();
	    //aqui o valor eh da metade do raio do quadrado. Como o seu sprite tem 32 pixels mas escalado 4 vezes...
	    shape.setRadius(32*Constants.pixelToMeter);
	    
	    FixtureDef fixtureDef = new FixtureDef();  
	    fixtureDef.shape = shape;  
	    fixtureDef.density = 20f;  
	    fixtureDef.friction = 1f; 
	    fixtureDef.restitution = 0.4f;
	    
	    body.createFixture(fixtureDef);
	   
	    shape.dispose();
	}
	
	
}
