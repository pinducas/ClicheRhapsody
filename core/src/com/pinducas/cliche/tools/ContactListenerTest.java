package com.pinducas.cliche.tools;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.pinducas.cliche.actors.Player;

public class ContactListenerTest implements ContactListener {

	private Player player;
	
	public ContactListenerTest(Player player) {
		this.player = player;
	}
	
	@Override
	public void beginContact(Contact contact) {
		Fixture f1 = contact.getFixtureA();
		Fixture f2 = contact.getFixtureB();

		String s1 = (String)f1.getBody().getUserData();
		String s2 = (String)f2.getBody().getUserData();

	
		if(s1.equals("ground") && s2.equals("player")||
				s1.equals("player") && s2.equals("ground")){
			player.grounded = true;
			System.out.println("BOB");
		}
		
	}

	@Override
	public void endContact(Contact contact) {
		Fixture f1 = contact.getFixtureA();
		Fixture f2 = contact.getFixtureB();

		String s1 = (String)f1.getBody().getUserData();
		String s2 = (String)f2.getBody().getUserData();

		if(s1.equals("ground") && s2.equals("player")||
				s1.equals("player") && s2.equals("ground")){
			player.grounded = false;
			System.out.println("BOB HASKIN");
		}
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
