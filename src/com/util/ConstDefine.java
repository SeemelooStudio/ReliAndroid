package com.util;



public class ConstDefine 
{
	
	//hander msg
	public static final int MSG_I_HANDLE_OK = 0x0001;
	public static final int MSG_I_HANDLE_Fail = 0x0002;
	
	
	//http
	public static final int HTTP_TIME_OUT = 10*1000;
	public static final int HTTP_BUFF_SIZE = 8192;
	public static final String HTTP_ENCODE = "UTF-8";
	
	//http url
	public static final String S_GET_USERINFO = "/Mush/Host/Service/setMsg";
	

	
	//show msg info
	public static final String I_MSG_0001 = "应用在努力加载中...";
	public static final String I_MSG_0002 = "权限验证中，请稍候...";

	//show msg err
	public static final String E_MSG_0001 = "网络状况不好，请稍候再试.";
	
}
