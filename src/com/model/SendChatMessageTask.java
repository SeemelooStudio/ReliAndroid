package com.model;

import com.reqst.BusinessRequest;
import com.reqst.ServerHttpRequest;
import com.util.ConstDefine;
import com.util.JsonHelper;

import android.os.AsyncTask;
import android.util.Log;

public class SendChatMessageTask extends AsyncTask<String, Void, Boolean> {

	private ChatMessage message;
	
	public SendChatMessageTask(ChatMessage message){
		this.message = message;
	}
	
	@Override
	protected Boolean doInBackground(String... urls) {
		try {
			ServerHttpRequest httpReq = new ServerHttpRequest();
			String url = urls[0];
			String requestData = JsonHelper.toJSON(message);
			String response = httpReq.doPut(url, requestData, "application/json");
			Log.v("message", response);
			return true;
		}
		catch(Exception ex) {
			Log.v("sendMessageFail", ex.getMessage());
		}
		return false;
	}

}
