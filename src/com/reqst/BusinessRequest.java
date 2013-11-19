package com.reqst;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONStringer;

import android.util.Log;

import com.model.ListItem;
import com.model.UserInfo;
import com.model.WarnListItem;
import com.model.WeatherDetailItem;
import com.model.WeatherDetailTempInfo;
import com.util.JsonHelper;

public class BusinessRequest {

	
	/**
	 * get mainMenu by userinfo
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
			//ServerHttpRequest httpReq = new ServerHttpRequest();
			//String strResp = httpReq.dopost(ConstDefine.S_GET_USERINFO, jsonStrUser);
			//TODO
			String strResp = "{'strMemu':'1234567'}";  //test
			//change userInfo
			Log.v("strResp", strResp);
			
			respUser = JsonHelper.parseObject(strResp, UserInfo.class);  
			
		} catch (Exception ex) {
			throw ex;
		}
		
		//return
		return respUser.getStrMemu();
	}
	
	
	/**
	 * get Daily list
	 * @return
	 * @throws JSONException 
	 */
	public static ArrayList<ListItem> getDailyList(ListItem objSearchCon) throws JSONException 
	{
		JSONStringer jsonStrCon = JsonHelper.toJSONString(objSearchCon);
		
		Log.v("jsonStrCondition", jsonStrCon.toString());
		//ServerHttpRequest httpReq = new ServerHttpRequest();
		//String strResp = httpReq.dopost(ConstDefine.S_GET_USERINFO, jsonStrCon);
		//ArrayList<ListItem>  lstDaily = (ArrayList<ListItem>) JsonHelper.parseCollection(strResp, List.class, ListItem.class);	
		//TODO
		
		ArrayList<ListItem>  lstDaily = new ArrayList<ListItem>();
		for(int i=0; i<10; i++)
        {
        	ListItem  item = new ListItem();
        	item.setList_id("" +i);
        	item.setList_name("name" +i);
        	item.setList_other("other" +i);
        	lstDaily.add(item);
        }

	   return  lstDaily;
	}
	
	/**
	 * get warn list
	 * @return
	 * @throws JSONException 
	 */
	public static ArrayList<WarnListItem> getWarnList(WarnListItem objSearchCon) throws JSONException 
	{
		JSONStringer jsonStrCon = JsonHelper.toJSONString(objSearchCon);
		Log.v("jsonStrCondition", jsonStrCon.toString());
		
		//ServerHttpRequest httpReq = new ServerHttpRequest();
		//String strResp = httpReq.dopost(ConstDefine.S_GET_USERINFO, jsonStrCon);
		//ArrayList<WarnListItem>  lstWarn = (ArrayList<WarnListItem>) JsonHelper.parseCollection(strResp, List.class, WarnListItem.class);	
		//TODO
		
		ArrayList<WarnListItem>  lstWarn = new ArrayList<WarnListItem>();
        for(int i=0; i<13; i++)
        {
        	WarnListItem  lst = new WarnListItem();
        	lst.setWarn_id("" +i);
        	lst.setWarn_title("左家庄热力站连续三日超标" +i);
        	lst.setWarn_content("左家庄热力站连续三日超标%10，左家庄热力站连续三日超标%20" +i);
          	lst.setWarn_date("13:30");
        	lst.setWarn_other("image");
        	lstWarn.add(lst);
        }

	   return  lstWarn;
	}
	
	
	/**
	 * get warn detail
	 * @param user
	 * @return
	 * @throws Exception 
	 */
	public static WarnListItem getWarnDetailById(String strWarnId) throws Exception{
		
		//create parame
		Map<String, String> mapParam = new HashMap();
		mapParam.put("warn_id", strWarnId);
		WarnListItem respWarnInfo = new WarnListItem();
		
		try {
			
			ServerHttpRequest httpReq = new ServerHttpRequest();
			//String strResp = httpReq.doGet(ConstDefine.S_GET_USERINFO, mapParam);
			//TODO
			String strResp = "{'warn_content':'1234567555555555'}";  //test
			respWarnInfo = JsonHelper.parseObject(strResp, WarnListItem.class);  
			
		} catch (Exception ex) {
			throw ex;
		}
		
		//return
		return respWarnInfo;
	}
	
	
	/**
	 * 
	 * @param objSearchCon
	 * @return
	 * @throws JSONException
	 */
	public static ArrayList<ListItem> getWeatherList(ListItem objSearchCon) throws JSONException 
	{
		JSONStringer jsonStrCon = JsonHelper.toJSONString(objSearchCon);
		
		Log.v("jsonStrCondition", jsonStrCon.toString());
		//ServerHttpRequest httpReq = new ServerHttpRequest();
		//String strResp = httpReq.dopost(ConstDefine.S_GET_USERINFO, jsonStrCon);
		//ArrayList<ListItem>  lstWeather = (ArrayList<ListItem>) JsonHelper.parseCollection(strResp, List.class, ListItem.class);	
		//TODO
		
		ArrayList<ListItem>  lstWeather = new ArrayList<ListItem>();
		for(int i=0; i<10; i++)
        {
			ListItem  item = new ListItem();
			item.setList_id(""+i);
			item.setList_name("双榆树气象站" +i);
			item.setList_other("18/26" +i);
			lstWeather.add(item);
        }

	   return  lstWeather;
	}
	
	
	
	/**
	 * get warn detail
	 * @param user
	 * @return
	 * @throws Exception 
	 */
	public static WeatherDetailTempInfo getWenduTabDetailById(String strListId,String strDayFlag) throws Exception{
		
		//create parame
		Map<String, String> mapParam = new HashMap();
		mapParam.put("list_id", strListId);
		mapParam.put("day_id", strDayFlag);
		WeatherDetailTempInfo respInfo = new WeatherDetailTempInfo();
		
		try {
			
			ServerHttpRequest httpReq = new ServerHttpRequest();
			//String strResp = httpReq.doGet(ConstDefine.S_GET_USERINFO, mapParam);
			//TODO
			String strResp = "{'strTemp':'80'}";  //test
			respInfo = JsonHelper.parseObject(strResp, WeatherDetailTempInfo.class);  
			
		} catch (Exception ex) {
			throw ex;
		}
		
		//return
		return respInfo;
	}
	
	
	/**
	 * 
	 * @param objSearchCon
	 * @return
	 * @throws JSONException
	 */
	public static ArrayList<WeatherDetailItem> getWenduTabDetailListById(WeatherDetailTempInfo objSearchCon) throws JSONException 
	{
		JSONStringer jsonStrCon = JsonHelper.toJSONString(objSearchCon);
		
		Log.v("jsonStrCondition", jsonStrCon.toString());
		//ServerHttpRequest httpReq = new ServerHttpRequest();
		//String strResp = httpReq.dopost(ConstDefine.S_GET_USERINFO, jsonStrCon);
		//ArrayList<WeatherDetailItem>  lstWeather = (ArrayList<WeatherDetailItem>) JsonHelper.parseCollection(strResp, List.class, WeatherDetailItem.class);	
		//TODO
		
		ArrayList<WeatherDetailItem>  lstWendu = new ArrayList<WeatherDetailItem>();
	  	 for(int i=0; i<14; i++)
        {
        	WeatherDetailItem  wendu= new WeatherDetailItem();
        	wendu.setW_time("4" + i + ":PM");
        	wendu.setW_wendu("2" +i);
        	wendu.setW_tianqi("小雨");
        	lstWendu.add(wendu);
        }

	   return  lstWendu;
	}
	
	
	
	
	
	
}
