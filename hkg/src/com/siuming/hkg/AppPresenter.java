package com.siuming.hkg;

import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

import com.siuming.hkg.HkgTopicTitleBar.TitleBarListener;
import com.siuming.hkg.view.activity.MainActivity;
import com.siuming.hkg.view.component.HkgViewPager;
import com.siuming.hkg.view.page.ListViewPage;
import com.siuming.hkg.view.page.RefListViewPage;

//for main logic
public class AppPresenter {
	MainActivity mainActivity;
	
	HkgTopicTitleBar topicTitleBar;
	
	HkgViewPager viewPager;
    RefListViewPage topicPage;
    ListViewPage historyPage;
    
    RefPagePresenter topicPagePresenter;
    
    public void startApp(){
		createTitleBar();
		createPageViewer();
		setListener();
    }

	public void setMainActivity(MainActivity activity) {
		mainActivity = activity;
	}
	
	private void createTitleBar(){
		topicTitleBar = new HkgTopicTitleBar(mainActivity);
	}

	private void createPageViewer() {
		viewPager = new HkgViewPager(mainActivity);		
		topicPage = new RefListViewPage(mainActivity);
		historyPage = new ListViewPage(mainActivity);
		
		viewPager.addPage(topicPage,"主題");
		viewPager.addPage(historyPage,"主題2");
		
		topicPagePresenter = new RefPagePresenter(topicPage);
		
		historyPage.setMockData(10);		
	}
	
	private void setListener(){
		topicTitleBar.setTitleBarListener(new TitleBarListenerImpl());
		topicPage.setOnItemClickListener(new OnItemClickListenerImpl());
	}

	private class TitleBarListenerImpl implements TitleBarListener{

		//this function will start in the beginning, so app will load topic whe start app
		@Override
		public void onSpinnerSelected(AdapterView<?> parent, View view, int position, long arg3) {
			Toast.makeText(mainActivity, "selected " + position, Toast.LENGTH_SHORT)
			.show();
			GoldenConfig.setType(position);
			topicPagePresenter.requestRefresh();
		}

		@Override
		public void onSettingButtonClick(View arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onPostButtonClick(View arg0) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	private class OnItemClickListenerImpl implements OnItemClickListener{

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
			// TODO Auto-generated method stub
			
		}
		
	}
}
