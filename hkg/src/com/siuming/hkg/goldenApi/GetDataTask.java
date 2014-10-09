package com.siuming.hkg.goldenApi;

import android.os.Handler;
import android.os.Message;


class GetDataTask implements Runnable{
	Handler handler;
	String website;
	ApiRequest apiRequest;
	
	public GetDataTask(Handler handler,String website) {
		super();
		this.handler = handler;
		this.website = website;
	}

	@Override
	public void run() {
		apiRequest = new ApiRequest();
		String resultListJsonStr = apiRequest.requestData(website);
		Message msg = Message.obtain();
		msg.obj = resultListJsonStr;
		msg.what = apiRequest.getHttpStatus();
		handler.sendMessage(msg);
	}
}
