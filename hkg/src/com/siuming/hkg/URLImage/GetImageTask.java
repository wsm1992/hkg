package com.siuming.hkg.URLImage;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;

public class GetImageTask implements Runnable{
	Handler handler;
	String website;
	Bitmap bitmap;
	
	public GetImageTask(Handler handler,String website) {
		super();
		this.handler = handler;
		this.website = website;
	}
	
	@Override
	public void run() {
		try {  
            String filePath = website; 
            //mFileName = "test.jpg";  
            Message msg = Message.obtain();
            byte[] data = getImage(filePath);  
            if(data!=null){  
            	bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
            	msg.obj = bitmap;
            }else{  
            	msg.obj = null;
            }            
    		
            handler.sendMessage(msg); 
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
	}
	
	public byte[] getImage(String path) throws Exception{  
        URL url = new URL(path);  
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();  
        conn.setConnectTimeout(5 * 1000);  
        conn.setRequestMethod("GET");  
        InputStream inStream = conn.getInputStream();  
        if(conn.getResponseCode() == HttpURLConnection.HTTP_OK){  
            return readStream(inStream);  
        }  
        return null;  
    }  
	
	public static byte[] readStream(InputStream inStream) throws Exception{  
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();  
        byte[] buffer = new byte[1024];  
        int len = 0;  
        while( (len=inStream.read(buffer)) != -1){  
            outStream.write(buffer, 0, len);  
        }  
        outStream.close();  
        inStream.close();  
        return outStream.toByteArray();  
    }  
}
