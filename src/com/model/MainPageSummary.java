package com.model;

public class MainPageSummary {
	private float forecastHighest;
	private float forecastLowest;
	private float forecastAverage;
	private String windSpeedAndDirection;
	private String weatherDescription;
	private int weatherIcon;
	private int countWarnings;
	private int countPhotos;
	private int countMessages;
	
	public float getForecastHighest ()
	{
		return this.forecastHighest;
	}
	
	public void setForecastHighest(float forecastHighest) {
		this.forecastHighest = forecastHighest;
	}
	
	public float getForecastLowest()
	{ 
		return this.forecastLowest;
	}
	
	public void setForecastLowest(float forecastLowest)
	{
		this.forecastLowest = forecastLowest;
	}
	
	public float getForecastAverage()
	{
		return this.forecastAverage;
	}
	
	public void setForecastAverage(float forecastAverage) {
		this.forecastAverage = forecastAverage;
	}
	
	public String getWindSpeedAndDirection()
	{
		return this.windSpeedAndDirection;
	}
	
	public void setWindSpeedAndDirection( String windSpeedAndDirection)
	{
		this.windSpeedAndDirection = windSpeedAndDirection;
	}
	
	public String getWeatherDiscription()
	{
		return this.weatherDescription;
	}
	
	public void setWeatherDescription( String weatherDescription )
	{
		this.weatherDescription = weatherDescription;
	}
	
	public int getWeatherIcon()
	{
		return this.weatherIcon;
	}
	
	public void setWeatherIcon(int weatherIcon)
	{
		this.weatherIcon = weatherIcon;
	}
	
	public int getCountWarnings()
	{
		return countWarnings;
	}
	public void setCountWarnings( int countWarnings )
	{
		this.countWarnings = countWarnings;
	}
	
	public int getCountPhotos()
	{
		return this.countPhotos; 
	}
	
	public void setCountPhotos(int countPhotos)
	{
		this.countPhotos = countPhotos;
	}
	
	public int getCountMessages(){
		return this.countMessages;
	}
	
	public void setCountMessages(int countMessages){
		this.countMessages = countMessages;
	}
}
