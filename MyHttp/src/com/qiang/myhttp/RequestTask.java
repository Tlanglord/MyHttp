package com.qiang.myhttp;

import org.apache.http.HttpResponse;

import android.os.AsyncTask;

public class RequestTask extends AsyncTask<Void, Integer, Object> {

	private Request request;
	
	
	public RequestTask(Request request) {
		this.request = request;
	}

	@Override
	protected Object doInBackground(Void... params) {
		try {
		HttpResponse httpResponse = HttpClientUtil.execute(request);
		return request.mCallback.handle(httpResponse);
		} catch (Exception e) {
			return e;
		}
	}

	@Override
	protected void onPostExecute(Object result) {
		if(result instanceof Exception){
		   request.mCallback.onFailure((Exception)result);
		}else{
			request.mCallback.onSuccess(result.toString());
		}
		super.onPostExecute(result);
		
	}
	
	@Override
	protected void onProgressUpdate(Integer... values) {
		super.onProgressUpdate(values);
	}
	
}
