package com.kilobolt.ZombieBird;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.kilobolt.Screens.SplashScreen;
import com.kilobolt.ZBHelpers.AssetLoader;
import com.kilobolt.ZBHelpers.Constants;

public class ZBGame extends Game implements ApplicationListener  {

	public interface RequestHandler {
		public void showAd();
	}
 
	public static RequestHandler requestHandler;

    public ZBGame(RequestHandler handler) {
    	requestHandler = handler;
    }


	@Override
	public void create() {
		AssetLoader.load(Constants.levelData[0][0]);
		setScreen(new SplashScreen(this, requestHandler));
		//setScreen(new GameScreen(requestHandler));
		
		
	}

	@Override
	public void dispose() {
		super.dispose();
		AssetLoader.dispose();
	}

}