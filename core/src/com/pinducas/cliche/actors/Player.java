package com.pinducas.cliche.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.PovDirection;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.pinducas.cliche.tools.Const;	

public class Player extends Actor {
	public final int IDLE = 0, WALK = 1;
	
	private boolean pressing_jump;
	public boolean grounded;
	
	private int state;
	private int subState;
	
	private float walk_delta;
	
	public Vector2 position;
	public Vector2 speed;
	
	//DISPOSABLE
	private Texture sheet;
	
	//NULLABLE		
	private Animation walk_animation;
	private Controller gamepad;
	
	public Player(World world,Controller gamepad, float x, float y){
		
		body = Const.createDynamicBox(world,this, x, y,50,78,0,0,0,false);
		
		CircleShape cs = new CircleShape();
		cs.setRadius(34*Const.pixelToMeter);
		cs.setPosition(new Vector2(0,60*Const.pixelToMeter));
		
		FixtureDef fixtureDef = new FixtureDef();  
		fixtureDef.shape = cs;  
		fixtureDef.density = 1;  
		fixtureDef.friction = 0;  
		fixtureDef.restitution = 0;
				
		body.createFixture(fixtureDef);
		
		cs.dispose();
		
		
		
		this.gamepad = gamepad;
		
		this.id = Const.PLAYER;
		this.name = "Player";
		
		//IMAGE LOADING
		sheet = new Texture(Gdx.files.internal("Teste/walk.png"));
		
		//WALK ANIMATION
		TextureRegion [] walk_region = new TextureRegion[4];
		for(int i = 0; i < 4; i++){
			walk_region[i] = new TextureRegion(sheet,i*100,0,100,150);
		}
		walk_animation = new Animation(0.2f,walk_region);
		
		currentFrame = walk_animation.getKeyFrame(walk_delta,true);
		
		init();
	}
	
	@Override
	public void init(){
		position = new Vector2();
		position.x = this.body.getPosition().x;
		position.y = this.body.getPosition().y;
		
		speed = new Vector2();
		speed.x = 0;
		speed.y = 0;
		
		walk_delta = 0;
		state = IDLE;
		subState = 0;
		
		pressing_jump = false;
		
		facingRight = true;
		
		grounded = false;
	}
	
	@Override
	public void update(float delta){		
		if(grounded && body.getLinearVelocity().y != 0){
			grounded = false;
		}
		
		if(subState == 100)System.out.println(grounded);
				
		if(gamepad == null)keyboardControl();
		else gamepadControl();
		
		if(state == IDLE){
		
		}
		else if(state == WALK){
			walk_delta += delta;
		}
					
		body.setLinearVelocity(speed.x*Const.pixelToMeter*delta*10f, body.getLinearVelocity().y);
				
		position.x = this.getX();
		position.y = this.getY();
		
	}
	
	@Override
	public void draw(SpriteBatch batch){
		currentFrame = walk_animation.getKeyFrame(walk_delta,true);
		
		if(currentFrame.isFlipX() == facingRight)currentFrame.flip(true, false);
		
		batch.draw(currentFrame, getX() -50 * Const.pixelToMeter, getY()- 45 * Const.pixelToMeter
				,50*Const.pixelToMeter,0,100* Const.pixelToMeter,150* Const.pixelToMeter,1,1 ,0);
	}
	
	@Override
	public void dispose(){
		body = null;
		gamepad = null;
		walk_animation = null;
		currentFrame = null;
		sheet.dispose();
	}
	
	private void keyboardControl(){
		if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
			facingRight = true;
			state = WALK;
			subState = 0;
			speed.x = 1200;
		}
		else if(Gdx.input.isKeyPressed(Input.Keys.LEFT)){
			facingRight = false;
			state = WALK;
			subState = 0;
			speed.x = -1200;
		}
		else{
			walk_delta =  0;
			state = IDLE;
			speed.x = 0;
		}
		if(Gdx.input.isKeyJustPressed(Input.Keys.Z) && grounded){
			body.applyLinearImpulse(0, 6, 0, 0, true);
			translate(0, 0.1f);
			grounded = false;
		}
	}
	private void gamepadControl(){
		if(gamepad.getPov(0) == PovDirection.east){
			facingRight = true;
			state = WALK;
			subState = 0;
			speed.x = 1200f;
		}
		else if(gamepad.getPov(0) == PovDirection.west){
			facingRight = false;
			state = WALK;
			subState = 0;
			speed.x = -1200f;
		}
		else{
			walk_delta =  0;
			state = IDLE;
			speed.x = 0;
		}
		if(gamepad.getButton(2) && !pressing_jump && grounded){
			body.applyLinearImpulse(0, 6, 0, 0, true);
			translate(0, 2*Const.pixelToMeter);
			grounded = false;
			pressing_jump = true;
		}
		if(!gamepad.getButton(2))pressing_jump = false;
		
	}
	
}
