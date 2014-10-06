package com.siuming.hkg.view.page;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.SimpleAdapter;

import com.siuming.hkg.GoldenConfig;
import com.siuming.hkg.R;
import com.siuming.hkg.util.MapKey;
import com.siuming.hkg.view.component.RefreshListView;
import com.siuming.hkg.view.component.RefreshListView.RefreshListener;

public class RefListViewPage extends LinearLayout implements IListViewPage {
	RefreshListView listView;
	boolean isShowingLoading;
	LinearLayout LoadingLayout;

	public RefListViewPage(Context context) {
		super(context, null);
		LayoutInflater.from(context).inflate(R.layout.ref_list_view_page, this);
		listView = (RefreshListView) findViewById(R.id.RefListView1);
		LoadingLayout = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.subview_loading, null);
	}
	
	public void setOnRefreshListener(RefreshListener refreshListener) {
		listView.setOnRefreshListener(refreshListener);
	}

	public void setMockData(int id) {
		String[] keyList = new String[] { MapKey.MESSAGE_TITLE};
		int[] idList = new int[] { R.id.messageTitle};
		int layoutId = R.layout.subview_topic;
		ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
		
		SimpleAdapter adapter = new SimpleAdapter(GoldenConfig.getContext(), list, layoutId, keyList, idList);
		listView.setAdapter(adapter);	
		
	}

	@Override
	public void setOnItemClickListener(OnItemClickListener impl) {
		listView.setOnItemClickListener(impl);
	}

	@Override
	public void setListViewAdapter(SimpleAdapter adapter) {
		listView.setAdapter(adapter);
	}
	
	public void showLoading(){
		if (!isShowingLoading) {
			isShowingLoading = true;
			listView.addHeaderView(LoadingLayout);
		}
		listView.setSelection(1);
	}
	
	public void unShowLoading(){
		listView.removeHeaderView(LoadingLayout);
		isShowingLoading = false;
	}

}
