package com.pinducas.cliche.tools;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.pinducas.cliche.actors.Player;

public class ContactListenerTest implements ContactListener {

	private Player player;
	
	public ContactListenerTest(Player player) {
		this.player = player;
	}
	
	@Override
	public void beginContact(Contact contact) {
		GameObject a = (GameObject)contact.getFixtureA().getBody().getUserData();
		GameObject b = (GameObject)contact.getFixtureB().getBody().getUserData();
		
		Body bb = contact.getFixtureB().getBody();
		
		if(a.id == Const.PLAYER && b.id == Const.PLATFORM && 
				player.getY()-26*Const.pixelToMeter > bb.getPosition().y + 36*Const.pixelToMeter){
			player.grounded = true;			
		}
		
	}

	@Override
	public void endContact(Contact contact) {
		
	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {
		// TODO Auto-generated method stub
		
	}

}
