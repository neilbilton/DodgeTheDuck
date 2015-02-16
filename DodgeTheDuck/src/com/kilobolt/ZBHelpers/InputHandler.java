package com.kilobolt.ZBHelpers;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.kilobolt.GameObjects.Bird;
import com.kilobolt.GameWorld.GameWorld;
import com.kilobolt.ui.SimpleButton;

public class InputHandler implements InputProcessor {
	private Bird myBird;
	private GameWorld myWorld;
	private int midPointY;

	private List<SimpleButton> menuButtons, 
							   gameButtons, 
							   levelButtons, 
							   prevnextButtons,
							   pauseButtons, 
							   gameoverButtons;

	private SimpleButton playButton, upButton, downButton, 
						 prevButton, nextButton, pauseButton,
						 resumeButton, changelevelButton, restartgameButton,
						 replaylevelButton, nextlevelButton;

	private float scaleFactorX;
	private float scaleFactorY;

	public static int levelButtonPositions[][] = { { 80, 100 }, 
												   { 180, 100 },
												   { 280, 100 }, 
												   { 380, 100 }, 
												   { 480, 100 }, 
												   { 80, 200 }, 
												   { 180, 200 }, 
												   { 280, 200 }, 
												   { 380, 200 },
												   { 480, 200 } 
												};
	
	public InputHandler(GameWorld myWorld, float scaleFactorX, float scaleFactorY) {
		
		this.myWorld = myWorld;
		myBird = myWorld.getBird();
		//poo = myWorld.getPoo();

		midPointY = myWorld.getMidPointY();

		this.scaleFactorX = scaleFactorX;
		this.scaleFactorY = scaleFactorY;

		menuButtons = new ArrayList<SimpleButton>();
		gameButtons = new ArrayList<SimpleButton>();
		pauseButtons = new ArrayList<SimpleButton>();
		prevnextButtons = new ArrayList<SimpleButton>();
		levelButtons = new ArrayList<SimpleButton>();
		gameoverButtons = new ArrayList<SimpleButton>();

		ReloadMenuButtons();
		ReloadLevelButtons();
		ReloadPauseButtons();
		ReloadGameButtons();
		ReloadGameOverButtons();

	}

	public void ReloadMenuButtons()
	{
		menuButtons.clear();
		
		playButton = new SimpleButton(
				20,
				midPointY-35, 117, 110, AssetLoader.playButtonUp,
				AssetLoader.playButtonDown);
		
		menuButtons.add(playButton);	
	}
	
	public void ReloadGameButtons()
	{
		gameButtons.clear();
		
		int buttonSize = 85;
		
		upButton = new SimpleButton(
				0,
				midPointY*2-buttonSize, 
				buttonSize, 
				buttonSize, 
				AssetLoader.upButtonUp,
				AssetLoader.upButtonDown);
		
		downButton = new SimpleButton(
				Constants.GAME_WIDTH - buttonSize,
				midPointY*2-buttonSize, 
				buttonSize, 
				buttonSize, 
				AssetLoader.downButtonUp,
				AssetLoader.downButtonDown);

		pauseButton = new SimpleButton(
				0,
				0, 35, 35, AssetLoader.pauseButton,
				AssetLoader.pauseButton);
		
		gameButtons.add(pauseButton);
		gameButtons.add(upButton);
		gameButtons.add(downButton);

	}
	
	public void ReloadGameOverButtons()
	{
		gameoverButtons.clear();
		
		replaylevelButton = new SimpleButton(
				320-(AssetLoader.replaylevelButtonUp.getRegionWidth())-10,
				240, 
				AssetLoader.replaylevelButtonUp.getRegionWidth(), 
				AssetLoader.replaylevelButtonUp.getRegionHeight()*1.5f, 
				AssetLoader.replaylevelButtonUp,
				AssetLoader.replaylevelButtonDown);
		gameoverButtons.add(replaylevelButton);

		nextlevelButton = new SimpleButton(
				320+10,
				240, 
				AssetLoader.nextlevelButtonUp.getRegionWidth(), 
				AssetLoader.nextlevelButtonUp.getRegionHeight()*1.5f, 
				AssetLoader.nextlevelButtonUp,
				AssetLoader.nextlevelButtonDown);
		gameoverButtons.add(nextlevelButton);
		
	}

	public void ReloadPauseButtons()
	{
		pauseButtons.clear();
		
		resumeButton = new SimpleButton(
				320-(AssetLoader.resumeButtonUp.getRegionWidth()),
				midPointY-100, 
				AssetLoader.resumeButtonUp.getRegionWidth()*2, 
				AssetLoader.resumeButtonUp.getRegionHeight()*2, 
				AssetLoader.resumeButtonUp,
				AssetLoader.resumeButtonDown);
		pauseButtons.add(resumeButton);

		changelevelButton = new SimpleButton(
				320-(AssetLoader.changelevelButtonUp.getRegionWidth()),
				midPointY-20, 
				AssetLoader.changelevelButtonUp.getRegionWidth()*2, 
				AssetLoader.changelevelButtonUp.getRegionHeight()*2, 
				AssetLoader.changelevelButtonUp,
				AssetLoader.changelevelButtonDown);
		pauseButtons.add(changelevelButton);

		restartgameButton = new SimpleButton(
				320-(AssetLoader.restartgameButtonUp.getRegionWidth()),
				midPointY+60, 
				AssetLoader.restartgameButtonUp.getRegionWidth()*2, 
				AssetLoader.restartgameButtonUp.getRegionHeight()*2, 
				AssetLoader.restartgameButtonUp,
				AssetLoader.restartGameButtonDown);
		pauseButtons.add(restartgameButton);
		
	}
	
	public void ReloadLevelButtons()
	{
		levelButtons.clear();
		prevnextButtons.clear();
		
		if (myWorld.getCurrentLevel() > 0)
		{
			prevButton = new SimpleButton(
					20,
					midPointY-20, 
					40, 40, 
					AssetLoader.prevButton,
					AssetLoader.prevButton);		
		}
		else
		{
			prevButton = new SimpleButton(
					20,
					midPointY-20, 
					40, 40, 
					AssetLoader.playButtonDown,
					AssetLoader.playButtonDown);			
		}
		
		prevnextButtons.add(prevButton);
		
		if (myWorld.getCurrentLevel() < Constants.levelData.length-1)
		{
			nextButton = new SimpleButton(
					580,
					midPointY-20, 
					40, 40, 
					AssetLoader.nextButton,
					AssetLoader.nextButton);			
		}
		else
		{
			nextButton = new SimpleButton(
					580,
					midPointY-20, 
					40, 40, 
					AssetLoader.playButtonDown,
					AssetLoader.playButtonDown);			
		}
		prevnextButtons.add(nextButton);
		
		for (int i = 0;i<10;i++)
		{
			SimpleButton levelButton = null;
			
			if (myWorld.getCurrentLevel() > AssetLoader.getLevelCompleted())
			{
				//System.out.println("ReloadLevelButtons:enabled");
				levelButton = new SimpleButton(
						levelButtonPositions[i][0],
						levelButtonPositions[i][1],
						80, 62, 
						AssetLoader.playButtonDown,
						AssetLoader.playButtonDown,
						false);									
			}
			else
			{
				if (i <= AssetLoader.getSubLevelCompleted())
				{
					levelButton = new SimpleButton(
							levelButtonPositions[i][0],
							levelButtonPositions[i][1],
							80, 62, 
							AssetLoader.playButtonUp,
							AssetLoader.playButtonUp,
							true);
				}
				else
				{
					levelButton = new SimpleButton(
							levelButtonPositions[i][0],
							levelButtonPositions[i][1],
							80, 62, 
							AssetLoader.playButtonDown,
							AssetLoader.playButtonDown,
							false);					
				}
			}
			
			levelButtons.add(levelButton);	
		}
	}
	
	
	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {

		//System.out.println("touchDown");

		screenX = scaleX(screenX);
		screenY = scaleY(screenY);

		if (myWorld.isPaused())
		{

			resumeButton.isTouchDown(screenX, screenY);			
			restartgameButton.isTouchDown(screenX, screenY);					
			changelevelButton.isTouchDown(screenX, screenY);
			
			return true;
		}

		if (myWorld.isMenu()) {
			
			playButton.isTouchDown(screenX, screenY);
			
		} else if (myWorld.isChooseLevel()) {
			
			for (int i=0;i<10;i++)
			{
				levelButtons.get(i).isTouchDown(screenX, screenY);
			}
			
			prevButton.isTouchDown(screenX, screenY);
			nextButton.isTouchDown(screenX, screenY);
			
		} else if (myWorld.isReady()) {
			
			myWorld.start();
			myBird.onClick();
			
		} else if (myWorld.isRunning()) {
		
			System.out.println("touchDownEvent");

			if (upButton.isTouchDown(screenX, screenY))
			{
				System.out.println("touchDown:Up");
				myBird.onUp();
			}

			if (downButton.isTouchDown(screenX, screenY))			
			{
				System.out.println("touchDown:Down");
				myBird.onDown();
			}
						
		} else if (myWorld.isPaused()) {
		
			resumeButton.isTouchDown(screenX, screenY);
			restartgameButton.isTouchDown(screenX, screenY);
			changelevelButton.isTouchDown(screenX, screenY);
			
		} else if (myWorld.isGameOver()) {
			
			replaylevelButton.isTouchDown(screenX, screenY); 
			nextlevelButton.isTouchDown(screenX, screenY);

		}

		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		
		//System.out.println("touchUp");

		screenX = scaleX(screenX);
		screenY = scaleY(screenY);

		if (myWorld.isPaused()) {

			System.out.println("touchUp:isPaused");

			if (resumeButton.isTouchUp(screenX, screenY))
				myWorld.pause(false);
			
			if (restartgameButton.isTouchUp(screenX, screenY))
			{
				myWorld.pause(false);
				myWorld.restartGame();
			}
				
			if (changelevelButton.isTouchUp(screenX, screenY))
			{
				myWorld.pause(false);
				myWorld.chooselevel();
			}
			
			return true;
		}

		if (myWorld.isMenu()) {
			if (playButton.isTouchUp(screenX, screenY)) 
			{
				myWorld.chooselevel();
				return true;
			}
		}
		else if (myWorld.isChooseLevel()) {
			
			for (int i=0;i<10;i++)
			{
				if (levelButtons.get(i).enabled)
				{
					if (levelButtons.get(i).isTouchUp(screenX, screenY))
					{
						myWorld.StartLevel(i);
						return true;
					}
				}
			}
			
			if (prevButton.isTouchUp(screenX, screenY))
			{
				myWorld.prevLevel();
				ReloadLevelButtons();
				return true;
			}
			
			if (nextButton.isTouchUp(screenX, screenY))
			{
				myWorld.nextLevel();				
				ReloadLevelButtons();
				return true;
			}


			return true;
			
		} else if (myWorld.isRunning()) {
			
			System.out.println("touchUpEvent");
			
			if (upButton.isTouchUpNear(screenX, screenY))
			{
				System.out.println("touchUp:UpButton");
				myBird.onTouchUp(-250);
				return true;
			}

			if (downButton.isTouchUpNear(screenX, screenY))			
			{
				System.out.println("touchUp:DownButton");
				myBird.onTouchUp(250);
				return true;
			}
			
			if (pauseButton.isTouchDown(screenX, screenY))
				myWorld.pause(true);

		} else if (myWorld.isGameOver()) {
		
			if (replaylevelButton.isTouchUp(screenX, screenY))
			{
				myWorld.pause(false);
				myWorld.restartLevel();
			}

			if (nextlevelButton.isTouchUp(screenX, screenY))
			{
				//if (myWorld.isLevelPassed())
				if (myWorld.getCurrentSubLevel() <= AssetLoader.getSubLevelCompleted())
					myWorld.StartNextLevel();
			}

		}
		
		return false;
	}

	@Override
	public boolean keyDown(int keycode) {

		if (myWorld.isPaused())
			return true;

		// Can now use Space Bar to play the game
		if (keycode == Keys.SPACE) {

			if (myWorld.isMenu()) {
				myWorld.StartLevel();
			} else if (myWorld.isReady()) {
				myWorld.start();
			}

			//myBird.onClick();
			//poo.onClick(myBird.getX(), myBird.getY());

			if (myWorld.isGameOver()) {
				myWorld.restartLevel();
			}

		}

		if (keycode == Keys.DOWN) {
			myBird.onDown();			
		}
		
		if (keycode == Keys.UP) {
			myBird.onUp();
		}
		
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}

	private int scaleX(int screenX) {
		return (int) (screenX / scaleFactorX);
	}

	private int scaleY(int screenY) {
		return (int) (screenY / scaleFactorY);
	}

	public List<SimpleButton> getMenuButtons() {
		return menuButtons;
	}
	
	public List<SimpleButton> getLevelButtons() {
		return levelButtons;
	}

	public List<SimpleButton> getPauseButtons() {
		return pauseButtons;
	}

	public List<SimpleButton> getGameOverButtons() {
		return gameoverButtons;
	}

	public List<SimpleButton> getPrevNextButtons() {
		return prevnextButtons;
	}

	public List<SimpleButton> getGameButtons() {
		return gameButtons;
	}
}
