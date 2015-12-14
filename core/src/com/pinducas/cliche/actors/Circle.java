package com.pinducas.cliche.actors;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.pinducas.cliche.core.Constants;

public class Circle extends Actor{

	public Circle(World world, float x,float y){
		
		
		criaCorpo(world, x, y);
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
	    fixtureDef.density = 0.1f;  
	    fixtureDef.friction = 0.0f;  
	    fixtureDef.restitution = 0f;
	    
	    body.createFixture(fixtureDef);
	    body.setFixedRotation(true);	    
	   
	    shape.dispose();
	}
	
	
}
