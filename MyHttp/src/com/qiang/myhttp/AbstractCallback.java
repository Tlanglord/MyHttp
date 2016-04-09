package com.qiang.myhttp;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.util.EntityUtils;

import com.qiang.myhttp.utils.TextUtils;

public abstract class AbstractCallback implements ICallback {
    protected String filePath;
    private boolean isCancel;
	
	private void ifNeedCancel() throws IllegalAccessException{
		if(isCancel){
			throw new IllegalAccessException("throw the request");
		}
	}
	public String handle(HttpResponse httpResponse, IRequsetListener listener) throws Exception,
			IOException {
		HttpEntity httpEntity = httpResponse.getEntity();
		StatusLine statusLine = httpResponse.getStatusLine();
		ifNeedCancel();
		switch (statusLine.getStatusCode()) {
		case HttpStatus.SC_OK:
			if (!TextUtils.isEmptyString(filePath)) {
				FileOutputStream fos = new FileOutputStream(filePath);
				InputStream is = httpEntity.getContent();
				byte[] buffer = new byte[2048];
				int len = 0;
				long length = httpEntity.getContentLength();
				long curPos = 0;
				while((len = is.read(buffer)) !=  -1){
					fos.write(buffer);
					curPos += len;
					if(listener != null){
						listener.updateProgress((int) (curPos/1024), (int) (length/1024));
					}
				}
				fos.flush();
				fos.close();
				is.close();
				return bindData(filePath);
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
	
	/* (non-Javadoc)
	 * @see com.qiang.myhttp.ICallback#reTryCount()
	 */
	// @Override
	// public int reTryCount() {
	// return 0;
	// }
	
	public String handle(HttpResponse httpResponse) throws Exception{
		return handle(httpResponse, null);
	}
	
	/* (non-Javadoc)
	 * @see com.qiang.myhttp.ICallback#reTryCount()
	 */
	@Override
	public int reTryCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	protected abstract String bindData(String content);

	/* (non-Javadoc)
	 * @see com.qiang.myhttp.ICallback#cancel(boolean)
	 */
	@Override
	public void cancel(boolean cancel) {
		isCancel = cancel;
	}
	
	
	/* (non-Javadoc)
	 * @see com.qiang.myhttp.ICallback#preRequest()
	 */
	
	/* (non-Javadoc)
	 * @see com.qiang.myhttp.ICallback#preRequest()
	 */
	@Override
	public String preRequest() {
		// TODO Auto-generated method stub
		return null;
	}
	
	/* (non-Javadoc)
	 * @see com.qiang.myhttp.ICallback#postRequest(java.lang.String)
	 */
	@Override
	public String postRequest(String result) {
		return result;
	}
}
