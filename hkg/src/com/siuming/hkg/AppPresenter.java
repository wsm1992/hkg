package com.siuming.hkg;

import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.siuming.hkg.HkgTopicTitleBar.TitleBarListener;
import com.siuming.hkg.view.activity.MainActivity;
import com.siuming.hkg.view.component.HkgViewPager;
import com.siuming.hkg.view.page.ListViewPage;
import com.siuming.hkg.view.page.RefListViewPage;

public class AppPresenter {
	MainActivity mainActivity;
	
	HkgTopicTitleBar topicTitleBar;
	
	HkgViewPager viewPager;
    RefListViewPage topicPage;
    ListViewPage historyPage;

	public void setMainActivity(MainActivity activity) {
		mainActivity = activity;
	}
	
	public void createTitleBar(){
		topicTitleBar = new HkgTopicTitleBar(mainActivity);
		topicTitleBar.setTitleBarListener(getTitleBarListener());
	}

	public void setPageViewer() {
		viewPager = new HkgViewPager(mainActivity);		
		topicPage = new RefListViewPage(mainActivity);
		historyPage = new ListViewPage(mainActivity);
		
		viewPager.addPage(topicPage,"主題");
		viewPager.addPage(historyPage,"主題2");
		
		topicPage.setMockData(10);
		historyPage.setMockData(10);
		
		topicPage.setOnItemClickListener(getOnTopicViewItemClickListener());
	}

	public TitleBarListener getTitleBarListener() {
		return new TitleBarListener() {
			
			@Override
			public void onSpinnerSelected(AdapterView<?> parent, View view, int position, long arg3) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onSettingButtonClick(View arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onPostButtonClick(View arg0) {
				// TODO Auto-generated method stub
				
			}
		};
	}

	public OnItemClickListener getOnTopicViewItemClickListener() {
		// TODO Auto-generated method stub
		return null;
	}

}
