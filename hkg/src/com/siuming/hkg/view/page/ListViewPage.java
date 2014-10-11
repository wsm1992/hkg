package com.siuming.hkg.view.page;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.siuming.hkg.GoldenConfig;
import com.siuming.hkg.R;
import com.siuming.hkg.util.MapKey;

public class ListViewPage extends LinearLayout  implements IListViewPage {
	ListView listView;

	public ListViewPage(Context context) {
		super(context, null);
		LayoutInflater.from(context).inflate(R.layout.simple_list_view_page, this);
		listView = (ListView) findViewById(R.id.ListView1);
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
	}
	
	@Override
	public void setOnItemClickListener(OnItemClickListener impl) {
		listView.setOnItemClickListener(impl);
	}

	@Override
	public void setListViewAdapter(SimpleAdapter adapter) {
		listView.setAdapter(adapter);
	}

}
