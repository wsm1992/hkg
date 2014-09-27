package com.siuming.hkg;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.siuming.hkg.RefreshListView.RefreshListener;
import com.siuming.hkg.util.MapKey;

public class RefListViewPage extends LinearLayout implements IListViewPage {
	RefreshListView listView;

	public RefListViewPage(Context context) {
		super(context, null);
		LayoutInflater.from(context).inflate(R.layout.ref_list_view_page, this);
		listView = (RefreshListView) findViewById(R.id.RefListView1);
	}
	
	public void setOnRefreshListener(RefreshListener refreshListener) {
		listView.setOnRefreshListener(refreshListener);
	}

	public void setMockData(int id) {
		String[] keyList = new String[] { MapKey.MESSAGE_TITLE, MapKey.AUTHOR_NAME,
				MapKey.TOTAL_REPLIES, MapKey.RATING, MapKey.MESSAGE_ID, MapKey.LAST_REPLY_DATE };
		int[] idList = new int[] { R.id.messageTitle, R.id.auther, R.id.totalReplies, R.id.rating };
		int layoutId = R.layout.subview_topic;
		ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		for(int i=0;i<id;i++){
		map.put(MapKey.MESSAGE_TITLE, "Abc");
		map.put(MapKey.AUTHOR_NAME, "abc");
		map.put(MapKey.TOTAL_REPLIES, "abc");
		map.put(MapKey.RATING,"abc");
		map.put(MapKey.MESSAGE_ID, "abc");
		map.put(MapKey.LAST_REPLY_DATE, "abc");
		list.add(map);
		}
		
		SimpleAdapter adapter = new SimpleAdapter(GoldenConfig.getContext(), list, layoutId, keyList, idList);
		listView.setAdapter(adapter);
		
		setOnRefreshListener(new RefreshListener() {
			
			@Override
			public void waitUpdate() {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void showUpdate() {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void refreshing() {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void loadUpdate() {
				// TODO Auto-generated method stub
				
			}
		});
	}

	@Override
	public void setOnItemClickListener(OnItemClickListener impl) {
		listView.setOnItemClickListener(impl);
	}

	@Override
	public void setListViewAdapter(SimpleAdapter adapter) {
		// TODO Auto-generated method stub
		
	}

}
