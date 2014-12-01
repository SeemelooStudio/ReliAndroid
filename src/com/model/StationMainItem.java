package com.model;

public class StationMainItem  {

	private int stationId;
	
	private String stationName;
	
	private float plannedGJToday;
	
	private float plannedGJYesterday;
	
	private float actualGJToday;
	
	private float actualGJYesterday;
	
	private float calculatedGJYesterday;
	
	private float pressureOut;
	
	private float pressureIn;
	
	private float temperatureOut;
	
	private float temperatureIn;
	
	private boolean isChaoBiao;

	public float getPlannedGJToday() {
		return plannedGJToday;
	}

	public void setPlannedGJToday(float plannedGJToday) {
		this.plannedGJToday = plannedGJToday;
	}

	public float getActualGJToday() {
		return actualGJToday;
	}

	public void setActualGJToday(float actualGJToday) {
		this.actualGJToday = actualGJToday;
	}

	public float getPlannedGJYesterday() {
		return plannedGJYesterday;
	}

	public void setPlannedGJYesterday(float plannedGJYesterday) {
		this.plannedGJYesterday = plannedGJYesterday;
	}

	public float getCalculatedGJYesterday() {
		return calculatedGJYesterday;
	}

	public void setCalculatedGJYesterday(float calculatedGJYesterday) {
		this.calculatedGJYesterday = calculatedGJYesterday;
	}

	public float getActualGJYesterday() {
		return actualGJYesterday;
	}

	public void setActualGJYesterday(float actualGJYesterday) {
		this.actualGJYesterday = actualGJYesterday;
	}

	public float getPressureOut() {
		return pressureOut;
	}

	public void setPressureOut(float pressureOut) {
		this.pressureOut = pressureOut;
	}

	public float getPressureIn() {
		return pressureIn;
	}

	public void setPressureIn(float pressureIn) {
		this.pressureIn = pressureIn;
	}

	public float getTemperatureOut() {
		return temperatureOut;
	}

	public void setTemperatureOut(float temperatureOut) {
		this.temperatureOut = temperatureOut;
	}

	public float getTemperatureIn() {
		return temperatureIn;
	}

	public void setTemperatureIn(float temperatureIn) {
		this.temperatureIn = temperatureIn;
	}

	public boolean setIsChaoBiao() {
		return isChaoBiao;
	}

	public void setChaoBiao(boolean isChaoBiao) {
		this.isChaoBiao = isChaoBiao;
	}

	public int getStationId() {
		return stationId;
	}

	public void setStationId(int stationId) {
		this.stationId = stationId;
	}

	public String getStationName() {
		return stationName;
	}

	public void setStationName(String stationName) {
		this.stationName = stationName;
	}
}
