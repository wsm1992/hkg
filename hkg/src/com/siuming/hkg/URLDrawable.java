package com.siuming.hkg;

import java.io.IOException;
import java.io.InputStream;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;

public class URLDrawable extends BitmapDrawable {
	private Drawable drawable;
	private Context context;
	@SuppressWarnings("deprecation")
	public URLDrawable(Context context) {
		this.context = context;
		drawable = context.getResources().getDrawable(R.drawable.loading);
		this.setBounds(getDefaultImageBounds(context));
		drawable.setBounds(getDefaultImageBounds(context));
	}
	
	public void setImage(Bitmap bitmap){
		drawable = new BitmapDrawable(bitmap);
		drawable.setBounds(0,0,bitmap.getWidth(),bitmap.getHeight());
	}

	public void setFailImage(Context context) {
		drawable = context.getResources().getDrawable(R.drawable.loading_fail);
		this.setBounds(getDefaultImageBounds(context));
		drawable.setBounds(getDefaultImageBounds(context));
	}

	public void getFaces(String paramString) {
		AssetManager am = context.getResources().getAssets(); 
		Bitmap image;
		try {   
	        InputStream is = am.open(paramString);   
	        image = BitmapFactory.decodeStream(is);   
	        is.close();   
	        setImage(image);
	        reviseFacesBounds(image);
	    } catch (IOException e) {   
	        e.printStackTrace();   
	    }   
	}

	private void reviseFacesBounds(Bitmap bitmap) {
		drawable.setBounds(0,0,bitmap.getWidth()*3,bitmap.getHeight()*3);
		this.setBounds(drawable.getBounds());
	}

	@Override
	public void draw(Canvas canvas) {
		if (drawable != null) {
			drawable.draw(canvas);
		}
	}

	public static  Rect getDefaultImageBounds(Context context) {
		Rect bounds = new Rect(0, 0, 94, 94);
		return bounds;
	}
	
	public static  Rect getMaxBounds(Context context) {
		DisplayMetrics dm = new DisplayMetrics();
		((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(dm);
		int width = dm.widthPixels;
		int height = width;

		Rect bounds = new Rect(0, 0, width, height);
		return bounds;
	}


	public void reviseBounds(Context context) {
		int maxWidth = getMaxBounds(context).width();
		double factor = (double) drawable.getIntrinsicWidth() / (double) maxWidth;
		factor = factor < 1 ? 1 : factor;
		int newWidth = (int) (drawable.getIntrinsicWidth() / factor);
		int newHeight = (int) (drawable.getIntrinsicHeight() / factor);

		drawable.setBounds(0, 0, newWidth, newHeight);
		this.setBounds(drawable.getBounds());
	}
	
	
}