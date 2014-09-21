package com.siuming.hkg;

import android.content.Context;
import android.content.SharedPreferences;

public final class GoldenConfig {
	final static String CONFIG = "CONFIG";
	final static String TYPE = "TYPE";
	final static String SENSOR_MODE = "SENSOR_MODE";
	final static String FILTER_MODE = "FILTER_MODE";
	static Context context;
	static SharedPreferences sharedPreferences;
	static int type;
	static boolean sensorMode = false;
	static boolean filterMode = false;
	
	//static ImageCache imageCache;
	
	public static void init(Context context){
		GoldenConfig.context = context;
		sharedPreferences = context.getSharedPreferences(CONFIG, Context.MODE_PRIVATE);
		GoldenConfig.type = sharedPreferences.getInt(TYPE, 0);
		//imageCache = new ImageCache();
		GoldenConfig.sensorMode = sharedPreferences.getBoolean(SENSOR_MODE, false);
		GoldenConfig.filterMode = sharedPreferences.getBoolean(FILTER_MODE, false);
	}

	public static void setType(int type) {
		GoldenConfig.type = type;
		sharedPreferences.edit().putInt(TYPE, type).commit();
	}
	
	public static int getType(){
		return type;
	}
	
	public static String getTypeShot(){
		return context.getResources().getStringArray(R.array.goldenTypesShot)[type];
	}

	public static Context getContext() {
		return GoldenConfig.context;
	}

	/*public static ImageCache getImageCache() {
		return imageCache;
	}*/

	public static void setSensorMode(boolean sensorMode) {
		GoldenConfig.sensorMode = sensorMode;
		sharedPreferences.edit().putBoolean(SENSOR_MODE, sensorMode).commit();
	}

	public static boolean getSensorMode() {
		return sensorMode;
	}

	public static void setFilterMode(boolean filterMode) {
		GoldenConfig.filterMode = filterMode;
		sharedPreferences.edit().putBoolean(FILTER_MODE, filterMode).commit();
	}

	public static boolean getFilterMode() {
		return filterMode;
	}
	
}
