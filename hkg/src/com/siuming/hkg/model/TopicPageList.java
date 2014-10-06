package com.siuming.hkg.model;

import java.util.ArrayList;
import java.util.HashMap;

import android.widget.SimpleAdapter;

import com.siuming.hkg.GoldenConfig;
import com.siuming.hkg.R;
import com.siuming.hkg.util.MapKey;

public class TopicPageList {
	ArrayList<TopicPage> topicPageList = new ArrayList<TopicPage>();
	
	int layoutId = R.layout.subview_topic;
	String[] keyList = new String[] { MapKey.MESSAGE_TITLE, MapKey.AUTHOR_NAME, MapKey.TOTAL_REPLIES,
			MapKey.RATING, MapKey.MESSAGE_ID ,MapKey.LAST_REPLY_DATE};
	int[] idList = new int[] { R.id.messageTitle, R.id.auther, R.id.totalReplies,
			R.id.rating };
	ArrayList<HashMap<String, Object>> hashMaplist = new ArrayList<HashMap<String, Object>>();
	SimpleAdapter adapter= new SimpleAdapter(GoldenConfig.getContext(), hashMaplist, layoutId, keyList, idList);
	
	
	public void addTopicPage(String str){
		TopicPage temp = new TopicPage(str);
		topicPageList.add(temp);
	}
	
	public void updateHashMapList(int index) {
		TopicPage page = topicPageList.get(index);
		for(int i=0;i<page.getTopicList().size();i++){
			Topic temp = page.getTopicList().get(i);
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
	}
	
	public SimpleAdapter getAdapter(){
		return adapter;
	}

	public void clear() {
		topicPageList.clear();
		hashMaplist.clear();
	}
}
