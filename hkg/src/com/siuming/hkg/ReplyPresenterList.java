package com.siuming.hkg;

import java.util.HashMap;

public class ReplyPresenterList {
	static ReplyPresenterList presenterList;
	
	HashMap<String, ReplyPresenter> hashMap;
	private ReplyPresenterList(){
		hashMap = new HashMap<String, ReplyPresenter>();
	}
	
	public static ReplyPresenterList getInstance(){
		if(presenterList==null){
			presenterList = new ReplyPresenterList();
		}
		return presenterList;
	}
	
	public void addPresenter(String messageId, ReplyPresenter presenter){
		hashMap.put(messageId, presenter);
	}
	
	public ReplyPresenter getPresenter(String messageId){
		return hashMap.get(messageId);
	}
}
