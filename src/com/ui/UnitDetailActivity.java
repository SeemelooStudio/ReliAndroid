package com.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.chart.impl.SupplyAndBackwardDetailChart;
import com.model.StationDetail;
import com.reqst.BusinessRequest;
import com.util.BaseHelper;
import com.util.ConstDefine;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class UnitDetailActivity extends FragmentActivity  {

	private String _heatSourceName = "";
	private int _heatSourceId;
	private int _heatSourceRecentId;
	private float _heatSourceRecent_InstWater;
	private float _heatSourceRecent_InstHeat;
	private float _heatSourceRecent_AccuWater;
	private float _heatSourceRecent_AccuHeat;
	private float _heatSourceRecent_WaterSupply;
	private float _temperatureIn;
	private float _temperatureOut;
	private float _pressureIn;
	private float _pressureOut;
	
	private ProgressDialog _diaLogProgress = null;
	private DetailGraphFragment _frgTemperatureGraph = null;
	private DetailGraphFragment _frgPressureGraph = null;
	private DetailHistoryFragment _frgHistory = null;
	private DetailGraphFragmentPagerAdapter _fragmentPagerAdapter = null;
	private ViewPager _viewPager = null;
	private ListView _detailList = null;
	private TextView _supplyTemperature = null;
	private TextView _supplyPressure = null;
	private TextView _backTemperature = null;
	private TextView _backPressure = null;
	
	private ArrayList<View> _tabs = new ArrayList<View>();
	private ArrayList<View> _indicators = new ArrayList<View>();
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.station_detail);

		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setIcon(R.drawable.main_item_five_image);

		Intent inten = this.getIntent();
		Bundle mBundle = inten.getExtras();
		if (mBundle != null) {
			_heatSourceName = mBundle.getString("heat_source_recent_name");
			this.setTitle(_heatSourceName);
			setHeatSourceId(mBundle.getInt("heat_source_id"));
			_heatSourceRecentId = mBundle.getInt("heat_source_recent_id");
			_heatSourceRecent_InstWater = mBundle.getFloat("heat_source_recent_instWater");
			_heatSourceRecent_InstHeat = mBundle.getFloat("heat_source_recent_instHeat");
			_heatSourceRecent_AccuWater = mBundle.getFloat("heat_source_recent_accuWater");
			_heatSourceRecent_AccuHeat = mBundle.getFloat("heat_source_recent_accuHeat");
			_heatSourceRecent_WaterSupply = mBundle.getFloat("heat_source_recent_waterSupply");
			_temperatureIn = mBundle.getFloat("heat_source_recent_temperatureIn");
			_temperatureOut = mBundle.getFloat("heat_source_recent_temperatureOut");
			_pressureIn = mBundle.getFloat("heat_source_recent_pressureIn");
			_pressureOut = mBundle.getFloat("heat_source_recent_pressureOut");
		}
		
		_detailList = (ListView)findViewById(R.id.station_detail_list);
		_supplyTemperature = (TextView)findViewById(R.id.station_supply_temperature);
		_supplyPressure = (TextView)findViewById(R.id.station_supply_pressure);
		_backTemperature = (TextView)findViewById(R.id.station_back_temperature);
		_backPressure = (TextView)findViewById(R.id.station_back_pressure);
		
		initFragments();
		initFragmentPagerAdapter();
		initTabs();
		getDetail();
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
	
	private void initFragments() {
		_frgTemperatureGraph = new DetailGraphFragment();
		_frgTemperatureGraph.setDataType(SupplyAndBackwardDetailChart.TYPE_TEMPERATURE);
		_frgTemperatureGraph.setSourceType(DetailGraphFragment.SOURCE_TYPE_HEAT_SOURCE);
		_frgTemperatureGraph.setSourceId(_heatSourceId+"");
		_frgTemperatureGraph.setUnitId(_heatSourceRecentId+"");
		
		_frgPressureGraph = new DetailGraphFragment();
		_frgPressureGraph.setDataType(SupplyAndBackwardDetailChart.TYPE_PRESSURE);
		_frgPressureGraph.setSourceType(DetailGraphFragment.SOURCE_TYPE_HEAT_SOURCE);
		_frgPressureGraph.setSourceId(_heatSourceId+"");
		_frgPressureGraph.setUnitId(_heatSourceRecentId+"");
		
		_frgHistory = new DetailHistoryFragment();
		_frgHistory.setSourceType(DetailHistoryFragment.SOURCE_TYPE_HEAT_SOURCE);
	}
	private void initFragmentPagerAdapter() {
		_fragmentPagerAdapter = new DetailGraphFragmentPagerAdapter(getSupportFragmentManager());  
	       
		_viewPager = (ViewPager)findViewById(R.id.station_detail_pager);  
		_viewPager.setAdapter(_fragmentPagerAdapter);
		_viewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {  
            @Override  
            public void onPageSelected(int position) {
            	_tabs.get(position).performClick();
            }  
            @Override  
            public void onPageScrollStateChanged(int state) {
                
            }  
        }); 
        
	}
	private void initTabs() {
		_tabs.clear();
		_tabs.add(findViewById(R.id.station_temperature_tab));
		_tabs.add(findViewById(R.id.station_pressure_tab));
		_tabs.add(findViewById(R.id.station_history_tab));
		
		_indicators.clear();
		_indicators.add(findViewById(R.id.station_temperature_indicator));
		_indicators.add(findViewById(R.id.station_pressure_indicator));
		_indicators.add(findViewById(R.id.station_history_indicator));
		
		for ( int i = 0; i < _tabs.size(); i++ ) {
			_tabs.get(i).setOnClickListener(onClickTabListener);
		}
	}
	private OnClickListener onClickTabListener = new OnClickListener() {
		@Override
		public void onClick(View view) {
			int viewId = view.getId();
			View tab;
			for ( int i = 0; i < _tabs.size(); i++ ) {
				tab = _tabs.get(i);
				if ( viewId == tab.getId() ) {
					tab.setBackgroundResource(R.drawable.button_default);
					_indicators.get(i).setVisibility(View.VISIBLE);
					_viewPager.setCurrentItem(i);
				} else {
					tab.setBackgroundResource(R.drawable.button_dark);
					_indicators.get(i).setVisibility(View.INVISIBLE);
				}
			}
			
		}
	};
	private void getDetail() {
		renderDetail(); 
	}
	private void renderDetail(){

		List<HashMap<String, Object>> details = new ArrayList<HashMap<String, Object>>();
		
		HashMap<String, Object> item = new HashMap<String, Object>();
		item.put("name_1",getString(R.string.station_realtime_heat));
		item.put("value_1", _heatSourceRecent_InstHeat);
		item.put("name_2",getString(R.string.station_total_heat));
		item.put("value_2", _heatSourceRecent_AccuHeat);
		details.add(item);
		
		item = new HashMap<String, Object>();
		item.put("name_1",getString(R.string.station_realtime_flow));
		item.put("value_1", _heatSourceRecent_InstWater);
		item.put("name_2",getString(R.string.station_total_flow));
		item.put("value_2", _heatSourceRecent_AccuWater);
		details.add(item);

		item = new HashMap<String, Object>();
		item.put("name_1", getString(R.string.station_supply_water_quantity));
		item.put("value_1", _heatSourceRecent_WaterSupply);
		details.add(item);
		
		
		_detailList.setAdapter(new SimpleAdapter(this, details, R.layout.station_detail_list_item,  
	 			 new String[] { "name_1","value_1", "name_2","value_2"}, 
				  new int[] {R.id.detail_item_name_1, R.id.detail_item_value_1, R.id.detail_item_name_2, R.id.detail_item_value_2 }));
		
		_supplyTemperature.setText( _temperatureOut + getString(R.string.degree_unit) );
		_backTemperature.setText( _temperatureIn + getString(R.string.degree_unit) );
		_supplyPressure.setText( _pressureOut + getString(R.string.pressure_unit) );
		_backPressure.setText( _pressureIn + getString(R.string.pressure_unit) );
	}
	public int getHeatSourceId() {
		return _heatSourceId;
	}

	public void setHeatSourceId(int heatSourceId) {
		this._heatSourceId = heatSourceId;
	}

	private class DetailGraphFragmentPagerAdapter extends FragmentPagerAdapter {
	    private final int TAB_POSITION_TEMPERATURE = 0;
	    private final int TAB_POSITION_PRESSURE = 1;
	    private final int TAB_POSITION_HISTORY = 2;
	    private final int TAB_COUNT = 3;
	    
		public DetailGraphFragmentPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			switch (position) {
			case TAB_POSITION_TEMPERATURE:
				return _frgTemperatureGraph;
			case TAB_POSITION_PRESSURE:
				return _frgPressureGraph;
			case TAB_POSITION_HISTORY:
				return _frgHistory;
			}
			throw new IllegalStateException("No fragment at position " + position);
		}

		@Override
		public int getCount() {
			return TAB_COUNT;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			String tabLabel = "";
			switch (position) {
			case TAB_POSITION_TEMPERATURE:
				tabLabel = getString(R.string.forcast);
				break;
			case TAB_POSITION_PRESSURE:
				tabLabel = getString(R.string.week_name);
				break;
			case TAB_POSITION_HISTORY:
				tabLabel = getString(R.string.search_history);
				break;
			}
			return tabLabel;
		}
	}
}
