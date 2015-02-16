package com.kilobolt.ZombieBird;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.kilobolt.ZombieBird.ZBGame.RequestHandler;

public class Main implements RequestHandler {

	   private static Main application;
	   
	   public static void main (String[] argv) 
	   {
	        if (application == null) 
	        {
	            application = new Main();
	        }
	        
			LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
			cfg.title = "Dodge The Duck";
			cfg.useGL20 = false;
			cfg.width = 1920 / 3;
			cfg.height = 1080 / 3;
			
			new LwjglApplication(new ZBGame(application), cfg);

	   }
	   
		@Override
		public void showAd() {
			// TODO Auto-generated method stub
			
		}
}
