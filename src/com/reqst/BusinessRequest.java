package com.reqst;

import org.json.JSONStringer;

import android.util.Log;

import com.model.UserInfo;
import com.util.ConstDefine;
import com.util.JsonHelper;

public class BusinessRequest {

	
	/**
	 * 
	 * @param user
	 * @return
	 * @throws Exception 
	 */
	public static String getMainMenuByLoginUser(UserInfo user) throws Exception{
		
		//change jsonstring
		JSONStringer jsonStrUser = JsonHelper.toJSONString(user);
		UserInfo respUser = new UserInfo();
		
		try {
			
			Log.v("jsonStrUser", jsonStrUser.toString());
			ServerHttpRequest httpReq = new ServerHttpRequest();
			//String strResp = httpReq.dopost(ConstDefine.S_GET_USERINFO, jsonStrUser);
			//TODO
			String strResp = "{'strMemu':'123'}";  //test
			//change userInfo
			Log.v("strResp", strResp);
			
			respUser = JsonHelper.parseObject(strResp, UserInfo.class);  
			
		} catch (Exception ex) {
			throw ex;
		}
		
		//return
		return respUser.getStrMemu();
	}
}
