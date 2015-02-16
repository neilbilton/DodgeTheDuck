package com.kilobolt.GameWorld;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.kilobolt.GameObjects.Bird;
import com.kilobolt.GameObjects.ScrollHandler;
import com.kilobolt.ZBHelpers.AssetLoader;
import com.kilobolt.ZBHelpers.Constants;
import com.kilobolt.ZombieBird.ZBGame.RequestHandler;

public class GameWorld {

	private Bird bird;
	private ScrollHandler scroller;
	private Rectangle ground;
	private int score = 0;
	private int bonus = 0;
	private float runTime = 0;
	private int midPointY;
	private GameRenderer renderer;
	
	private GameState currentState;
	private int currentLevel = 0;
	private int currentSubLevel = 0;

	public static RequestHandler requestHandler;

	public enum GameState {
		MENU, CHOOSE_LEVEL, READY, RUNNING, PAUSED, LEVEL_PASSED, LEVEL_FAILED
	}

	public GameWorld(int midPointY, RequestHandler handler) {
		
		requestHandler = handler;
		
		AssetLoader.music.setLooping(true);
		AssetLoader.music.play();
		
		currentState = GameState.MENU;
		this.midPointY = midPointY;
		bird = new Bird(100, midPointY - 5, 85, 64);
		//poo = new Poo(33, midPointY - 5, 17, 12);
		// The grass should start 66 pixels below the midPointY
		scroller = new ScrollHandler(this, midPointY + 66, handler);
		ground = new Rectangle(0, 600, 640, 11);
	}

	public void pause(boolean status)
	{
		System.out.println("pause");
		scroller.pause(status);
	}

	public boolean isPaused()
	{
		return scroller.isPaused();
	}

	public void update(float delta) {
		runTime += delta;

		switch (currentState) {
		case READY:
		case MENU:
			updateReady(delta);
			break;

		case RUNNING:
			updateRunning(delta);
			break;
		default:
			break;
		}

	}

	private void updateReady(float delta) {
		bird.updateReady(runTime);
		//poo.updateReady(runTime);
		scroller.updateReady(delta);
	}

	public void updateRunning(float delta) {
		if (delta > .15f) {
			delta = .15f;
		}

		bird.update(delta);
		//poo.update(delta);
		scroller.update(delta);

//		if (scroller.collides(bird) && bird.isAlive()) {
//			scroller.stop();
//			bird.die();
//			AssetLoader.dead.play();
//			renderer.prepareTransition(255, 255, 255, .3f);
//
//			AssetLoader.fall.play();
//		}

		if ( (Intersector.overlaps(bird.getBoundingCircle(), ground)) ||
		   (scroller.collides(bird) && bird.isAlive()) ) 
		{

			if (bird.isAlive()) {
				AssetLoader.dead.play();
				renderer.prepareTransition(255, 255, 255, .3f);

				bird.die();
			}

			//requestHandler.showAd();
			
			scroller.stop();
			bird.decelerate();
			currentState = GameState.LEVEL_FAILED;

//			if (score > AssetLoader.getHighScore()) 
//			{
//				AssetLoader.setHighScore(score);
//				//AssetLoader.cheering.play();
//				currentState = GameState.HIGHSCORE;
//			}
		}
	}

	public boolean nextLevel()
	{

		if (currentLevel < Constants.levelData.length-1)
		{
			currentLevel++;
			System.out.println("nextLevel:" + currentLevel);
			ReloadAssets();
			return true;
		}
		else
			return false;
	}

	public void ReloadAssets()
	{
		AssetLoader.load(Constants.levelData[currentLevel][0]);
		renderer.initAssets();
	}
	
	public boolean prevLevel()
	{
		if (currentLevel > 0)
		{
			currentLevel--;
			System.out.println("prevLevel:" + currentLevel);
			ReloadAssets();

			return true;
		}
		else
			return false;
	}

	public int getCurrentLevel()
	{
		return currentLevel;
	}
	
	public int getCurrentSubLevel()
	{
		return currentSubLevel;
	}

	public int getRequiredBonus()
	{
		return 1; // currentSubLevel+1;
	}

	public Bird getBird() {
		return bird;
	}

//	public Poo getPoo() {
//		return poo;
//	}

	public int getMidPointY() {
		return midPointY;
	}

	public ScrollHandler getScroller() {
		return scroller;
	}

	public int getScore() {
		return score;
	}

	public int getBonus() {
		return bonus;
	}

	public void addScore(int increment) {
		score += increment;
	}

	public void addBonus(int increment) {
		bonus += increment;
	}

	public void start() {
		currentState = GameState.RUNNING;
	}

	public void StartLevel(int newLevel) {
		// TODO Auto-generated method stub
		currentSubLevel = newLevel;
		StartLevel();
	}

	public void StartNextLevel() {
		// TODO Auto-generated method stub
		currentSubLevel++;
		
		if (currentSubLevel == 10)
		{
			currentSubLevel = 0;
			currentLevel++;
			ReloadAssets();
		}
		
		restartLevel();
	}

	public void StartLevel() {
		score = 0;
		bonus = 0;
    	currentState = GameState.READY;
		renderer.prepareTransition(0, 0, 0, 1f);
	}

	public int Difficulty() {
		return 10; //40+(currentSubLevel*5);
	}
	
	public int Speed() {
		return -(280+(currentSubLevel*5)+(currentLevel*10));
	}
	
	public void GameOver(boolean passed) {
		
		scroller.stop();
		bird.decelerate();
		currentState = GameState.LEVEL_PASSED;
		
		if (passed)
		{
			AssetLoader.setSubLevelCompleted(currentSubLevel);
		
			if (currentSubLevel == 9)
			{
				AssetLoader.setLevelCompleted(currentLevel++);
				AssetLoader.setSubLevelCompleted(0);
			}
		}
	}

	public void chooselevel() {
		score = 0;
		bonus = 0;
		currentState = GameState.CHOOSE_LEVEL;
		bird.onRestart(midPointY - 5);
		scroller.onRestart();
		renderer.prepareTransition(0, 0, 0, 1f);
	}
	
	public void restartLevel() {
		score = 0;
		bonus = 0;
		bird.onRestart(midPointY - 5);
		scroller.onRestart();
		StartLevel(currentSubLevel);
	}

	public void restartGame() {
		score = 0;
		bonus = 0;
		currentState = GameState.MENU;
		bird.onRestart(midPointY - 5);
		scroller.onRestart();
		renderer.prepareTransition(0, 0, 0, 1f);
	}
	
	public boolean isReady() {
		return currentState == GameState.READY;
	}

	public boolean isLevelPassed() {
		return currentState == GameState.LEVEL_PASSED;
	}

	public boolean isGameOver() {
		if (
			 (currentState == GameState.LEVEL_FAILED) ||
			 (currentState == GameState.LEVEL_PASSED)
		   )
			return true;
		else
			return false;
		
	}

	public boolean isMenu() {
		return currentState == GameState.MENU;
	}

	public boolean isChooseLevel() {
		return currentState == GameState.CHOOSE_LEVEL;
	}
	
	public boolean isRunning() {
		return currentState == GameState.RUNNING;
	}

	public void setRenderer(GameRenderer renderer) {
		this.renderer = renderer;
	}


}
