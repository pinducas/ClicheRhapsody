package com.pinducas.cliche.map;

import java.util.Scanner;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.World;
import com.pinducas.cliche.actors.Player;
import com.pinducas.cliche.tools.Const;
import com.pinducas.cliche.tools.ContactListenerTest;

public class Map {

	protected int beginx;
	protected int beginy;
	protected int endx;
	protected int endy;
	
	
	protected int [][] tilemap;
	
	
	//NULLABLE
	protected Player jogador;
	protected OrthographicCamera camera;
	protected ContactListener listener;
	protected Item [] itens;
	protected TextureRegion[] tileRegions;
	protected Platform [] platforms;
	
	//DISPOSABLE
	private Texture tileSheet;	
	
	public Map(World world,Player jogador, OrthographicCamera camera,String path){
		this.camera = camera;
		this.jogador = jogador;
		
		tileSheet = new Texture(Gdx.files.internal("Teste/Tiles.png"));
		
		loadMap(path,world);
		
		tileRegions = new TextureRegion[16*16];
		
		int current = 0;
		for(int line = 0; line < 16; line ++){
			for(int col = 0; col < 16; col++){
				tileRegions[current] = new TextureRegion(tileSheet,col*32,line*32,32,32);
				current ++;
			}
		}
				
		itens = new Item[0];
		
		
		listener = new ContactListenerTest(jogador);
		world.setContactListener(listener);
		
	}
	
	public void update(float delta){
		for(Item i:itens)i.update(camera);
		
		float camx = camera.position.x - camera.viewportWidth/2;
		float camy = camera.position.y - camera.viewportHeight/2;
		
		beginx = (int)(camx/(72*Const.pixelToMeter));
		beginy = (int)(camy/(72*Const.pixelToMeter));
		endx = (int)((camx+camera.viewportWidth)/(72*Const.pixelToMeter))+1;
		endy = (int)((camy+camera.viewportHeight)/(72*Const.pixelToMeter))+1;
		
		if(beginx < 0)beginx = 0;
		if(beginy < 0)beginy = 0;
		if(endx > tilemap[0].length-1)endx = tilemap[0].length-1;
		if(endy > tilemap.length-1)endy = tilemap.length-1;
					
			
			
			
		
	}
	
	public void draw(SpriteBatch batch){
		for(Item i:itens)i.draw(batch);
		
		for(int line = beginy; line < endy; line ++){
			for(int col = beginx; col < endx; col++){
				if(tilemap[line][col] == -1)continue;
				batch.draw(tileRegions[tilemap[line][col]],col* 72*Const.pixelToMeter, line*72*Const.pixelToMeter,
						0,0,72*Const.pixelToMeter,72*Const.pixelToMeter,1,1,0);
			}
		}
		batch.setColor(Color.WHITE);
	
	}
	
	public void dispose(){
		
	}
	
	public void loadMap(String path,World world){
		FileHandle file = Gdx.files.local(path);
		String sfile = file.readString();
		Scanner in = new Scanner(sfile);
		
		String []temp = in.nextLine().split("#");
		
		int width = Integer.parseInt(temp[1]);
		int height = Integer.parseInt(temp[0]);
		
		tilemap = new int[height][width];
		
		for(int line = 0; line < height; line++){
			temp = in.nextLine().split(" ");
			for(int col = 0; col < width; col++){
				tilemap[line][col] = Integer.parseInt(temp[col]);
			}
		}
		
		int numPlatforms = Integer.parseInt(in.nextLine());
		platforms = new Platform[numPlatforms];
		
		for(int i = 0; i < numPlatforms; i++){
			temp = in.nextLine().split(" ");
			platforms[i] = new Platform(world, Integer.parseInt(temp[0]), Integer.parseInt(temp[1]),
					Integer.parseInt(temp[2]), Integer.parseInt(temp[3]), Integer.parseInt(temp[4]));
		}
		
		
		in.close();
	}
	
}

