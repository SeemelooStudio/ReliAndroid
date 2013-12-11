package com.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.model.WeatherStationListItem;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SimpleAdapter;

public class WeatherDetailStationsFragment extends Fragment implements
		SearchView.OnQueryTextListener {
	private ArrayList<WeatherStationListItem> _originWeatherStations;
	private List<HashMap<String, Object>> _parsedWeatherStations;
	private ListView _lvWeatherStations;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.weather_list, container, false);
	}

	@Override
	public void onStart() {
		super.onStart();
		_lvWeatherStations = (ListView) getView().findViewById(
				R.id.station_list);
		_lvWeatherStations.setTextFilterEnabled(true);
	}

	@Override
	public void onResume() {
		super.onResume();
		renderWeatherStationsData();
	}

	public void renderWeatherStationsData() {

		if (null == _parsedWeatherStations) {
			return;
		}
		View view = getView();
		_lvWeatherStations.setAdapter(new SimpleAdapter(getActivity()
				.getApplicationContext(), _parsedWeatherStations,
				R.layout.weather_station_list_item, new String[] { "list_id", "list_name",
						"list_other" }, new int[] { R.id.list_id,
						R.id.list_name, R.id.list_other }));

		SearchView searchView = (SearchView) view
				.findViewById(R.id.weather_search_view);
		searchView.setOnQueryTextListener(this);
		searchView.setSubmitButtonEnabled(false);

	}

	private List<HashMap<String, Object>> parseWeatherStations(
			ArrayList<WeatherStationListItem> originWeatherStations) {

		List<HashMap<String, Object>> parsedWeatherStations = new ArrayList<HashMap<String, Object>>();
		for (WeatherStationListItem oneRec : originWeatherStations) {
			HashMap<String, Object> item = new HashMap<String, Object>();
			item.put("list_id", oneRec.getStrListId());
			item.put("list_name", oneRec.getStrListName());
			item.put("list_other", oneRec.getStrListOther());
			parsedWeatherStations.add(item);
		}
		return parsedWeatherStations;
	}

	@Override
	public boolean onQueryTextChange(String newText) {
        updateLayout( searchItem(newText) );  
		return false;
	}

	@Override
	public boolean onQueryTextSubmit(String query) {
		// TODO Auto-generated method stub
		return false;
	}

	private void updateLayout(List<HashMap<String, Object>> resultList) {

		_lvWeatherStations.setAdapter(new SimpleAdapter(getActivity(),
				resultList, R.layout.weather_station_list_item, new String[] { "list_id",
						"list_name", "list_other" }, new int[] { R.id.list_id,
						R.id.list_name, R.id.list_other }));

	}

	private List<HashMap<String, Object>> searchItem(String name) {
		List<HashMap<String, Object>> resultList = new ArrayList<HashMap<String, Object>>();

		for (int i = 0; i < _originWeatherStations.size(); i++) {
			int index = ((WeatherStationListItem) _originWeatherStations.get(i)).getStrListName()
					.indexOf(name);

			if (index != -1) {
				HashMap<String, Object> item = new HashMap<String, Object>();
				item.put("list_id", _originWeatherStations.get(i).getStrListId());
				item.put("list_name", _originWeatherStations.get(i).getStrListName());
				item.put("list_other", _originWeatherStations.get(i).getStrListOther());
				resultList.add(item);
			}
		}

		return resultList;
	}

	public void setWeatherStationList(ArrayList<WeatherStationListItem> weatherStations) {
		_originWeatherStations = weatherStations;
		_parsedWeatherStations = parseWeatherStations(weatherStations);
	}

}
