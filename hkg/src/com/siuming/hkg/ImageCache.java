package com.siuming.hkg;

import java.util.HashMap;

import android.graphics.Bitmap;

public class ImageCache {
static ImageCache imageCache;
	
	HashMap<String, Bitmap> hashMap;
	private ImageCache(){
		hashMap = new HashMap<String, Bitmap>();
	}
	
	public static ImageCache getInstance(){
		if(imageCache==null){
			imageCache = new ImageCache();
		}
		return imageCache;
	}
	
	public void addImage(String messageId, Bitmap bitmap){
		hashMap.put(messageId, bitmap);
	}
	
	public Bitmap getImage(String url){
		return hashMap.get(url);
	}
}
