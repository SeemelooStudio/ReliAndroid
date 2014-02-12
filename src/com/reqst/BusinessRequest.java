package com.reqst;


import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
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
import com.model.HeatSourceDetail;
import com.model.StationDetail;
import com.model.StationHistoryListItem;
import com.model.StationListItem;
import com.model.StationMainItem;
import com.model.HeatSourceMainItem;
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
	public static WeatherDetailTempInfo[] getWenduTabDetailById(String weatherTypeId) throws Exception{
		
		
		String requestAddress = (ConstDefine.WEB_SERVICE_URL + ConstDefine.S_GET_OFFICIALWEATHER).replace("{weatherTypeId}", weatherTypeId);
		try {
			ServerHttpRequest httpReq = new ServerHttpRequest();
			String strResp = httpReq.doGet(requestAddress);
			WeatherDetailTempInfo[] respInfo = JsonHelper.parseArray(strResp, WeatherDetailTempInfo.class);  
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
	
    public static ArrayList<WeatherDetailItem> getWeatherHisListData(Date fromDate, Date toDate) throws Exception  {  
       
		ServerHttpRequest httpReq = new ServerHttpRequest();
		String strRequestAddress = (ConstDefine.WEB_SERVICE_URL + ConstDefine.S_GET_OFFICIALWEATHERDETAILS)
				.replace("{fromDate}", new SimpleDateFormat("yyyy-MM-dd").format(fromDate))
				.replace("{toDate}", new SimpleDateFormat("yyyy-MM-dd").format(toDate));
		try {
			String strResp = httpReq.doGet(strRequestAddress);
			ArrayList<WeatherDetailItem>  lstWeather = (ArrayList<WeatherDetailItem>) JsonHelper.parseCollection(strResp, List.class, WeatherDetailItem.class);	
			return  lstWeather;
		}
		catch (Exception ex){
			throw ex;
		}
    } 
	
    public static  List<WeatherPreChartItem> getWeatherChartList() throws Exception 
    {
    	WeatherDetailTempInfo[] sevenDays = getWenduTabDetailById(WeatherType.SevenDays.getStrValue());
    	List<WeatherPreChartItem> chartList = new ArrayList<WeatherPreChartItem>();
    	
    	for(WeatherDetailTempInfo day : sevenDays) {
    		WeatherPreChartItem  item = new WeatherPreChartItem();
    		item.setStrDate( new SimpleDateFormat("yyyy-MM-dd").format(day.getDay()) );
    		item.setStrHighTmpture(  day.getForecastHighest() + "");
    		item.setStrShorttemTure( day.getForecastLowest() + "");
    		chartList.add(item);
    	}
		
    	return chartList;
    }
    public static  List<SupplyAndBackwardItem> getStationSupplyAndReturnTemperatureList(String stationId, Date startDate, Date endDate) throws Exception
    {
    	List<SupplyAndBackwardItem> dataList = new ArrayList<SupplyAndBackwardItem>();
    	
    	String fromDate = new SimpleDateFormat("yyyy-MM-dd%20HH:mm:ss").format(startDate);
    	String toDate = new SimpleDateFormat("yyyy-MM-dd%20HH:mm:ss").format(endDate);
    	
    	
    	ServerHttpRequest httpReq = new ServerHttpRequest();
		String strRequestAddress = (ConstDefine.WEB_SERVICE_URL + ConstDefine.S_GET_STATION_TEMPERATURE_HISTORY)
				.replace("{fromDate}", fromDate)
				.replace("{toDate}", toDate)
				.replace("{stationId}", stationId);
		try {
			String strResp = httpReq.doGet(strRequestAddress);
			dataList = (ArrayList<SupplyAndBackwardItem>) JsonHelper.parseCollection(strResp, List.class, SupplyAndBackwardItem.class);	
			return dataList;
		}
		catch (Exception ex) {
			throw ex;
		}
		/*
		//TODO: get data from sever
    	long DAY = 1000 * 60 * 60 * 24;
    	long startTime = startDate.getTime();
    	long endTime = endDate.getTime();
    	
    	for (;startTime < endTime ; startTime+=DAY) {
    		SupplyAndBackwardItem item = new SupplyAndBackwardItem();
    		item.setTime(new Date(startTime));
    		
    		DecimalFormat df = new DecimalFormat("#.00");
    		float supply = Float.parseFloat(df.format((new Random()).nextDouble() * 30 + 80));
    		float back = Float.parseFloat(df.format((new Random()).nextDouble() * 15 + 30));

    		item.setSupply(supply);
    		item.setBackward(back);
 
    		dataList.add(item);
    	}
		
    	return dataList;*/
    }
    public static  List<SupplyAndBackwardItem> getHeatSourceSupplyAndReturnPressureList(String heatSourceId,String unitId, Date startDate, Date endDate) throws Exception
    {
    	List<SupplyAndBackwardItem> dataList = new ArrayList<SupplyAndBackwardItem>();
    	String fromDate = new SimpleDateFormat("yyyy-MM-dd%20HH:mm:ss").format(startDate);
    	String toDate = new SimpleDateFormat("yyyy-MM-dd%20HH:mm:ss").format(endDate);
    	
    	
    	ServerHttpRequest httpReq = new ServerHttpRequest();
		String strRequestAddress = (ConstDefine.WEB_SERVICE_URL + ConstDefine.S_GET_HEATSOURCE_RECENT_PRESSURE_HISTORY)
				.replace("{fromDate}", fromDate)
				.replace("{toDate}", toDate)
				.replace("{heatSourceId}", heatSourceId)
				.replace("{heatSourceRecentId}", unitId);
		try {
			String strResp = httpReq.doGet(strRequestAddress);
			dataList = (ArrayList<SupplyAndBackwardItem>) JsonHelper.parseCollection(strResp, List.class, SupplyAndBackwardItem.class);	
			return dataList;
		}
		catch (Exception ex) {
			throw ex;
		}
    }
    public static  List<SupplyAndBackwardItem> getHeatSourceSupplyAndBackwardTemperatureList(String heatSourceId,String unitId, Date startDate, Date endDate) throws Exception
    {
    	
    	List<SupplyAndBackwardItem> dataList = new ArrayList<SupplyAndBackwardItem>();
    	String fromDate = new SimpleDateFormat("yyyy-MM-dd%20HH:mm:ss").format(startDate);
    	String toDate = new SimpleDateFormat("yyyy-MM-dd%20HH:mm:ss").format(endDate);
    	
    	
    	ServerHttpRequest httpReq = new ServerHttpRequest();
		String strRequestAddress = (ConstDefine.WEB_SERVICE_URL + ConstDefine.S_GET_HEATSOURCE_RECENT_TEMPERATURE_HISTORY)
				.replace("{fromDate}", fromDate)
				.replace("{toDate}", toDate)
				.replace("{heatSourceId}", heatSourceId)
				.replace("{heatSourceRecentId}", unitId);
		try {
			String strResp = httpReq.doGet(strRequestAddress);
			dataList = (ArrayList<SupplyAndBackwardItem>) JsonHelper.parseCollection(strResp, List.class, SupplyAndBackwardItem.class);	
			return dataList;
		}
		catch (Exception ex) {
			throw ex;
		}
		/*
		//TODO: get data from sever
    	long DAY = 1000 * 60 * 60 * 24;
    	long startTime = startDate.getTime();
    	long endTime = endDate.getTime();
    	
    	for (;startTime < endTime ; startTime+=DAY) {
    		SupplyAndBackwardItem item = new SupplyAndBackwardItem();
    		item.setTime(new Date(startTime));
    		
    		DecimalFormat df = new DecimalFormat("#.00");
    		float supply = Float.parseFloat(df.format((new Random()).nextDouble() * 30 + 80));
    		float back = Float.parseFloat(df.format((new Random()).nextDouble() * 15 + 30));

    		item.setSupply(supply);
    		item.setBackward(back);
 
    		dataList.add(item);
    	}
		
    	return dataList;
    	*/
    }
    public static  List<SupplyAndBackwardItem> getStationSupplyAndBackwardPressureList(String stationId, Date startDate, Date endDate)  throws Exception
    {
    	List<SupplyAndBackwardItem> dataList = new ArrayList<SupplyAndBackwardItem>();
    	String fromDate = new SimpleDateFormat("yyyy-MM-dd%20HH:mm:ss").format(startDate);
    	String toDate = new SimpleDateFormat("yyyy-MM-dd%20HH:mm:ss").format(endDate);
    	
    	
    	ServerHttpRequest httpReq = new ServerHttpRequest();
		String strRequestAddress = (ConstDefine.WEB_SERVICE_URL + ConstDefine.S_GET_STATION_PRESSURE_HISTORY)
				.replace("{fromDate}", fromDate)
				.replace("{toDate}", toDate)
				.replace("{stationId}", stationId);
		try {
			String strResp = httpReq.doGet(strRequestAddress);
			dataList = (ArrayList<SupplyAndBackwardItem>) JsonHelper.parseCollection(strResp, List.class, SupplyAndBackwardItem.class);	
			return dataList;
		}
		catch (Exception ex) {
			throw ex;
		}
    	//TODO: get data from sever
    	/*long DAY = 1000 * 60 * 60 * 24;
    	long startTime = startDate.getTime();
    	long endTime = endDate.getTime();
    	for (;startTime < endTime ; startTime+=DAY) {
    		SupplyAndBackwardItem item = new SupplyAndBackwardItem();
    		item.setDate(new Date(startTime));
    		
    		DecimalFormat df = new DecimalFormat("#.00");
    		double supply = Double.parseDouble(df.format((new Random()).nextDouble() + 0.5));
    		double back = Double.parseDouble(df.format((new Random()).nextDouble() * 0.5));

    		item.setSupply(supply);
    		item.setBackward(back);
 
    		dataList.add(item);
    	}
		
    	return dataList;*/
    }
    
    public static StationDetail getStationDetail(int stationId) throws Exception{
    	
    	String requestAddress = (ConstDefine.WEB_SERVICE_URL + ConstDefine.S_GET_STATION).replace("{UserName}", "zhaoyaqi").replace("{StationId}", stationId+"");
    	
		try {
			ServerHttpRequest httpReq = new ServerHttpRequest();
			String strResp = httpReq.doGet(requestAddress);
			StationDetail info = JsonHelper.parseObject(strResp, StationDetail.class);
			return info;
		} catch (Exception ex) {
			throw ex;
		}
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
    		
    		DecimalFormat df = new DecimalFormat("#.0");
    		double percent = Double.parseDouble(df.format((new Random()).nextDouble() * 20 - 10));
    		item.setActualOverCalculateGJ(percent);
    		
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
		
		ServerHttpRequest httpReq = new ServerHttpRequest();
		String strRequestAddress = (ConstDefine.WEB_SERVICE_URL + ConstDefine.S_GET_HEATSOURCES);
		try {
			String strResp = httpReq.doGet(strRequestAddress);
			ArrayList<StationListItem>  lstHotSrc = (ArrayList<StationListItem>) JsonHelper.parseCollection(strResp, List.class, StationListItem.class);
			return  lstHotSrc;
		}
		catch (Exception ex){
			throw ex;
		}
		
		/*
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
		
		return lstHotPosition;*/
	}
	
	/**
	 * get hot source main List
	 * @return
	 */
	public static ArrayList<HeatSourceMainItem> getHeatSourceMainList() throws Exception{
		
		ServerHttpRequest httpReq = new ServerHttpRequest();
		String strRequestAddress = (ConstDefine.WEB_SERVICE_URL + ConstDefine.S_GET_HEATSOURCES);
		try {
			String strResp = httpReq.doGet(strRequestAddress);
			ArrayList<HeatSourceMainItem>  lstHotSrc = (ArrayList<HeatSourceMainItem>) JsonHelper.parseCollection(strResp, List.class, HeatSourceMainItem.class);
			
			//
			//ArrayList<HeatSourceMainItem>  lstHotSrc = new ArrayList<HeatSourceMainItem>();
			//for(int i=0; i<15; i++)
	        //{
			//	HeatSourceMainItem  item = new HeatSourceMainItem();
	        //	item.setArea("东部");
	        //	item.setCombineMode("外部");
	        //	item.setUnitType("燃气");
	        //	item.setHeatSourceName("国华北");
	        //	item.setHeatSourceId("1");
	        //	lstHotSrc.add(item);
	        //}

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
		new SendChatMessageTask(chatMessage, "put").execute(url);
	}
	
	public static void SendImage(ChatMessage chatMessage) {
		ServerHttpRequest httpReq = new ServerHttpRequest();
		String url = ConstDefine.WEB_SERVICE_URL + ConstDefine.S_POST_IMAGE;
		url = url.replace("{UserName}", "zhaoyaqi");
		new SendChatMessageTask(chatMessage, "post").execute(url);
		
		
	}
	
	public static ArrayList<HeatSourceDetail> getHeatSourceDetail(String heatSourceId) throws Exception{
		
		ServerHttpRequest httpReq = new ServerHttpRequest();
		String url = (ConstDefine.WEB_SERVICE_URL + ConstDefine.S_GET_HEATSOURCE).replace("{heatSourceId}", heatSourceId);
		ArrayList<HeatSourceDetail>  lstDetails = new ArrayList<HeatSourceDetail>();
		try {
			String strResp = httpReq.doGet(url);
			lstDetails = (ArrayList<HeatSourceDetail>) JsonHelper.parseCollection(strResp, List.class, HeatSourceDetail.class);
			return  lstDetails;
		}catch(Exception ex){
			throw ex;
		}
	}
	
}
