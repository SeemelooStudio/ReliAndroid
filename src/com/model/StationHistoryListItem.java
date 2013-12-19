package com.model;

import java.util.Date;

public class StationHistoryListItem {
	
	private Date date;
	private double planGJ;
	private double calculateGJ;
	private double actualGJ;
	private double actualOverCalculateGJ;
	private double forcastTemperature;
	private double actualTemperature;
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public double getPlanGJ() {
		return planGJ;
	}
	public void setPlanGJ(double planGJ) {
		this.planGJ = planGJ;
	}
	public double getActualGJ() {
		return actualGJ;
	}
	public void setActualGJ(double actualGJ) {
		this.actualGJ = actualGJ;
	}
	public double getActualOverCalculateGJ() {
		return actualOverCalculateGJ;
	}
	public void setActualOverCalculateGJ(double actualOverCalculateGJ) {
		this.actualOverCalculateGJ = actualOverCalculateGJ;
	}
	public double getCalculateGJ() {
		return calculateGJ;
	}
	public void setCalculateGJ(double calculateGJ) {
		this.calculateGJ = calculateGJ;
	}
	public double getForcastTemperature() {
		return forcastTemperature;
	}
	public void setForcastTemperature(double forcastTemperature) {
		this.forcastTemperature = forcastTemperature;
	}
	public double getActualTemperature() {
		return actualTemperature;
	}
	public void setActualTemperature(double actualTemperature) {
		this.actualTemperature = actualTemperature;
	}
	
	
	
}
