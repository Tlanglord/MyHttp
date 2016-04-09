package com.qiang.myhttp;

import java.io.IOException;
import java.net.HttpURLConnection;

import org.apache.http.HttpConnection;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.util.EntityUtils;

import com.qiang.myhttp.utils.TextUtils;

public abstract class AbstractCallback implements ICallback {
	String path;

	public String handle(HttpResponse httpResponse) throws Exception,
			IOException {
		HttpEntity httpEntity = httpResponse.getEntity();
		StatusLine statusLine = httpResponse.getStatusLine();
		
		switch (statusLine.getStatusCode()) {
		case HttpStatus.SC_OK:
			if (!TextUtils.isEmptyString(path)) {

			} else {
				return bindData(EntityUtils.toString(httpEntity));
			}
		case HttpStatus.SC_BAD_REQUEST:
			return "BAD_REQUESR";
		case HttpStatus.SC_UNAUTHORIZED:
			return "UNAUTHORIZED";
		case HttpStatus.SC_METHOD_NOT_ALLOWED:
			return "METHOD_NOT_ALLOWED";
		}
		return "Empty";
		
	}

	protected abstract String bindData(String content);

}
