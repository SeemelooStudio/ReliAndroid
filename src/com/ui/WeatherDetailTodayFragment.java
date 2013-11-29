package com.ui;

import java.util.HashMap;
import java.util.List;

import com.model.WeatherDetailTempInfo;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class WeatherDetailTodayFragment extends Fragment {
	private WeatherDetailTempInfo _weatherSummary;
	private List<HashMap<String, Object>> _weatherDetails;
	
	private TextView _tvStationName;
	private TextView _tvCurrentTempreture;
	private TextView _tvWeatherSummary;
	private ListView _lvWeatherDetails;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate( R.layout.weather_detail_today, container, false);	
	}
	@Override
	public void onResume() {
		super.onResume();
		Log.d("WeatherFragment", "onTodayResume");
		renderWeatherDetailData();
	}
	@Override
	public void onStart() {
		super.onStart();
		View view = getView();
		_lvWeatherDetails = (ListView)getView().findViewById(R.id.oneTabList);
		_tvCurrentTempreture = (TextView) view.findViewById(R.id.txtOneTab1);
		_tvStationName = (TextView) view.findViewById(R.id.txtOneTab2);
		_tvWeatherSummary = (TextView) view.findViewById(R.id.txtOneTab3);
	}
	
	public void renderWeatherDetailData() {
		if ( null == _weatherSummary ) {
			return;
		}

		_tvCurrentTempreture.setText(_weatherSummary.getStrForecastAverage());
		_tvStationName.setText(_weatherSummary.getStrName());
		String strMsg  = getString(R.string.today) + getString(R.string.forcast) + getString(R.string.highest_tempreture) + _weatherSummary.getStrForecastHighest() + "\n";
		       strMsg += getString(R.string.today) + getString(R.string.forcast) + getString(R.string.lowest_tempreture) + _weatherSummary.getStrForecastLowest() + "\n";
		       strMsg += getString(R.string.today) + getString(R.string.forcast) + getString(R.string.average_tempreture) + _weatherSummary.getStrForecastAverage() + "\n";
		       strMsg += getString(R.string.update_time) + _weatherSummary.getStrUpTime();
		       _tvWeatherSummary.setText(strMsg);
		
		
		if ( null == _weatherDetails ) {
			return;
		}

		//set temperature list
		_lvWeatherDetails.setAdapter(new SimpleAdapter(getActivity(), _weatherDetails, R.layout.weather_detail_item,  
	 			 new String[] { "time", "tempreture", "weather" }, 
				  new int[] {R.id.w_time, R.id.w_tempreture, R.id.w_weather}));
		_lvWeatherDetails.setTextFilterEnabled(true); 

	}
	
	public void setWeatherDetailInfo(WeatherDetailTempInfo weatherSummary) {
		_weatherSummary = weatherSummary;
	}
	public void setWeatherDetailList(List<HashMap<String, Object>> weatherDetails) {
		_weatherDetails = weatherDetails;
	}
}
