package com.siuming.hkg;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

public class ImageCache {
	static ImageCache imageCache;
	File dir = new File(Environment.getExternalStorageDirectory()+File.separator+"hkg"+File.separator);
	private ImageCache(){
		if (!dir.exists()) {
			dir.mkdir();
		}
	}
	
	public static ImageCache getInstance(){
		if(imageCache==null){
			imageCache = new ImageCache();
		}
		return imageCache;
	}
	
	public void addImage(String url, Bitmap bitmap){
		String filename = dir + File.separator +url.replace("/", "_");
		File file = new File(filename);
		FileOutputStream fos;
		try {
			if (file.exists()) {
				file.delete();
			}
			file.createNewFile();			
			fos = new FileOutputStream(file);
			bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
			fos.flush();
			fos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Bitmap getImage(String url){
		String filename = dir + File.separator +url.replace("/", "_");
		File file = new File(filename);
		FileInputStream fis;
		Bitmap bitmap = null;
		if(!file.exists()){
			return null;
		}
		try {			
			fis = new FileInputStream(file);
			if(fis.available() == 0){
				fis.close();
				return null;
			}
			byte[] readBytes = new byte[fis.available()];
			while (fis.read(readBytes) != -1) {
			}
			bitmap = BitmapFactory.decodeByteArray(readBytes, 0, readBytes.length);
			fis.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bitmap;
	}
}
