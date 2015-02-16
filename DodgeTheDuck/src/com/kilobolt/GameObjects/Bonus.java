package com.kilobolt.GameObjects;

import java.util.Random;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.kilobolt.ZBHelpers.Constants;

public class Bonus extends Scrollable {

	private Random r;

	private Rectangle bonus;
	private boolean isScored = false;
	public Circle scoreCircle;
	
	// When Pipe's constructor is invoked, invoke the super (Scrollable)
	// constructor
	public Bonus(float x, float y, int width, int height, float scrollSpeed, float groundY) {
		super(x, y, width, height, scrollSpeed);
				
		// Initialize a Random object for Random number generation
		r = new Random();
		bonus = new Rectangle();

		scoreCircle = new Circle();
		
		reset(x);
	}

	@Override
	public void update(float delta) {
		// Call the update method in the superclass (Scrollable)
		super.update(delta);

		// The set() method allows you to set the top left corner's x, y
		// coordinates,
		// along with the width and height of the rectangle

		bonus.set(position.x, position.y, width, height);		
		scoreCircle.set(position.x + (width/2), position.y  + (height/2), Constants.SCORE_CIRCLE);		

	}

	@Override
	public void reset(float newX) {
		// Call the reset method in the superclass (Scrollable)
		super.reset(newX);
		// Change the height to a random number
		position.y = r.nextInt(300) + 5;
		isScored = false;
	}

	public void onRestart(float x, float scrollSpeed) {
		velocity.x = scrollSpeed;
		reset(x);
	}

	public Rectangle getBonus() {
		return bonus;
	}

	public boolean collides(Bird bird) {

		//System.out.println("collides:" + position.x + ":" + bird.getX() + bird.getWidth() + ":" + bird.getX() + ":");
		
		if (position.x < bird.getX() + bird.getWidth()) {

			if (Intersector.overlaps(scoreCircle, bird.getBoundingCircle()))
			{
				//System.out.println("bird:" + bird.getX() + ":" + bird.getY() + ":" + bird.getWidth() + ":" + bird.getHeight() + ":");
				//System.out.println("circle:" + bird.getBoundingCircle().x + ":" + bird.getBoundingCircle().y + ":" + bird.getBoundingCircle().radius + ":");
				//System.out.println("target:" + bonus.getX() + ":" + bonus.getY() + ":" + bonus.width + ":" + bonus.height + ":");
			}
			
			return (Intersector.overlaps(scoreCircle, bird.getBoundingCircle()));
		}
		return false;
	}
	
	public boolean isScored() {
		return isScored;
	}

	public void setScored(boolean b) {
		isScored = b;
	}
}
