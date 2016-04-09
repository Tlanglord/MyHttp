package com.qiang.myhttp;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpEntity;

public class Request {

	public RequestMethod method;
	public Map<String, String> mHeaderMap = new HashMap<String, String>();
	public HttpEntity entity;

	private String mUrl;
	public AbstractCallback mCallback;
	public IRequsetListener listener;
	private RequestTask task;
	private boolean isRunning;

	public void setIsRunning(boolean isRunning) {
		this.isRunning = isRunning;
	}

	public IRequsetListener getListener() {
		return listener;
	}

	public void setListener(IRequsetListener listener) {
		this.listener = listener;
	}

	public String getUrl() {
		return mUrl;
	}

	public void setUrl(String url) {
		this.mUrl = url;
	}

	public enum RequestMethod {
		GET, DELETE, PUT, POST
	}

	public Request() {

	}

	public Request(String url, RequestMethod method) {
		this.mUrl = url;
		this.method = method;
	}

	public void execute() {
		task = new RequestTask(this);
		task.execute();
	}

	public void addHeader(String key, String value) {
		mHeaderMap.put(key, value);
	}

	public void setCallback(AbstractCallback callback) {
		mCallback = callback;
	}

	/**
	 * 
	 */
	public void cancel() {
		if (mCallback != null) {
			mCallback.cancel(true);
		}
		if (task != null && !task.isCancelled()) {
			task.cancel(true);
		}
	}
	
	public boolean isRunning(){
		return isRunning;
	}
}
