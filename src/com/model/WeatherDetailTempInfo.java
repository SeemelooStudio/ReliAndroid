package com.model;

import java.util.Date;

public class WeatherDetailTempInfo{

	
	
	private String strListId;
	private String strDayFlag;
	
	private String strTemp;
	private String strName;
	private String strTiWen;
	private String strWendu;
	
	private Date day;
	private String windSpeedAndDirection;
	private float forecastHighest;
	private float forecastLowest;
	private float forecastAverage;
	private float actualHighest;
	private float actualLowest;
	private float actualAverage;
	private String weatherDescription;
	private int weatherIcon;
	
	public String getStrListId() {
		return strListId;
	}
	public void setStrListId(String strListId) {
		this.strListId = strListId;
	}
	public String getStrDayFlag() {
		return strDayFlag;
	}
	public void setStrDayFlag(String strDayFlag) {
		this.strDayFlag = strDayFlag;
	}
	public String getStrTemp() {
		return strTemp;
	}
	public void setStrTemp(String strTemp) {
		this.strTemp = strTemp;
	}
	public String getStrName() {
		return strName;
	}
	public void setStrName(String strName) {
		this.strName = strName;
	}
	public String getStrTiWen() {
		return strTiWen;
	}
	public void setStrTiWen(String strTiWen) {
		this.strTiWen = strTiWen;
	}
	public String getStrWendu() {
		return strWendu;
	}
	public void setStrWendu(String strWendu) {
		this.strWendu = strWendu;
	}
	public String getWindSpeedAndDirection() {
		return windSpeedAndDirection;
	}
	public void setWindSpeedAndDirection(String windSpeedAndDirection) {
		this.windSpeedAndDirection = windSpeedAndDirection;
	}
	public float getForecastHighest() {
		return this.forecastHighest;
	}
	public void setForecastHighest(float forecastHighest) {
		this.forecastHighest = forecastHighest;
	}
	public float getForecastAverage() {
		return this.forecastAverage;
	}
	public void setForecastAverage(float forecastAverage) {
		this.forecastAverage = forecastAverage;
	}
	public float getForecastLowest() {
		return this.forecastLowest;
	}
	public void setForecastLowest(float forecastLowest) {
		this.forecastLowest = forecastLowest;
	}
	public float getActualHighest() {
		return this.actualHighest;
	}
	public void setActualHighest(float actualHighest) {
		this.actualHighest = actualHighest;
	}
	public float getActualAverage() {
		return this.actualAverage;
	}
	public void setActualAverage(float actualAverage) {
		this.actualAverage = actualAverage;
	}
	public float getActualLowest() {
		return this.actualLowest;
	}
	public void setActualLowest(float actualLowest) {
		this.actualLowest = actualLowest;
	}
	public String getWeatherDescription() {
		return this.weatherDescription;
	}
	public void setWeatherDescription(String weatherDescription) {
		this.weatherDescription = weatherDescription;
	}
	public Date getDay() {
		return day;
	}
	public void setDay(Date day) {
		this.day = day;
	}
	public int getWeatherIcon() {
		return weatherIcon;
	}
	public void setWeatherIcon(int weatherIcon) {
		this.weatherIcon = weatherIcon;
	}
} 
