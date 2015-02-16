package com.kilobolt.GameObjects;

import java.util.Random;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.kilobolt.ZBHelpers.AssetLoader;
import com.kilobolt.ZBHelpers.Constants;

public class Enemy extends Scrollable {

	private Random r;

	private Rectangle enemy;

	public static final int VERTICAL_GAP = 45;
	//private float groundY;
	private boolean isScored = false;

	public Circle killCircle;
	private Circle explodeCircle;
	private Circle nearCircle;
	
	private int movecloser =0;
	private int isnear = 0;
	
	public int asset = 0;

	// When Pipe's constructor is invoked, invoke the super (Scrollable)
	// constructor
	public Enemy(float x, float y, int width, int height, float scrollSpeed, float groundY) {
		super(x, y, width, height, scrollSpeed);
				
		// Initialize a Random object for Random number generation
		r = new Random();
		enemy = new Rectangle();
		isnear = 0;
		//this.groundY = groundY;
		killCircle = new Circle();
		explodeCircle = new Circle();
		nearCircle = new Circle();
		
		reset(x);
	}

	@Override
	public void update(float delta) {
		// Call the update method in the superclass (Scrollable)
		super.update(delta);

		// The set() method allows you to set the top left corner's x, y
		// coordinates,
		// along with the width and height of the rectangle
		
		enemy.set(position.x, position.y, width, height);		
		killCircle.set(position.x + (width/2), position.y  + (height/2), Constants.COLLISION_CIRCLE);		

	}

	@Override
	public void reset(float newX) {
		// Call the reset method in the superclass (Scrollable)
		super.reset(newX);
		// Change the height to a random number
		position.y = r.nextInt(300) + 5;
		isnear = 0;
		movecloser = 0;
		isScored = false;
		asset = r.nextInt(3)+1;
	}

	public void onRestart(float x, float scrollSpeed) {
		velocity.x = scrollSpeed;
		isnear = 0;
		movecloser = 0;
		reset(x);
	}

	public Rectangle getEnemy() {
		return enemy;
	}

	public boolean collides(Bird bird) {

		//System.out.println("collides:" + position.x + ":" + bird.getX() + bird.getWidth() + ":" + bird.getX() + ":");
		
		if (position.x < bird.getX() + bird.getWidth()) {

			if (Intersector.overlaps(killCircle, bird.getBoundingCircle()))
			{
				//System.out.println("bird:" + bird.getX() + ":" + bird.getY() + ":" + bird.getWidth() + ":" + bird.getHeight() + ":");
				//System.out.println("circle:" + bird.getBoundingCircle().x + ":" + bird.getBoundingCircle().y + ":" + bird.getBoundingCircle().radius + ":");
				//System.out.println("target:" + enemy.getX() + ":" + enemy.getY() + ":" + enemy.width + ":" + enemy.height + ":");
			}
			
			return (Intersector.overlaps(killCircle, bird.getBoundingCircle()));
		}
		return false;
	}
	
	public int isnear(Bird bird) 
	{
		if (isnear < 2)
		{
			if (position.x < bird.getX() + bird.getWidth()) 
			{
				if (Intersector.overlaps(explodeCircle, bird.getBoundingCircle()))
				{
					isnear = 2;
					AssetLoader.dead.play();
				}
				else if (Intersector.overlaps(nearCircle, bird.getBoundingCircle()))
				{
					isnear = 1;
				}
			}
		}

		return isnear;
	}

	public boolean movecloser(Bird bird) 
	{
		if (isnear < 2)
		{
			movecloser++;
			if (movecloser == 10)
			{
				movecloser=0;
				if (position.x < bird.getX() + bird.getWidth()) 
				{
					return (Intersector.overlaps(nearCircle, bird.getBoundingCircle()));
				}
			}
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
