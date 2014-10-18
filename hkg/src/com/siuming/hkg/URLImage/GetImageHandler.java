package com.siuming.hkg.URLImage;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;

public class GetImageHandler  extends Handler{
	GetImageListener getImageListener;
	
	public GetImageHandler(GetImageListener listener){
		getImageListener = listener;
	}
	@Override
	public void handleMessage(Message msg){
		Bitmap bitmap = (Bitmap) msg.obj;
		if(bitmap!=null){
			onSuccess(bitmap);
		}else{
			ontFail("fail");
		}
	}
	
	private void onSuccess(Bitmap bitmap) {
		getImageListener.onSuccess(bitmap);
	}
	
	private void ontFail(String str) {
		getImageListener.ontFail(str);
	}
}
