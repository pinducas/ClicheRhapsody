package com.pinducas.cliche.map;

import java.util.Scanner;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.PovDirection;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
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
	protected int ribbon;
	
	public boolean editMode;
	
	private boolean pressing;
	private boolean moving;
	
	protected int [][] tilemap;
	
	protected Vector2 cursor;
	
	//NULLABLE
	protected Player jogador;
	protected OrthographicCamera camera;
	protected ContactListener listener;
	protected Item [] itens;
	protected TextureRegion[] tileRegions;
	protected Platform [] platforms;
	protected Controller gamepad;
	
	//DISPOSABLE
	private Texture tileSheet;	
	
	public Map(World world,Player jogador, OrthographicCamera camera,Controller gamepad,String path){
		this.camera = camera;
		this.jogador = jogador;
		this.gamepad = gamepad;
		
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
		
		cursor = new Vector2(0,0);
		pressing = false;
		moving = false;
				
	}
	
	public void update(float delta){
		for(Item i:itens)i.update(camera);
		
		float camx = camera.position.x - camera.viewportWidth/2;
		float camy = camera.position.y - camera.viewportHeight/2;
		
		beginx = (int)(camx/(96*Const.pixelToMeter));
		beginy = (int)(camy/(96*Const.pixelToMeter));
		endx = (int)((camx+camera.viewportWidth)/(96*Const.pixelToMeter))+1;
		endy = (int)((camy+camera.viewportHeight)/(96*Const.pixelToMeter))+1;
		
		if(beginx < 0)beginx = 0;
		if(beginy < 0)beginy = 0;
		if(endx > tilemap[0].length-1)endx = tilemap[0].length-1;
		if(endy > tilemap.length-1)endy = tilemap.length-1;
		
		if(editMode){
			if(gamepad != null){

				if(gamepad.getPov(0) == PovDirection.east){
					if(!moving)cursor.x ++;
					moving = true;
				}
				else if(gamepad.getPov(0) == PovDirection.west){
					if(!moving)cursor.x --;
					moving = true;
				}
				else if(gamepad.getPov(0) == PovDirection.north){
					if(!moving)cursor.y ++;
					moving = true;
				}
				else if(gamepad.getPov(0) == PovDirection.south){
					if(!moving)cursor.y --;
					moving = true;
				}
				else{
					moving = false;
				}
				if(gamepad.getButton(2)){
					if(!pressing){
						tilemap[(int)cursor.y][(int)cursor.x]++;
					}
					pressing = true;
				}
				else if(gamepad.getButton(1)){
					if(!pressing){
						tilemap[(int)cursor.y][(int)cursor.x]+=4;
					}
					pressing = true;
				}
				else if(gamepad.getButton(3)){
					if(!pressing){
						tilemap[(int)cursor.y][(int)cursor.x]+=16;
					}
					pressing = true;
				}
				else if(gamepad.getButton(0)){
					if(!pressing){
						tilemap[(int)cursor.y][(int)cursor.x]+=64;
						
					}
					pressing = true;
				}
				else if(gamepad.getButton(4)){
					if(!pressing){
						ribbon = tilemap[(int)cursor.y][(int)cursor.x];
					}
					pressing = true;
				}
				else if(gamepad.getButton(5)){
					if(!pressing){
						tilemap[(int)cursor.y][(int)cursor.x] = ribbon;
					}
					pressing = true;
				}
				else{
					pressing = false;
				}
					
				if(tilemap[(int)cursor.y][(int)cursor.x] > tileRegions.length-1){
					tilemap[(int)cursor.y][(int)cursor.x] = 0;
				}
				
			}
			
			
			
			
		}
	}
	
	public void draw(SpriteBatch batch){
		for(Item i:itens)i.draw(batch);
		
		for(int line = beginy; line < endy; line ++){
			for(int col = beginx; col < endx; col++){
				if(tilemap[line][col] == -1)continue;
				batch.draw(tileRegions[tilemap[line][col]],col* 96*Const.pixelToMeter, line*96*Const.pixelToMeter,
						0,0,32,32,3*Const.pixelToMeter,3*Const.pixelToMeter,0);
			}
		}
		
		batch.setColor(new Color(1,0,0,0.8f));
		
		batch.draw(tileRegions[1],cursor.x*96*Const.pixelToMeter,cursor.y*96*Const.pixelToMeter,
				0,0,32,32,1*Const.pixelToMeter,1*Const.pixelToMeter,0);
		
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

