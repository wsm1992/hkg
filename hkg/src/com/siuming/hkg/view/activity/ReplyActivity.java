package com.siuming.hkg.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;

import com.siuming.hkg.AppPresenter;
import com.siuming.hkg.R;
import com.siuming.hkg.util.MapKey;

public class ReplyActivity extends Activity{
	AppPresenter ap = AppPresenter.getInstance();    
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//set title bar stytle
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.main);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.titlebar_topic);
		
		Intent intent =getIntent();
		String messageId=intent.getStringExtra(MapKey.MESSAGE_ID);
		ap.setReplyActivity(this,messageId);
	}
}
