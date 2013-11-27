package com.model;

public class MainPageSummary {
	private String strForecastHighest;
	private String strForecastLowest;
	private String strForecastAverage;
	private String strWind;
	private String strWeather;
	private String strWeatherIcon;
	private String strCountWarnings;
	private String strCountPhotos;
	private String strCountMessages;
	
	public String getStrForecastHighest ()
	{
		return this.strForecastHighest;
	}
	
	public void setStrForecastHighest(String strForecastHighest) {
		this.strForecastHighest = strForecastHighest;
	}
	
	public String getStrForecastLowest()
	{ 
		return this.strForecastLowest;
	}
	
	public void setStrForecastLowest(String strForecastLowest)
	{
		this.strForecastLowest = strForecastLowest;
	}
	
	public String getStrForecastAverage()
	{
		return this.strForecastAverage;
	}
	
	public void setStrForecastAverage(String strForecastAverage) {
		this.strForecastAverage = strForecastAverage;
	}
	
	public String getStrWind()
	{
		return this.strWind;
	}
	
	public void setStrWind( String strWind)
	{
		this.strWind = strWind;
	}
	
	public String getStrWeather()
	{
		return this.strWeather;
	}
	
	public void setStrWeather( String strWeather )
	{
		this.strWeather = strWeather;
	}
	
	public String getStrWeatherIcon()
	{
		return this.strWeatherIcon;
	}
	
	public void setStrWeatherIcon(String strWeatherIcon)
	{
		this.strWeatherIcon = strWeatherIcon;
	}
	
	public String getStrCountWarnings()
	{
		return strCountWarnings;
	}
	public void setStrCountWarnings( String strCountWarnings )
	{
		this.strCountWarnings = strCountWarnings;
	}
	
	public String getStrCountPhotos()
	{
		return this.strCountPhotos; 
	}
	
	public void setStrCountPhotos(String strCountPhotos)
	{
		this.strCountPhotos = strCountPhotos;
	}
	
	public String getStrCountMessages(){
		return this.strCountMessages;
	}
	
	public void setStrCountMessages(String strCountMessages){
		this.strCountMessages = strCountMessages;
	}
}
