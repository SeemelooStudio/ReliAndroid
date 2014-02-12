package com.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.model.HeatSourceDetail;
import com.reqst.BusinessRequest;
import com.util.BaseHelper;
import com.util.ConstDefine;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

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
	private int _heatSourceId;
	private String _eastOrWest;
	private String _innerOrOuter;
	private int _peakCoalCount;
	private int _peakGasCount;
	private String _waterLine;
	private String _gasLine;
	private boolean _isInSystem; 
	
	private ArrayList<HeatSourceDetail> _heatSourceDetail= new ArrayList<HeatSourceDetail>();
	private ListView _detailList = null;
	private ProgressDialog diaLogProgress= null;
	
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
			setHeatSourceId(mBundle.getInt("heat_source_id"));
			_eastOrWest = mBundle.getString("heat_source_east_or_west");
			_innerOrOuter = mBundle.getString("heat_source_inner_or_outer");
			_peakCoalCount = mBundle.getInt("heat_source_peak_coal_count" );
			_peakGasCount  = mBundle.getInt("heat_source_peak_gas_count" );
			_waterLine  = mBundle.getString("heat_source_water_line" );
			_gasLine  = mBundle.getString("heat_source_gas_line" );
			_isInSystem  = mBundle.getBoolean("heat_source_is_in_system" );
		}
		
		_detailList = (ListView)findViewById(R.id.heat_source_detail_list);
		getDetail();
		renderDetail();
		getUnitList(); 
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
	    
		for (int i = 0; i < _heatSourceDetail.size(); i ++ ) 
		{   
			HashMap<String, Object> item = new HashMap<String, Object>();
		    item.put("id", "#" + (i+1) + ":");
		    item.put("name", _heatSourceDetail.get(i).getName());
		    item.put("supply_temperature", _heatSourceDetail.get(i).getTemperatureOut() + getString(R.string.degree_unit));
		    item.put("backward_temperature",_heatSourceDetail.get(i).getTemperatureIn() + getString(R.string.degree_unit));
		    item.put("supply_pressure",_heatSourceDetail.get(i).getPressureOut() + getString(R.string.pressure_unit));
		    item.put("backward_pressure",_heatSourceDetail.get(i).getPressureIn() + getString(R.string.pressure_unit));
		    data.add(item);
		}
		

		ListView historyList = (ListView)findViewById(R.id.heat_source_unit_list);
		
		historyList.setOnItemClickListener(new OnItemClickListener() {
		    @Override
		    public void onItemClick(AdapterView<?> parent, View view,
		            int position, long id) {
		    		Intent intent = new Intent(HeatSourceDetailActivity.this, UnitDetailActivity.class);
				  Bundle bundle = new Bundle();
				  HeatSourceDetail detail = _heatSourceDetail.get(position);
				  bundle.putString("heat_source_recent_name", detail.getName());
				  bundle.putInt("heat_source_recent_id", detail.getHeatSourceRecentId());
				  bundle.putInt("heat_source_id", detail.getHeatSourceId());
				  bundle.putFloat("heat_source_recent_instHeat", detail.getInstHeat());
				  bundle.putFloat("heat_source_recent_instWater", detail.getInstWater());
				  bundle.putFloat("heat_source_recent_accuHeat", detail.getAccuHeat());
				  bundle.putFloat("heat_source_recent_accuWater", detail.getAccuWater());
				  bundle.putFloat("heat_source_recent_waterSupply", detail.getWaterSupply());
				  
				  bundle.putFloat("heat_source_recent_temperatureIn", detail.getTemperatureIn());
				  bundle.putFloat("heat_source_recent_temperatureOut", detail.getTemperatureOut());
				  bundle.putFloat("heat_source_recent_pressureIn", detail.getPressureIn());
				  bundle.putFloat("heat_source_recent_pressureOut", detail.getPressureOut());
				  
				  
				  intent.putExtras(bundle);
				  startActivity(intent);
		    }
		});

		historyList.setAdapter( new SimpleAdapter(this, data, R.layout.unit_list_item,  
	 			 new String[] {"id", "name", "supply_temperature", "backward_temperature", "supply_pressure", "backward_pressure"}, 
				  new int[] {R.id.unit_item_id, R.id.unit_item_name, R.id.unit_item_supply_temperature, R.id.unit_item_backward_temperature, R.id.unit_item_supply_pressure, R.id.unit_item_backward_pressure}));

	}
	private void getDetail(){
		diaLogProgress = BaseHelper.showProgress(HeatSourceDetailActivity.this,ConstDefine.I_MSG_0003,false);
		new Thread() {
	        public void run() { 
	                Message msgSend = new Message();
	        	    try {
	        	    	_heatSourceDetail = BusinessRequest.getHeatSourceDetail( ""+ _heatSourceId );
	        	    	msgSend.what = ConstDefine.MSG_I_HANDLE_OK;
					} catch (Exception e) {
						msgSend.what = ConstDefine.MSG_I_HANDLE_Fail;
					}
	                handler.sendMessage(msgSend);
	        	}
	    }.start();
	}
	
	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {               
        public void handleMessage(Message message) {
                switch (message.what) {
                case ConstDefine.MSG_I_HANDLE_OK:  
        			renderUnitList();
        		 	diaLogProgress.dismiss();
        		    break;
                case ConstDefine.MSG_I_HANDLE_Fail:                                        
                	//close process
                	diaLogProgress.dismiss();
                	BaseHelper.showToastMsg(HeatSourceDetailActivity.this,ConstDefine.E_MSG_0001);
                    break;
	            }
	        }
	  };
	private void renderDetail(){
		List<HashMap<String, Object>> details = new ArrayList<HashMap<String, Object>>();
		
		HashMap<String, Object> item = new HashMap<String, Object>();
		item.put("name_1",getString(R.string.heat_source_area));
		item.put("value_1", _eastOrWest);
		item.put("name_2",getString(R.string.heat_source_combine_moede));
		item.put("value_2", _innerOrOuter);
		details.add(item);
		
		item = new HashMap<String, Object>();
		item.put("name_1",getString(R.string.heat_source_gas_count));
		item.put("value_1", _peakGasCount);
		item.put("name_2",getString(R.string.heat_source_coral_count));
		item.put("value_2", _peakCoalCount);
		details.add(item);

		item = new HashMap<String, Object>();
		item.put("name_1", getString(R.string.heat_source_water_line));
		item.put("value_1",_waterLine);
		item.put("name_2",getString(R.string.heat_source_steam_line));
		item.put("value_2",_gasLine);
		details.add(item);
		
		item = new HashMap<String, Object>();
		item.put("name_1", getString(R.string.heat_source_is_connect));
		if ( _isInSystem ) {
			item.put("value_1", "是");
		} else {
			item.put("value_1", "否");
		}
		
		details.add(item);		
		_detailList.setAdapter(new SimpleAdapter(this, details, R.layout.heat_source_detail_list_item,  
	 			 new String[] { "name_1","value_1", "name_2","value_2"}, 
				  new int[] {R.id.detail_item_name_1, R.id.detail_item_value_1, R.id.detail_item_name_2, R.id.detail_item_value_2 }));

	}
	public int getHeatSourceId() {
		return _heatSourceId;
	}

	public void setHeatSourceId(int heatSourceId) {
		this._heatSourceId = heatSourceId;
	}

	
}
