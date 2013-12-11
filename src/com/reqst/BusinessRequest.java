package com.reqst;

import java.net.Authenticator;
import java.net.HttpURLConnection;
import java.net.PasswordAuthentication;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONStringer;

import android.util.Base64;
import android.util.Log;

import com.model.ChatMessage;
import com.model.HotPosListItem;
import com.model.HotPosMainItem;
import com.model.HotSrcMainItem;
import com.model.HotSrcTitleInfo;
import com.model.KnowledgeBaseItem;
import com.model.WeatherStationListItem;
import com.model.MainPageSummary;
import com.model.SendChatMessageTask;
import com.model.UserInfo;
import com.model.WarnListItem;
import com.model.WeatherDetailItem;
import com.model.WeatherDetailTempInfo;
import com.model.WeatherType;
import com.util.ConstDefine;
import com.model.WeatherPreChartItem;
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
			ServerHttpRequest httpReq = new ServerHttpRequest();
			String strRequestAddress = ConstDefine.WEB_SERVICE_URL + ConstDefine.S_GET_USERINFO.replace("{UserName}", user.getUserName());
			String strResp = httpReq.doGet(strRequestAddress);
			Log.v("strResp", strResp);
			
			respUser = JsonHelper.parseObject(strResp, UserInfo.class);  
			return respUser.getMenu();
			
		} catch (Exception ex) {
			return "1234567";
			
		}
		
		
	}
	
	
	/**
	 * get Daily list
	 * @return
	 * @throws JSONException 
	 */
	public static ArrayList<WeatherStationListItem> getDailyList(WeatherStationListItem objSearchCon) throws Exception 
	{
		JSONStringer jsonStrCon = JsonHelper.toJSONString(objSearchCon);
		
		Log.v("jsonStrCondition", jsonStrCon.toString());
		
		
		ServerHttpRequest httpReq = new ServerHttpRequest();
		String strRequestAddress = ConstDefine.WEB_SERVICE_URL + ConstDefine.S_GET_DAILYREPORTS;
		try {
			String strResp = httpReq.doGet(strRequestAddress);
			ArrayList<WeatherStationListItem>  lstDaily = (ArrayList<WeatherStationListItem>) JsonHelper.parseCollection(strResp, List.class, WeatherStationListItem.class);	
			return  lstDaily;
		}
		catch(Exception ex) {
			throw ex;
		}

	   
	}
	
	/**
	 * get warn list
	 * @return
	 * @throws JSONException 
	 */
	public static ArrayList<WarnListItem> getWarnList(WarnListItem objSearchCon) throws Exception 
	{
		JSONStringer jsonStrCon = JsonHelper.toJSONString(objSearchCon);
		Log.v("jsonStrCondition", jsonStrCon.toString());
		
		ServerHttpRequest httpReq = new ServerHttpRequest();
		String strRequestAddress = ConstDefine.WEB_SERVICE_URL + ConstDefine.S_GET_WARNINGS;
		try {
			String strResp = httpReq.doGet(strRequestAddress);
			ArrayList<WarnListItem>  lstWarn = (ArrayList<WarnListItem>) JsonHelper.parseCollection(strResp, List.class, WarnListItem.class);	
			return  lstWarn;
		}catch(Exception ex){
			throw ex;
		}
	   
	}
	
	
	/**
	 * get warn detail
	 * @param user
	 * @return
	 * @throws Exception 
	 */
	public static WarnListItem getWarnDetailById(String strWarnId) throws Exception{
		
		//create parame
		@SuppressWarnings({ "unchecked", "rawtypes" })
		Map<String, String> mapParam = new HashMap();
		mapParam.put("warn_id", strWarnId);
		WarnListItem respWarnInfo = new WarnListItem();
		
		try {
			
			@SuppressWarnings("unused")
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
	 * @param null
	 * @return
	 * @throws JSONException
	 */
	public static ArrayList<WeatherStationListItem> getWeatherList() throws Exception 
	{
		
		ServerHttpRequest httpReq = new ServerHttpRequest();
		String strRequestAddress = ConstDefine.WEB_SERVICE_URL + ConstDefine.S_GET_WEATHERSTATIONS;
		
		try {
			String strResp = httpReq.doGet(strRequestAddress);
			ArrayList<WeatherStationListItem>  lstWeather = (ArrayList<WeatherStationListItem>) JsonHelper.parseCollection(strResp, List.class, WeatherStationListItem.class);	
			return  lstWeather;
		}
		catch (Exception ex) {
			throw ex;
		}
	}
	
	
	
	/**
	 * get warn detail
	 * @param user
	 * @return
	 * @throws Exception 
	 */
	public static WeatherDetailTempInfo getWenduTabDetailById(String strListId,String strDayFlag) throws Exception{
		
		
		String requestAddress = (ConstDefine.WEB_SERVICE_URL + ConstDefine.S_GET_OFFICIALWEATHER).replace("{weatherTypeId}", WeatherType.Today.getStrValue());
		try {
			ServerHttpRequest httpReq = new ServerHttpRequest();
			String strResp = httpReq.doGet(requestAddress);
			WeatherDetailTempInfo respInfo = JsonHelper.parseObject(strResp, WeatherDetailTempInfo.class);  
			return respInfo;
		} catch (Exception ex) {
			throw ex;
		}
		
	}
	
	
	/**
	 * 
	 * @param objSearchCon
	 * @return
	 * @throws JSONException
	 */
	public static ArrayList<WeatherDetailItem> getWenduTabDetailListById(WeatherDetailTempInfo objSearchCon) throws Exception 
	{
		JSONStringer jsonStrCon = JsonHelper.toJSONString(objSearchCon);
		
		Log.v("jsonStrCondition", jsonStrCon.toString());
		
		ServerHttpRequest httpReq = new ServerHttpRequest();
		String strRequestAddress = (ConstDefine.WEB_SERVICE_URL + ConstDefine.S_GET_OFFICIALWEATHERDETAILS).replace("{fromDate}", "2013-11-23").replace("{toDate}", "2013-11-28");
		try {
			String strResp = httpReq.doGet(strRequestAddress);
			ArrayList<WeatherDetailItem>  lstWeather = (ArrayList<WeatherDetailItem>) JsonHelper.parseCollection(strResp, List.class, WeatherDetailItem.class);	
			return  lstWeather;
		}
		catch (Exception ex){
			throw ex;
		}
	   
	}
	
	
    /**
     * 
     * @return
     */
    public static ArrayList<WeatherDetailItem> getWeatherHisListData() throws Exception  {  
       
		ServerHttpRequest httpReq = new ServerHttpRequest();
		String strRequestAddress = (ConstDefine.WEB_SERVICE_URL + ConstDefine.S_GET_OFFICIALWEATHERDETAILS).replace("{fromDate}", "2013-11-23").replace("{toDate}", "2013-11-28");
		try {
			String strResp = httpReq.doGet(strRequestAddress);
			ArrayList<WeatherDetailItem>  lstWeather = (ArrayList<WeatherDetailItem>) JsonHelper.parseCollection(strResp, List.class, WeatherDetailItem.class);	
			return  lstWeather;
		}
		catch (Exception ex){
			throw ex;
		}
    } 
	
	
	/**
	 * 
	 */
    public static  List<WeatherPreChartItem> getWeatherChartList() 
    {
    	
    	List<WeatherPreChartItem> chartList = new ArrayList<WeatherPreChartItem>();
    	
		WeatherPreChartItem  item1 = new WeatherPreChartItem();
		item1.setStrDate("2013-07-21");
		item1.setStrHighTmpture("100");
		item1.setStrShorttemTure("80");
		WeatherPreChartItem  item2 = new WeatherPreChartItem();
		item2.setStrDate("2013-07-22");
		item2.setStrHighTmpture("50");
		item2.setStrShorttemTure("40");
		WeatherPreChartItem  item3 = new WeatherPreChartItem();
		item3.setStrDate("2013-07-23");
		item3.setStrHighTmpture("60");
		item3.setStrShorttemTure("30");
		WeatherPreChartItem  item4 = new WeatherPreChartItem();
		item4.setStrDate("2013-07-24");
		item4.setStrHighTmpture("120");
		item4.setStrShorttemTure("80");
		WeatherPreChartItem  item5 = new WeatherPreChartItem();
		item5.setStrDate("2013-07-25");
		item5.setStrHighTmpture("30");
		item5.setStrShorttemTure("20");
		WeatherPreChartItem  item6 = new WeatherPreChartItem();
		item6.setStrDate("2013-07-26");
		item6.setStrHighTmpture("90");
		item6.setStrShorttemTure("50");
		WeatherPreChartItem  item7 = new WeatherPreChartItem();
		item7.setStrDate("2013-07-27");
		item7.setStrHighTmpture("80");
		item7.setStrShorttemTure("20");
		
		chartList.add(item1);
		chartList.add(item2);
		chartList.add(item3);
		chartList.add(item4);
		chartList.add(item5);
		chartList.add(item6);
		chartList.add(item7);
		
    	return chartList;
    }
	

    /**
     * 
     * @return
     */
	public static ArrayList<HotPosMainItem> getHotPositionMainList() throws Exception{ 
		ServerHttpRequest httpReq = new ServerHttpRequest();
		String strRequestAddress = (ConstDefine.WEB_SERVICE_URL + ConstDefine.S_GET_STATIONS).replace("{UserName}", "zhaoyaqi");
		try {
			String strResp = httpReq.doGet(strRequestAddress);
			ArrayList<HotPosMainItem>  lstHotSrc = (ArrayList<HotPosMainItem>) JsonHelper.parseCollection(strResp, List.class, HotPosMainItem.class);	
			return  lstHotSrc;
		}
		catch (Exception ex){
			throw ex;
		}
	}
	
	public static ArrayList<HotPosListItem> getHotPositonQueryList(HotPosListItem objSearchCon) throws Exception 
	{
		/*
		JSONStringer jsonStrCon = JsonHelper.toJSONString(objSearchCon);
		Log.v("jsonStrCondition", jsonStrCon.toString());
		ServerHttpRequest httpReq = new ServerHttpRequest();
		String strRequestAddress = ConstDefine.WEB_SERVICE_URL + ConstDefine.S_GET_WEATHERSTATIONS;
		try {
			String strResp = httpReq.doGet(strRequestAddress);
			ArrayList<HotPosListItem>  lstHotPosition = (ArrayList<HotPosListItem>) JsonHelper.parseCollection(strResp, List.class, HotPosListItem.class);	
			return  lstHotPosition;
		}
		catch (Exception ex) {
			throw ex;
		}*/
		
		ArrayList<HotPosListItem>  lstHotPosition = new ArrayList<HotPosListItem>();
		for(int i=0; i<15; i++)
        {
			HotPosListItem  item = new HotPosListItem();
			item.setStrStationId("" +i);
			item.setStrStationName("热力站"+i);
			item.setStrWarnColor("#8888CF" +i);
			item.setStrAddress("世缘" +i);
			item.setStrDirect("东部" +i);
			lstHotPosition.add(item);
        }
		
		return lstHotPosition;
	}
	
	/**
	 * get hot source main List
	 * @return
	 */
	public static ArrayList<HotSrcMainItem> getHotSourceMainList() throws Exception{
	
		ServerHttpRequest httpReq = new ServerHttpRequest();
		String strRequestAddress = (ConstDefine.WEB_SERVICE_URL + ConstDefine.S_GET_HEATSOURCES);
		try {
			String strResp = httpReq.doGet(strRequestAddress);
			ArrayList<HotSrcMainItem>  lstHotSrc = (ArrayList<HotSrcMainItem>) JsonHelper.parseCollection(strResp, List.class, HotSrcMainItem.class);	
			return  lstHotSrc;
		}
		catch (Exception ex){
			throw ex;
		}	
	}
	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public static HotSrcTitleInfo getHotSourceAllStatic() throws Exception{
		HotSrcTitleInfo hotStaticInfo = new HotSrcTitleInfo();
		try {
			
			ServerHttpRequest httpReq = new ServerHttpRequest();
			String strRequestAddress = ConstDefine.WEB_SERVICE_URL + ConstDefine.S_GET_HEATSOURCESUMMARY;
			String strResp = httpReq.doGet(strRequestAddress);
			hotStaticInfo = JsonHelper.parseObject(strResp, HotSrcTitleInfo.class);  
			
		} catch (Exception ex) {
			throw ex;
		}
		
		
		//return
		return hotStaticInfo;
	}
	
	
	/**
	 * get Daily list
	 * @return
	 * @throws JSONException 
	 */
	public static ArrayList<KnowledgeBaseItem> getKnowledgeBaseList(KnowledgeBaseItem objSearchCon) throws JSONException 
	{
		JSONStringer jsonStrCon = JsonHelper.toJSONString(objSearchCon);
		
		Log.v("jsonStrCondition", jsonStrCon.toString());
		//ServerHttpRequest httpReq = new ServerHttpRequest();
		//String strResp = httpReq.dopost(ConstDefine.S_GET_USERINFO, jsonStrCon);
		//ArrayList<ListItem>  lstDaily = (ArrayList<ListItem>) JsonHelper.parseCollection(strResp, List.class, ListItem.class);	
		//TODO
		
		ArrayList<KnowledgeBaseItem>  lstDaily = new ArrayList<KnowledgeBaseItem>();
		for(int i=0; i<9; i++)
        {
			KnowledgeBaseItem  item = new KnowledgeBaseItem();
        	item.setStrDocId("" +i);
        	item.setStrDocName("文档资料" +i);
        	item.setStrDocUpTime("2013-01-2" +i);
        	lstDaily.add(item);
        }

	   return  lstDaily;
	}
	
	public static MainPageSummary getMainPageSummary() throws Exception
	{
		MainPageSummary mainPageSummary = new MainPageSummary();
		try {	
			ServerHttpRequest httpReq = new ServerHttpRequest();
			String strRequestAddress = ConstDefine.WEB_SERVICE_URL + ConstDefine.S_GET_SUMMARY;
			String strResp = httpReq.doGet(strRequestAddress);
			mainPageSummary = JsonHelper.parseObject(strResp, MainPageSummary.class);  
		} catch (Exception ex) {
			throw ex;
		}
		
		return mainPageSummary;
	}
	
	public static ArrayList<ChatMessage> getMessages(String strUserName) throws Exception
	{
		ServerHttpRequest httpReq = new ServerHttpRequest();
		String strRequestAddress = (ConstDefine.WEB_SERVICE_URL + ConstDefine.S_GET_MESSAGES).replace("{UserName}", strUserName);
		try {
			String strResp = httpReq.doGet(strRequestAddress);
			ArrayList<ChatMessage>  lstMessages = (ArrayList<ChatMessage>) JsonHelper.parseCollection(strResp, List.class, ChatMessage.class);	
			return  lstMessages;
		}
		catch (Exception ex){
			throw ex;
		}
	}
	
	public static void Authentication(UserInfo userInfo) {
		ServerHttpRequest httpReq = new ServerHttpRequest();
		String url = ConstDefine.WEB_SERVICE_URL + ConstDefine.S_AUTHENTICATION;
		Map<String, String> requestData = new HashMap<String, String>();
		requestData.put("strUserName", userInfo.getUserName());
		requestData.put("strEncodedPassword", Base64.encodeToString(userInfo.getUserPwd().getBytes(), Base64.NO_WRAP));
		try {
			String response = httpReq.doPost(url, requestData);
			Log.v("autentication", response);
		}
		catch(Exception ex) {
			
		}
	}
	
	public static void SendMessage(ChatMessage chatMessage){
		String url = (ConstDefine.WEB_SERVICE_URL + ConstDefine.S_PUT_MESSAGE).replace("{UserName}", "zhaoyaqi");
		new SendChatMessageTask(chatMessage).execute(url);
	}
}
