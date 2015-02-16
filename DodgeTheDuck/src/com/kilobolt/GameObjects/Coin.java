package com.kilobolt.GameObjects;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.kilobolt.ZBHelpers.Constants;

public class Coin extends Scrollable {

	private Rectangle coin;

	public static final int VERTICAL_GAP = 45;

	private boolean isScored = false;
	public Circle scoreCircle;

	// When Pipe's constructor is invoked, invoke the super (Scrollable)
	// constructor
	public Coin(float x, float y, int width, int height, float scrollSpeed, float groundY) {
		super(x, y, width, height, scrollSpeed);
				
		// Initialize a Random object for Random number generation

		coin = new Rectangle();
		scoreCircle = new Circle();
	}

	@Override
	public void update(float delta) {
		// Call the update method in the superclass (Scrollable)
		super.update(delta);

		coin.set(position.x, position.y, width, height);
		scoreCircle.set(position.x + (width/2), position.y  + (height/2), Constants.SCORE_CIRCLE);		

	}

	@Override
	public void reset(float newX) {
		// Call the reset method in the superclass (Scrollable)
		super.reset(newX);
		isScored = false;
	}

	public void onRestart(float x, float scrollSpeed) {
		velocity.x = scrollSpeed;
		reset(x);
	}

	public Rectangle getCoin() {
		return coin;
	}

	public boolean collides(Bird bird) {
		if (position.x < bird.getX() + bird.getWidth()) {
			
			return (Intersector.overlaps(bird.getBoundingCircle(), coin));
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
