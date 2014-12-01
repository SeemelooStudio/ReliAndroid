package com.ui;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.MenuItem;

public class HeatSourceQueryActivity  extends FragmentActivity implements TabListener  {

	private HeatSourceListFragment _frgList;
	private HeatSourceStatisticsFragment _frgStatistics;
	private HeatSourceFragmentPagerAdapter _viewPagerAdapter;
	private ViewPager _viewPager;
	
	 /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.heat_source_query);
        
        initFragments();
		initViewPager();
	    initActionBar();
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
	private void initActionBar() {
	    final ActionBar actionBar = getActionBar();
	    actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
	    
	    for (int i = 0; i < _viewPagerAdapter.getCount(); ++i) {  
            actionBar.addTab(actionBar.newTab()  
                    .setText(_viewPagerAdapter.getPageTitle(i))  
                    .setTabListener(this));  
        }
	    actionBar.setDisplayHomeAsUpEnabled(true); 
	    setTitle(getString(R.string.heat_source));
		
	}

	private void initViewPager() {
		this._viewPagerAdapter = new HeatSourceFragmentPagerAdapter(getSupportFragmentManager());  
	       
        _viewPager = (ViewPager)findViewById(R.id.heat_source_pager);  
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

	private void initFragments() {
		_frgList = new HeatSourceListFragment();
		_frgStatistics = new HeatSourceStatisticsFragment();
		
	}
	private class HeatSourceFragmentPagerAdapter extends FragmentPagerAdapter {
	    private final int TAB_POSITION_STAT = 0;
	    private final int TAB_POSITION_LIST = 1;
	    private final int TAB_COUNT = 2;
	    
		public HeatSourceFragmentPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			switch (position) {
			case TAB_POSITION_STAT:
				return _frgStatistics;
			case TAB_POSITION_LIST:
				return _frgList;

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
			case TAB_POSITION_STAT:
				tabLabel = "全网统计";
				break;
			case TAB_POSITION_LIST:
				tabLabel = "热源列表";
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
        _viewPager.setCurrentItem(tab.getPosition()); 
    } 

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}
		
}
