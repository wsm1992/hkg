package com.siuming.hkg.view.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

import com.siuming.hkg.AppPresenter;
import com.siuming.hkg.GoldenConfig;
import com.siuming.hkg.R;

public class MainActivity extends Activity{
	AppPresenter ap = new AppPresenter();    
    
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//set title bar stytle
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.main);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.titlebar_topic);
		
		GoldenConfig.init(this);
		ap.setMainActivity(this);
		ap.startApp();
	}
	
}
