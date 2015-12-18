package com.pinducas.cliche.tools;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class Const {
	
	public static final int PLAYER = 0, TILE = 1,PLATFORM = 2;
	
	public static final float pixelToMeter = 20f/1280f;
	public static final float meterToPixel = 1280f/20f;

	public static final float BOX_TIME_STEP = 1/90.f;
	public static final int BOX_VELOCITY_ITERATIONS = 7;
	public static final int BOX_POSITION_ITERATIONS = 3;

	
	public static Body createDynamicBox(World world,Object data, float x, float y,float width,float height,float density, float friction, float restitution,boolean rotate){
		
		BodyDef bodyDef = new BodyDef();  
	    bodyDef.type = BodyType.DynamicBody;  
	    bodyDef.position.set(x * Const.pixelToMeter, y * Const.pixelToMeter);
	    
	    Body body = world.createBody(bodyDef);
	    
	    PolygonShape shape = new PolygonShape();
	    shape.setAsBox(width/2 * Const.pixelToMeter, height/2 * Const.pixelToMeter);
	    
	    FixtureDef fixtureDef = new FixtureDef();  
	    fixtureDef.shape = shape;  
	    fixtureDef.density = density;  
	    fixtureDef.friction = friction;  
	    fixtureDef.restitution = restitution;
	    
	    body.createFixture(fixtureDef);
	    body.setFixedRotation(!rotate);	
	    body.setUserData(data);
	   
	    shape.dispose();
	    
	    return body;
	}
	
	public static Body createStaticBox(World world,Object data, float x, float y,float width,float height, float friction, float restitution){
		
		BodyDef bodyDef = new BodyDef();  
	    bodyDef.type = BodyType.StaticBody;  
	    bodyDef.position.set(x * Const.pixelToMeter, y * Const.pixelToMeter);
	    
	    Body body = world.createBody(bodyDef);
	    
	    PolygonShape shape = new PolygonShape();
	    shape.setAsBox(width/2 * Const.pixelToMeter, height/2 * Const.pixelToMeter);
	    
	    FixtureDef fixtureDef = new FixtureDef();  
	    fixtureDef.shape = shape;  
	    fixtureDef.friction = friction;  
	    fixtureDef.restitution = restitution;
	    
	    body.createFixture(fixtureDef);
	    body.setUserData(data);
	   
	    shape.dispose();
	    
	    return body;
	}
	
}
