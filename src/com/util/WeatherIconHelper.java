package com.util;

import java.util.HashMap;
import java.util.Map;

import com.ui.R;


public class WeatherIconHelper {
	private static Map<Integer, Integer> mapWeatherIcons;
	static
    {
		mapWeatherIcons = new HashMap<Integer, Integer>();
		mapWeatherIcons.put(0, R.drawable.ic_weather_33);//
		mapWeatherIcons.put(1, R.drawable.ic_weather_01);//sunny
		mapWeatherIcons.put(2, R.drawable.ic_weather_02);//party cloudy
		mapWeatherIcons.put(3, R.drawable.ic_weather_03);//cloudy
		mapWeatherIcons.put(4, R.drawable.ic_weather_07);//overcast
		mapWeatherIcons.put(5, R.drawable.ic_weather_04);//sunny with cloudy
		mapWeatherIcons.put(6, R.drawable.ic_weather_06);//cloudy with sunny
		mapWeatherIcons.put(7, R.drawable.ic_weather_07);//cloudy with overcast 
		mapWeatherIcons.put(8, R.drawable.ic_weather_07);//overcast with cloudy
		mapWeatherIcons.put(9, R.drawable.ic_weather_13);//light rain
		mapWeatherIcons.put(10, R.drawable.ic_weather_14);//moderate rain
		mapWeatherIcons.put(11, R.drawable.ic_weather_25);//heavy rain
		mapWeatherIcons.put(12, R.drawable.ic_weather_12);//rainstorm
		mapWeatherIcons.put(13, R.drawable.ic_weather_18);//heavy rainstorm
		mapWeatherIcons.put(14, R.drawable.ic_weather_15);//very heavy rainstorm
		mapWeatherIcons.put(15, R.drawable.ic_weather_20);//freezing rain
		mapWeatherIcons.put(16, R.drawable.ic_weather_19);//hail
		mapWeatherIcons.put(17, R.drawable.ic_weather_23);//light snow
		mapWeatherIcons.put(18, R.drawable.ic_weather_22);//moderate snow
		mapWeatherIcons.put(19, R.drawable.ic_weather_22);//heavy snow
		mapWeatherIcons.put(20, R.drawable.ic_weather_24);//snowstorm
		mapWeatherIcons.put(21, R.drawable.ic_weather_24);//heavy snowstorm
		mapWeatherIcons.put(22, R.drawable.ic_weather_26);//sleet
		mapWeatherIcons.put(23, R.drawable.ic_weather_31);//primal frost 
		mapWeatherIcons.put(24, R.drawable.ic_weather_31);//final frost
		mapWeatherIcons.put(25, R.drawable.ic_weather_31);//rime
		mapWeatherIcons.put(26, R.drawable.ic_weather_11);//fog
		mapWeatherIcons.put(27, R.drawable.ic_weather_08);//haze
		mapWeatherIcons.put(28, R.drawable.ic_weather_09);//floating sand/dust 
		mapWeatherIcons.put(29, R.drawable.ic_weather_09);//blowing sand/dust
		mapWeatherIcons.put(30, R.drawable.ic_weather_09);//sand/dust storm
		mapWeatherIcons.put(31, R.drawable.ic_weather_09);//severe sand/dust storm
		mapWeatherIcons.put(32, R.drawable.ic_weather_09);//super sand/dust storm
		mapWeatherIcons.put(33, R.drawable.ic_weather_15);//thunderbolt
		mapWeatherIcons.put(34, R.drawable.ic_weather_32);//tornado
    }
	static public Integer getWeatherIconResourceId( Integer intWeatherId) {
		Integer intWeatherIconResourceId = R.drawable.ic_weather_33;
		intWeatherIconResourceId = mapWeatherIcons.get(intWeatherId);
		return intWeatherIconResourceId;
	}
}
