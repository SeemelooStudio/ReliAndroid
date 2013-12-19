package com.reqst;


import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.json.JSONException;
import org.json.JSONStringer;

import android.util.Base64;
import android.util.Log;

import com.model.ChatMessage;
import com.model.StationDetail;
import com.model.StationHistoryListItem;
import com.model.StationListItem;
import com.model.StationMainItem;
import com.model.HeatSourceDetail;
import com.model.HeatSourceTitle;
import com.model.KnowledgeBaseItem;
import com.model.SupplyAndBackwardItem;
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
    public static  List<SupplyAndBackwardItem> getStationSupplyAndReturnTemperatureList(String stationID, Date startDate, Date endDate) 
    {
    	
    	List<SupplyAndBackwardItem> dataList = new ArrayList<SupplyAndBackwardItem>();
    	
    	//TODO: get data from sever
    	long DAY = 1000 * 60 * 60 * 24;
    	long startTime = startDate.getTime();
    	long endTime = endDate.getTime();
    	for (;startTime < endTime ; startTime+=DAY) {
    		SupplyAndBackwardItem item = new SupplyAndBackwardItem();
    		item.setDate(new Date(startTime));
    		
    		DecimalFormat df = new DecimalFormat("#.00");
    		double supply = Double.parseDouble(df.format((new Random()).nextDouble() * 10 + 10));
    		double back = Double.parseDouble(df.format((new Random()).nextDouble() * 5 + 5));

    		item.setSupply(supply);
    		item.setBackward(back);
 
    		dataList.add(item);
    	}
		
    	return dataList;
    }
    public static  List<SupplyAndBackwardItem> getHeatSourceSupplyAndReturnPressureList(String heatSourceId, Date startDate, Date endDate) 
    {
    	
    	List<SupplyAndBackwardItem> dataList = new ArrayList<SupplyAndBackwardItem>();
    	
    	//TODO: get data from sever
    	long DAY = 1000 * 60 * 60 * 24;
    	long startTime = startDate.getTime();
    	long endTime = endDate.getTime();
    	
    	for (;startTime < endTime ; startTime+=DAY) {
    		SupplyAndBackwardItem item = new SupplyAndBackwardItem();
    		item.setDate(new Date(startTime));
    		
    		DecimalFormat df = new DecimalFormat("#.00");
    		double supply = Double.parseDouble(df.format((new Random()).nextDouble() * 10 + 50));
    		double back = Double.parseDouble(df.format((new Random()).nextDouble() * 10 + 4));

    		item.setSupply(supply);
    		item.setBackward(back);
 
    		dataList.add(item);
    	}
		
    	return dataList;
    }
    public static  List<SupplyAndBackwardItem> getHeatSourceSupplyAndBackwardTemperatureList(String heatSourceId, Date startDate, Date endDate) 
    {
    	
    	List<SupplyAndBackwardItem> dataList;
    	
    	//TODO: get data from sever
    	dataList = new ArrayList<SupplyAndBackwardItem>();
    	long DAY = 1000 * 60 * 60 * 24;
    	long startTime = startDate.getTime();
    	long endTime = endDate.getTime();
    	for (;startTime < endTime ; startTime+=DAY) {
    		SupplyAndBackwardItem item = new SupplyAndBackwardItem();
    		item.setDate(new Date(startTime));
    		
    		DecimalFormat df = new DecimalFormat("#.00");
    		double supply = Double.parseDouble(df.format((new Random()).nextDouble() * 10 + 10));
    		double back = Double.parseDouble(df.format((new Random()).nextDouble() * 5 + 5));

    		item.setSupply(supply);
    		item.setBackward(back);
 
    		dataList.add(item);
    	}
		
    	return dataList;
    }
    public static  List<SupplyAndBackwardItem> getStationSupplyAndBackwardPressureList(String stationID, Date startDate, Date endDate) 
    {
    	
    	List<SupplyAndBackwardItem> dataList = new ArrayList<SupplyAndBackwardItem>();
    	
    	//TODO: get data from sever
    	long DAY = 1000 * 60 * 60 * 24;
    	long startTime = startDate.getTime();
    	long endTime = endDate.getTime();
    	for (;startTime < endTime ; startTime+=DAY) {
    		SupplyAndBackwardItem item = new SupplyAndBackwardItem();
    		item.setDate(new Date(startTime));
    		
    		DecimalFormat df = new DecimalFormat("#.00");
    		double supply = Double.parseDouble(df.format((new Random()).nextDouble() * 10 + 50));
    		double back = Double.parseDouble(df.format((new Random()).nextDouble() * 10 + 4));

    		item.setSupply(supply);
    		item.setBackward(back);
 
    		dataList.add(item);
    	}
		
    	return dataList;
    }
    
    public static StationDetail getStationDetail(String stationID) {
    	StationDetail info;
    	
    	//TODO: get data from server
    	info = new StationDetail();
    	info.setStrStationId(stationID);
    	info.setStrStationName("日坛中学");
    	info.setSupplyTemperature(100.4);
    	info.setBackwardTemperature(41);
    	info.setSupplyPressure(0.65);
    	info.setBackwardPressure(0.38);
    	info.setTotalHeat(10844.3);
    	info.setTotalFlow(53624);
    	info.setRealtimeHeat(1.1);
    	info.setRealtimeFlow(0.07);
    	info.setSupplyWaterQuantity(0.8);
    	
    	return info;
    }
    
    public static ArrayList<StationHistoryListItem> getStationHistoryList(String heatSourceId, Date startDate, Date endDate) {
    	ArrayList<StationHistoryListItem> historyList;
    	
    	//TODO: get data from server
    	historyList = new ArrayList<StationHistoryListItem>();
    	long DAY = 1000 * 60 * 60 * 24;
    	long startTime = startDate.getTime();
    	long endTime = endDate.getTime();
    	for (;startTime < endTime ; startTime+=DAY) {
    		StationHistoryListItem item = new StationHistoryListItem();
    		item.setDate(new Date(startTime));
    		item.setActualGJ(80.0);
    		item.setCalculateGJ(70.0);
    		item.setPlanGJ(75.0);
    		item.setActualOverCalculateGJ(14.5);
    		item.setActualTemperature(5.0);
    		item.setForcastTemperature(3.0);
 
    		historyList.add(item);
    	}
    	
    	return historyList;
    }
    /**
     * 
     * @return
     */
	public static ArrayList<StationMainItem> getStationMainList() throws Exception{ 
		ServerHttpRequest httpReq = new ServerHttpRequest();
		String strRequestAddress = (ConstDefine.WEB_SERVICE_URL + ConstDefine.S_GET_STATIONS).replace("{UserName}", "zhaoyaqi");
		try {
			String strResp = httpReq.doGet(strRequestAddress);
			ArrayList<StationMainItem>  lstHotSrc = (ArrayList<StationMainItem>) JsonHelper.parseCollection(strResp, List.class, StationMainItem.class);	
			return  lstHotSrc;
		}
		catch (Exception ex){
			throw ex;
		}
	}
	
	public static ArrayList<StationListItem> getStationQueryList(StationListItem objSearchCon) throws Exception 
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
		
		ArrayList<StationListItem>  lstHotPosition = new ArrayList<StationListItem>();
		for(int i=0; i<15; i++)
        {
			StationListItem  item = new StationListItem();
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
	public static ArrayList<HeatSourceDetail> getHeatSourceMainList() throws Exception{
	
		ServerHttpRequest httpReq = new ServerHttpRequest();
		String strRequestAddress = (ConstDefine.WEB_SERVICE_URL + ConstDefine.S_GET_HEATSOURCES);
		try {
			String strResp = httpReq.doGet(strRequestAddress);
			ArrayList<HeatSourceDetail>  lstHotSrc = (ArrayList<HeatSourceDetail>) JsonHelper.parseCollection(strResp, List.class, HeatSourceDetail.class);	
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
	public static HeatSourceTitle getHeatSourceAllStatic() throws Exception{
		HeatSourceTitle hotStaticInfo = new HeatSourceTitle();
		try {
			
			ServerHttpRequest httpReq = new ServerHttpRequest();
			String strRequestAddress = ConstDefine.WEB_SERVICE_URL + ConstDefine.S_GET_HEATSOURCESUMMARY;
			String strResp = httpReq.doGet(strRequestAddress);
			hotStaticInfo = JsonHelper.parseObject(strResp, HeatSourceTitle.class);  
			
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
