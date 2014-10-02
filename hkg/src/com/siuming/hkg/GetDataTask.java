package com.siuming.hkg;

import org.apache.http.HttpStatus;

import com.siuming.hkg.goldenApi.ApiRequest;

import android.os.Handler;
import android.os.Message;

public class GetDataTask implements Runnable{
	Handler handler;
	int page;
	ApiRequest apiRequest;
	
	public GetDataTask(Handler handler,int page) {
		super();
		this.handler = handler;
		this.page = page;
	}

	@Override
	public void run() {
		apiRequest = new ApiRequest();
		String resultListJsonStr = getResultJsonStr();
		Message msg = Message.obtain();
		msg.obj = resultListJsonStr;
		msg.arg1 = page;
		msg.what = apiRequest.getHttpStatus();
		handler.sendMessage(msg);
	}

	private String getResultJsonStr() {
		//TODO 分為取topic和取post
		return apiRequest.requestTopicListJson(page);
	}
}
