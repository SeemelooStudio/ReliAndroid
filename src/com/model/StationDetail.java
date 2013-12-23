package com.model;

public class StationDetail {

	private String stationId;
	
	private String stationName;
	
	private float temperatureOut;
	
	private float pressureOut;
	
	private float temperatureIn;
	
	private float pressureIn;
	
	private float instantaneousHeat;
	
	private float instantaneousWater;
	
	private float accumulatedHeat;
	
	private float accumulatedWater;
	
	private float supplyWaterQuantity;

	public String getStationId() {
		return stationId;
	}

	public void setStationId(String stationId) {
		this.stationId = stationId;
	}

	public String getStationName() {
		return stationName;
	}

	public void setStationName(String stationName) {
		this.stationName = stationName;
	}

	public float getTemperatureOut() {
		return temperatureOut;
	}

	public void setTemperatureOut(float temperatureOut) {
		this.temperatureOut = temperatureOut;
	}

	public float getPressureOut() {
		return pressureOut;
	}

	public void pressureOut(float pressureOut) {
		this.pressureOut = pressureOut;
	}

	public float getTemperatureIn() {
		return temperatureIn;
	}

	public void setTemperatureIn(float temperatureIn) {
		this.temperatureIn = temperatureIn;
	}

	public float getPressureIn() {
		return pressureIn;
	}

	public void setPressureIn(float pressureIn) {
		this.pressureIn = pressureIn;
	}

	public float getInstantaneousHeat() {
		return instantaneousHeat;
	}

	public void setInstantaneousHeat(float instantaneousHeat) {
		this.instantaneousHeat = instantaneousHeat;
	}

	public float getInstantaneousWater() {
		return instantaneousWater;
	}

	public void setInstantaneousWater(float instantaneousWater) {
		this.instantaneousWater = instantaneousWater;
	}

	public float getAccumulatedHeat() {
		return accumulatedHeat;
	}

	public void setAccumulatedHeat(float accumulatedHeat) {
		this.accumulatedHeat = accumulatedHeat;
	}

	public float getAccumulatedWater() {
		return accumulatedWater;
	}

	public void setAccumulatedWater(float accumulatedWater) {
		this.accumulatedWater = accumulatedWater;
	}

	public float getSupplyWaterQuantity() {
		return supplyWaterQuantity;
	}

	public void setSupplyWaterQuantity(float supplyWaterQuantity) {
		this.supplyWaterQuantity = supplyWaterQuantity;
	}
}
