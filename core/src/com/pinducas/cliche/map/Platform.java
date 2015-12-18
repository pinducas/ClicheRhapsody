package com.pinducas.cliche.map;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.pinducas.cliche.tools.Const;
import com.pinducas.cliche.tools.GameObject;

public class Platform extends GameObject{
	
	private Body body;
	
	public Platform(World world,float x,float y,float width,float height,float friction){
		id = Const.PLATFORM;
		name = "Platform";
		body = Const.createStaticBox(world, this, x, y, width, height, friction, 0);
		
		
	}

}
