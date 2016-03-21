package com.qiang.myhttp;

public interface ICallback {
	void onSuccess(String content);
	void onFailure(Exception exception);
}
