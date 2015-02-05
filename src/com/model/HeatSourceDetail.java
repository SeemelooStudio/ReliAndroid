package com.model;

public class HeatSourceDetail {

	private int heatSourceId;
	
	private int heatSourceRecentId;
	
	private String heatSourceName;
	
	private float pressureIn;
	
	private float pressureOut;
	
	private float temperatureIn;
	
	private float temperatureOut;

	private float instHeat;
	
	private float instWater;
	
	private float accuHeat;
	
	private float accuWater;

    private float accuWaterIn;
	
	private float waterSupply;
	
	private String name;

    public String getLastUpdatedAt() {
        return lastUpdatedAt;
    }

    public void setLastUpdatedAt(String lastUpdatedAt) {
        this.lastUpdatedAt = lastUpdatedAt;
    }

    private String lastUpdatedAt;
	
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

	public float getPressureIn() {
		return pressureIn;
	}

	public void setPressureIn(float pressureIn) {
		this.pressureIn = pressureIn;
	}

	public float getPressureOut() {
		return pressureOut;
	}

	public void setPressureOut(float pressureOut) {
		this.pressureOut = pressureOut;
	}

	public float getTemperatureIn() {
		return temperatureIn;
	}

	public void setTemperatureIn(float temperatureIn) {
		this.temperatureIn = temperatureIn;
	}

	public float getTemperatureOut() {
		return temperatureOut;
	}

	public void setTemperatureOut(float temperatureOut) {
		this.temperatureOut = temperatureOut;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getHeatSourceRecentId() {
		return heatSourceRecentId;
	}

	public void setHeatSourceRecentId(int heatSourceRecentId) {
		this.heatSourceRecentId = heatSourceRecentId;
	}

	public float getInstHeat() {
		return instHeat;
	}

	public void setInstHeat(float instHeat) {
		this.instHeat = instHeat;
	}

	public float getInstWater() {
		return instWater;
	}

	public void setInstWater(float instWater) {
		this.instWater = instWater;
	}

	public float getAccuHeat() {
		return accuHeat;
	}

	public void setAccuHeat(float accuHeat) {
		this.accuHeat = accuHeat;
	}

	public float getAccuWater() {
		return accuWater;
	}

	public void setAccuWater(float accuWater) {
		this.accuWater = accuWater;
	}

	public float getWaterSupply() {
		return waterSupply;
	}

	public void setWaterSupply(float waterSupply) {
		this.waterSupply = waterSupply;
	}

    public float getAccuWaterIn() {
        return accuWaterIn;
    }

    public void setAccuWaterIn(float accuWaterIn) {
        this.accuWaterIn = accuWaterIn;
    }
}
