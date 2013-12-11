package com.model;

import java.net.Authenticator;
import java.net.HttpURLConnection;
import java.net.PasswordAuthentication;

import com.util.ConstDefine;

public class UserInfo {
	
	private String userId;
	
	private String userName;
	
	private String userPwd;
	
	private String menu;

	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPwd() {
		return userPwd;
	}

	public void setUserPwd(String userPwd) {
		this.userPwd = userPwd;
	}

	public String getMenu() {
		return menu;
	}

	public void setMenu(String menu) {
		this.menu = menu;
	}
	

}
