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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.PointStyle;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint.Align;

import com.model.WeatherPreChartItem;

/**
 * Project status demo chart.
 */
public class WeatherPreChart extends AbstractChart {
  
	
	   private List<WeatherPreChartItem> lstWeather;
	   
	   private String[] titles = new String[] {"最高温度" , "最低温度", "平均温度"};
	   
	   private int[] colors =  new int[] { Color.parseColor("#ffec8000"),
				Color.parseColor("#ff33b5e5"), Color.parseColor("#ff33b5a2") };
	   
	   private  List<Date[]> dates = new ArrayList<Date[]>();
	   
	   private  List<double[]> values = new ArrayList<double[]>();
	   
	   public WeatherPreChart(List<WeatherPreChartItem> obj)
	   {
		   lstWeather = obj;
	   }
   
	  /**
	   * Returns the chart name.
	   * @return the chart name
	   */
	  public String getName() {
	    return "7 days tempreture";
	  }
	  
	  /**
	   * Returns the chart description.
	   * @return the chart description
	   */
	  public String getDesc() {
	    return "weteher prevouis";
	  }
	  
	  /**
	   * Executes the chart demo.
	   * @param context the context
	   * @return the built intent
	   */
	  public Intent execute(Context context) {
		
		try {
			this.setDateAndValue();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		  
	    return ChartFactory.getTimeChartIntent(context, 
	    		buildDateDataset(titles, dates, values), getRender(), "MM-dd");
	  }
	
	  /**
	   * 
	   * @param context
	   * @return
	   */
	  public GraphicalView createChart(Context context) {
		    try {
				this.setDateAndValue();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    
		    return ChartFactory.getTimeChartView(context, 
		    		buildDateDataset(titles, dates, values),getRender(), "MM-dd");
		  }
	
	  /**
	 * @throws ParseException 
	   * 
	   */
	  @SuppressLint("SimpleDateFormat")
	private void setDateAndValue() throws ParseException{
		  
		    int length = titles.length;
		    double high[] = new double[lstWeather.size()];
		    double shrot[] = new double[lstWeather.size()];
		    double avg[] = new double[lstWeather.size()];
		    SimpleDateFormat sdf =  new SimpleDateFormat("yyyy-MM-dd");
		    for (int i = 0; i < length; i++) {
		      for(int j = 0; j < lstWeather.size(); j++)
		      {
		    	  WeatherPreChartItem item = lstWeather.get(j);
			      dates.add(new Date[lstWeather.size()]);
			      dates.get(i)[j] = sdf.parse(item.getStrDate());
			      high[j] = Double.parseDouble(item.getStrHighTmpture());
			      shrot[j] = Double.parseDouble(item.getStrShorttemTure());
			      avg[j] = Double.parseDouble(item.getStrAvgTemperature());
		      }
		    }
		    values.add(high);
		    values.add(shrot);
		    values.add(avg);
		    length = values.get(0).length;
	  }
		  
		  
	  /**
	   * 
	   * @return
	   */
	  private XYMultipleSeriesRenderer getRender(){
		  
		  PointStyle[] styles = new PointStyle[] { PointStyle.POINT, PointStyle.POINT, PointStyle.POINT};
		  XYMultipleSeriesRenderer renderer = buildRenderer(colors, styles);
		  setChartSettings(renderer, "温度趋势", "时间", "温度", dates.get(0)[0]
			  .getTime(), dates.get(0)[dates.get(0).length - 1].getTime(), -18, 30, Color.parseColor("#33FFFFFF"), Color.WHITE);
		  
		  renderer.setXLabels(dates.get(0).length);

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
			renderer.setAxisTitleTextSize(16);
			renderer.setLegendTextSize(18);
			renderer.setXLabelsAlign(Align.CENTER);
			renderer.setXLabelsPadding(10f);

			return renderer;

	  }
	     
}
