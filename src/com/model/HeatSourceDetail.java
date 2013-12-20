package com.model;

public class HeatSourceDetail {

	private String heatSourceId;
	
	private String heatSourceName;
	
	private String area;
	
	private String combineMode;
	
	private String waterLineName;
	
	private String steamLineName;
	
	private int gasfiredBoilerCount;
	
	private int coalfiredBoilerCount;
	
	private boolean isGridConnected;

	public String getHeatSourceId() {
		return heatSourceId;
	}

	public void setHeatSourceId(String heatSourceId) {
		this.heatSourceId = heatSourceId;
	}

	public String getHeatSourceName() {
		return heatSourceName;
	}

	public void setHeatSourceName(String heatSourceName) {
		this.heatSourceName = heatSourceName;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getCombineMode() {
		return combineMode;
	}

	public void setCombineMode(String combineMode) {
		this.combineMode = combineMode;
	}

	public String getWaterLineName() {
		return waterLineName;
	}

	public void setWaterLineName(String waterLineName) {
		this.waterLineName = waterLineName;
	}

	public String getSteamLineName() {
		return steamLineName;
	}

	public void setSteamLineName(String steamLineName) {
		this.steamLineName = steamLineName;
	}

	public int getGasfiredBoilerCount() {
		return gasfiredBoilerCount;
	}

	public void setGasfiredBoilerCount(int gasfiredBoilerCount) {
		this.gasfiredBoilerCount = gasfiredBoilerCount;
	}

	public int getCoalfiredBoilerCount() {
		return coalfiredBoilerCount;
	}

	public void setCoalfiredBoilerCount(int coalfiredBoilerCount) {
		this.coalfiredBoilerCount = coalfiredBoilerCount;
	}

	public boolean isGridConnected() {
		return isGridConnected;
	}

	public void setGridConnected(boolean isGridConnected) {
		this.isGridConnected = isGridConnected;
	}
}
