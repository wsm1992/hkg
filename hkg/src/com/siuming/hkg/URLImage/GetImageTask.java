package com.siuming.hkg.URLImage;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.lang.Class;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.http.AndroidHttpClient;
import android.os.Handler;
import android.os.Message;

import com.siuming.hkg.HttpClientHelper;

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
            Message msg = Message.obtain();
            byte[] data = null;
            try{
            	data = getImage(filePath);  
            }catch(Exception e){
            	e.printStackTrace();
            }
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
        

		HttpClient client = HttpClientHelper.getHttpClient();
		HttpGet httpGet = new HttpGet(path);
		HttpResponse response = client.execute(httpGet);
		InputStream inStream = response.getEntity().getContent();  
        if(response.getStatusLine().getStatusCode() == HttpURLConnection.HTTP_OK){  
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
