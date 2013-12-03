package com.ui;

import java.util.HashMap;
import java.util.List;

import com.model.WeatherDetailTempInfo;
import com.util.DateHelper;
import com.util.WeatherIconHelper;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class WeatherDetailTodayFragment extends Fragment {
	private WeatherDetailTempInfo _weatherSummary;
	private List<HashMap<String, Object>> _weatherDetails;
	private TextView _tvUpdateTime;
	private TextView _tvCurrentTempreture;
	private TextView _tvYesterdayTempreture;
	private TextView _tvHighestTempreture;
	private TextView _tvLowestTempreture;
	private TextView _tvWind;
	private TextView _tvWeather;
	private ListView _lvWeatherDetails;
	private ImageView _ivWeatherIcon;
	
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
		_tvUpdateTime = (TextView) view.findViewById(R.id.today_date);
		_lvWeatherDetails = (ListView)view.findViewById(R.id.oneTabList);
		_tvCurrentTempreture = (TextView) view.findViewById(R.id.today_average_tempreture);
		_tvYesterdayTempreture = (TextView) view.findViewById(R.id.yesterday_average_tempreture);
		_tvWeather = (TextView) view.findViewById(R.id.today_weather);
		_tvHighestTempreture = (TextView) view.findViewById(R.id.today_highest_tempreture);
		_tvLowestTempreture = (TextView)view.findViewById(R.id.today_lowest_tempreture);
		_tvWind = (TextView)view.findViewById(R.id.today_wind);
		_ivWeatherIcon = (ImageView)view.findViewById(R.id.today_weather_icon);
	}
	
	public void renderWeatherDetailData() {
		if ( null == _weatherSummary ) {
			return;
		}
		String strDegreeUnit = getActivity().getString(R.string.degree_unit);
		String strDate = DateHelper.getFullDate(_weatherSummary.getStrUpTime(), "mm/dd/yyyy", getActivity().getApplicationContext()) 
				+ " "
				+ DateHelper.getDayOfWeek(_weatherSummary.getStrUpTime(), "mm/dd/yyyy", getActivity().getApplicationContext());
		_tvUpdateTime.setText( strDate );
		_tvCurrentTempreture.setText(_weatherSummary.getStrForecastAverage() + strDegreeUnit);
		_tvWeather.setText(_weatherSummary.getStrWeatherShortDescription());
		_tvYesterdayTempreture.setText(_weatherSummary.getStrForecastAverage() + strDegreeUnit);
		_tvWind.setText(_weatherSummary.getStrFengsu());
		_tvHighestTempreture.setText(_weatherSummary.getStrForecastHighest() + strDegreeUnit);
		_tvLowestTempreture.setText(_weatherSummary.getStrForecastLowest() + strDegreeUnit);
		
		_ivWeatherIcon.setImageResource( WeatherIconHelper.getWeatherIconResourceId(2) );
		
		if ( null == _weatherDetails ) {
			return;
		}

		//set temperature list
		_lvWeatherDetails.setAdapter(new SimpleAdapter(getActivity(), _weatherDetails, R.layout.weather_detail_recent_item,  
	 			 new String[] { "date","day_of_week", "tempreture", "weather" }, 
				  new int[] {R.id.w_date,R.id.w_day_of_week, R.id.w_tempreture, R.id.w_weather_icon}));
		_lvWeatherDetails.setTextFilterEnabled(true); 

	}
	
	public void setWeatherDetailInfo(WeatherDetailTempInfo weatherSummary) {
		_weatherSummary = weatherSummary;
	}
	public void setWeatherDetailList(List<HashMap<String, Object>> weatherDetails) {
		_weatherDetails = weatherDetails;
	}
}
