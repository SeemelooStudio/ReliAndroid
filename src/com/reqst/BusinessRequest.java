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
import com.model.ListItem;
import com.model.UserInfo;
import com.model.WarnListItem;
import com.model.WeatherDetailItem;
import com.model.WeatherDetailTempInfo;
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
	
	
    /**
     * 
     * @return
     */
    public static ArrayList<WeatherDetailItem> getWeatherHisListData() {  
       
		//ServerHttpRequest httpReq = new ServerHttpRequest();
		//String strResp = httpReq.dopost(ConstDefine.S_GET_USERINFO, jsonStrCon);
		//ArrayList<WeatherDetailItem>  weatherHislist = (ArrayList<WeatherDetailItem>) JsonHelper.parseCollection(strResp, List.class, WeatherDetailItem.class);	
		//TODO
    	
    	ArrayList <WeatherDetailItem> weatherHislist = new ArrayList<WeatherDetailItem>();
        for(int i=0; i<20; i++)
        {
        	WeatherDetailItem  lst = new WeatherDetailItem();
        	lst.setW_time("4" + i + ":PM");
        	lst.setW_wendu("2" +i);
        	lst.setW_tianqi("小雨");
        	weatherHislist.add(lst);
        }

        return weatherHislist;
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
	public static ArrayList<HotPosMainItem> getHotPositionMainList(){
		
		 ArrayList<HotPosMainItem>  dbhostSrcLst = new ArrayList<HotPosMainItem>();
	     for(int i=0; i<30; i++)
	     {
	    	 HotPosMainItem  item = new HotPosMainItem();
	     	item.setHotPosId(i+"");
	     	item.setTitle(i+"号热力站");
	     	item.setWenduLeft("8" +i+".88");
	     	item.setWenduLeftPa("1.35MPa");
	     	item.setWenduRight("4" +i+".66");
	     	item.setWenduRightPa("0.32MPa");
	     	dbhostSrcLst.add(item);
	     }
	
	     return dbhostSrcLst;
	}
	
	
	
	
	
	
	
	
	
	
	/**
	 * get hot source main List
	 * @return
	 */
	public static ArrayList<HotSrcMainItem> getHotSourceMainList(){
		
		   ArrayList<HotSrcMainItem>  dbhostSrcLst = new ArrayList<HotSrcMainItem>();
	        for(int i=0; i<32; i++)
	        {
	        	HotSrcMainItem  item = new HotSrcMainItem();
	        	item.setHotsrcId(i+"");
	        	item.setTitle(i+"号热源");
	        	item.setWenduLeft("8" +i+".88");
	        	item.setWenduLeftPa("1.35MPa");
	        	item.setWenduRight("4" +i+".66");
	        	item.setWenduRightPa("0.32MPa");
	        	dbhostSrcLst.add(item);
	        }
		
		return dbhostSrcLst;
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
			//String strResp = httpReq.doGet(ConstDefine.S_GET_USERINFO, mapParam);
			//TODO
			String strResp = "{'all_num':'30', 'today_num':'100,457','net_num':'73.2'}";  //test
			hotStaticInfo = JsonHelper.parseObject(strResp, HotSrcTitleInfo.class);  
			
		} catch (Exception ex) {
			throw ex;
		}
		
		//return
		return hotStaticInfo;
	}
	
	
}
