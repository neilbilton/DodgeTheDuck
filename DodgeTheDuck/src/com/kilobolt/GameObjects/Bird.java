package com.kilobolt.GameObjects;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.kilobolt.ZBHelpers.AssetLoader;
import com.kilobolt.ZBHelpers.Constants;

public class Bird {

	public Vector2 position;
	private Vector2 velocity;
	private Vector2 acceleration;

	private float rotation;
	private int width;
	private float height;

	private float originalY;

	private boolean isAlive;

	public Circle boundingCircle;

	public Bird(float x, float y, int width, int height) {
		this.width = width;
		this.height = height;
		this.originalY = y;
		position = new Vector2(x, y);
		velocity = new Vector2(0, 0);
		acceleration = new Vector2(0, 2);
		boundingCircle = new Circle();
		isAlive = true;
	}

	public void update(float delta) {

		delta = .015f;
		//velocity.add(acceleration.cpy().scl(delta));

		//if (velocity.y > 200) {
		//	velocity.y = 200;
		//}

		// CEILING CHECK
		if (position.y < -13) {
			position.y = -13;
			velocity.y = 0;
		}

		if (position.y > 300) {
			position.y = 300;
			velocity.y = 0;
		}

		position.add(velocity.cpy().scl(delta));

		// Set the circle's center to be (9, 6) with respect to the bird.
		// Set the circle's radius to be 6.5f;
		boundingCircle.set(position.x + (width/2), position.y  + (height/2), Constants.BIRD_CIRCLE);

	}

	public void updateReady(float runTime) {
		position.y = 2 * (float) Math.sin(7 * runTime) + originalY;
	}

	public boolean isFalling() {
		return velocity.y > 110;
	}

	public boolean shouldntFlap() {
		return velocity.y > 2 || !isAlive;
	}

	public void onClick() {
		if (isAlive) {
			AssetLoader.flap.play();
			velocity.y = -5;
		}
	}

	public void onTouchUp(int direction)
	{
		if (isAlive) {			
			System.out.println("onTouchUp:Direction" + direction + "Velocity:" + velocity.y);
			if (velocity.y == direction)
				velocity.y = +0;
		}
	}
	
	public void onUp() {
		if (isAlive) {			
			//AssetLoader.flap.play();
			velocity.y = -250;
		}
	}

	public void onDown() {
		if (isAlive) {
			//AssetLoader.flap.play();
			velocity.y = +250;
		}
	}

	public void die() {
		isAlive = false;
		velocity.y = 0;
	}

	public void decelerate() {
		acceleration.y = 0;
	}

	public void onRestart(int y) {
		rotation = 0;
		position.y = y;
		velocity.x = 0;
		velocity.y = 0;
		acceleration.x = 0;
		acceleration.y = 2;
		isAlive = true;
	}

	public float getX() {
		return position.x;
	}

	public float getY() {
		return position.y;
	}

	public float getWidth() {
		return width;
	}

	public float getHeight() {
		return height;
	}

	public float getRotation() {
		return rotation;
	}

	public Circle getBoundingCircle() {
		return boundingCircle;
	}

	public boolean isAlive() {
		return isAlive;
	}

}
