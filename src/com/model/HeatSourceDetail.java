package com.model;

public class HeatSourceDetail {

	private String heatSourceId;
	
	private String heatSourceName;
	
	private String pressureOut;
	
	private String pressureIn;
	
	private String temperatureOut;
	
	private String temperatureIn;

	public String getHeatSourceId() {
		return heatSourceId;
	}

	public void setHeatSourceId(String strHeatSourceId) {
		this.heatSourceId = strHeatSourceId;
	}

	public String getHeatSourceName() {
		return heatSourceName;
	}

	public void setHeatSourceName(String strHeatSourceName) {
		this.heatSourceName = strHeatSourceName;
	}

	public String getPressureOut() {
		return pressureOut;
	}

	public void setPressureOut(String strPressureOut) {
		this.pressureOut = strPressureOut;
	}

	public String getPressureIn() {
		return pressureIn;
	}

	public void setPressureIn(String strPressureIn) {
		this.pressureIn = strPressureIn;
	}

	public String getTemperatureOut() {
		return temperatureOut;
	}

	public void setTemperatureOut(String strTemperatureOut) {
		this.temperatureOut = strTemperatureOut;
	}

	public String getTemperatureIn() {
		return temperatureIn;
	}

	public void setTemperatureIn(String strTemperatureIn) {
		this.temperatureIn = strTemperatureIn;
	}

}
