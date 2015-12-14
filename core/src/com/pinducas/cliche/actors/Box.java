package com.pinducas.cliche.actors;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.pinducas.cliche.core.Constants;

public class Box extends Actor{

	public Box(World world,float x,float y,float width,float height){
		criaCorpo(world, x, y, width/2f,height/2f);
	}
	
	private void criaCorpo(World world, float x, float y,float width,float height){
		BodyDef bodyDef = new BodyDef();  
	    bodyDef.type = BodyType.StaticBody;  
	    bodyDef.position.set(x * Constants.pixelToMeter, y * Constants.pixelToMeter);
	    
	    body = world.createBody(bodyDef);
	    
	    PolygonShape shape = new PolygonShape();
	    //aqui o valor eh da metade do raio do quadrado. Como o seu sprite tem 32 pixels mas escalado 4 vezes...
	    shape.setAsBox(width * Constants.pixelToMeter, height * Constants.pixelToMeter);
	    
	    FixtureDef fixtureDef = new FixtureDef();  
	    fixtureDef.shape = shape;  
	    fixtureDef.density = 0.1f;  
	    fixtureDef.friction = 1f;  
	    fixtureDef.restitution = 0f;
	    
	    body.createFixture(fixtureDef);
	    body.setFixedRotation(true);	    
	   
	    body.setUserData("ground");
	    
	    shape.dispose();
	}
	
	
}
