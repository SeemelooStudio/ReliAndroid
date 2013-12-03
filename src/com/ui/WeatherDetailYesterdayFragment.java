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

public class WeatherDetailYesterdayFragment extends Fragment {
	private WeatherDetailTempInfo _weatherSummary;
	private List<HashMap<String, Object>> _weatherDetails;
	
	private TextView _tvStationName;
	private TextView _tvCurrentTempreture;
	private TextView _tvWeatherSummary;
	private ListView _lvWeatherDetails;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate( R.layout.weather_detail_yesterday, container, false);	
	}
	@Override
	public void onResume() {
		super.onResume();
		Log.d("WeatherFragment", "onYesterdayResume");
		renderWeatherDetailData();
	}
	@Override
	public void onStart() {
		super.onStart();
		View view = getView();
		_lvWeatherDetails = (ListView)getView().findViewById(R.id.twoTabList);
		_tvCurrentTempreture = (TextView) view.findViewById(R.id.txtTwoTab1);
		_tvStationName = (TextView) view.findViewById(R.id.txtTwoTab2);
		_tvWeatherSummary = (TextView) view.findViewById(R.id.txtTwoTab3);
	}
	
	public void renderWeatherDetailData() {
		if ( null == _weatherSummary ) {
			return;
		}

		_tvCurrentTempreture.setText(_weatherSummary.getStrForecastAverage());
		_tvStationName.setText(_weatherSummary.getStrName());
		String strMsg  = getString(R.string.yesterday) + getString(R.string.actrual) + getString(R.string.highest_tempreture) + _weatherSummary.getStrForecastHighest() + "\n";
		       strMsg += getString(R.string.yesterday) + getString(R.string.actrual) + getString(R.string.lowest_tempreture) + _weatherSummary.getStrForecastLowest() + "\n";
		       strMsg += getString(R.string.yesterday) + getString(R.string.actrual) + getString(R.string.average_tempreture) + _weatherSummary.getStrForecastAverage() + "\n";
		       strMsg += getString(R.string.update_time) + _weatherSummary.getStrUpTime();
		       _tvWeatherSummary.setText(strMsg);
		
		
		if ( null == _weatherDetails ) {
			return;
		}

		//set temperature list
		_lvWeatherDetails.setAdapter(new SimpleAdapter(getActivity(), _weatherDetails, R.layout.weather_detail_recent_item,  
	 			 new String[] { "date","day_of_week", "tempreture", "weather" }, 
				  new int[] {R.id.w_date,R.id.w_day_of_week, R.id.w_tempreture, R.id.w_weather}));
		_lvWeatherDetails.setTextFilterEnabled(true);  

	}
	
	public void setWeatherDetailInfo(WeatherDetailTempInfo weatherSummary) {
		_weatherSummary = weatherSummary;
	}
	public void setWeatherDetailList(List<HashMap<String, Object>> weatherDetails) {
		_weatherDetails = weatherDetails;
	}
}
