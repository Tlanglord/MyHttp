package com.qiang.myhttp.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

import com.qiang.myhttp.JsonCallback;
import com.qiang.myhttp.R;
import com.qiang.myhttp.Request;
import com.qiang.myhttp.Request.RequestMethod;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		findViewById(R.id.hello_world).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				testReqest();
			}
		});
	}

	private void testReqest() {
		Request request = new Request();
		request.setUrl("http://www.wpdqq.com/");
		request.addHeader("Content-Type", "text/json");
		request.method = RequestMethod.POST;
		request.setCallback(new JsonCallback(){

			@Override
			public void onSuccess(String content) {
				Log.v("testhttp", content);
			}

			@Override
			public void onFailure(Exception exception) {
				Log.v("testhttp", exception.getMessage());
			}
			
		});
		request.execute();
	}
}
