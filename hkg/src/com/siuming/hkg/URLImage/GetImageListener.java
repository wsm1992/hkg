package com.siuming.hkg.URLImage;

import android.graphics.Bitmap;

public interface GetImageListener {
	public void onSuccess(Bitmap bitmap);
	public void ontFail(String str);
}
