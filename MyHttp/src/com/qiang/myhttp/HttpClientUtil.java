package com.qiang.myhttp;

import java.util.Map.Entry;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;



public class HttpClientUtil {

	public static HttpResponse execute(Request request) throws Exception {
		switch (request.method) {
		case PUT:
			break;
		case GET:
		//	get(request);
			break;
		case DELETE:
			break;
		case POST:
			return post(request);
		default:
			break;
		}
		
		return null;
	}

	private static HttpResponse post(Request request) throws Exception{
		HttpClient  client = new DefaultHttpClient();
	    HttpPost post = new HttpPost(request.getUrl());
	    addHeader(post, request);
	    post.setEntity(request.entity);
	    return  client.execute(post);
	}

//	private static void get(Request request) {
//		
//	}
	
	private  static void addHeader(HttpUriRequest httpUriRequest, Request request){
		for( Entry<String, String> entry : request.mHeaderMap.entrySet()) {
			httpUriRequest.addHeader(entry.getKey(), entry.getValue());
		}
	}

}
