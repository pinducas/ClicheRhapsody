package com.pinducas.cliche.map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.World;
import com.pinducas.cliche.actors.Player;
import com.pinducas.cliche.tools.ContactListenerTest;

public class Map {

	//NULLABLE
	protected Player jogador;
	protected OrthographicCamera camera;
	protected ContactListener listener;
	protected Tile [] tiles;
	protected Item [] itens;
	
	//DISPOSABLE
	private Texture tileSheet;	
	
	
	public Map(World world,Player jogador, OrthographicCamera camera){
		this.camera = camera;
		this.jogador = jogador;
		
		tileSheet = new Texture(Gdx.files.internal("Teste/Tiles.png"));
		
		tiles = new Tile[15];
		itens = new Item[0];
		
		for(int i = 0; i < 15; i++)
			tiles[i] = new Tile(world,new TextureRegion(tileSheet,32,0,32,32), 100+96*i, 100);
		
		listener = new ContactListenerTest(jogador);
		world.setContactListener(listener);
		
		init();
	}
	
	public void init(){
		
	}
	
	public void update(float delta){
		for(Tile t:tiles)t.update(camera);;
	}
	
	public void draw(SpriteBatch batch){
		for(Tile t:tiles)t.draw(batch);
		
	}
	
	public void dispose(){
		if(itens.length > 0)
			for(Item i:itens)i.dispose();
	}
	
	public void loadMap(){
		
	}
	
}

