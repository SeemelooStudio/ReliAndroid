package com.reqst;

import java.net.HttpURLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONStringer;

import com.util.ConstDefine;

import android.util.Log;


public class ServerHttpRequest {

	private static final String TAG = "ServiceHttpRequest";
		
	private HttpClient httpClient;
	private HttpParams httpParams;
	
	/***
	 * 
	 */
	public ServerHttpRequest() {
		httpParams = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(httpParams, ConstDefine.HTTP_TIME_OUT);
		HttpConnectionParams.setSoTimeout(httpParams, ConstDefine.HTTP_TIME_OUT);
		HttpConnectionParams.setSocketBufferSize(httpParams,  ConstDefine.HTTP_BUFF_SIZE);
		HttpClientParams.setRedirecting(httpParams, true);
		httpClient = new DefaultHttpClient(httpParams);
	}
	
	 /**
	  * 
	  * @param url
	  * @return
	  * @throws Exception
	  */
	public String doGet(String url) throws Exception {
		return doGet(url, null);
	}

	/**
	 * 
	 * @param url
	 * @return
	 * @throws Exception
	 */
	public String doPost(String url) throws Exception {
		return doPost(url, null);
	}

    /**
    * 
    * @param strUrl
    * @param params
    * @return
    * @throws Exception
    */
	public String doGet(String strUrl, Map<String, String> params)
			throws Exception {
		
		// add QueryString
		String strParam = "";
		if (params != null){
			Iterator<Entry<String, String>> iter = params.entrySet().iterator();
			while (iter.hasNext()){
				Entry<String, String> entry = (Entry<String, String>) iter.next();
				strParam += "&" + entry.getKey() + "=" + URLEncoder.encode(entry.getValue(),  ConstDefine.HTTP_ENCODE);
			}
			
			if (strParam.length() > 0) strParam.replaceFirst("&", "?");
			strUrl += strParam;
		}
		
		// create HttpGet object
		HttpGet get = new HttpGet(strUrl);
		Log.v(TAG, "HttpGet URL" + strUrl);
		
		try{
			String strResp = "";
			
			// request
			HttpResponse resp = httpClient.execute(get);
			if (resp.getStatusLine().getStatusCode() == HttpURLConnection.HTTP_OK){
				strResp = EntityUtils.toString(resp.getEntity());
			}
			else{
				// 如果返回的StatusCode不是OK则抛异常
				throw new Exception("Error Response:"+ resp.getStatusLine().toString());
			}
			
			//return
			return strResp;
		} 
		finally {
			get.abort();
		}
	}

	/**
	 * 
	 * @param url
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public String doPost(String url, Map<String, String> params) throws Exception {
		
		// create post param
		List<NameValuePair> data = new ArrayList<NameValuePair>();
		if (params != null) {
			Iterator<Entry<String, String>> iter = params.entrySet().iterator();
			while (iter.hasNext()) {
				Entry<String, String> entry = (Entry<String, String>) iter.next();
				data.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
			}
		}
		
		HttpPost post = new HttpPost(url);
		try {
			
			// add param
			if (params != null){
				post.setEntity(new UrlEncodedFormEntity(data, HTTP.UTF_8));
			}
			
			Log.v(TAG, "doPost: " + url);
			Log.v(TAG, "HttpPost: " + post);
			Log.v(TAG, "data: " + data.toString());
			HttpResponse resp = httpClient.execute(post);
			String strResp = "";
			if (resp.getStatusLine().getStatusCode() == HttpURLConnection.HTTP_OK) {
				strResp = EntityUtils.toString(resp.getEntity());
				Log.v(TAG, "strResp: " + strResp);
			}
			else{
				// 如果返回的StatusCode不是OK则抛异常
				throw new Exception("Error Response:"
						+ resp.getStatusLine().toString());
			}
			
			return strResp;
		} 
		finally {
			post.abort();
		}
	}

	/**
	 * 
	 * @param url
	 * @param data
	 * @param contentType
	 * @return
	 * @throws Exception
	 */
	public String doPost(String url, String data, String contentType) throws Exception {
		
		HttpPost post = new HttpPost(url);
		
		try {
			//create entity
			StringEntity se = new StringEntity(data, HTTP.UTF_8);
			se.setContentType(contentType);
			post.setEntity(se);
			
			//request
			Log.v(TAG, "doPost: " + url);
			Log.v(TAG, "HttpPost: " + post);
			Log.v(TAG + "  data: ", data.toString());
			
			String strResp = "";
			HttpResponse resp = httpClient.execute(post);
			if (resp.getStatusLine().getStatusCode() == HttpURLConnection.HTTP_OK){
				strResp = EntityUtils.toString(resp.getEntity());
			}else{
				// 如果返回的StatusCode不是OK则抛异常
				throw new Exception("Error Response:" + resp.getStatusLine().toString());
			}
			
			//return
			return strResp;
			
		} finally {
			post.abort();
		}
	}

	
	/**
	 * 
	 * @param strUrl
	 * @param reqJson
	 * @return
	 */
	public String  dopost(String strUrl,JSONStringer reqJson)
	{
		String strResp = "";
		HttpPost request = new HttpPost(strUrl);
		request.setHeader("Accept", "application/json");
		request.setHeader("Content-type", "application/json");
		
		try {
			
			//create requestString
			StringEntity entity = new StringEntity(reqJson.toString());
			request.setEntity(entity);
			
			//acess wcf service
			DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpResponse response = httpClient.execute(request);
			if (response.getStatusLine().getStatusCode() == HttpURLConnection.HTTP_OK)
				strResp = EntityUtils.toString(response.getEntity());

			Log.d("WebInvoke", "Saving : "+ response.getStatusLine().getStatusCode());
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//retuun
		return strResp;
	}
}
