package com.siuming.hkg.model;

import java.util.ArrayList;
import java.util.HashMap;

import android.widget.SimpleAdapter;

import com.siuming.hkg.GoldenConfig;
import com.siuming.hkg.R;
import com.siuming.hkg.util.MapKey;
import com.siuming.hkg.view.page.ListViewPage;

//contain the Reply list
public class ReplyPageList {
ArrayList<ReplyPage> replyPageList = new ArrayList<ReplyPage>();
		
	int bufferSize = 0;
	int showedPage = 0;
	
	//add reply Page with json string
	public void addReplyPage(String str){
		ReplyPage temp = new ReplyPage(str);
		replyPageList.add(temp);
	}
	
	public boolean updateData() {
		if(replyPageList.size()>showedPage){
			ReplyPage page = replyPageList.get(showedPage++);
			page.updateHashMapList();
			return true;
		}else{
			return false;
		}
	}
	
	public void clear() {
		showedPage = 0;
		replyPageList.clear();
	}
	
	public void setBufferSize(int size){
		bufferSize = size;
	}

	public int loadedPage() {
		return replyPageList.size();
	}

	public int showedPage() {
		return showedPage;
	}

	public boolean canLoad() {
		return replyPageList.size() - showedPage < bufferSize;
	}

	public SimpleAdapter getAdapter(int page) {
		return replyPageList.get(page).getAdapter();
	}
}
