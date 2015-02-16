package com.kilobolt.GameObjects;

import java.util.Random;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.kilobolt.ZBHelpers.Constants;

public class Target extends Scrollable {

	private Random r;

	private Rectangle target, hunter;

	public static final int VERTICAL_GAP = 45;
	private float groundY;
	private boolean isScored = false;

	public Circle killCircle;
	private Circle explodeCircle;
	private Circle nearCircle;
	
	private int movecloser =0;
	private int isnear = 0;
	private float yDelta = 0;
	private boolean yDirection = false;

	// When Pipe's constructor is invoked, invoke the super (Scrollable)
	// constructor
	public Target(float x, float y, 
				  int width, int height, 
				  float scrollSpeed, float groundY,
				  float ydelta, boolean ydirection) {
		super(x, y, width, height, scrollSpeed);
				
		// Initialize a Random object for Random number generation
		r = new Random();
		target = new Rectangle();
		hunter = new Rectangle();
		isnear = 0;
		this.groundY = groundY;
		killCircle = new Circle();
		explodeCircle = new Circle();
		nearCircle = new Circle();
		
		yDelta = ydelta/4;
		yDirection = ydirection;
		
		reset(x);
	}

	@Override
	public void update(float delta) {
		// Call the update method in the superclass (Scrollable)
		super.update(delta);

		// The set() method allows you to set the top left corner's x, y
		// coordinates,
		// along with the width and height of the rectangle

		if (yDirection)
			position.y = position.y - yDelta;
		else
			position.y = position.y + yDelta;
			
		target.set(position.x, position.y, width, height);
		hunter.set(position.x, position.y + height + VERTICAL_GAP, width,
				groundY - (position.y + height + VERTICAL_GAP));
		
		killCircle.set(position.x + (width/2), position.y  + (height/2), Constants.COLLISION_CIRCLE);		
		explodeCircle.set(position.x + (position.x/2), position.y  + (position.y/2), 0);		
		nearCircle.set(position.x + (position.x/2), position.y  + (position.y/2), 0);		
		

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
	}

	public void onRestart(float x, float scrollSpeed) {
		velocity.x = scrollSpeed;
		isnear = 0;
		movecloser = 0;
		reset(x);
	}

	public Rectangle getTarget() {
		return target;
	}

	public Rectangle getHunter() {
		return hunter;
	}

	public boolean collides(Bird bird) {

		//System.out.println("collides:" + position.x + ":" + bird.getX() + bird.getWidth() + ":" + bird.getX() + ":");
		
		if (position.x < bird.getX() + bird.getWidth()) {

			if (Intersector.overlaps(killCircle, bird.getBoundingCircle()))
			{
				//System.out.println("bird:" + bird.getX() + ":" + bird.getY() + ":" + bird.getWidth() + ":" + bird.getHeight() + ":");
				//System.out.println("circle:" + bird.getBoundingCircle().x + ":" + bird.getBoundingCircle().y + ":" + bird.getBoundingCircle().radius + ":");
				//System.out.println("target:" + target.getX() + ":" + target.getY() + ":" + target.width + ":" + target.height + ":");
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
//				if (Intersector.overlaps(explodeCircle, bird.getBoundingCircle()))
//				{
//					isnear = 2;
//					AssetLoader.dead.play();
//				}
				if (Intersector.overlaps(nearCircle, bird.getBoundingCircle()))
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
			if (movecloser == 20)
			{
				movecloser=0;
				if (position.x < bird.getX()+100 + bird.getWidth()) 
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
