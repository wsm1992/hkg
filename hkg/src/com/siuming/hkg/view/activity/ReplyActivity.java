package com.siuming.hkg.view.activity;

import com.siuming.hkg.GoldenConfig;
import com.siuming.hkg.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

public class ReplyActivity extends Activity{
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//set title bar stytle
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.main);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.titlebar_topic);
		

	}
}
