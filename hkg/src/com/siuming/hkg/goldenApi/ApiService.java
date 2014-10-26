package com.siuming.hkg.goldenApi;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.os.Handler;

//the facade of Golden Api
public class ApiService {
	ApiListener apiListener;
	String threadName;
	ExecutorService executorService;
	
	public ApiService(ApiListener listener, ExecutorService service){
		apiListener = listener;
		executorService = service;
	}
	
	public void setThreadName(String name){
		threadName = name;
	}
	
	public void request(ApiModel apiModel){
		Handler handler = new GoldenDataHandler(apiListener);
		GetDataTask task = new GetDataTask(handler,apiModel.getUrl());
		executorService.execute(task);
//		Thread getDataThread = new Thread(task);
//		getDataThread.setName(threadName);
//		getDataThread.start();
	}
	
}
