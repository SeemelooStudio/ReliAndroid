package com.model;

import java.util.Date;

public class HeatSourceHistoryListItem {
	private Date date;
	private double dailyHeat;
	public double getDailyHeat() {
		return dailyHeat;
	}
	public void setDailyHeat(double dailyHeat) {
		this.dailyHeat = dailyHeat;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
}
