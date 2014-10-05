package com.siuming.hkg.goldenApi;

import java.io.IOException;
import java.net.SocketException;
import java.net.SocketTimeoutException;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;

import android.net.http.AndroidHttpClient;

public class ApiRequest {

	AndroidHttpClient client;
	HttpResponse response;
	HttpGet httpGet;
	int httpStatus = -1;
	String httpStatusStr;	

	public String requestData(String url) {
		String result = "";
		try {
			executeHttpGet(url);
			if (isRequestSuccess()) {
				result = EntityUtils.toString(response.getEntity());
			}
			httpStatus = response.getStatusLine().getStatusCode();
			httpStatusStr = "status: "+httpStatus;
		} catch (SocketTimeoutException e) {
			setHttpStatus(e);
			e.printStackTrace();
			result = httpStatusStr;
		} catch (SocketException e) {
			setHttpStatus(e);
			e.printStackTrace();
			result = httpStatusStr;
		} catch (Exception e) {
			setHttpStatus(e);
			e.printStackTrace();
			result = httpStatusStr;
		} finally {
			client.close();
		}
		return result;
	}

	private boolean isRequestSuccess() {
		return response.getStatusLine().getStatusCode() == HttpStatus.SC_OK;
	}

	public void abortHttpGet() {
		if (httpGet != null) {
			httpGet.abort();
		}
	}

	/**
	 * execute the HTTP request
	 */
	private void executeHttpGet(String url) throws IOException {
		client = AndroidHttpClient.newInstance("");
		httpGet = new HttpGet(url);
		response = client.execute(httpGet);
	}

	private void setHttpStatus(Exception e) {
		int result = -1;
		if (e instanceof SocketTimeoutException) {
			result = -3;
		} else if (e instanceof SocketException) {
			result = -2;
		} else {
			result = -1;
		}
		httpStatusStr = e.getMessage();
		httpStatus = result;
	}

	public int getHttpStatus() {
		return httpStatus;
	}	

}
