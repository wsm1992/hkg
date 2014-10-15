package com.siuming.hkg;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Html.ImageGetter;
import android.widget.TextView;



public class URLImageGetter implements ImageGetter {
	TextView textView;
	Context context;
	URLDrawable urlDrawable;
//	ImageCache imageCache = GoldenConfig.getImageCache();

	public URLImageGetter(Context contxt, TextView textView) {
		this.context = contxt;
		this.textView = textView;
	}

	@Override
	public Drawable getDrawable(String paramString) {
		urlDrawable = new URLDrawable(context);
		if (paramString.substring(0,6).equals("/faces") == true) {
			urlDrawable.getFaces(paramString.substring(1));
		} else {
//			imageCache.setOnImageCallbackListener(imageCallBack);
//			imageCache.get(paramString, textView);
		}
		/*
		 * ImageGetterAsyncTask getterTask = new
		 * ImageGetterAsyncTask(urlDrawable); getterTask.execute(paramString);
		 */
		return urlDrawable;
	}
}
