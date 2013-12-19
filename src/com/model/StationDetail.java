package com.model;

public class StationDetail {

	private String strStationId;
	
	private String strStationName;
	
	private double supplyTemperature;
	
	private double supplyPressure;
	
	private double backwardTemperature;
	
	private double backwardPressure;
	
	private double realtimeHeat;
	
	private double realtimeFlow;
	
	private double totalHeat;
	
	private double totalFlow;
	
	private double supplyWaterQuantity;

	public String getStrStationId() {
		return strStationId;
	}

	public void setStrStationId(String strStationId) {
		this.strStationId = strStationId;
	}

	public String getStrStationName() {
		return strStationName;
	}

	public void setStrStationName(String strStationName) {
		this.strStationName = strStationName;
	}

	public double getSupplyTemperature() {
		return supplyTemperature;
	}

	public void setSupplyTemperature(double supplyTemperature) {
		this.supplyTemperature = supplyTemperature;
	}

	public double getSupplyPressure() {
		return supplyPressure;
	}

	public void setSupplyPressure(double supplyPressure) {
		this.supplyPressure = supplyPressure;
	}

	public double getBackwardTemperature() {
		return backwardTemperature;
	}

	public void setBackwardTemperature(double returnTemperature) {
		this.backwardTemperature = returnTemperature;
	}

	public double getBackwardPressure() {
		return backwardPressure;
	}

	public void setBackwardPressure(double returnPressure) {
		this.backwardPressure = returnPressure;
	}

	public double getRealtimeHeat() {
		return realtimeHeat;
	}

	public void setRealtimeHeat(double realtimeHeat) {
		this.realtimeHeat = realtimeHeat;
	}

	public double getRealtimeFlow() {
		return realtimeFlow;
	}

	public void setRealtimeFlow(double realtimeFlow) {
		this.realtimeFlow = realtimeFlow;
	}

	public double getTotalHeat() {
		return totalHeat;
	}

	public void setTotalHeat(double totalHeat) {
		this.totalHeat = totalHeat;
	}

	public double getTotalFlow() {
		return totalFlow;
	}

	public void setTotalFlow(double totalFlow) {
		this.totalFlow = totalFlow;
	}

	public double getSupplyWaterQuantity() {
		return supplyWaterQuantity;
	}

	public void setSupplyWaterQuantity(double supplyWaterQuantity) {
		this.supplyWaterQuantity = supplyWaterQuantity;
	}
}
