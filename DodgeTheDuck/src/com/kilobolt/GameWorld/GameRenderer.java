package com.kilobolt.GameWorld;

import java.util.List;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenEquations;
import aurelienribon.tweenengine.TweenManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.kilobolt.GameObjects.Bird;
import com.kilobolt.GameObjects.Bonus;
import com.kilobolt.GameObjects.Coin;
import com.kilobolt.GameObjects.Enemy;
import com.kilobolt.GameObjects.ScrollHandler;
import com.kilobolt.GameObjects.Target;
import com.kilobolt.TweenAccessors.Value;
import com.kilobolt.TweenAccessors.ValueAccessor;
import com.kilobolt.ZBHelpers.AssetLoader;
import com.kilobolt.ZBHelpers.Constants;
import com.kilobolt.ZBHelpers.InputHandler;
import com.kilobolt.ui.SimpleButton;

public class GameRenderer {

	private GameWorld myWorld;
	private OrthographicCamera cam;
	private ShapeRenderer shapeRenderer;

	//private Random r;

	private SpriteBatch batcher;

	private int midPointY;

	// Game Objects
	private Bird bird;
	//private Poo poo;
	private ScrollHandler scroller;
	//private Grass frontGrass, backGrass;
	//private Pipe pipe1, pipe2, pipe3;
	//private List<Target> targets;

	public int xTop = 0;
	public int xBottom = 0;
	public int xDelay = 0;
	public boolean xFlip = true;
	
	// Game Assets
	private TextureRegion startpage, backbottom, backtop, birdUp2, gameoverbg;
	
	private Animation birdAnimation;

	// Tween stuff
	private TweenManager manager;
	private Value alpha = new Value();

	// Buttons
	private List<SimpleButton> menuButtons;
	private List<SimpleButton> levelButtons;
	private List<SimpleButton> pauseButtons;
	private List<SimpleButton> gameButtons;
	private List<SimpleButton> prevnextButtons;
	private List<SimpleButton> gameoverButtons;
	
	
	private Color transitionColor;

	public GameRenderer(GameWorld world, int gameHeight, int midPointY) {
		myWorld = world;

		this.midPointY = midPointY;
		
		this.menuButtons = ((InputHandler) Gdx.input.getInputProcessor())
				.getMenuButtons();
		this.pauseButtons = ((InputHandler) Gdx.input.getInputProcessor())
				.getPauseButtons();
		this.gameoverButtons = ((InputHandler) Gdx.input.getInputProcessor())
				.getGameOverButtons();
		this.levelButtons = ((InputHandler) Gdx.input.getInputProcessor())
				.getLevelButtons();
		this.gameButtons = ((InputHandler) Gdx.input.getInputProcessor())
				.getGameButtons();
		this.prevnextButtons = ((InputHandler) Gdx.input.getInputProcessor())
				.getPrevNextButtons();
		
		//r = new Random();
		cam = new OrthographicCamera();
		cam.setToOrtho(true, Constants.GAME_WIDTH, gameHeight);

		batcher = new SpriteBatch();
		batcher.setProjectionMatrix(cam.combined);
		shapeRenderer = new ShapeRenderer();
		shapeRenderer.setProjectionMatrix(cam.combined);

		initGameObjects();
		initAssets();

		transitionColor = new Color();
		prepareTransition(255, 255, 255, .5f);
	}

	private void initGameObjects() {
		bird = myWorld.getBird();
		//poo = myWorld.getPoo();
		scroller = myWorld.getScroller();
		//frontGrass = scroller.getFrontGrass();
		//backGrass = scroller.getBackGrass();
//		pipe1 = scroller.getPipe1();
//		pipe2 = scroller.getPipe2();
//		pipe3 = scroller.getPipe3();
	}

	public void initAssets() {
		//bg = AssetLoader.bg;
		backbottom = AssetLoader.backbottom;
		gameoverbg = AssetLoader.gameoverbg;
		backtop = AssetLoader.backtop;
		startpage = AssetLoader.startpage;
		birdAnimation = AssetLoader.birdAnimation;
		birdUp2 = AssetLoader.birdUp2;
		//zbLogo = AssetLoader.zbLogo;
		//gameOver = AssetLoader.gameOver;
		//highScore = AssetLoader.highScore;
		//scoreboard = AssetLoader.scoreboard;
		//retry = AssetLoader.retry;
		//star = AssetLoader.star;
		//noStar = AssetLoader.noStar;
	}

//	private void drawGrass() {
//		// Draw the grass
//		batcher.draw(grass, frontGrass.getX(), frontGrass.getY(),
//				frontGrass.getWidth(), frontGrass.getHeight());
//		batcher.draw(grass, backGrass.getX(), backGrass.getY(),
//				backGrass.getWidth(), backGrass.getHeight());
//	}

	private void drawCoins() {

		for (Coin coin : scroller.coins) {

			if (!coin.isScored())
				batcher.draw(AssetLoader.star,
							 coin.getX(),
							 coin.getY(), 
							 coin.getWidth(), 
							 coin.getHeight());
				
			//shapeRenderer.circle(coin.scoreCircle.x, coin.scoreCircle.y, coin.scoreCircle.radius);
		}
	}

	private void drawTargets() {

		for (Target target : scroller.targets) {

				batcher.draw(AssetLoader.targetTexture,
							 target.getX(),
							 target.getY(), 
							 target.getWidth(), 
							 target.getHeight());
			
			//shapeRenderer.circle(target.killCircle.x, target.killCircle.y, target.killCircle.radius);
		}
	}

	private void drawBonuses() {

		for (Bonus bonus : scroller.bonuses) {

			if (!bonus.isScored())
				batcher.draw(AssetLoader.bonus,
							 bonus.getX(),
							 bonus.getY(), 
							 bonus.getWidth(), 
							 bonus.getHeight());			
			
			//shapeRenderer.circle(bonus.scoreCircle.x, bonus.scoreCircle.y, bonus.scoreCircle.radius);
		}
	}
	
	private void drawEnemies() {

		for (Enemy enemy : scroller.enemies) {

			if (enemy.asset == 1)
			{
				batcher.draw(AssetLoader.enemy1,
							 enemy.getX(),
							 enemy.getY(), 
							 enemy.getWidth(), 
							 enemy.getHeight());
			}
			else if (enemy.asset == 2)
			{
				batcher.draw(AssetLoader.enemy2,
						 enemy.getX(),
						 enemy.getY(), 
						 enemy.getWidth(), 
						 enemy.getHeight());				
			}
			else if (enemy.asset == 3)
			{
				batcher.draw(AssetLoader.enemy3,
						 enemy.getX(),
						 enemy.getY(), 
						 enemy.getWidth(), 
						 enemy.getHeight());				
			}
			//shapeRenderer.circle(enemy.killCircle.x, enemy.killCircle.y, enemy.killCircle.radius);
		}
	}
	
	

	private void drawBird(float runTime) {

		//float birdScale = 1.5f;
		
		if ( (scroller.gamePaused) || (!myWorld.isRunning()) ) {
			batcher.draw(birdUp2, 
						 bird.getX(), 
						 bird.getY(),
						 85, 
						 64,
						 85, 
						 64,
						 1, 
						 1, 
						 bird.getRotation());

		} else 
		{
			batcher.draw(birdAnimation.getKeyFrame(runTime), 
						 bird.getX(),
						 bird.getY(), 
						 85, 
						 64,
						 85, 
						 64,
						 1, 
						 1, 
						 bird.getRotation());
		}

		//shapeRenderer.circle(bird.boundingCircle.x, bird.boundingCircle.y, bird.boundingCircle.radius);
	}

	private void drawMenuUI() {
		
		//batcher.draw(zbLogo, Constants.GAME_WIDTH / 2 - 250, midPointY - 100,
		//	zbLogo.getRegionWidth() * 3, zbLogo.getRegionHeight() * 3);

		batcher.draw(startpage, 0, 0, 640, 385);

		for (SimpleButton button : menuButtons) {
			button.draw(batcher);
		}

	}


	private void drawChooseLevelUI() {
		
		//batcher.draw(zbLogo, Constants.GAME_WIDTH / 2 - 250, midPointY - 100,
		//			 zbLogo.getRegionWidth() * 3, zbLogo.getRegionHeight() * 3);
		
		String levelTitle = Constants.levelData[myWorld.getCurrentLevel()][0];
		
		AssetLoader.shadow.draw(batcher, levelTitle, 150 - (3 * levelTitle.length()), 20);
		AssetLoader.font.draw(batcher, "" + levelTitle, 150 - (3 * levelTitle.length()), 20);
			
		for (SimpleButton button : levelButtons) {
			button.draw(batcher);
		}

		for (SimpleButton button : prevnextButtons) {
			button.draw(batcher);
		}

	}
	
	private void drawGameUI() {

		for (SimpleButton button : gameButtons) {
			button.draw(batcher);
		}

	}
	

	private void drawPauseButtons() {
	
		for (SimpleButton button : pauseButtons) {
			button.draw(batcher);
		}

	}
	
	private void drawReady() {

		String levelNumber = "Level " + (myWorld.getCurrentSubLevel()+1);
		String levelMessage = "Collect " + myWorld.Difficulty() + " Stars";
		String levelMessage2 = "And " + myWorld.getRequiredBonus() + " Bonus Coins!";
		
		AssetLoader.shadow.draw(batcher, "" + levelNumber,
								280 - (3 * levelNumber.length()),
								midPointY - 100);
		AssetLoader.font.draw(batcher, "" + levelNumber,
								280 - (3 * levelNumber.length()), 
								midPointY - 100);

		AssetLoader.shadow.draw(batcher, "" + levelMessage,
								200 - (3 * levelMessage.length()), 
								midPointY - 50);
		AssetLoader.font.draw(batcher, "" + levelMessage,
								200 - (3 * levelMessage.length()), 
								midPointY - 50);
		
		AssetLoader.shadow.draw(batcher, "" + levelMessage2,
								180 - (3 * levelMessage2.length()), 
								midPointY);
		AssetLoader.font.draw(batcher, "" + levelMessage2,
								180 - (3 * levelMessage2.length()), 
								midPointY);

	}

	private void drawGameOver() {
		
		String Message;

		String score = "You scored " + myWorld.getScore();
		
		if (myWorld.isLevelPassed())
			Message = "Level Passed!";
		else
			Message = "Level Failed!";			
		
		batcher.draw(gameoverbg, 120, 100, 400, 200);
		
		AssetLoader.shadow.draw(batcher, "" + score,
				200 - (3 * score.length()), midPointY - 60);
		AssetLoader.font.draw(batcher, "" + score,
				200 - (3 * score.length()), midPointY - 60);

		AssetLoader.shadow.draw(batcher, "" + Message,
				200 - (3 * Message.length()), midPointY);
		AssetLoader.font.draw(batcher, "" + Message,
				200 - (3 * Message.length()), midPointY);
		
		for (SimpleButton button : gameoverButtons) {
			button.draw(batcher);
		}
		
		
	}

	private void drawScore() {
		
		int length = ("" + myWorld.getScore()).length();
		
		AssetLoader.shadow.draw(batcher, "" + myWorld.getScore(),
				215 - (3 * length), midPointY - 170);
		AssetLoader.font.draw(batcher, "" + myWorld.getScore(),
				215 - (3 * length), midPointY - 170);

		AssetLoader.shadow.draw(batcher, "" + myWorld.getBonus(),
				425 - (3 * length), midPointY - 170);
		AssetLoader.redfont.draw(batcher, "" + myWorld.getBonus(),
				425 - (3 * length), midPointY - 170);

	}

//	private void drawHighScore() {
//		batcher.draw(highScore, 22, midPointY - 40, 96, 14);
//	}

	public void render(float delta, float runTime) {

		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

		shapeRenderer.begin(ShapeType.Filled);

		// Draw Background color
		shapeRenderer.setColor(55 / 255.0f, 80 / 255.0f, 100 / 255.0f, 1);

		batcher.begin();
		batcher.disableBlending();

		if (!myWorld.isMenu()) {
			batcher.draw(backbottom, xTop, 0, 2000, 385);
			batcher.draw(backbottom, xTop+1999, 0, 2000, 385);
			
			if (!scroller.gamePaused)
				xDelay++;
		}	
		
		if ( (myWorld.isRunning()) && (xDelay>0) )
		{
			xDelay = 0;
			xTop = xTop - 1;
			xBottom = xBottom -2;
		}

		if (xBottom < -2000)
		{
			xBottom = 0;
		}

		if (xTop < -2000)
		{
			xTop = 0;
			xFlip = !xFlip;
		}

		batcher.enableBlending();

		if (!myWorld.isMenu()) {
			batcher.draw(backtop, xBottom, 0, 2000, 385);
			batcher.draw(backtop, xBottom+1999, 0, 2000, 385);
		}

		if (myWorld.isRunning()) {
			drawScore();
			drawCoins();
			drawBonuses();
			drawTargets();
			drawEnemies();
			drawBird(runTime);
			drawGameUI(); 
		} else if (myWorld.isReady()) {
			drawScore();
			drawGameUI();
			drawBird(runTime);
			drawReady();
		} else if (myWorld.isMenu()) {
			drawMenuUI();
		} else if (myWorld.isChooseLevel()) {
			drawChooseLevelUI();
		} else if (myWorld.isGameOver()) {
			drawScore();
			drawCoins();
			drawBonuses();
			drawTargets();
			drawEnemies();
			drawBird(runTime);
			drawGameOver();
		}

		if (myWorld.isPaused()) {
			
			drawPauseButtons();
		}
		shapeRenderer.end();

		batcher.end();
		drawTransition(delta);

	}

	public void prepareTransition(int r, int g, int b, float duration) {
		transitionColor.set(r / 255.0f, g / 255.0f, b / 255.0f, 1);
		alpha.setValue(1);
		Tween.registerAccessor(Value.class, new ValueAccessor());
		manager = new TweenManager();
		Tween.to(alpha, -1, duration).target(0)
				.ease(TweenEquations.easeOutQuad).start(manager);
	}

	private void drawTransition(float delta) {
		if (alpha.getValue() > 0) {
			manager.update(delta);
			Gdx.gl.glEnable(GL10.GL_BLEND);
			Gdx.gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
			shapeRenderer.begin(ShapeType.Filled);
			shapeRenderer.setColor(transitionColor.r, transitionColor.g,
					transitionColor.b, alpha.getValue());
			shapeRenderer.rect(0, 0, Constants.GAME_WIDTH, 400);
			shapeRenderer.end();
			Gdx.gl.glDisable(GL10.GL_BLEND);

		}
	}

}
