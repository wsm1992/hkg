package com.siuming.hkg.view.activity;

import com.siuming.hkg.AppPresenter;
import com.siuming.hkg.GoldenConfig;
import com.siuming.hkg.HkgTopicTitleBar;
import com.siuming.hkg.R;
import com.siuming.hkg.R.layout;
import com.siuming.hkg.view.component.HkgViewPager;
import com.siuming.hkg.view.page.ListViewPage;
import com.siuming.hkg.view.page.RefListViewPage;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;

public class MainActivity extends Activity{
	AppPresenter ap = new AppPresenter();    
    
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.main);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.titlebar_topic);
		
		GoldenConfig.init(this);
		ap.setMainActivity(this);
		ap.createTitleBar();
		ap.setPageViewer();
	}
	
}
