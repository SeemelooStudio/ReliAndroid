package com.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import com.model.WeatherDetailItem;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SimpleAdapter;

public class WeatherDetailHistoryFragment extends Fragment implements SearchView.OnQueryTextListener{
	private List<HashMap<String, Object>> _parsedWeatherDetials;
	private ArrayList<WeatherDetailItem> _originWeatherDetails;
	private ListView _lvWeatherHistory;

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
		_lvWeatherHistory = (ListView)getView().findViewById(R.id.fourTabList);
	}
	@Override
	public void onResume() {
		super.onResume();
		renderWeatherDetailData();
	}
	
	public void renderWeatherDetailData() {

		if ( null == _parsedWeatherDetials ) {
			return;
		}
		View view = getView();
		
		_lvWeatherHistory.setAdapter(new SimpleAdapter(getActivity().getApplicationContext(),_parsedWeatherDetials, R.layout.weather_detail_item,  
				new String[] { "time", "tempreture", "weather" }, 
				  new int[] {R.id.w_time, R.id.w_tempreture, R.id.w_weather}));
		_lvWeatherHistory.setTextFilterEnabled(true); 
		
		SearchView searchView = (SearchView) view.findViewById(R.id.wether_his_search);    
		searchView.setOnQueryTextListener( this );
        searchView.setSubmitButtonEnabled(false); 

	}
	

	public void setOriginDataList(ArrayList<WeatherDetailItem> originWeatherDetails) {
		_originWeatherDetails = originWeatherDetails;
		_parsedWeatherDetials = parseWeatherDetails(_originWeatherDetails);
	}
	@Override  
    public boolean onQueryTextChange(String newText) {  
     	List<HashMap<String, Object>>  resultlst = searchHisItem(newText);  
        updateLayout(resultlst);  
        return false;  
     } 
	 
    @Override  
    public boolean onQueryTextSubmit(String query) {  
	   return false;  
    }
	private void updateLayout(List<HashMap<String, Object>> resultList) {  
		
		_lvWeatherHistory.setAdapter(new SimpleAdapter( getActivity(), resultList, R.layout.weather_detail_item,  
				  new String[] { "time", "tempreture", "weather" }, 
				  new int[] {R.id.w_time, R.id.w_tempreture, R.id.w_weather}));
				
	} 
	

   private List<HashMap<String, Object>> searchHisItem(String name) 
	{  
		List<HashMap<String, Object>> mSearchHisList = new ArrayList<HashMap<String, Object>>(); 
	    
	    for (int i = 0; i < _originWeatherDetails.size(); i++) 
	    { 	
	        int index =((WeatherDetailItem) _originWeatherDetails.get(i)).getW_time().indexOf(name);  

		    if (index != -1) {
		    	HashMap<String, Object> item = new HashMap<String, Object>(); 
		    	item.put("time", _originWeatherDetails.get(i).getW_time()); 
		        item.put("tempreture", _originWeatherDetails.get(i).getW_wendu()); 
		        item.put("weather", _originWeatherDetails.get(i).getW_tianqi()); 
		        mSearchHisList.add(item);  
		    } 
	    }
	    
	    return mSearchHisList;
	}
   
   private List<HashMap<String, Object>> parseWeatherDetails(ArrayList<WeatherDetailItem> originWeatherDetails) {  
	   
		List<HashMap<String, Object>> parsedWeatherDetails = new ArrayList<HashMap<String, Object>>(); 
		for (WeatherDetailItem oneRec: originWeatherDetails) 
		{   
			HashMap<String, Object> item = new HashMap<String, Object>();  
		    item.put("time", oneRec.getW_time()); 
			item.put("tempreture", oneRec.getW_wendu()); 
			item.put("weather", oneRec.getW_tianqi()); 
			parsedWeatherDetails.add(item);  
		}
		return parsedWeatherDetails;
	} 
}
