package com.kilobolt.ZBHelpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class AssetLoader {

	public static Texture texture, logoTexture, duckTexture, targetTexture, 
						  hunterTexture, coinTexture, starTexture;
	
	public static TextureRegion logo, zbLogo, bg, grass, bird, birdDown, birdUp2, birdUp, 
							    playButtonUp, playButtonDown, 
								upButtonUp, upButtonDown, prevButton, nextButton, pauseButton,
								downButtonUp, downButtonDown, resumeButtonUp, resumeButtonDown, 
								changelevelButtonUp, changelevelButtonDown,
								restartgameButtonUp, restartGameButtonDown, startpage,
								ready, gameOver, highScore, scoreboard, star, noStar, retry,
								backbottom, backtop, gameoverbg, 
								nextlevelButtonUp, nextlevelButtonDown,
								replaylevelButtonUp, replaylevelButtonDown,
								enemy1, enemy2, enemy3,
								bonus;
	
	public static Animation birdAnimation;
	public static Sound dead, flap, coin, fall, cheering;
	public static Music music;
	public static BitmapFont font, redfont, shadow, whiteFont;
	private static Preferences prefs;

	public static void load(String level) {

		logoTexture = new Texture(Gdx.files.internal("data/graphics/logo.png"));
		logoTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);

		logo = new TextureRegion(logoTexture, 0, 0, 512, 114);

		texture = new Texture(Gdx.files.internal("data/graphics/texture.png"));
		texture.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);

		Texture buttonPlayUpTexture = new Texture(Gdx.files.internal("data/graphics/playUp.png"));
		Texture buttonPlayDownTexture = new Texture(Gdx.files.internal("data/graphics/playDown.png"));
		
		playButtonUp = new TextureRegion(buttonPlayUpTexture, 0, 0, 117, 110);
		playButtonUp.flip(false, true);

		playButtonDown = new TextureRegion(buttonPlayDownTexture, 0, 0, 117, 110);
		playButtonDown.flip(false, true);

		prevButton = new TextureRegion(buttonPlayUpTexture, 0, 0, 117, 110);
		prevButton.flip(true, true);
		prevButton = new TextureRegion(buttonPlayUpTexture, 0, 0, 117, 110);
		prevButton.flip(true, true);

		nextButton = new TextureRegion(buttonPlayUpTexture, 0, 0, 117, 110);
		nextButton.flip(false, true);
		nextButton = new TextureRegion(buttonPlayUpTexture, 0, 0, 117, 110);
		nextButton.flip(false, true);

		Texture buttonDownTexture = new Texture(Gdx.files.internal("data/graphics/buttonDownRed195x220.png"));
		downButtonUp = new TextureRegion(buttonDownTexture, 0, 0, 256, 256);
		downButtonUp.flip(false, true);
		downButtonDown = new TextureRegion(buttonDownTexture, 0, 0, 256, 256);
		downButtonDown.flip(false, true);
		
		Texture buttonUpTexture = new Texture(Gdx.files.internal("data/graphics/buttonUpRed195x220.png"));
		upButtonUp = new TextureRegion(buttonUpTexture, 0, 0, 256, 256);
		upButtonUp.flip(false, true);
		upButtonDown = new TextureRegion(buttonUpTexture, 0, 0, 256, 256);
		upButtonDown.flip(false, true);

		Texture buttonResumeTexture = new Texture(Gdx.files.internal("data/graphics/buttonResume.png"));
		resumeButtonUp = new TextureRegion(buttonResumeTexture, 0, 0, 162, 28);
		resumeButtonUp.flip(false, true);
		resumeButtonDown = new TextureRegion(buttonResumeTexture, 162, 0, 162, 28);
		resumeButtonDown.flip(false, true);

		Texture buttonChangeLevelTexture = new Texture(Gdx.files.internal("data/graphics/buttonChangeLevel.png"));
		changelevelButtonUp = new TextureRegion(buttonChangeLevelTexture, 0, 0, 162, 28);
		changelevelButtonUp.flip(false, true);
		changelevelButtonDown = new TextureRegion(buttonChangeLevelTexture, 162, 0, 162, 28);
		changelevelButtonDown.flip(false, true);

		Texture buttonNextLevelTexture = new Texture(Gdx.files.internal("data/graphics/buttonNextLevel.png"));
		nextlevelButtonUp = new TextureRegion(buttonNextLevelTexture, 0, 0, 162, 28);
		nextlevelButtonUp.flip(false, true);
		nextlevelButtonDown = new TextureRegion(buttonNextLevelTexture, 162, 0, 162, 28);
		nextlevelButtonDown.flip(false, true);

		Texture buttonReplayLevelTexture = new Texture(Gdx.files.internal("data/graphics/buttonReplayLevel.png"));
		replaylevelButtonUp = new TextureRegion(buttonReplayLevelTexture, 0, 0, 162, 28);
		replaylevelButtonUp.flip(false, true);
		replaylevelButtonDown = new TextureRegion(buttonReplayLevelTexture, 162, 0, 162, 28);
		replaylevelButtonDown.flip(false, true);

		Texture buttonRestartGameTexture = new Texture(Gdx.files.internal("data/graphics/buttonRestartGame.png"));
		restartgameButtonUp = new TextureRegion(buttonRestartGameTexture, 0, 0, 162, 28);
		restartgameButtonUp.flip(false, true);
		restartGameButtonDown = new TextureRegion(buttonRestartGameTexture, 162, 0, 162, 28);
		restartGameButtonDown.flip(false, true);

		Texture buttonPauseTexture = new Texture(Gdx.files.internal("data/graphics/buttonPause.png"));
		pauseButton = new TextureRegion(buttonPauseTexture, 0, 0, 25, 25);
		pauseButton.flip(false, true);

		ready = new TextureRegion(texture, 60, 82, 45, 10);
		ready.flip(false, true);

		retry = new TextureRegion(texture, 60, 92, 45, 10);
		retry.flip(false, true);
		
		gameOver = new TextureRegion(texture, 59, 103, 48, 8);
		gameOver.flip(false, true);

		scoreboard = new TextureRegion(texture, 111, 83, 97, 37);
		scoreboard.flip(false, true);

		star = new TextureRegion(texture, 152, 70, 10, 10);
		noStar = new TextureRegion(texture, 165, 70, 10, 10);

		star.flip(false, true);
		noStar.flip(false, true);

		highScore = new TextureRegion(texture, 58, 113, 49, 8);
		highScore.flip(false, true);

		Texture startpageTexture = new Texture(Gdx.files.internal("data/graphics/startpage.png"));
		startpage = new TextureRegion(startpageTexture, 0, 0, 600, 400);
		startpage.flip(false, true);

		String levelPath = "data/graphics/" + level + "/";
		
		Texture BackBottomTexture = new Texture(Gdx.files.internal(levelPath + "backbottom.png"));
		backbottom = new TextureRegion(BackBottomTexture, 0, 0, 2000, 385);
		backbottom.flip(false, true);

		Texture BackTopTexture = new Texture(Gdx.files.internal(levelPath + "backtop.png"));
		backtop = new TextureRegion(BackTopTexture, 0, 0, 2000, 385);
		backtop.flip(false, true);

		Texture BonusTexture = new Texture(Gdx.files.internal(levelPath + "bonus.png"));
		bonus = new TextureRegion(BonusTexture, 0, 0, 32, 32);
		bonus.flip(false, true);

		Texture Enemy1Texture = new Texture(Gdx.files.internal(levelPath + "enemy1.png"));
		enemy1 = new TextureRegion(Enemy1Texture, 0, 0, 32, 32);
		enemy1.flip(false, true);

		Texture Enemy2Texture = new Texture(Gdx.files.internal(levelPath + "enemy2.png"));
		enemy2 = new TextureRegion(Enemy2Texture, 0, 0, 32, 32);
		enemy2.flip(false, true);

		Texture Enemy3Texture = new Texture(Gdx.files.internal(levelPath + "enemy3.png"));
		enemy3 = new TextureRegion(Enemy3Texture, 0, 0, 32, 32);
		enemy3.flip(false, true);

		Texture GameOverBGTexture = new Texture(Gdx.files.internal("data/graphics/gameover400x200.png"));
		gameoverbg = new TextureRegion(GameOverBGTexture, 0, 0, 400, 200);
		gameoverbg.flip(false, true);
		
		zbLogo = new TextureRegion(texture, 213, 44, 158, 25);
		zbLogo.flip(false, true);

		duckTexture = new Texture(Gdx.files.internal(levelPath + "dodge.png"));

		birdDown = new TextureRegion(duckTexture, 0, 0, 170, 128);
		birdDown.flip(false, true);

		bird = new TextureRegion(duckTexture, 170, 0, 170, 128);
		bird.flip(false, true);

		birdUp = new TextureRegion(duckTexture, 340, 0, 170, 128);
		birdUp.flip(false, true);

		birdUp2 = new TextureRegion(duckTexture, 510, 0, 170, 128);
		birdUp2.flip(false, true);

		TextureRegion[] birds = { birdDown, bird, birdUp, birdUp2 };
		birdAnimation = new Animation(0.10f, birds);
		birdAnimation.setPlayMode(Animation.LOOP_PINGPONG);

		targetTexture = new Texture(Gdx.files.internal(levelPath + "target.png"));
		starTexture = new Texture(Gdx.files.internal(levelPath + "star.png"));
		star = new TextureRegion(starTexture, 0, 0, 24, 24);
		star.flip(false, true);
		
		
		//hunterTexture = new Texture(Gdx.files.internal("data/graphics/hunter.png"));
		//hunter = new TextureRegion(hunterTexture, 0, 0, 32, 64);
		//hunter.flip(false, true);

		
		dead = Gdx.audio.newSound(Gdx.files.internal("data/sounds/dead.wav"));
		flap = Gdx.audio.newSound(Gdx.files.internal("data/sounds/flap.wav"));
		coin = Gdx.audio.newSound(Gdx.files.internal("data/sounds/coin.wav"));
		fall = Gdx.audio.newSound(Gdx.files.internal("data/sounds/fall.wav"));
		music = Gdx.audio.newMusic(Gdx.files.internal("data/sounds/test_cbr.mp3"));
		cheering = Gdx.audio.newSound(Gdx.files.internal("data/sounds/cheering.mp3"));

		font = new BitmapFont(Gdx.files.internal("data/fonts/text.fnt"));
		font.setScale(0.6f, -0.6f);

		redfont = new BitmapFont(Gdx.files.internal("data/fonts/textred.fnt"));
		redfont.setScale(0.6f, -0.6f);

		whiteFont = new BitmapFont(Gdx.files.internal("data/fonts/whitetext.fnt"));
		whiteFont.setScale(0.6f, -0.6f);

		shadow = new BitmapFont(Gdx.files.internal("data/fonts/shadow.fnt"));
		shadow.setScale(0.6f, -0.6f);

		// Create (or retrieve existing) preferences file
		prefs = Gdx.app.getPreferences("DodgeTheDuck");

		if (!prefs.contains("highScore")) {
			prefs.putInteger("highScore", 0);
		}
		
		if (!prefs.contains("levelCompleted")) {
			prefs.putInteger("levelCompleted", 0);
		}
		
		if (!prefs.contains("sublevelCompleted")) {
			prefs.putInteger("sublevelCompleted", 0);
		}
		
	}

	public static void setHighScore(int val) {
		prefs.putInteger("highScore", val);
		prefs.flush();
	}

	public static int getHighScore() {
		return prefs.getInteger("highScore");
	}

	public static void setLevelCompleted(int val) {
		int newLevel = prefs.getInteger("levelCompleted") + val;
		prefs.putInteger("levelCompleted", newLevel);
		prefs.flush();
	}

	public static void setSubLevelCompleted(int val) {
		int newLevel = prefs.getInteger("sublevelCompleted") + val;
		prefs.putInteger("sublevelCompleted", newLevel);
		prefs.flush();
	}

	public static int getLevelCompleted() {
		return prefs.getInteger("levelCompleted");
	}

	public static int getSubLevelCompleted() {
		return prefs.getInteger("sublevelCompleted");
	}

	public static void dispose() {
		// We must dispose of the texture when we are finished.
		texture.dispose();

		// Dispose sounds
		dead.dispose();
		flap.dispose();
		coin.dispose();

		font.dispose();
		shadow.dispose();
	}

}