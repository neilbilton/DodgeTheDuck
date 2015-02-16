package com.kilobolt.GameObjects;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.kilobolt.GameWorld.GameWorld;
import com.kilobolt.ZBHelpers.AssetLoader;
import com.kilobolt.ZBHelpers.Constants;
import com.kilobolt.ZBHelpers.InputHandler;
import com.kilobolt.ZombieBird.ZBGame.RequestHandler;

public class ScrollHandler {

	public List<Target> targets;
	public List<Enemy> enemies;
	public List<Coin> coins;
	public List<Bonus> bonuses;

	Random r = new Random();
	int xPos = 800;
	int newCoinPos = 0;

	private GameWorld gameWorld;
	private float groundY;

	public boolean gamePaused = false;
	
	public ScrollHandler(GameWorld gameWorld, float yPos, RequestHandler handler) {
		this.gameWorld = gameWorld;

		groundY = yPos;
		InitGameObjects();

	}

	
	private void InitGameObjects()
	{
		targets = new ArrayList<Target>();
		coins = new ArrayList<Coin>();
		enemies = new ArrayList<Enemy>();
		bonuses = new ArrayList<Bonus>();

		Target newTarget;
		Enemy newEnemy;
		Bonus newBonus;
		
		xPos = 700;

		targets.clear();
		coins.clear();
		
		for (int i = 0; i < 4 + (gameWorld.getCurrentSubLevel()/3); i++) {
			newTarget = new Target(xPos, 0, 48, 48, gameWorld.Speed()-r.nextInt(50), groundY, r.nextFloat(), r.nextBoolean());
			xPos += 70 + r.nextInt(100);
			targets.add(newTarget);
		}

		xPos = 700 + r.nextInt(600);
		for (int i = 0; i < 1+(gameWorld.getCurrentSubLevel()/3); i++) {
			newEnemy = new Enemy(xPos, 0, 48, 48, gameWorld.Speed()-gameWorld.Difficulty(), groundY);
			xPos += 100 + r.nextInt(500);
			enemies.add(newEnemy);
		}

		xPos = 700 + r.nextInt(600);
		for (int i = 0; i < 1+(gameWorld.getCurrentSubLevel()/3); i++) {
			newBonus = new Bonus(xPos, 0, 48, 48, gameWorld.Speed()-gameWorld.Difficulty(), groundY);
			xPos += 200 + r.nextInt(600);
			bonuses.add(newBonus);
		}

		xPos = 700;
		
		for (int i = 0; i < Constants.shape1.length; i++) {
			for (int j = 0; j < Constants.shape1[i].length; j++) {
				coins.add(new Coin(xPos, Constants.shape1[i][j]*5, 48, 48, gameWorld.Speed(),
						groundY));
			}
			xPos += 50;
		}

		xPos += 200;
		for (int i = 0; i < Constants.shape2.length; i++) {
			for (int j = 0; j < Constants.shape2[i].length; j++) {
				coins.add(new Coin(xPos, Constants.shape2[i][j]*5, 48, 48, gameWorld.Speed(),
						groundY));
			}
			xPos += 50;
		}

		xPos += 200;
		for (int i = 0; i < Constants.shape3.length; i++) {
			for (int j = 0; j < Constants.shape3[i].length; j++) {
				coins.add(new Coin(xPos, Constants.shape3[i][j]*5, 48, 48, gameWorld.Speed(),
						groundY));
			}
			xPos += 50;
		}

		xPos += 200;
		for (int i = 0; i < Constants.shape4.length; i++) {
			for (int j = 0; j < Constants.shape4[i].length; j++) {
				coins.add(new Coin(xPos, Constants.shape4[i][j]*5, 48, 48, gameWorld.Speed(),
						groundY));
			}
			xPos += 50;
		}
		
		newCoinPos = xPos - 400;
	}
	
	public void updateReady(float delta) {

	}

	public void update(float delta) {
		
		if (gamePaused)
			return;
		
		// update targets
		for (Target target : targets) {
			target.update(delta);

			if (target.isScrolledLeft())
				target.reset(700  + r.nextInt(70));
		}

		for (Enemy enemy : enemies) {
			enemy.update(delta);

			if (enemy.isScrolledLeft())
				enemy.reset(700 + r.nextInt(600));
		}

		for (Bonus bonus : bonuses) {
			bonus.update(delta);

			if (bonus.isScrolledLeft())
				bonus.reset(700 + r.nextInt(600));
		}

		for (Coin coin : coins) {
			coin.update(delta);

			if (coin.isScrolledLeft())
				coin.reset(newCoinPos);
		}

	}

	public void stop() {

		for (Target target : targets) {
			target.stop();
		}
		for (Coin coin : coins) {
			coin.stop();
		}
	}

	public void pause(boolean status) 
	{
		gamePaused = status;
	}

	public boolean isPaused() 
	{
		return gamePaused;
	}

	int timer = 0;
	
	public boolean collides(Bird bird) {

		if (gamePaused)
			return false;
		
		for (Target target : targets) {
			if (target.collides(bird) && !target.isScored()) {
				target.setScored(true);
				AssetLoader.fall.play();
				return true;
			}
		}

		for (Enemy enemy : enemies) {
			if (enemy.collides(bird) && !enemy.isScored()) {
				enemy.setScored(true);
				AssetLoader.fall.play();
				return true;
			}
		}

		for (Coin coin : coins) {
			if (coin.collides(bird) && !coin.isScored()) {
				addScore(1);
				AssetLoader.coin.play();				
				coin.setScored(true);
				CheckLevelCompleted();
			}
		}
		
		for (Bonus bonus : bonuses) {
			if (bonus.collides(bird) && !bonus.isScored()) 
			{
				addBonus(1);
				AssetLoader.cheering.play();
				bonus.setScored(true);
				CheckLevelCompleted();
			}
		}

		return false;
	}

	private void CheckLevelCompleted()
	{
		if (
			(gameWorld.getScore() >= gameWorld.Difficulty()) &&
			(gameWorld.getBonus() >= gameWorld.getRequiredBonus())
		   )
		{
			// level completed
			AssetLoader.cheering.play();
			gameWorld.GameOver(true);
			((InputHandler) Gdx.input.getInputProcessor()).ReloadLevelButtons();			
		}
		
	}
	
	private void addScore(int increment) {
		gameWorld.addScore(increment);
	}

	private void addBonus(int increment) {
		gameWorld.addBonus(increment);
	}

	public void onRestart() {

		InitGameObjects();
	}

}
