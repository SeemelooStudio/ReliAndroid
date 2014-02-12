package com.model;

import java.util.Date;

public class HeatSourceMainItem {

	private int heatSourceId;
	
	private String heatSourceName;
	
	private Date  lastUpdatedAt;
	
	
	private String heatSourceType;
	
	private String eastOrWest;
	
	private String innerOrOuter;
	
	private String waterLine;
	
	private String gasLine;
	
	private int peakCoalCount;
	
	private int peakGasCount;
	
	private boolean isInSystem;
	
	public int getHeatSourceId() {
		return heatSourceId;
	}

	public void setHeatSourceId(int heatSourceId) {
		this.heatSourceId = heatSourceId;
	}

	public String getHeatSourceName() {
		return heatSourceName;
	}

	public void setHeatSourceName(String heatSourceName) {
		this.heatSourceName = heatSourceName;
	}

	public Date getLastUpdatedAt() {
		return lastUpdatedAt;
	}

	public void setLastUpdatedAt(Date lastUpdatedAt) {
		this.lastUpdatedAt = lastUpdatedAt;
	}

	

	public String getHeatSourceType() {
		return heatSourceType;
	}

	public void setHeatSourceType(String heatSourceType) {
		this.heatSourceType = heatSourceType;
	}

	public String getEastOrWest() {
		return eastOrWest;
	}

	public void setEastOrWest(String eastOrWest) {
		this.eastOrWest = eastOrWest;
	}

	public String getInnerOrOuter() {
		return innerOrOuter;
	}

	public void setInnerOrOuter(String innerOrOuter) {
		this.innerOrOuter = innerOrOuter;
	}

	public String getWaterLine() {
		return waterLine;
	}

	public void setWaterLine(String waterLine) {
		this.waterLine = waterLine;
	}

	public String getGasLine() {
		return gasLine;
	}

	public void setGasLine(String gasLine) {
		this.gasLine = gasLine;
	}

	public int getPeakCoalCount() {
		return peakCoalCount;
	}

	public void setPeakCoalCount(int peakCoalCount) {
		this.peakCoalCount = peakCoalCount;
	}

	public int getPeakGasCount() {
		return peakGasCount;
	}

	public void setPeakGasCount(int peakGasCount) {
		this.peakGasCount = peakGasCount;
	}

	public boolean isInSystem() {
		return isInSystem;
	}

	public void setInSystem(boolean isInSystem) {
		this.isInSystem = isInSystem;
	}

	

}
