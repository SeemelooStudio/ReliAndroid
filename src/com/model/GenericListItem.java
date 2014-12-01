package com.model;

public class GenericListItem {
	
	private String strListId;
	private String strListName;
	private String strListOther;
	private float temperature = 0.0f;
	
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
	public float getTemperature() {
		return this.temperature;
	}
	public void setTemperature(float temperature) {
		this.temperature = temperature;
	}
	
}
