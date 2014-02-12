package com.model;

public class StationListItem {

	private int stationId;
	
	private String stationName;
	
	private String type;
	
	private String eastOrWest;

	public int getStationId() {
		return stationId;
	}

	public void setStationId(int stationId) {
		this.stationId = stationId;
	}

	public String getStationName() {
		return stationName;
	}

	public void setStationName(String strStationName) {
		this.stationName = strStationName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getEastOrWest() {
		return eastOrWest;
	}

	public void setEastOrWest(String eastOrWest) {
		this.eastOrWest = eastOrWest;
	}

}
