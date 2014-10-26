package com.siuming.hkg.URLImage;

import java.io.IOException;
import java.io.InputStream;

import com.siuming.hkg.R;
import com.siuming.hkg.R.drawable;

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

public class UrlDrawable extends BitmapDrawable {
	private Drawable drawable;
	private Context context;
	Canvas canvas;
	@SuppressWarnings("deprecation")
	public UrlDrawable(Context context) {
		this.context = context;
		drawable = context.getResources().getDrawable(R.drawable.loading);
		setBounds(getDefaultImageBounds(context));
	}
	
	public void setImage(Bitmap bitmap){
		drawable = new BitmapDrawable(context.getResources(),bitmap);
		setBounds(0,0,bitmap.getWidth(),bitmap.getHeight());
		reviseBounds(context);
		if(canvas!=null){
			drawable.draw(canvas);
		}
	}

	public void setFailImage(Context context) {
		drawable = context.getResources().getDrawable(R.drawable.loading_fail);
		setBounds(getDefaultImageBounds(context));
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
		setBounds(0,0,bitmap.getWidth()*3,bitmap.getHeight()*3);
	}

	@Override
	public void draw(Canvas canvas) {
		this.canvas = canvas;
		if (drawable != null) {
			drawable.draw(canvas);
		}
	}
	
	@Override
	public void setBounds(Rect bounds){
		super.setBounds(bounds);
		drawable.setBounds(bounds);
	}
	
	@Override
	public void setBounds(int left,int top,int right,int bottom){
		super.setBounds(left,top,right,bottom);
		drawable.setBounds(left,top,right,bottom);
	}

	public static  Rect getDefaultImageBounds(Context context) {
		Rect bounds = new Rect(0, 0, 94, 94);
		return getMaxBounds(context);
	}
	
	public static  Rect getMaxBounds(Context context) {
		DisplayMetrics dm = new DisplayMetrics();
		((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(dm);
		int width = dm.widthPixels*8/10;
		int height = width;

		Rect bounds = new Rect(0, 0, width, height);
		return bounds;
	}


	public void reviseBounds(Context context) {
		int maxWidth = getMaxBounds(context).width();
		if(drawable.getIntrinsicWidth()>maxWidth){
			double factor = (double) drawable.getIntrinsicWidth() / (double) maxWidth;
			int newHeight = (int) (drawable.getIntrinsicHeight() / factor);	
			setBounds(0, 0, maxWidth, newHeight);
		}
	}
	
	
}