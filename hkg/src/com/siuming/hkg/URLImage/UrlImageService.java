package com.siuming.hkg.URLImage;

import android.os.Handler;

public class UrlImageService {
	GetImageListener getImageListener;
	String threadName = "get image";
	
	public UrlImageService(GetImageListener listener){
		getImageListener = listener;
	}
	
	public void setThreadName(String name){
		threadName = name;
	}
	
	public void request(String url){
		Handler handler = new GetImageHandler(getImageListener);
		GetImageTask task = new GetImageTask(handler,url);
		Thread getDataThread = new Thread(task);
		getDataThread.setName(threadName);
		getDataThread.start();
	}
}
