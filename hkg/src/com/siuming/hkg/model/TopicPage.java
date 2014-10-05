package com.siuming.hkg.model;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.widget.SimpleAdapter;

import com.siuming.hkg.GoldenConfig;
import com.siuming.hkg.R;
import com.siuming.hkg.util.MapKey;

public class TopicPage {
	ArrayList<Topic> topicList;
	
	int layoutId = R.layout.subview_topic;
	String[] keyList = new String[] { MapKey.MESSAGE_TITLE, MapKey.AUTHOR_NAME, MapKey.TOTAL_REPLIES,
			MapKey.RATING, MapKey.MESSAGE_ID ,MapKey.LAST_REPLY_DATE};
	int[] idList = new int[] { R.id.messageTitle, R.id.auther, R.id.totalReplies,
			R.id.rating };
	SimpleAdapter adapter;
	ArrayList<HashMap<String, Object>> hashMaplist = new ArrayList<HashMap<String, Object>>();
	
	
	public TopicPage(String jsonStr){
		try {
			topicList = new ArrayList<Topic>();
			JSONObject dataJobj = new JSONObject(jsonStr);
			JSONArray topicJarray = dataJobj.getJSONArray("topic_list");
			for (int i = 0; i < topicJarray.length(); i++) {
				JSONObject topicJobj = topicJarray.getJSONObject(i);
				Topic temp = new Topic(topicJobj);
				topicList.add(temp);
			}
			createSimpleAdapter();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void createSimpleAdapter() {
		for(int i=0;i<topicList.size();i++){
			Topic temp = topicList.get(i);
			HashMap<String, Object> map;
			map = new HashMap<String, Object>();
			map.put(MapKey.MESSAGE_TITLE, temp.getMessageTitle());
			map.put(MapKey.AUTHOR_NAME, temp.getAutherName());
			map.put(MapKey.TOTAL_REPLIES, temp.getTotalReplies());
			map.put(MapKey.RATING, temp.getRatingStr());
			map.put(MapKey.MESSAGE_ID, temp.getMessageId());
			map.put(MapKey.LAST_REPLY_DATE, temp.getLastReplayDate());
			hashMaplist.add(map);
		}
		adapter = new SimpleAdapter(GoldenConfig.getContext(), hashMaplist, layoutId, keyList, idList);
	}
	
	public SimpleAdapter getAdapter(){
		return adapter;
	}
}
