package com.ui;

import java.util.List;

import org.achartengine.GraphicalView;

import com.chart.impl.WeatherPreChart;
import com.model.WeatherPreChartItem;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class WeatherDetailWeekFragment extends Fragment {
	private List<WeatherPreChartItem> weatherDetialList = null;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.weather_detail_week, container, false);	
	}
	
	@Override
	public void onResume() {
		super.onResume();
		Log.d("WeatherFragment", "onWeekResume");
		renderWeatherDetailData();
	}
	
	public void renderWeatherDetailData() {

		if ( null == weatherDetialList ) {
			return;
		}
		View view = getView();
        //set chart
	    WeatherPreChart  weatherChart = new WeatherPreChart(weatherDetialList);
		LinearLayout layout = (LinearLayout) view.findViewById(R.id.weather_chart);
		
		GraphicalView mChartView = weatherChart.createChart(getActivity());
		layout.addView(mChartView);

	}
	
	public void setWeatherDetailList(List<WeatherPreChartItem> listData) {
		weatherDetialList = listData;
	}
}
