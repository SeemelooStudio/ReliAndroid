/**
 * Copyright (C) 2009, 2010 SC 4ViewSoft SRL
 *  
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *  
 *      http://www.apache.org/licenses/LICENSE-2.0
 *  
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.chart.impl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.PointStyle;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;

import com.model.SupplyAndBackwardItem;

/**
 * Project status demo chart.
 */
public class SupplyAndBackwardDetailChart extends AbstractChart {

	private List<SupplyAndBackwardItem> dataList;
	private int dataType;
	private int[] colors;
	private String[] titles;
	private String chartName = "";
	private String xAxisName = "";
	private String yAxisName = "时间";

	private List<Date[]> dates = new ArrayList<Date[]>();
	private List<double[]> values = new ArrayList<double[]>();

	public static final int TYPE_TEMPERATURE = 0;
	public static final int TYPE_PRESSURE = 1;

	public SupplyAndBackwardDetailChart(List<SupplyAndBackwardItem> obj,
			int dataType) {
		dataList = obj;
		this.dataType = dataType;

		switch (dataType) {
		case TYPE_TEMPERATURE:
			chartName = "温度趋势图";
			xAxisName = "温度(°)";
			titles = new String[] { "供水温度", "回水温度" };
			colors =  new int[] { Color.parseColor("#ff3fb58e"),
					Color.parseColor("#ff33b5e5") };
			break;
		case TYPE_PRESSURE:
			chartName = "压力趋势图";
			xAxisName="压力(MPa)";
			titles = new String[] { "供水压力", "回水压力" };
			colors =  new int[] { Color.parseColor("#ffec8000"),
					Color.parseColor("#ffffc801") };
			break;
		}
	}

	public String getName() {
		return chartName;
	}

	public String getDesc() {
		return chartName;
	}
	public int getDataType() {
		return dataType;
	}
	public Intent execute(Context context) {

		try {
			this.setDateAndValue();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return ChartFactory.getTimeChartIntent(context,
				buildDateDataset(titles, dates, values), getRender(),
				"yyyy-MM-dd HH:mm");
	}

	public GraphicalView createChart(Context context) {
		try {
			this.setDateAndValue();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return ChartFactory.getTimeChartView(context,
				buildDateDataset(titles, dates, values), getRender(),
				"yyyy-MM-dd HH:mm");
	}

	private void setDateAndValue() throws ParseException {

		int length = titles.length;
		int dataSize = dataList.size();
		double supply[] = new double[dataSize];
		double backward[] = new double[dataSize];

		for (int i = 0; i < length; i++) {
			dates.add(new Date[dataList.size()]);
			for (int j = 0; j < dataSize; j++) {
				SupplyAndBackwardItem item = dataList.get(j);
				supply[i] = item.getSupply();
				backward[i] = item.getBackward();
			}
		}
		values.add(supply);
		values.add(backward);
	}

	private XYMultipleSeriesRenderer getRender() {

		PointStyle[] styles = new PointStyle[] { PointStyle.POINT,
				PointStyle.POINT };
		XYMultipleSeriesRenderer renderer = buildRenderer(colors, styles);
		
        switch (dataType) {
		case TYPE_TEMPERATURE:

			setChartSettings(renderer, chartName, yAxisName, xAxisName,
					0, 0,0, 120,
					Color.parseColor("#33FFFFFF"), Color.WHITE);
			break;
		case TYPE_PRESSURE:
			setChartSettings(renderer, chartName, yAxisName, xAxisName,
					0, 0,0, 1.8,
					Color.parseColor("#33FFFFFF"), Color.WHITE);
			break;
        }
		
		
		int length = renderer.getSeriesRendererCount();
        for (int i = 0; i < length; i++) {
            XYSeriesRenderer seriesRenderer = (XYSeriesRenderer) renderer
                    .getSeriesRendererAt(i);                
            seriesRenderer.setLineWidth(4f);
            seriesRenderer.setChartValuesSpacing(10);
            seriesRenderer.setChartValuesTextSize(16);
            seriesRenderer.setDisplayChartValues(true);

        }
        

        renderer.setExternalZoomEnabled(true);
		renderer.setShowGrid(true);
		renderer.setChartTitleTextSize(24);
		return renderer;
	}

}
