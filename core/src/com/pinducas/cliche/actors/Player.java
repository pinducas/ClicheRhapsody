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
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.pinducas.cliche.tools.Constants;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

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
	public TextureRegion currentFrame;
	private Animation walk_animation;
	private Controller gamepad;
	
	public Player(World world,Controller gamepad, float x, float y){
		criaCorpo(world, x, y);
		
		this.gamepad = gamepad;
		
		//IMAGE LOADING
		sheet = new Texture(Gdx.files.internal("Teste/Sheet.png"));
		
		//WALK ANIMATION
		TextureRegion [] walk_region = new TextureRegion[4];
		walk_region[0] = new TextureRegion(sheet,0,0,32,32);
		walk_region[1] = new TextureRegion(sheet,32,0,32,32);
		walk_region[2] = new TextureRegion(sheet,64,0,32,32);
		walk_region[3] = new TextureRegion(sheet,96,0,32,32);
		
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
		
		//System.out.println(grounded);
		
		if(gamepad == null)keyboardControl();
		else gamepadControl();
		
		//STATE HANDLING
		if(state == IDLE){
			if(subState == 0){
				//HERE HE COULD BE IN IDLE
			}
			else if(subState == 1){
				//HERE HE COULD SLEEP BECAUSE THE PLAYER WAS STILL FOR TOO LONG
			}
		}
		else if(state == WALK){
			walk_delta += delta;
		}
			
		//TESTING
		body.setLinearVelocity(speed.x*Constants.pixelToMeter, body.getLinearVelocity().y);
		//movimenta(speed.x*delta*Constants.pixelToMeter, speed.y*delta*Constants.pixelToMeter);
		
		position.x = this.getX();
		position.y = this.getY();
		
	}
	
	@Override
	public void draw(SpriteBatch batch){
		currentFrame = walk_animation.getKeyFrame(walk_delta,true);
		
		if(currentFrame.isFlipX() == facingRight)currentFrame.flip(true, false);
		
		batch.draw(currentFrame, getX() - 16 * Constants.pixelToMeter, getY()- 36 * Constants.pixelToMeter
				,16*Constants.pixelToMeter,0,32* Constants.pixelToMeter,32* Constants.pixelToMeter,3,3 ,0);
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
			speed.x = 300;
		}
		else if(Gdx.input.isKeyPressed(Input.Keys.LEFT)){
			facingRight = false;
			state = WALK;
			subState = 0;
			speed.x = -300;
		}
		else{
			walk_delta =  0;
			state = IDLE;
			speed.x = 0;
		}
		if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE) && grounded){
			body.applyLinearImpulse(0, 18, 0, 0, true);
			grounded = false;
		}
	}
	private void gamepadControl(){
		if(gamepad.getPov(0) == PovDirection.east){
			facingRight = true;
			state = WALK;
			subState = 0;
			speed.x = 300f;
		}
		else if(gamepad.getPov(0) == PovDirection.west){
			facingRight = false;
			state = WALK;
			subState = 0;
			speed.x = -300f;
		}
		else{
			walk_delta =  0;
			state = IDLE;
			speed.x = 0;
		}
		if(gamepad.getButton(2) && !pressing_jump){
			body.applyLinearImpulse(0, 30, 0, 0, true);
			pressing_jump = true;
		}
		if(!gamepad.getButton(2))pressing_jump = false;
		
	}
	
	private void criaCorpo(World world, float x, float y){
		BodyDef bodyDef = new BodyDef();  
	    bodyDef.type = BodyType.DynamicBody;  
	    bodyDef.position.set(x * Constants.pixelToMeter, y * Constants.pixelToMeter);
	    
	    body = world.createBody(bodyDef);
	    
	    PolygonShape shape = new PolygonShape();
	    //aqui o valor eh da metade do raio do quadrado. Como o seu sprite tem 32 pixels mas escalado 4 vezes...
	    shape.setAsBox(10 * 3 * Constants.pixelToMeter, 12 * 3 * Constants.pixelToMeter);
	    
	    FixtureDef fixtureDef = new FixtureDef();  
	    fixtureDef.shape = shape;  
	    fixtureDef.density = 1f;  
	    fixtureDef.friction = 0f;  
	    fixtureDef.restitution = 0f;
	    
	    body.createFixture(fixtureDef);
	    body.setFixedRotation(true);	
	    body.setUserData("player");
	   
	    shape.dispose();
	}
	
}
