package com.model;

import java.util.Random;

import com.util.WeatherIconHelper;

public class WeatherDetailItem {

	private float forecastHighest;
	private float forecastLowest;
	private float forecastAverage;
	private float actualHighest;
	private float actualLowest;
	private float actualAverage;
	private String day;
	private String windDirection;
	private String weatherDescription;
	private int weatherType;
	
	public String getDay() {
		return day;
	}
	public void setDay(String day) {
		this.day = day;
	}
	public String getWindDirection() {
		return windDirection;
	}
	public void setWindDirection(String windDirection) {
		this.windDirection = windDirection;
	}
	public String getWeatherDescription() {
		return weatherDescription;
	}
	public void setWeatherDescription(String weatherDescription) {
		this.weatherDescription = weatherDescription;
	}
	public Integer getWeatherType(){
		Random rand = new Random();
	    Integer intWeatherId = rand.nextInt(34) ;
		return WeatherIconHelper.getWeatherIconResourceId(intWeatherId);
	}
	public float getForecastHighest() {
		return forecastHighest;
	}
	public void setForecastHighest(float forecastHighest) {
		this.forecastHighest = forecastHighest;
	}
	public float getForecastLowest() {
		return forecastLowest;
	}
	public void setForecastLowest(float forecastLowest) {
		this.forecastLowest = forecastLowest;
	}
	public float getForecastAverage() {
		return forecastAverage;
	}
	public void setForecastAverage(float forecastAverage) {
		this.forecastAverage = forecastAverage;
	}
	public float getActualHighest() {
		return actualHighest;
	}
	public void setActualHighest(float actualHighest) {
		this.actualHighest = actualHighest;
	}
	public float getActualLowest() {
		return actualLowest;
	}
	public void setActualLowest(float actualLowest) {
		this.actualLowest = actualLowest;
	}
	public float getActualAverage() {
		return actualAverage;
	}
	public void setActualAverage(float actualAverage) {
		this.actualAverage = actualAverage;
	}
}
