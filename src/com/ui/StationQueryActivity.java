package com.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.ActionBar.Tab;
import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.app.ActionBar.TabListener;
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
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;

public class StationQueryActivity  extends FragmentActivity implements TabListener {
	
    private StationListFragment _stationFrag;
    private EditStationListFragment _addNewStationFrag;
    private StationFragmentPagerAdapter _viewPagerAdapter;
    private ViewPager _viewPager;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.station_query);
        initFragments();
        initViewPager();
        initActionBar();
	    setTitle(getString(R.string.station));
    }
    @Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.station_list_fragment_actions, menu);
		
		final ActionBar actionBar = getActionBar(); 
		MenuItem searchItem = menu.findItem(R.id.action_search);
		SearchView searchView = (SearchView)searchItem.getActionView();
		searchView.setIconifiedByDefault(false);
		searchView.setOnQueryTextListener(new OnQueryTextListener() {

			@Override
			public boolean onQueryTextChange(String newText) {
				_stationFrag.search(newText);
				_addNewStationFrag.search(newText);
				return false;
			}

			@Override
			public boolean onQueryTextSubmit(String query) {
				return false;
			}
			
		});  
		
        searchView.setSubmitButtonEnabled(true);
	    return super.onCreateOptionsMenu(menu);
	}
    private void initFragments()
    {
    	_stationFrag = new StationListFragment();
    	_addNewStationFrag = new EditStationListFragment();
    }
    private void initViewPager() {
		this._viewPagerAdapter = new StationFragmentPagerAdapter(getSupportFragmentManager());  
	       
        _viewPager = (ViewPager)findViewById(R.id.station_pager);  
        _viewPager.setAdapter(_viewPagerAdapter);
        _viewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {  
            @Override  
            public void onPageSelected(int position) {  
                final ActionBar actionBar = getActionBar();  
                actionBar.setSelectedNavigationItem(position);  
            }  
            @Override  
            public void onPageScrollStateChanged(int state) { 
            }  
        }); 
	}
    private void initActionBar() {
    	final ActionBar actionBar = getActionBar();
	    actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
	    for (int i = 0; i < _viewPagerAdapter.getCount(); ++i) {  
            actionBar.addTab(actionBar.newTab()  
                    .setText(_viewPagerAdapter.getPageTitle(i))  
                    .setTabListener(this));  
        }
	    actionBar.setDisplayHomeAsUpEnabled(true); 
	}
    
    @Override
	public boolean onOptionsItemSelected(MenuItem item) {
		 switch (item.getItemId()) {
		    // Respond to the action bar's Up/Home button
		    case android.R.id.home:
		    	this.finish();
		        return true;
		    case R.id.action_search :
				 return true;
		    }
		    return super.onOptionsItemSelected(item);
	 }   
    
    private class StationFragmentPagerAdapter extends FragmentPagerAdapter {
	    
	    private final int TAB_POSITION_LIST = 0;
	    private final int TAB_POSITION_ADD = 1;
	    private final int TAB_COUNT = 2;
	    
		public StationFragmentPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			switch (position) {
				case TAB_POSITION_ADD:
					return _addNewStationFrag;
				case TAB_POSITION_LIST:
					return _stationFrag;
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
			case TAB_POSITION_LIST:
				tabLabel = "热力站列表";
				break;
			case TAB_POSITION_ADD:
				tabLabel = "管理热力站";
				break;
			}
			return tabLabel;
		}
	}
	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		_viewPager.setCurrentItem(tab.getPosition()); 
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	} 
}
