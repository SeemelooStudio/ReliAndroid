package com.ui;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import com.model.WeatherDetailItem;
import com.reqst.BusinessRequest;

import android.support.v4.app.Fragment;
import android.text.format.DateFormat;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SimpleAdapter;

public class WeatherDetailHistoryFragment extends Fragment implements SearchView.OnQueryTextListener{
	private List<HashMap<String, Object>> _parsedWeatherDetails;
	private ArrayList<WeatherDetailItem> _originWeatherDetails;
	private ListView _lvWeatherHistory;
	private EditText _searchFromDate;
    private EditText _searchToDate;
    
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.weather_detail_history, container, false);	
	}
	@Override
	public void onStart() {
		super.onStart();
		_lvWeatherHistory = (ListView)getView().findViewById(R.id.weather_history_list);
		initHistorySearchView();
	}
	
	@Override
	public void onResume() {
		super.onResume();
		initHistorySearchView();
		renderWeatherDetailData();
	}
	
	public void search() throws Exception {
		Date fromDate = new SimpleDateFormat("yyyy-MM-dd").parse(_searchFromDate.getText().toString());
		Date toDate =  new SimpleDateFormat("yyyy-MM-dd").parse(_searchToDate.getText().toString());
		_originWeatherDetails = BusinessRequest.getWeatherHisListData(fromDate, toDate);
		_parsedWeatherDetails = parseWeatherDetails(_originWeatherDetails);
	}
	
	private void initHistorySearchView() {
		_searchFromDate = (EditText) getView().findViewById(R.id.pick_from_date) ;
		_searchToDate = (EditText) getView().findViewById(R.id.pick_to_date);
	
		InputMethodManager imm = (InputMethodManager)this.getActivity().getSystemService(
	    	      Context.INPUT_METHOD_SERVICE);
		_searchFromDate.setInputType(0);
		_searchToDate.setInputType(0);
	    imm.hideSoftInputFromWindow(_searchFromDate.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
	    imm.hideSoftInputFromWindow(_searchToDate.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);	
	}
	
	public void renderWeatherDetailData() {

		if ( null == _parsedWeatherDetails ) {
			return;
		}
		_lvWeatherHistory.setAdapter(new SimpleAdapter(getActivity().getApplicationContext(),_parsedWeatherDetails, R.layout.weather_detail_item,  
				new String[] { "day", "forecast", "actual" }, 
				  new int[] {R.id.day, R.id.forecast, R.id.actual}));
		_lvWeatherHistory.setTextFilterEnabled(true); 

	}
	

	public void setOriginDataList(ArrayList<WeatherDetailItem> originWeatherDetails) {
		_originWeatherDetails = originWeatherDetails;
		_parsedWeatherDetails = parseWeatherDetails(_originWeatherDetails);
	}
	@Override  
    public boolean onQueryTextChange(String newText) {  
        return false;  
     } 
	 
    @Override  
    public boolean onQueryTextSubmit(String query) {  
	   return false;  
    }
	public void updateLayout(List<HashMap<String, Object>> resultList) {  
		
		_lvWeatherHistory.setAdapter(new SimpleAdapter( getActivity(), resultList, R.layout.weather_detail_item,  
				  new String[] { "day", "forecastAverage", "actualAverage" }, 
				  new int[] {R.id.day, R.id.forecast, R.id.actual}));
				
	} 
	
   
   private List<HashMap<String, Object>> parseWeatherDetails(ArrayList<WeatherDetailItem> originWeatherDetails) {  
	   List<HashMap<String, Object>> parsedWeatherDetails = new ArrayList<HashMap<String, Object>>(); 
	   for (WeatherDetailItem oneRec: originWeatherDetails) 
	   {   
			HashMap<String, Object> item = new HashMap<String, Object>();  
		    item.put("day", new SimpleDateFormat("MM月dd日").format( oneRec.getDay() )); 
			item.put("forecast", oneRec.getForecastAverage() + "°"); 
			item.put("actual", oneRec.getActualAverage() + "°"); 
			parsedWeatherDetails.add(item);  
		}
		return parsedWeatherDetails;
	} 
}
