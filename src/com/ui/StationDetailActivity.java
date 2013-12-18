package com.ui;

import java.util.ArrayList;

import com.chart.impl.SupplyAndBackwardDetailChart;

import android.content.Intent;
import android.os.Bundle;
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

public class StationDetailActivity extends FragmentActivity  {

	private String stationName = "";
	private String stationId = "";
	
	private DetailGraphFragment _frgTemperatureGraph = null;
	private DetailGraphFragment _frgPressureGraph = null;
	private DetailHistoryFragment _frgHistory = null;
	private DetailGraphFragmentPagerAdapter _fragmentPagerAdapter = null;
	private ViewPager _viewPager = null;
	
	private ArrayList<View> _tabs = new ArrayList<View>();
	private ArrayList<View> _indicators = new ArrayList<View>();
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.station_detail);

		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setIcon(R.drawable.main_item_six_image);

		Intent inten = this.getIntent();
		Bundle mBundle = inten.getExtras();
		if (mBundle != null) {
			stationName = mBundle.getString("station_name");
			this.setTitle(stationName);
			setStationId(mBundle.getString("station_id"));
		}
		
		initFragments();
		initFragmentPagerAdapter();
		initTabs();
		
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
		_frgTemperatureGraph.setSourceType(DetailGraphFragment.SOURCE_TYPE_STATION);
		
		_frgPressureGraph = new DetailGraphFragment();
		_frgPressureGraph.setDataType(SupplyAndBackwardDetailChart.TYPE_PRESSURE);
		_frgPressureGraph.setSourceType(DetailGraphFragment.SOURCE_TYPE_STATION);
		
		_frgHistory = new DetailHistoryFragment();
		_frgHistory.setSourceType(DetailGraphFragment.SOURCE_TYPE_STATION);
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
	public String getStationId() {
		return stationId;
	}

	public void setStationId(String stationId) {
		this.stationId = stationId;
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
