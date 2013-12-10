package com.model;

import java.net.Authenticator;
import java.net.HttpURLConnection;
import java.net.PasswordAuthentication;

import com.util.ConstDefine;

public class UserInfo {
	
	private String strUserId;
	
	private String strUserName;
	
	private String strUserPwd;
	
	private String strMenu;

	
	public String getStrUserId() {
		return strUserId;
	}

	public void setStrUserId(String strUserId) {
		this.strUserId = strUserId;
	}

	public String getStrUserName() {
		return strUserName;
	}

	public void setStrUserName(String strUserName) {
		this.strUserName = strUserName;
	}

	public String getStrUserPwd() {
		return strUserPwd;
	}

	public void setStrUserPwd(String strUserPwd) {
		this.strUserPwd = strUserPwd;
	}

	public String getStrMenu() {
		return strMenu;
	}

	public void setStrMenu(String strMenu) {
		this.strMenu = strMenu;
	}
	

}
