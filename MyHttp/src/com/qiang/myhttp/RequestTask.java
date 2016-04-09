package com.qiang.myhttp;

import org.apache.http.HttpResponse;

import android.os.AsyncTask;

public class RequestTask extends AsyncTask<Void, Integer, Object> {

	private Request request;

	public RequestTask(Request request) {
		this.request = request;
	}
	
	@Override
	protected void onPreExecute() {
		
	}

	@Override
	protected Object doInBackground(Void... params) {
		request.setIsRunning(true);
		int tryCount = 0;
		int triedCount = 0;
		if(request.mCallback != null){
			tryCount = request.mCallback.reTryCount();
		}
		return executeReq(tryCount, triedCount);
	}

	private Object executeReq(int tryCount, int triedCount) {
		request.setIsRunning(true);
		Object result = null;
		if(request.mCallback != null){
			result = request.mCallback.preRequest();
			if(result != null){
				return result;
			}
		}
		try {
			HttpResponse httpResponse = HttpClientUtil.execute(request);
			if (request.listener != null) {
	             result = request.mCallback.handle(httpResponse,
						new IRequsetListener() {

							@Override
							public void updateProgress(int curPos,
									int totalLength) {
								publishProgress(curPos, totalLength);
							}
						});
			} else {
				result = request.mCallback.handle(httpResponse);
			}
			return request.mCallback.postRequest((String) result);
		} catch (Exception e) {
			if(triedCount < tryCount){
				executeReq(tryCount, triedCount++);
			}
			return e;
		}
	}

	@Override
	protected void onPostExecute(Object result) {
		request.setIsRunning(false);
		if (result instanceof Exception) {
			request.mCallback.onFailure((Exception) result);
		} else {
			request.mCallback.onSuccess(result.toString());
		}
		super.onPostExecute(result);
	}

	@Override
	protected void onProgressUpdate(Integer... values) {
		super.onProgressUpdate(values);
		request.listener.updateProgress(values[0], values[1]);
	}
}
