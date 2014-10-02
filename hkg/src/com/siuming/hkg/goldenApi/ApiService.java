package com.siuming.hkg.goldenApi;
import android.os.Handler;

import com.siuming.hkg.GetDataTask;
import com.siuming.hkg.GoldenDataHandler;
import com.siuming.hkg.GoldenDataHandler.ApiListener;

public class ApiService {
	ApiListener apiListener;
	int page;
	String threadName;
	
	public ApiService(ApiListener listener){
		apiListener = listener;
	}
	
	public void setPage(int p){
		page = p;
	}
	
	public void setThreadName(String name){
		threadName = name;
	}
	
	public void request(){
		Handler handler = new GoldenDataHandler(apiListener);
		GetDataTask task = new GetDataTask(handler,page);
		Thread getDataThread = new Thread(task);
		getDataThread.setName(threadName);
		getDataThread.start();
	}
	
}
