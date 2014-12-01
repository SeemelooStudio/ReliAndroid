package com.reqst;


import java.io.UnsupportedEncodingException;
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

import android.app.Activity;
import android.util.Base64;
import android.util.Log;

import com.model.ChatMessage;
import com.model.HeatSourceDetail;
import com.model.HeatSourceHistoryListItem;
import com.model.StationDetail;
import com.model.StationHistoryListItem;
import com.model.StationListItem;
import com.model.StationMainItem;
import com.model.HeatSourceMainItem;
import com.model.HeatSourceTitle;
import com.model.KnowledgeBaseItem;
import com.model.SupplyAndBackwardItem;
import com.model.GenericListItem;
import com.model.MainPageSummary;
import com.model.SendChatMessageTask;
import com.model.UserInfo;
import com.model.WarnListItem;
import com.model.WeatherDetailItem;
import com.model.WeatherDetailTempInfo;
import com.model.WeatherType;
import com.util.AccountHelper;
import com.util.ConstDefine;
import com.model.WeatherPreChartItem;
import com.util.JsonHelper;

public class BusinessRequest {

	
	public static String getLatestVersion( Activity activity) throws Exception
	{
		ServerHttpRequest httpReq = new ServerHttpRequest();
		return httpReq.doGet( AccountHelper.getBaseUrl(activity) + ConstDefine.S_CHECKVERSION);
	}
	/**
	 * get mainMenu by userinfo
	 * @param user
	 * @return
	 * @throws Exception 
	 */
	public static String getMainMenuByLoginUser(UserInfo user, Activity activity) throws Exception{
		
		//change jsonstring
		JSONStringer jsonStrUser = JsonHelper.toJSONString(user);
		UserInfo respUser = new UserInfo();
		
		try {
			Log.v("jsonStrUser", jsonStrUser.toString());
			ServerHttpRequest httpReq = new ServerHttpRequest();
			String strRequestAddress = AccountHelper.getBaseUrl(activity) + ConstDefine.S_GET_USERINFO.replace("{UserName}", user.getUserName());
			String strResp = httpReq.doGet(strRequestAddress);
			Log.v("strResp", strResp);
			
			respUser = JsonHelper.parseObject(strResp, UserInfo.class);  
			return respUser.getMenu();
			
		} catch (Exception ex) {
			return "18235647";
			
		}
		
		
	}
	
	
	/**
	 * get Daily list
	 * @return
	 * @throws JSONException 
	 */
	public static ArrayList<GenericListItem> getDailyList(GenericListItem objSearchCon, Activity activity) throws Exception 
	{
		JSONStringer jsonStrCon = JsonHelper.toJSONString(objSearchCon);
		ServerHttpRequest httpReq = new ServerHttpRequest();
		String strRequestAddress = AccountHelper.getBaseUrl(activity) + ConstDefine.S_GET_DAILYREPORTS;
		try {
			String strResp = httpReq.doGet(strRequestAddress);
			ArrayList<GenericListItem>  lstDaily = (ArrayList<GenericListItem>) JsonHelper.parseCollection(strResp, List.class, GenericListItem.class);	
			return  lstDaily;
		}
		catch(Exception ex) {
			throw ex;
		}
	}
	/**
	 * get CustomerService Report list
	 * @return
	 * @throws JSONException 
	 */
	public static ArrayList<GenericListItem> getCustomerServiceList(GenericListItem objSearchCon, Activity activity) throws Exception 
	{
		ServerHttpRequest httpReq = new ServerHttpRequest();
		String strRequestAddress = AccountHelper.getBaseUrl(activity) + ConstDefine.S_GET_CUSTOMERSERVICE_REPORTS;
		try {
			String strResp = httpReq.doGet(strRequestAddress);
			ArrayList<GenericListItem>  lstDaily = (ArrayList<GenericListItem>) JsonHelper.parseCollection(strResp, List.class, GenericListItem.class);	
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
	public static ArrayList<WarnListItem> getWarnList(WarnListItem objSearchCon, Activity activity) throws Exception 
	{
		JSONStringer jsonStrCon = JsonHelper.toJSONString(objSearchCon);
		Log.v("jsonStrCondition", jsonStrCon.toString());
		
		ServerHttpRequest httpReq = new ServerHttpRequest();
		String strRequestAddress = AccountHelper.getBaseUrl(activity) + ConstDefine.S_GET_WARNINGS;
		try {
			String strResp = httpReq.doGet(strRequestAddress);
			ArrayList<WarnListItem>  lstWarn = (ArrayList<WarnListItem>) JsonHelper.parseCollection(strResp, List.class,  WarnListItem.class);	
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
	public static WarnListItem getWarnDetailById(String strWarnId, Activity activity) throws Exception{
		
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
	public static ArrayList<GenericListItem> getWeatherList(Activity activity) throws Exception 
	{
		
		ServerHttpRequest httpReq = new ServerHttpRequest();
		String strRequestAddress = AccountHelper.getBaseUrl(activity) + ConstDefine.S_GET_WEATHERSTATIONS;
		
		try {
			String strResp = httpReq.doGet(strRequestAddress);
			ArrayList<GenericListItem>  lstWeather = (ArrayList<GenericListItem>) JsonHelper.parseCollection(strResp, List.class, GenericListItem.class);	
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
	public static WeatherDetailTempInfo[] getWenduTabDetailById(String weatherTypeId, Activity activity) throws Exception{
		
		
		String requestAddress = (AccountHelper.getBaseUrl(activity) + ConstDefine.S_GET_OFFICIALWEATHER).replace("{weatherTypeId}", weatherTypeId);
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
	public static ArrayList<WeatherDetailItem> getWenduTabDetailListById(WeatherDetailTempInfo objSearchCon, Activity activity) throws Exception 
	{
		JSONStringer jsonStrCon = JsonHelper.toJSONString(objSearchCon);
		
		Log.v("jsonStrCondition", jsonStrCon.toString());
		
		ServerHttpRequest httpReq = new ServerHttpRequest();
		String strRequestAddress = (AccountHelper.getBaseUrl(activity) + ConstDefine.S_GET_OFFICIALWEATHERDETAILS).replace("{fromDate}", "2013-11-23").replace("{toDate}", "2013-11-28");
		try {
			String strResp = httpReq.doGet(strRequestAddress);
			ArrayList<WeatherDetailItem>  lstWeather = (ArrayList<WeatherDetailItem>) JsonHelper.parseCollection(strResp, List.class, WeatherDetailItem.class);	
			return  lstWeather;
		}
		catch (Exception ex){
			throw ex;
		}
	   
	}
	
    public static ArrayList<WeatherDetailItem> getWeatherHisListData(Date fromDate, Date toDate, Activity activity) throws Exception  {  
       
		ServerHttpRequest httpReq = new ServerHttpRequest();
		String strRequestAddress = (AccountHelper.getBaseUrl(activity) + ConstDefine.S_GET_OFFICIALWEATHERDETAILS)
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
	
    public static  List<WeatherPreChartItem> getWeatherChartList( Activity activity) throws Exception 
    {
    	WeatherDetailTempInfo[] sevenDays = getWenduTabDetailById(WeatherType.SevenDays.getStrValue(), activity);
    	List<WeatherPreChartItem> chartList = new ArrayList<WeatherPreChartItem>();
    	
    	for(WeatherDetailTempInfo day : sevenDays) {
    		WeatherPreChartItem  item = new WeatherPreChartItem();
    		item.setStrDate( new SimpleDateFormat("yyyy-MM-dd").format(day.getDay()) );
    		item.setStrHighTmpture(  day.getForecastHighest() + "");
    		item.setStrShorttemTure( day.getForecastLowest() + "");
    		item.setStrAvgTemperature(day.getForecastAverage() + "");
    		chartList.add(item);
    	}
		
    	return chartList;
    }
    public static  List<SupplyAndBackwardItem> getStationSupplyAndReturnList(String stationId, Date startDate, Date endDate, Activity activity) throws Exception
    {
    	List<SupplyAndBackwardItem> dataList = new ArrayList<SupplyAndBackwardItem>();
    	
    	String fromDate = new SimpleDateFormat("yyyy-MM-dd%20HH:mm:ss").format(startDate);
    	String toDate = new SimpleDateFormat("yyyy-MM-dd%20HH:mm:ss").format(endDate);
    	
    	
    	ServerHttpRequest httpReq = new ServerHttpRequest();
		String strRequestAddress = (AccountHelper.getBaseUrl(activity) + ConstDefine.S_GET_STATION_HISTORY)
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
    }
    public static  List<SupplyAndBackwardItem> getHeatSourceSupplyAndBackwardList(String heatSourceId,String unitId, Date startDate, Date endDate, Activity activity) throws Exception
    {
    	
    	List<SupplyAndBackwardItem> dataList = new ArrayList<SupplyAndBackwardItem>();
    	String fromDate = new SimpleDateFormat("yyyy-MM-dd%20HH:mm:ss").format(startDate);
    	String toDate = new SimpleDateFormat("yyyy-MM-dd%20HH:mm:ss").format(endDate);
    	
    	ServerHttpRequest httpReq = new ServerHttpRequest();
		String strRequestAddress = (AccountHelper.getBaseUrl(activity) + ConstDefine.S_GET_HEATSOURCE_RECENT_HISTORY)
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
    
    public static StationDetail getStationDetail(int stationId, Activity activity) throws Exception{
    	
    	String requestAddress = (AccountHelper.getBaseUrl(activity) + ConstDefine.S_GET_STATION).replace("{StationId}", stationId+"");
    	
		try {
			ServerHttpRequest httpReq = new ServerHttpRequest();
			String strResp = httpReq.doGet(requestAddress);
			StationDetail info = JsonHelper.parseObject(strResp, StationDetail.class);
			return info;
		} catch (Exception ex) {
			throw ex;
		}
    }
    
    public static ArrayList<StationHistoryListItem> getStationHistoryList(String stationId, Date startDate, Date endDate, Activity activity) 
    	throws Exception{
    	ArrayList<StationHistoryListItem> historyList;
    	
    	String fromDate = new SimpleDateFormat("yyyy-MM-dd%20HH:mm:ss").format(startDate);
    	String toDate = new SimpleDateFormat("yyyy-MM-dd%20HH:mm:ss").format(endDate);
    	
		ServerHttpRequest httpReq = new ServerHttpRequest();
    	String requestAddress = (AccountHelper.getBaseUrl(activity) + ConstDefine.S_GET_STATION_GJHISTORY)
    			.replace("{stationId}", stationId+"")
    			.replace("{fromDate}", fromDate)
				.replace("{toDate}", toDate);
    	
    	try {
			String strResp = httpReq.doGet(requestAddress);
			historyList = (ArrayList<StationHistoryListItem>) JsonHelper.parseCollection(strResp, List.class, StationHistoryListItem.class);	
			return historyList;
		}
		catch (Exception ex) {
			throw ex;
		}
    }
    
    public static ArrayList<HeatSourceHistoryListItem> getHeatSourceHistoryList(String heatSourceId, String heatSourceRecentId, Date startDate, Date endDate, Activity activity) 
        	throws Exception{
        	ArrayList<HeatSourceHistoryListItem> historyList;
        	
        	String fromDate = new SimpleDateFormat("yyyy-MM-dd").format(startDate);
        	String toDate = new SimpleDateFormat("yyyy-MM-dd").format(endDate);
        	
    		ServerHttpRequest httpReq = new ServerHttpRequest();
        	String requestAddress = (AccountHelper.getBaseUrl(activity) + ConstDefine.S_GET_HEATSOURCE_GJHISTORY)
        			.replace("{heatSourceId}", heatSourceId)
        			.replace("{heatSourceRecentId}", heatSourceRecentId)
        			.replace("{fromDate}", fromDate)
    				.replace("{toDate}", toDate);
        	
        	try {
    			String strResp = httpReq.doGet(requestAddress);
    			historyList = (ArrayList<HeatSourceHistoryListItem>) JsonHelper.parseCollection(strResp, List.class, HeatSourceHistoryListItem.class);	
    			return historyList;
    		}
    		catch (Exception ex) {
    			throw ex;
    		}
        }
    
    /**
     * 
     * @return
     */
	public static ArrayList<StationMainItem> getStationMainList( Activity activity) throws Exception{ 
		ServerHttpRequest httpReq = new ServerHttpRequest();
		String strRequestAddress = (AccountHelper.getBaseUrl(activity) + ConstDefine.S_GET_SAVED_STATIONS);
		try {
			String strResp = httpReq.dopost(strRequestAddress, JsonHelper.toJSONString(
					AccountHelper.getRegistedStationIds(activity) ));
			
			ArrayList<StationMainItem>  lstHotSrc = (ArrayList<StationMainItem>) JsonHelper.parseCollection(strResp, List.class, StationMainItem.class);	
			return  lstHotSrc;
		}
		catch (Exception ex){
			throw ex;
		}
	}
	
	public static ArrayList<StationListItem> getStationQueryList(StationListItem objSearchCon, Activity activity) throws Exception 
	{
		ServerHttpRequest httpReq = new ServerHttpRequest();
		String strRequestAddress = (AccountHelper.getBaseUrl(activity) + ConstDefine.S_GET_SAVED_STATIONS);
		try {
			String strResp = httpReq.dopost(strRequestAddress, JsonHelper.toJSONString(
					AccountHelper.getRegistedStationIds(activity) ));
			ArrayList<StationListItem>  lstHotSrc = (ArrayList<StationListItem>) JsonHelper.parseCollection(strResp, List.class, StationListItem.class);
			return  lstHotSrc;
		}
		catch (Exception ex){
			throw ex;
		}
	}
	
	public static ArrayList<StationListItem> getStationNames(Activity activity) throws Exception 
	{
		ServerHttpRequest httpReq = new ServerHttpRequest();
		String strRequestAddress = (AccountHelper.getBaseUrl(activity) + ConstDefine.S_GET_STATION_NAMES);
		try {
			String strResp = httpReq.doGet(strRequestAddress);
			ArrayList<StationListItem>  lstHotSrc = (ArrayList<StationListItem>) JsonHelper.parseCollection(strResp, List.class, StationListItem.class);
			return  lstHotSrc;
		}
		catch (Exception ex){
			throw ex;
		}
	}
	
	public static ArrayList<HeatSourceMainItem> getHeatSourceMainList(Activity activity) throws Exception{
		
		ServerHttpRequest httpReq = new ServerHttpRequest();
		String strRequestAddress = (AccountHelper.getBaseUrl(activity) + ConstDefine.S_GET_HEATSOURCES);
		try {
			String strResp = httpReq.doGet(strRequestAddress);
			ArrayList<HeatSourceMainItem>  lstHotSrc = (ArrayList<HeatSourceMainItem>) JsonHelper.parseCollection(strResp, List.class, HeatSourceMainItem.class);
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
	public static HeatSourceTitle getHeatSourceAllStatic( Activity activity) throws Exception{
		HeatSourceTitle hotStaticInfo = new HeatSourceTitle();
		try {
			
			ServerHttpRequest httpReq = new ServerHttpRequest();
			String strRequestAddress = AccountHelper.getBaseUrl(activity) + ConstDefine.S_GET_HEATSOURCESUMMARY;
			String strResp = httpReq.doGet(strRequestAddress);
			hotStaticInfo = JsonHelper.parseObject(strResp, HeatSourceTitle.class);  
			
		} catch (Exception ex) {
			throw ex;
		}
		return hotStaticInfo;
	}
	
	
	/**
	 * get Daily list
	 * @return
	 * @throws JSONException 
	 */
	public static ArrayList<KnowledgeBaseItem> getKnowledgeBaseList(KnowledgeBaseItem objSearchCon, Activity activity) throws JSONException 
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
	
	public static MainPageSummary getMainPageSummary(String userName, Activity activity) throws Exception
	{
		MainPageSummary mainPageSummary = new MainPageSummary();
		try {	
			ServerHttpRequest httpReq = new ServerHttpRequest();
			String strRequestAddress = (AccountHelper.getBaseUrl(activity) + ConstDefine.S_GET_SUMMARY).replace("{UserName}", userName);
			String strResp = httpReq.doGet(strRequestAddress);
			mainPageSummary = JsonHelper.parseObject(strResp, MainPageSummary.class);  
		} catch (Exception ex) {
			throw ex;
		}
		
		return mainPageSummary;
	}
	
	public static ArrayList<ChatMessage> getMessages(String strUserName, Activity activity) throws Exception
	{
		ServerHttpRequest httpReq = new ServerHttpRequest();
		String strRequestAddress = (AccountHelper.getBaseUrl(activity) + ConstDefine.S_GET_MESSAGES).replace("{UserName}", strUserName);
		try {
			String strResp = httpReq.doGet(strRequestAddress);
			ArrayList<ChatMessage>  lstMessages = (ArrayList<ChatMessage>) JsonHelper.parseCollection(strResp, List.class, ChatMessage.class);	
			return  lstMessages;
		}
		catch (Exception ex){
			throw ex;
		}
	}
	
	public static Boolean Authentication(UserInfo userInfo, Activity activity)  {
		ServerHttpRequest httpReq = new ServerHttpRequest();
		String url = AccountHelper.getBaseUrl(activity) + ConstDefine.S_AUTHENTICATION;
		Map<String, String> requestData = new HashMap<String, String>();
		requestData.put("userName", userInfo.getUserName());
		requestData.put("password", userInfo.getUserPwd());
		try {
			String response = httpReq.doGet(url, requestData);
			return Boolean.parseBoolean(response);
		}
		catch(Exception ex) {
			Log.v("authenticationError", ex.getMessage());
		}
		return false;
	}
	
	public static void SendMessage(ChatMessage chatMessage, Activity activity){
		String url = (AccountHelper.getBaseUrl(activity) + ConstDefine.S_PUT_MESSAGE).replace("{UserName}", chatMessage.getSendFromUserName());
		new SendChatMessageTask(chatMessage, "put").execute(url);
	}
	
	public static void SendImage(ChatMessage chatMessage, Activity activity) {
		ServerHttpRequest httpReq = new ServerHttpRequest();
		String url = AccountHelper.getBaseUrl(activity) + ConstDefine.S_POST_IMAGE;
		url = url.replace("{UserName}", chatMessage.getSendFromUserName());
		new SendChatMessageTask(chatMessage, "post").execute(url);
	}
	
	public static ArrayList<HeatSourceDetail> getHeatSourceDetail(String heatSourceId, Activity activity) throws Exception{
		
		ServerHttpRequest httpReq = new ServerHttpRequest();
		String url = (AccountHelper.getBaseUrl(activity) + ConstDefine.S_GET_HEATSOURCE).replace("{heatSourceId}", heatSourceId);
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
