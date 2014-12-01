package com.model;

import java.util.Date;

public class SupplyAndBackwardItem {

	private Date  time;
	private float  temperatureSupply;
	private float  temperatureBackward;
	private float  pressureSupply;
	private float  pressureBackward;
	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public float getTemperatureSupply() {
		return temperatureSupply;
	}

	public void setTemperatureSupply(float supply) {
		this.temperatureSupply = supply;
	}

	public float getTemperatureBackward() {
		return temperatureBackward;
	}

	public void setTemperatureBackward(float backward) {
		this.temperatureBackward = backward;
	}	
	
	public float getPressureSupply() {
		return pressureSupply;
	}

	public void setPressureSupply(float supply) {
		this.pressureSupply = supply;
	}

	public float getPressureBackward() {
		return pressureBackward;
	}

	public void setPressureBackward(float backward) {
		this.pressureBackward = backward;
	}
}
