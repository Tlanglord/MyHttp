package com.qiang.myhttp;

public abstract class StringCallback extends AbstractCallback {

	@Override
	protected String bindData(String content) {
		return content;
	}
	
}
