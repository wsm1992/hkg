package com.siuming.hkg.URLImage;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.text.Html.ImageGetter;
import android.widget.TextView;

import com.siuming.hkg.ImageCache;

public class UrlImageGetter implements ImageGetter {
	TextView textView;
	Context context;
	UrlDrawable urlDrawable;
	String textBody;
	static ExecutorService executorService = Executors.newFixedThreadPool(5);

	public UrlImageGetter(Context contxt, TextView textView, String textBody) {
		this.context = contxt;
		this.textView = textView;
		this.textBody= textBody;
	}

	@Override
	public Drawable getDrawable(String url) {
		urlDrawable = new UrlDrawable(context);
		if (url.substring(0,6).equals("/faces")) {
			urlDrawable.getFaces(url.substring(1));
		} else {
			ImageCache imageCache = ImageCache.getInstance();
			Bitmap temp = imageCache.getImage(url);
			if(temp!=null){
				urlDrawable.setImage(temp);
			} else {
				UrlImageService imgService = new UrlImageService(new GetImageListenerImpl(url));
				imgService.setThreadName("get iamge " + url);
				imgService.request(url);
			}
		}
		return urlDrawable;
	}
	
	public void loadPicSuccess(Bitmap bitmap,String url){
		urlDrawable.setImage(bitmap);
		ImageCache imageCache = ImageCache.getInstance();
		imageCache.addImage(url, bitmap);
//		textView.setText(Html.fromHtml(textBody, this,null));
	}
	
	class GetImageListenerImpl implements GetImageListener{
		
		private String url;

		public GetImageListenerImpl(String url){
			this.url = url;
		}
		@Override
		public void onSuccess(Bitmap bitmap) {
			loadPicSuccess(bitmap,url);
		}

		@Override
		public void ontFail(String str) {
			
		}
		
	}
}
