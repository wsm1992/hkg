package com.siuming.hkg.goldenApi;

import java.io.IOException;
import java.net.SocketException;
import java.net.SocketTimeoutException;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;

import android.net.http.AndroidHttpClient;

import com.siuming.hkg.GoldenConfig;

public class ApiRequest {

	AndroidHttpClient client;
	HttpResponse response;
	HttpGet httpGet;
	int httpStatus = -1;
	String httpStatusStr;
	/**
	 * API web site
	 */
	final static String site = "http://apps.hkgolden.com/";
	String goal;
	String path;
	/**
	 * API web site parameters
	 */
	String type = "BW";
	int page = 1;
	String filtermode;
	String sensormode;
	String returntype = "json";
	String messageId;

	/**
	 * result of request from API in Json format
	 */
	public String requestTopicListJson(int page) {
		abortHttpGet();
		String url = initTopicUrl(page);
		String result = getApiResponse(url);
		return result;
	}

	public String requestPostListJson(int page) {
		abortHttpGet();
		String url = initPostUrl(page);
		String result = getApiResponse(url);
		return result;
	}

	private String getApiResponse(String url) {
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
	 * init the web site of API
	 */
	private String initTopicUrl(int page) {
		this.page = page;
		goal = "newTopics";
		String url = initUrl();
		return url;
	}

	private String initPostUrl(int page) {
		this.page = page;
		goal = "newView";
		String url = initUrl();
		url += "&message=" + messageId;
		return url;
	}

	private String initUrl() {
		path = "android_api/v_1_0/" + goal + ".aspx";
		type = GoldenConfig.getTypeShot();
		filtermode = GoldenConfig.getFilterMode()?"Y":"N";
		sensormode = GoldenConfig.getSensorMode()?"Y":"N";
		String url = site + path + "?";
		url += "type=" + type;
		url += "&page=" + page;
		url += "&filtermode=" + filtermode;
		url += "&sensormode=" + sensormode;
		url += "&returntype=" + returntype;
		return url;
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
	
	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

}
