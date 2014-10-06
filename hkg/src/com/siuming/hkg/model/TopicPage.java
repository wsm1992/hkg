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

class TopicPage {
	ArrayList<Topic> topicList;
		
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
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
		
	ArrayList<Topic> getTopicList(){
		return topicList;
	}
}
