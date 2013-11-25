package com.model;

public enum WeatherType {
	Today(1), Yesterday(2), SevenDays(3), History(4);
	
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
