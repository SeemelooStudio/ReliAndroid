package com.model;

import java.util.HashMap;
import java.util.Map;

import com.reqst.BusinessRequest;
import com.reqst.ServerHttpRequest;
import com.util.ConstDefine;
import com.util.JsonHelper;

import android.os.AsyncTask;
import android.util.Log;

public class SendChatMessageTask extends AsyncTask<String, Void, Boolean> {

	private ChatMessage message;
	private String httpMethodType;
	
	public SendChatMessageTask(ChatMessage message, String httpMethodType){
		this.message = message;
		this.httpMethodType = httpMethodType;
	}
	
	@Override
	protected Boolean doInBackground(String... urls) {
		try {
			ServerHttpRequest httpReq = new ServerHttpRequest();
			String url = urls[0];
			
			if(httpMethodType == "put"){
				String requestData = JsonHelper.toJSON(message);
				String response = httpReq.doPut(url, requestData, "application/json");
				return true;
			}
			else {
				Map<String, String> requestData = new HashMap<String, String>();
				requestData.put("image", message.getImageUri());
				String response = httpReq.doPost(url, requestData);
				int result = Integer.parseInt(response);
				if(result > 0) {
					httpReq = new ServerHttpRequest();
					httpReq.doPut(url+"/"+response, JsonHelper.toJSON(message), "application/json");
					return true;
				}
				else {
					return false;
				}
			}
		}
		catch(Exception ex) {
			Log.v("sendMessageFail", ex.getMessage());
		}
		return false;
	}

}
