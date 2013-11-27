package com.reqst;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONStringer;

import android.util.Log;

import com.model.HotPosMainItem;
import com.model.HotSrcMainItem;
import com.model.HotSrcTitleInfo;
import com.model.KnowledgeBaseItem;
import com.model.ListItem;
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
			String strRequestAddress = ConstDefine.WEB_SERVICE_URL + ConstDefine.S_GET_USERINFO.replace("{UserName}", user.getStrUserName());
			String strResp = httpReq.doGet(strRequestAddress);
			Log.v("strResp", strResp);
			
			respUser = JsonHelper.parseObject(strResp, UserInfo.class);  
			return respUser.getStrMenu();
			
		} catch (Exception ex) {
			return "1234567";
			
		}
		
		
		
	}
	
	
	/**
	 * get Daily list
	 * @return
	 * @throws JSONException 
	 */
	public static ArrayList<ListItem> getDailyList(ListItem objSearchCon) throws Exception 
	{
		JSONStringer jsonStrCon = JsonHelper.toJSONString(objSearchCon);
		
		Log.v("jsonStrCondition", jsonStrCon.toString());
		
		
		ServerHttpRequest httpReq = new ServerHttpRequest();
		String strRequestAddress = ConstDefine.WEB_SERVICE_URL + ConstDefine.S_GET_DAILYREPORTS;
		try {
			String strResp = httpReq.doGet(strRequestAddress);
			ArrayList<ListItem>  lstDaily = (ArrayList<ListItem>) JsonHelper.parseCollection(strResp, List.class, ListItem.class);	
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
	public static ArrayList<ListItem> getWeatherList(ListItem objSearchCon) throws Exception 
	{
		JSONStringer jsonStrCon = JsonHelper.toJSONString(objSearchCon);
		
		Log.v("jsonStrCondition", jsonStrCon.toString());
		
		ServerHttpRequest httpReq = new ServerHttpRequest();
		String strRequestAddress = ConstDefine.WEB_SERVICE_URL + ConstDefine.S_GET_WEATHERSTATIONS;
		
		try {
			String strResp = httpReq.doGet(strRequestAddress);
			ArrayList<ListItem>  lstWeather = (ArrayList<ListItem>) JsonHelper.parseCollection(strResp, List.class, ListItem.class);	
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
			//create parame
			//Map<String, String> mapParam = new HashMap();
			//mapParam.put("list_id", strListId);
			//mapParam.put("day_id", strDayFlag);
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
        	item.setStrDocName("�ĵ�" +i);
        	item.setStrDocUpTime("2013-01-2" +i);
        	lstDaily.add(item);
        }

	   return  lstDaily;
	}
	
}
