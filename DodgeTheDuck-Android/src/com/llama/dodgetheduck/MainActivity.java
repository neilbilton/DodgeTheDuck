package com.llama.dodgetheduck;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.kilobolt.ZombieBird.ZBGame;
import com.kilobolt.ZombieBird.ZBGame.RequestHandler;
import com.purplebrain.adbuddiz.sdk.AdBuddiz;

public class MainActivity extends AndroidApplication implements RequestHandler {

	Context mContext;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        mContext = this;
        
        AdBuddiz.setPublisherKey("74779e43-07da-45a8-886c-2f1fc9703d63");
        AdBuddiz.cacheAds(this); // this = current Activity

        AndroidApplicationConfiguration cfg = new AndroidApplicationConfiguration();
        cfg.useGL20 = false;
        cfg.useGL20 = false;
        cfg.useAccelerometer = false;
        cfg.useCompass = false;

        RelativeLayout layout = new RelativeLayout(this);
        
    	requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);

        //initialize(new ZBGame(), cfg);

        ZBGame game = new ZBGame(this);
		View gameView = initializeForView(game, cfg);
		
		layout.addView(gameView);
		//layout.addView(adView, adParams);
		
		setContentView(layout);


    }

	   private final int SHOW_ADS = 1;
	    private final int HIDE_ADS = 0;

	    protected Handler handler = new Handler()
	    {
	        @Override
	        public void handleMessage(Message msg) {
	            switch(msg.what) {
	                case SHOW_ADS:
	                {
	                	AdBuddiz.showAd((Activity) mContext); // this = current Activity
	                    //adView.setVisibility(View.VISIBLE);
	                    break;
	                }
	                case HIDE_ADS:
	                {
	                	AdBuddiz.showAd((Activity) mContext); // this = current Activity
	                    //adView.setVisibility(View.GONE);
	                    break;
	                }
	            }
	        }
	    };

	@Override
	public void showAd() {
		// TODO Auto-generated method stub
		handler.sendEmptyMessage(SHOW_ADS);
	}
}