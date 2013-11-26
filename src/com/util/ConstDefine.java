package com.util;

import android.R;



public class ConstDefine 
{
	
	//hander msg
	public static final int MSG_I_HANDLE_OK = 0x0001;
	public static final int MSG_I_HANDLE_Fail = 0x0002;
	
	
	//http
	public static final int HTTP_TIME_OUT = 10*1000;
	public static final int HTTP_BUFF_SIZE = 8192;
	public static final String HTTP_ENCODE = "UTF-8";
	
	public static final String WEB_SERVICE_URL = "http://192.168.1.109:11223";
	//http url
	public static final String S_GET_USERINFO = "/MobileService/Users/{UserName}";
	public static final String S_GET_DAILYREPORTS = "/MobileService/DailyReports";
	public static final String S_GET_DAILYREPORT = "/MobileService/DailyReports/{DailyReportId}";
	public static final String S_GET_WARNINGS = "/MobileService/Warnings";
	public static final String S_GET_OFFICIALWEATHER = "/MobileService/Weathers/{weatherTypeId}";
	public static final String S_GET_WEATHERSTATIONS = "/MobileService/Weathers";
	//show msg info
	public static final String I_MSG_0001 = "fail";
	public static final String I_MSG_0002 = "wait";
	public static final String I_MSG_0003 = "正在获取数据，请稍候";
	public static final String I_MSG_0004 = "该功能尚未开放，请更新版本后再试";

	//show msg err
	public static final String E_MSG_0001 = "获取失败";
	
}
