package com.reqst;

import java.io.File;
import java.io.FileInputStream;
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
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
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
				// ���ص�StatusCode����OK�����쳣
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
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		boolean isImage = false;
		File file = null;
		if (params != null) {
			Iterator<Entry<String, String>> iter = params.entrySet().iterator();
			while (iter.hasNext()) {
				Entry<String, String> entry = (Entry<String, String>) iter.next();
				nameValuePairs.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
				if(entry.getKey() == "image") {
					isImage = true;
					file = new File(entry.getValue());
				}
			}
		}
		HttpPost post = new HttpPost(url);
		try {
			if(isImage) {
		    	FileInputStream fileInputStream = new FileInputStream(file);
		    	InputStreamEntity reqEntity = new InputStreamEntity(fileInputStream, file.length());
		    	reqEntity.setChunked(true);
		    	reqEntity.setContentType("binary/octet-stream");
		    	post.setEntity(reqEntity);
			}
			else {
				if (params != null){
					post.setEntity(new UrlEncodedFormEntity(nameValuePairs, HTTP.UTF_8));
				}
			}
			
			HttpResponse resp = httpClient.execute(post);
			String strResp = "";
			if (resp.getStatusLine().getStatusCode() == HttpURLConnection.HTTP_OK) {
				strResp = EntityUtils.toString(resp.getEntity());
				Log.v(TAG, "strResp: " + strResp);
			}
			else{
				throw new Exception("Error Response:" + resp.getStatusLine().toString());
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
	
	
	public String doPut(String url, String data, String contentType)
	{	
		HttpPut put = new HttpPut(url);
		
		try {
			//create entity
			StringEntity se = new StringEntity(data, HTTP.UTF_8);
			se.setContentType(contentType);
			put.setEntity(se);
			
			String strResp = "";
			HttpResponse resp = httpClient.execute(put);
			if (resp.getStatusLine().getStatusCode() == HttpURLConnection.HTTP_OK){
				strResp = EntityUtils.toString(resp.getEntity());
			}else{
				throw new Exception("Error Response:" + resp.getStatusLine().toString());
			}
			return strResp;
			
		}catch (Exception e)
		{
			e.printStackTrace();
		}finally {
			put.abort();
		}
		return "";
	}
}
