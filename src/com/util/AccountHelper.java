package com.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;

import com.model.StationListItem;
import com.model.StationMainItem;

import android.app.Activity;
import android.content.SharedPreferences;

public class AccountHelper  {

	public static final String UserName = "userName";
	public static final String Password = "password";
	public static final String BaseUrl = "baseUrl";
	public static final String SavedStations = "savedStations";
	public static final String SavedStationNames = "savedStationNames";
	public static final String AllStations = "allStations";
	
	public static void setUserName (String userName, Activity activity){
		SharedPreferences settings = activity.getSharedPreferences(UserName, 0);
		SharedPreferences.Editor editor = settings.edit();
		editor.putString(UserName, userName);
		editor.commit();
	}
	
	public static String getUserName (Activity activity){
		SharedPreferences settings = activity.getSharedPreferences(UserName, 0);
		return settings.getString(UserName, "");
	}
	
	public static void setSavedPassword (String password, Activity activity)
	{
		SharedPreferences settings = activity.getSharedPreferences(Password, 0);
		SharedPreferences.Editor editor = settings.edit();
		editor.putString(Password, password);
		editor.commit();
	}
	
	public static String getSavedPassword(Activity activity)
	{
		SharedPreferences settings = activity.getSharedPreferences(Password, 0);
		return settings.getString(Password, "");
	}
	public static void setBaseUrl(boolean isInner, Activity activity)
	{
		String baseUrl = "";
		if(isInner) {
			baseUrl = ConstDefine.WEB_SERVICE_URL;
		}
		else {
			baseUrl = ConstDefine.WEB_SERVICE_OUTER_URL;
		}
		SharedPreferences settings = activity.getSharedPreferences(BaseUrl, 0);
		SharedPreferences.Editor editor = settings.edit();
		editor.putString(BaseUrl, baseUrl);
		editor.commit();
	}
	
	public static String getBaseUrl(Activity activity)
	{
		SharedPreferences settings = activity.getSharedPreferences(BaseUrl, 0);
		return settings.getString(BaseUrl, ConstDefine.WEB_SERVICE_URL);
	}
	
	public static ArrayList<Integer> getRegistedStationIds(Activity activity)
	{
		SharedPreferences settings = activity.getSharedPreferences(SavedStations, 0);
		String[] strStationIds = settings.getString(SavedStations, "").replace("[", "").replace("]", "").split(",", 0);
		ArrayList<Integer> stationIds = new ArrayList<Integer>();
		for(String strStationId : strStationIds) {
			if(!strStationId.isEmpty()) {
				stationIds.add (Integer.parseInt(strStationId.trim() ) );
			}
		}
		return stationIds;
	}
	
	public static void setRegistedStationNames(Activity activity, ArrayList<StationListItem> stations) {
		String jsonString = JsonHelper.toJSON(stations);
		SharedPreferences settings = activity.getSharedPreferences(SavedStationNames, 0);
		SharedPreferences.Editor editor = settings.edit();
		editor.putString("json" + SavedStationNames, jsonString);
		editor.commit();
	}
	
	public static ArrayList<StationListItem> getRegistedStationNames (Activity activity) {
		try {
			JSONArray array = JSONSharedPreferences.loadJSONArray(activity.getBaseContext(), SavedStationNames, SavedStationNames);
			StationListItem[] items = JsonHelper.parseArray(array, StationListItem.class);
			ArrayList<StationListItem> arrayList_items = new ArrayList<StationListItem>(Arrays.asList(items));
			return arrayList_items;
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return  new ArrayList<StationListItem>();
	}
	
	public static void setRegistedStationIds( ArrayList<Integer> stationIds, Activity activity)
	{
		SharedPreferences settings = activity.getSharedPreferences(SavedStations, 0);
		SharedPreferences.Editor editor = settings.edit();
		editor.putString(SavedStations, stationIds.toString());
		editor.commit();
	}
	
	public static void setAllStationNames (Activity activity, ArrayList<StationListItem> stations){
		String jsonString = JsonHelper.toJSON(stations);
		SharedPreferences settings = activity.getSharedPreferences(AllStations, 0);
		SharedPreferences.Editor editor = settings.edit();
		editor.putString("json" + AllStations, jsonString);
		editor.commit();
	}
	
	public static void setAllStationNames ( Activity activity, JSONArray array )
	{
		JSONSharedPreferences.saveJSONArray(activity.getBaseContext(), AllStations, AllStations, array);
	}
	
	public static ArrayList<StationListItem> getAllStationNames (Activity activity) {
		try {
			JSONArray array = JSONSharedPreferences.loadJSONArray(activity.getBaseContext(), AllStations, AllStations);
			StationListItem[] items = JsonHelper.parseArray(array, StationListItem.class);
			ArrayList<StationListItem> arrayList_items = new ArrayList<StationListItem>(Arrays.asList(items));
			return arrayList_items;
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return  new ArrayList<StationListItem>();
	}
}
