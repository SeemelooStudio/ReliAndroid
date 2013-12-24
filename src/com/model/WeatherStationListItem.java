package com.model;

public class WeatherStationListItem {
	
	private String strListId;
	private String strListName;
	private String strListOther;
	private Double value = 0.0;
	
	public String getStrListId() {
		return strListId;
	}
	public void setStrListId(String list_id) {
		this.strListId = list_id;
	}
	public String getStrListName() {
		return strListName;
	}
	public void setStrListName(String list_name) {
		this.strListName = list_name;
	}
	public String getStrListOther() {
		return strListOther;
	}
	public void setStrListOther(String list_other) {
		this.strListOther = list_other;
	}
	public Double getValue() {
		return value;
	}
	public void setValue(Double value) {
		this.value = value;
	}
	
}
