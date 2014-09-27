package com.siuming.hkg.view.activity;

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
	HkgTopicTitleBar titlebar;
	
    HkgViewPager viewPager;//viewpager  
    RefListViewPage topicPage;
    ListViewPage historyPage;
    
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.main);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.titlebar_topic);
		GoldenConfig.init(this);
		setTitleBar();
		setPageviewer();
		setTopicView();
	}

	private void setTitleBar() {
		titlebar = new HkgTopicTitleBar(this);
		//TODO add listener
		//titlebar.setTitleBarListener(null);
	}

	private void setPageviewer() {
		viewPager = new HkgViewPager(this);		
		topicPage = new RefListViewPage(this);
		historyPage = new ListViewPage(this);
		
		viewPager.addPage(topicPage,"主題");
		viewPager.addPage(historyPage,"主題2");
		
		topicPage.setMockData(10);
		historyPage.setMockData(10);
	}
	
	private void setTopicView() {
		//TODO add listener
		//topicPage.setOnItemClickListener(null);
	}
	
}
