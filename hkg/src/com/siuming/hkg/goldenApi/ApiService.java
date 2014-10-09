package com.siuming.hkg.goldenApi;
import android.os.Handler;

//the facade of Golden Api
public class ApiService {
	ApiListener apiListener;
	String threadName;
	
	public ApiService(ApiListener listener){
		apiListener = listener;
	}
	
	public void setThreadName(String name){
		threadName = name;
	}
	
	public void request(ApiModel apiModel){
		Handler handler = new GoldenDataHandler(apiListener);
		GetDataTask task = new GetDataTask(handler,apiModel.getUrl());
		Thread getDataThread = new Thread(task);
		getDataThread.setName(threadName);
		getDataThread.start();
	}
	
}
