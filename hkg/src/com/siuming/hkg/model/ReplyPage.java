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

//contain one reply page data
public class ReplyPage {
	ArrayList<Reply> replyList;

	// data for adapter
	int layoutId = R.layout.subview_reply;
	String[] keyList = new String[] { MapKey.AUTHOR_NAME, MapKey.MESSAGE_BODY, MapKey.MESSAGE_DATE,
			MapKey.AUTHOR_GENDER };
	int[] idList = new int[] { R.id.textViewAuthorName, R.id.textViewMessageBody, R.id.textViewDate };
	ArrayList<HashMap<String, Object>> hashMaplist = new ArrayList<HashMap<String, Object>>();
	SimpleAdapter adapter = new SimpleAdapter(GoldenConfig.getContext(), hashMaplist, layoutId,
			keyList, idList);

	public ReplyPage(String jsonStr) {
		try {
			replyList = new ArrayList<Reply>();
			JSONObject dataJobj = new JSONObject(jsonStr);
			JSONArray topicJarray = dataJobj.getJSONArray("messages");
			for (int i = 0; i < topicJarray.length(); i++) {
				JSONObject replyJobj = topicJarray.getJSONObject(i);
				Reply temp = new Reply(replyJobj);
				replyList.add(temp);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	void updateHashMapList(){
		hashMaplist.clear();
		for(int i=0;i<replyList.size();i++){
			Reply temp = replyList.get(i);
			HashMap<String, Object> map;
			map = new HashMap<String, Object>();
			map.put(MapKey.AUTHOR_NAME, temp.getAuthorName());
			map.put(MapKey.MESSAGE_BODY, temp.getMessageBody());
			map.put(MapKey.MESSAGE_DATE, temp.getMessageDate());
			map.put(MapKey.AUTHOR_GENDER, temp.getAuthorGender());
			hashMaplist.add(map);
		}
		adapter.notifyDataSetChanged();
	}
		
	ArrayList<Reply> getTopicList(){
		return replyList;
	}
	
	SimpleAdapter getAdapter(){
		return adapter;
	}
}
