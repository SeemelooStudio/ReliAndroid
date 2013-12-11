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

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;

import com.model.WeatherPreChartItem;

/**
 * Project status demo chart.
 */
public class WeatherPreChart extends AbstractChart {
  
	
	   private List<WeatherPreChartItem> lstWeather;
	   
	   private String[] titles = new String[] { "abc", "def"};
	   
	   private int[] colors = new int[] { Color.BLUE, Color.GREEN };
	   
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
	    return "7��������Ԥ��";
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
	    		buildDateDataset(titles, dates, values), getRender(), "yyyy-MM-dd");
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
		    		buildDateDataset(titles, dates, values),getRender(), "yyyy-MM-dd");
		  }
	
	  /**
	 * @throws ParseException 
	   * 
	   */
	  private void setDateAndValue() throws ParseException{
		  
		    int length = titles.length;
		    double high[] = new double[lstWeather.size()];
		    double shrot[] = new double[lstWeather.size()];
		    SimpleDateFormat sdf =  new SimpleDateFormat("yyyy-MM-dd");
		    for (int i = 0; i < length; i++) {
		      for(int j = 0; j < lstWeather.size(); j++)
		      {
		    	  WeatherPreChartItem item = lstWeather.get(j);
			      dates.add(new Date[lstWeather.size()]);
			      dates.get(i)[j] = sdf.parse(item.getStrDate());
			      high[j] = Double.parseDouble(item.getStrHighTmpture());
			      shrot[j] = Double.parseDouble(item.getStrShorttemTure());
		      }
		    }
		    values.add(high);
		    values.add(shrot);
		    length = values.get(0).length;
	  }
		  
		  
	  /**
	   * 
	   * @return
	   */
	  private XYMultipleSeriesRenderer getRender(){
		  
		  PointStyle[] styles = new PointStyle[] { PointStyle.POINT, PointStyle.POINT};
		  XYMultipleSeriesRenderer renderer = buildRenderer(colors, styles);
		  setChartSettings(renderer, "7�����¶�", "����", "�¶�", dates.get(0)[0]
			  .getTime(), dates.get(0)[6].getTime(), 0, 200, Color.GRAY, Color.LTGRAY);
		  renderer.setXLabels(7);
		  renderer.setYLabels(10);
		  renderer.setDisplayChartValues(true);
		  return renderer;
	  }
	     
}
