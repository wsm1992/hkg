package com.siuming.hkg.goldenApi;

//create the url for get golden data
public class ApiModel {
	/**
	 * API web site
	 */
	final static String site = "http://apps.hkgolden.com/";
	String goal;
	String path;
	/**
	 * API web site parameters
	 */
	String type = "BW";
	int page = 1;
	String filtermode;
	String sensormode;
	static String returntype = "json";
	String messageId;
	
	public static int TOPIC = 0;
	public static int REPLY = 1;
	
	//topic or post 
	public void setGoal(int goal) {
		this.goal = goal==TOPIC?"newTopics":"newView";
	}
	//吹水台,XX台之類
	public void setType(String type) {
		this.type = type;
	}
	//頁數
	public void setPage(int page) {
		this.page = page;
	}
	//小圈子過濾模式 
	public void setFiltermode(boolean filtermode) {
		this.filtermode = filtermode?"Y":"N";
	}
	//懷舊模式
	public void setSensormode(boolean sensormode) {
		this.sensormode = sensormode?"Y":"N";
	}
	//post用,該post的ID
	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}
	
	String getUrl(){
		path = "android_api/v_1_0/" + goal + ".aspx";
		String url = site + path + "?";
		url += "type=" + type;
		url += "&page=" + page;
		url += "&filtermode=" + filtermode;
		url += "&sensormode=" + sensormode;
		url += "&returntype=" + returntype;
		if(messageId != null){
			url += "&message=" + messageId;
		}
		return url;
	}
}
