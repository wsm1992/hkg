package com.siuming.hkg.model;

import android.widget.SimpleAdapter;

//contain the Reply list
public class ReplyPageList {
	ReplyPage[] replyPageList = new ReplyPage[50];
	//ArrayList<ReplyPage> replyPageList = new ArrayList<ReplyPage>();
		
	int bufferSize = 0;
	int showedPage = 0;
	int lastPage = 0;
	int firstPage = 1;
	
	//add reply Page with json string
	public void addReplyPage(String str,int page){
		ReplyPage temp = new ReplyPage(str);
		replyPageList[page] = temp;
		if(page>lastPage){
			lastPage = page;
		}
	}
	
	public boolean updateData() {
		if(isFull(showedPage))
			return updateData(showedPage + 1);
		else
			return updateData(showedPage);
	}
	
	public boolean updateData(int p) {
		if(replyPageList[p].existData()){
			ReplyPage page = replyPageList[p];
			page.updateHashMapList();
			showedPage = p;
			return true;
		}else{
			return false;
		}
	}
	
	public void setBufferSize(int size){
		bufferSize = size;
	}

	public int showedPage() {
		return showedPage;
	}

	public boolean canLoad() {
		return lastPage - showedPage < bufferSize;
	}

	public SimpleAdapter getAdapter(int page) {
		return replyPageList[page].getAdapter();
	}

	public boolean loadedFirstPage() {
		if(replyPageList[firstPage]!= null)
			return replyPageList[firstPage].existData();
		else
			return false;
	}

	public int getFirstPage() {
		return firstPage;
	}

	public int nextToLoad() {
		if(isFull(lastPage)){
			return lastPage+1;
		}else 
			return lastPage;
	}

	public boolean isFull(int p) {
		if (p == 1) {
			if (replyPageList[p].getTopicList().size() >= 26) {
				return true;
			}
		} else if (p <= 0) {
			return true;
		} else {
			if (replyPageList[p].getTopicList().size() >= 25) {
				return true;
			}
		}
		return false;
	}
}
