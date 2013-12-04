package com.model;

import java.util.Random;

import com.util.WeatherIconHelper;

public class WeatherDetailItem {

	private String w_time;
	private String w_wendu;
	private String w_tianqi;
	
	public String getW_time() {
		return w_time;
	}
	public void setW_time(String w_time) {
		this.w_time = w_time;
	}
	public String getW_wendu() {
		return w_wendu;
	}
	public void setW_wendu(String w_wendu) {
		this.w_wendu = w_wendu;
	}
	public String getW_tianqi() {
		return w_tianqi;
	}
	public void setW_tianqi(String w_tianqi) {
		this.w_tianqi = w_tianqi;
	}
	public Integer getW_iconId(){
		Random rand = new Random();
	    Integer intWeatherId = rand.nextInt(34) ;
		return WeatherIconHelper.getWeatherIconResourceId(intWeatherId);
	}
	
	
	
}
