package com.pinducas.cliche.map;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.pinducas.cliche.tools.Const;
import com.pinducas.cliche.tools.GameObject;

public class Item extends GameObject{

	private Body body;
	private TextureRegion region;
	private boolean active;
	
	public Item(World world,TextureRegion region,float x, float y){
		id = Const.TILE;
		name = "tile";
		this.region = region;
		body = Const.createDynamicBox(world, this, x, y, 96, 94, 5,0.2f, 0.4f,true);
		active = false;
		
	}
	
	public void update(OrthographicCamera camera){
		double dist = Math.pow(body.getPosition().x - camera.position.x,2) + Math.pow(body.getPosition().y- camera.position.y,2);
		dist = Math.sqrt(dist);
		
		System.out.println("DIstance: "+dist+" Camera: "+(camera.viewportWidth*0.55f+48*Const.pixelToMeter));
		
		if(dist < camera.viewportWidth*0.6f+48*Const.pixelToMeter){
			active = true;
		}
		else active = false;
		
		
	}
	
	public void draw(SpriteBatch batch){
		if(active){
			batch.draw(region, body.getPosition().x - 16* Const.pixelToMeter, body.getPosition().y -  16* Const.pixelToMeter
					,16*Const.pixelToMeter,16*Const.pixelToMeter,32 * Const.pixelToMeter, 32 * Const.pixelToMeter,3,3,(float)Math.toDegrees(body.getAngle()));
		}
		
	}
	
}
