package com.siuming.hkg.goldenApi;

import android.os.Handler;
import android.os.Message;

class GoldenDataHandler extends Handler{
	ApiListener apiListener;
	
	public GoldenDataHandler(ApiListener handlerListener){
		this.apiListener = handlerListener;
	}	
	
	@Override
	public void handleMessage(Message msg){
		if(isSucess(msg)){
			onSuccess((String) msg.obj);
		}else{
			ontFail((String) msg.obj);
		}
	}

	private void onSuccess(String str) {
		apiListener.onSuccess(str);
	}
	
	private void ontFail(String str) {
		apiListener.ontFail(str);
	}

	private boolean isSucess(Message msg) {
		return msg.what == 200;
	}	
}