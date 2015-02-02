package com.model;

/**
 * Created by yaqiZhao on 2/2/2015.
 */
public class Overview {
    public float[] getInstanceHeat() {
        return instanceHeat;
    }

    public void setInstanceHeat(float[] instanceHeat) {
        this.instanceHeat = instanceHeat;
    }

    private float[] instanceHeat;

    public float[] getArea() {
        return area;
    }

    public void setArea(float[] area) {
        this.area = area;
    }

    private float[] area;

    public float[] getUnitHeatLoad() {
        return unitHeatLoad;
    }

    public void setUnitHeatLoad(float[] unitHeatLoad) {
        this.unitHeatLoad = unitHeatLoad;
    }

    private float[] unitHeatLoad;
}
