package com.pinducas.cliche.map;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.pinducas.cliche.tools.Const;
import com.pinducas.cliche.tools.GameObject;

public class Tile extends GameObject{

	private TextureRegion region;
	private Body body;
	public boolean active;
	
	public Tile(World world,TextureRegion region,float x, float y){
		id = Const.TILE;
		name = "tile";
		this.region = region;
		body = Const.createStaticBox(world, this, x, y, 72, 72, 10, 0);
		active = false;
	}
	
	public void update(OrthographicCamera camera){
		double dist = Math.pow(body.getPosition().x - camera.position.x,2) + Math.pow(body.getPosition().y- camera.position.y,2);
		dist = Math.sqrt(dist);
		
		if(dist < camera.viewportWidth*0.55f+36*Const.pixelToMeter){
			active = true;
		}
		else active = false;
		
		
	}
	
	public void draw(SpriteBatch batch){
		if(active){
			batch.draw(region, body.getPosition().x - 36* Const.pixelToMeter, body.getPosition().y - 36* Const.pixelToMeter
					,0 ,0,32 * Const.pixelToMeter, 32 * Const.pixelToMeter,2,2,0);
		}
		
	}
	
}
