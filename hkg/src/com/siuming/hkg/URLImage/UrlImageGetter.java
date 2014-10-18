package com.siuming.hkg.URLImage;
import com.siuming.hkg.ImageCache;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.text.Html.ImageGetter;
import android.widget.TextView;

public class UrlImageGetter implements ImageGetter {
	TextView textView;
	Context context;
	UrlDrawable urlDrawable;
//	ImageCache imageCache = GoldenConfig.getImageCache();

	public UrlImageGetter(Context contxt, TextView textView) {
		this.context = contxt;
		this.textView = textView;
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
	
	class GetImageListenerImpl implements GetImageListener{
		
		private String url;

		public GetImageListenerImpl(String url){
			this.url = url;
		}
		@Override
		public void onSuccess(Bitmap bitmap) {
			urlDrawable.setImage(bitmap);
			ImageCache imageCache = ImageCache.getInstance();
			imageCache.addImage(url, bitmap);
		}

		@Override
		public void ontFail(String str) {
			
		}
		
	}
}
