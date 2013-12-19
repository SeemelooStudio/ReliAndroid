package com.model;

public enum WeatherType {
	TodayAndYesterday(1), SevenDays(2), History(3);
	
	private final int value;
	
	private WeatherType(int value) {
		this.value = value;
	}
	
	public int getValue () {
		return value;
	}
	
	public String getStrValue() {
		return new Integer(getValue()).toString();
	}
}
