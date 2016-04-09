package com.qiang.myhttp;

public interface ICallback {
	void onSuccess(String content);
	void onFailure(Exception exception);
	void cancel(boolean isCancel);
	int reTryCount();
	String preRequest();
	String postRequest(String result);
}
