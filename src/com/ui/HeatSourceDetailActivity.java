package com.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.model.HeatSourceDetail;
import com.reqst.BusinessRequest;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class HeatSourceDetailActivity extends Activity  {

	private String _heatSourceName = "";
	private String _heatSourceId = "";
	private HeatSourceDetail _heatSourceDetail= null;
	private ListView _detailList = null;

	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.heat_source_detail);

		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setIcon(R.drawable.main_item_five_image);

		Intent inten = this.getIntent();
		Bundle mBundle = inten.getExtras();
		if (mBundle != null) {
			_heatSourceName = mBundle.getString("heat_source_name");
			this.setTitle(_heatSourceName);
			setHeatSourceId(mBundle.getString("heat_source_id"));
		}
		
		_detailList = (ListView)findViewById(R.id.heat_source_detail_list);
		getDetail();
		renderDetail();
		getUnitList(); 
		renderUnitList();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    // Inflate the menu items for use in the action bar
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.station_detail_activity_actions, menu);
	    return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		// Respond to the action bar's Up/Home button
		case android.R.id.home:
			this.finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	private void getUnitList() {
		
	}
	private void renderUnitList() {
		List<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>(); 
	    
		for (int i = 0; i < 3; i ++ ) 
		{   
			HashMap<String, Object> item = new HashMap<String, Object>();
			
		    item.put("id", "#" + (i+1));
		    item.put("name", "两广线");
		    item.put("supply_temperature","100.1" + getString(R.string.degree_unit));
		    item.put("backward_temperature","40.2" + getString(R.string.degree_unit));
		    item.put("supply_pressure","0.84" + getString(R.string.pressure_unit));
		    item.put("backward_pressure","0.42" + getString(R.string.pressure_unit));

		    data.add(item);
		}
		

		ListView historyList = (ListView)findViewById(R.id.heat_source_unit_list);
		
		historyList.setOnItemClickListener(new OnItemClickListener() {
		    @Override
		    public void onItemClick(AdapterView<?> parent, View view,
		            int position, long id) {
		    		Intent intent = new Intent(HeatSourceDetailActivity.this, UnitDetailActivity.class);
				  Bundle bundle = new Bundle();
				  bundle.putString("heat_source_name", "两广线");
				  bundle.putString("heat_source_id", "0");
				  intent.putExtras(bundle);
				  startActivity(intent);

		    }
		});

		historyList.setAdapter( new SimpleAdapter(this, data, R.layout.unit_list_item,  
	 			 new String[] {"id", "name", "supply_temperature", "backward_temperature", "supply_pressure", "backward_pressure"}, 
				  new int[] {R.id.unit_item_id, R.id.unit_item_name, R.id.unit_item_supply_temperature, R.id.unit_item_backward_temperature, R.id.unit_item_supply_pressure, R.id.unit_item_backward_pressure}));

	}
	private void getDetail(){
		_heatSourceDetail = BusinessRequest.getHeatSourceDetail( _heatSourceId );
			
	}
	private void renderDetail(){
		List<HashMap<String, Object>> details = new ArrayList<HashMap<String, Object>>();
		
		HashMap<String, Object> item = new HashMap<String, Object>();
		item.put("name_1",getString(R.string.heat_source_area));
		item.put("value_1", _heatSourceDetail.getArea());
		item.put("name_2",getString(R.string.heat_source_combine_moede));
		item.put("value_2", _heatSourceDetail.getCombineMode() );
		details.add(item);
		
		item = new HashMap<String, Object>();
		item.put("name_1",getString(R.string.heat_source_gas_count));
		item.put("value_1", _heatSourceDetail.getGasfiredBoilerCount() );
		item.put("name_2",getString(R.string.heat_source_coral_count));
		item.put("value_2", _heatSourceDetail.getCoalfiredBoilerCount() );
		details.add(item);

		item = new HashMap<String, Object>();
		item.put("name_1", getString(R.string.heat_source_water_line));
		item.put("value_1", _heatSourceDetail.getWaterLineName());
		item.put("name_2",getString(R.string.heat_source_steam_line));
		item.put("value_2", _heatSourceDetail.getSteamLineName() );
		details.add(item);
		
		item = new HashMap<String, Object>();
		item.put("name_1", getString(R.string.heat_source_is_connect));
		if ( _heatSourceDetail.isGridConnected() ) {
			item.put("value_1", "是");
		} else {
			item.put("value_1", "否");
		}
		
		details.add(item);		
		_detailList.setAdapter(new SimpleAdapter(this, details, R.layout.heat_source_detail_list_item,  
	 			 new String[] { "name_1","value_1", "name_2","value_2"}, 
				  new int[] {R.id.detail_item_name_1, R.id.detail_item_value_1, R.id.detail_item_name_2, R.id.detail_item_value_2 }));

	}
	public String getHeatSourceId() {
		return _heatSourceId;
	}

	public void setHeatSourceId(String stationId) {
		this._heatSourceId = stationId;
	}

	
}
