package com.siuming.hkg;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;

public class MainActivity extends Activity{
    private HkgViewPager viewPager;//viewpager  
    
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.main);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.titlebar_topic);
		GoldenConfig.init(this);
		setPageviewer();
	}
	
	private void setPageviewer() {
		viewPager = new HkgViewPager();
		viewPager.setViewPager((ViewPager) findViewById(R.id.viewpager));
		viewPager.setPagerTabStripper((PagerTabStrip) findViewById(R.id.pagertab));  
		
		RefListViewPage refViewPage = new RefListViewPage(this);
		ListViewPage viewPage = new ListViewPage(this);
		
		viewPager.addPage(refViewPage,"主題");
		viewPager.addPage(viewPage,"主題2");
		viewPager.init();
		
		refViewPage.setRefreshListView(10);
		viewPage.setRefreshListView(10);
	}
	
	
}
