package com.qiang.myhttp;

import com.alibaba.fastjson.JSON;

public abstract class JsonCallback extends AbstractCallback {

	@Override
	protected String bindData(String content) {
		return JSON.toJSONString(content); 
	}
}
