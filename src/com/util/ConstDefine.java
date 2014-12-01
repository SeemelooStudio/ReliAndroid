package com.util;


public class ConstDefine 
{
	//hander msg
	public static final int MSG_I_HANDLE_OK = 0x0001;
	public static final int MSG_I_HANDLE_Fail = 0x0002;
	public static final int MSG_I_HANDLE_SEARCH_OK = 0x0003;
	//http
	public static final int HTTP_TIME_OUT = 1*1000;
	public static final int HTTP_BUFF_SIZE = 8192;
	public static final String HTTP_ENCODE = "UTF-8";
	
    public static final String WEB_SERVICE_URL = "http://192.168.1.100:11223";
	public static final String WEB_SERVICE_OUTER_URL = "http://192.180.1.251:11223";
//	public static final String WEB_SERVICE_URL = "http://192.168.1.101:11223";
//	public static final String WEB_SERVICE_OUTER_URL = "http://192.168.1.101:11223";
	
	public static final String S_GET_USERINFO = "/MobileService/Users/{UserName}";
	public static final String S_GET_DAILYREPORTS = "/MobileService/DailyReports";
	public static final String S_GET_DAILYREPORT = "/MobileService/DailyReports/{DailyReportId}";
	public static final String S_GET_CUSTOMERSERVICE_REPORTS = "/MobileService/CustomerServiceReports";
	public static final String S_GET_CUSTOMERSERVICE_REPORT = "/MobileService/CustomerServiceReports/{CustomerServiceReportId}";
	
	public static final String S_GET_WARNINGS = "/MobileService/Warnings";
	public static final String S_GET_OFFICIALWEATHER = "/MobileService/Weathers/{weatherTypeId}";
	public static final String S_GET_WEATHERSTATIONS = "/MobileService/WeatherStations";
	public static final String S_GET_OFFICIALWEATHERDETAILS = "/MobileService/Weathers?from={fromDate}&to={toDate}";
	
	public static final String S_GET_HEATSOURCES = "/MobileService/HeatSources";
	public static final String S_GET_HEATSOURCE = "/MobileService/HeatSources/{heatSourceId}";
	public static final String S_GET_HEATSOURCESUMMARY = "/MobileService/HeatSourceSummary";
	public static final String S_GET_HEATSOURCE_RECENT_HISTORY = "/MobileService/HeatSources/{heatSourceId}/Recents/{heatSourceRecentId}/Histories?from={fromDate}&to={toDate}";
	public static final String S_GET_HEATSOURCE_GJHISTORY = "/MobileService/HeatSources/{heatSourceId}/Recents/{heatSourceRecentId}/GJHistories?from={fromDate}&to={toDate}";
	
	public static final String S_GET_STATIONS = "/MobileService/Stations";
	public static final String S_GET_STATION = "/MobileService/Stations/{StationId}";
	public static final String S_GET_SAVED_STATIONS = "/MobileService/SavedStations";
	public static final String S_GET_STATION_NAMES = "/MobileService/StationTitles";
	public static final String S_GET_STATION_HISTORY = "/MobileService/Stations/{stationId}/Histories?from={fromDate}&to={toDate}";
	public static final String S_GET_STATION_GJHISTORY = "/MobileService/Stations/{stationId}/GJHistories?from={fromDate}&to={toDate}";
	
	public static final String S_GET_SUMMARY = "/MobileService/Summary/{UserName}";
	public static final String S_GET_MESSAGES = "/MobileService/{UserName}/Messages";
	public static final String S_DAILY_REPORT_ROOT = "/DailyReports/";
	public static final String S_CUSTOMERSERVICE_REPORT_ROOT = "/CustomerServiceReports/";
	public static final String S_AUTHENTICATION = "/MobileService/AuthenticateUser";
	public static final String S_PUT_MESSAGE = "/MobileService/{UserName}/Messages";
	public static final String S_POST_IMAGE = "/MobileService/{UserName}/Messages/UploadPhoto";
	public static final String S_PUT_IMAGE = "/MobileService/{UserName}/Messages/UploadPhoto/{MessageId}";
	
	public static final String S_DOWNLOAD_LATEST = "http://192.168.1.100:14562/downloads/ReliMobile{VersionName}.apk";
	public static final String S_CHECKVERSION = "/MobileService/Version";
	//show msg info
	public static final String I_MSG_0001 = "fail";
	public static final String I_MSG_0002 = "wait";
	public static final String I_MSG_0003 = "正在获取数据，请稍候";
	public static final String I_MSG_0004 = "under construction";
    public static final String I_MSG_0005 = "正在下载文件，请稍后";
	//show msg err
	public static final String E_MSG_0001 = "获取失败";
	public static final String E_MSG_0002 = "没有版本号";
	
	
}
