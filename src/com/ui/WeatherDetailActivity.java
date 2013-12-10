package com.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONException;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.support.v4.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.model.WeatherDetailItem;
import com.model.WeatherDetailTempInfo;
import com.model.WeatherPreChartItem;
import com.model.WeatherType;
import com.reqst.BusinessRequest;
import com.util.BaseHelper;
import com.util.ConstDefine;
import com.util.DateHelper;


public class WeatherDetailActivity extends FragmentActivity implements TabListener {

	private String strListId = "1";
	private ProgressDialog _diaLogProgress = null;
	private WeatherDetailTempInfo _weatherSummaryToday = null;
	private WeatherDetailTempInfo _weatherSummaryYesterday = null;
	private List<HashMap<String, Object>> _weatherDetailsToday;
	private List<HashMap<String, Object>> _weatherDetailsYesterday;
    
	private List<WeatherPreChartItem> _weatherChartItems = null;
	private ArrayList<WeatherDetailItem>  _weatherDetailsHistory = new ArrayList<WeatherDetailItem>(); 
	
	private ViewPager _viewPager;  
    private WeatherFragmentPagerAdapter _viewPagerAdapter; 
    private WeatherDetailTodayFragment _frgToday;
    private WeatherDetailYesterdayFragment _frgYesterday;
    private WeatherDetailWeekFragment _frgWeek;
    private WeatherDetailHistoryFragment _frgHistory;
    
	 @Override
	 public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.weather_detail);
			
			initFragments();
			initViewPager();
		    initActionBar();
			
	 }
	@Override
	public void onStart() {
		super.onStart();
		fetchWeatherDetialData();
	}
	private void initFragments() {
	    _frgToday = new WeatherDetailTodayFragment();
		_frgYesterday = new WeatherDetailYesterdayFragment();
		_frgWeek = new WeatherDetailWeekFragment();
		_frgHistory = new WeatherDetailHistoryFragment();		
	}
	private void initViewPager() {
		this._viewPagerAdapter = new WeatherFragmentPagerAdapter(getSupportFragmentManager());  
       
        _viewPager = (ViewPager)findViewById(R.id.weather_pager);  
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
	private void initActionBar(){
	    //add tabs to ActionBar
	    final ActionBar actionBar = getActionBar();
	    actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
	    
	    for (int i = 0; i < _viewPagerAdapter.getCount(); ++i) {  
            actionBar.addTab(actionBar.newTab()  
                    .setText(_viewPagerAdapter.getPageTitle(i))  
                    .setTabListener(this));  
        }   	
    }

    
    @Override  
    public void onTabReselected(Tab tab, FragmentTransaction ft) {  
          
    }  
  
    @Override  
    public void onTabSelected(Tab tab, FragmentTransaction ft) {   
        _viewPager.setCurrentItem(tab.getPosition()); 
    }  
  
    @Override  
    public void onTabUnselected(Tab tab, FragmentTransaction ft) {  
          
    } 

	private void fetchWeatherDetialData(){
		 
    	_diaLogProgress = BaseHelper.showProgress(WeatherDetailActivity.this,ConstDefine.I_MSG_0003,false);
        new Thread() {
            public void run() { 
                Message msgSend = new Message();
        	    try {

        	    	_weatherSummaryToday = BusinessRequest.getWenduTabDetailById(strListId,WeatherType.Today.getStrValue());
        		 	_weatherDetailsToday = getWeatherDetailListData(strListId,"1");

        		 	_weatherSummaryYesterday = BusinessRequest.getWenduTabDetailById(strListId,WeatherType.Yesterday.getStrValue());
        		 	_weatherDetailsYesterday = getWeatherDetailListData(strListId,WeatherType.Yesterday.getStrValue());
        		 	
        		 	_weatherChartItems =  BusinessRequest.getWeatherChartList();
        		 	_weatherDetailsHistory =  BusinessRequest.getWeatherHisListData();
        		 	
        	    	msgSend.what = ConstDefine.MSG_I_HANDLE_OK;
					} catch (Exception e) {
						msgSend.what = ConstDefine.MSG_I_HANDLE_Fail;
					}
            	    oneTabhandler.sendMessage(msgSend);
            	}
        }.start();	 
	 }
	 

	 @SuppressLint("HandlerLeak")
	private Handler oneTabhandler = new Handler() {               
        public void handleMessage(Message message) {
                switch (message.what) {
                case ConstDefine.MSG_I_HANDLE_OK:                                        
        		 	_diaLogProgress.dismiss();
        		 	_frgToday.setWeatherDetailInfo(_weatherSummaryToday);
        		    _frgToday.setWeatherDetailList(_weatherDetailsToday);
        		    _frgYesterday.setWeatherDetailInfo(_weatherSummaryYesterday);
        		    _frgYesterday.setWeatherDetailList(_weatherDetailsYesterday);
        		 	_frgWeek.setWeatherDetailList(_weatherChartItems);
        		 	_frgHistory.setOriginDataList(_weatherDetailsHistory);
        		 	

        		 	_frgToday.renderWeatherDetailData();
        		 	_frgYesterday.renderWeatherDetailData();

                    break;
                case ConstDefine.MSG_I_HANDLE_Fail:                                        
                	//close process
                	_diaLogProgress.dismiss();
                	BaseHelper.showToastMsg(WeatherDetailActivity.this,ConstDefine.E_MSG_0001);
                    break;
	            }
	        }
	  };
	 
	  
	  /**
	   * 
	   * @return
	 * @throws JSONException 
	   */
	  private List<HashMap<String, Object>> getWeatherDetailListData(String strListId ,String dayFlag) throws Exception {  
	   
	    
	  	ArrayList<WeatherDetailItem> wenduDbDatalist = new ArrayList<WeatherDetailItem>();
      
	  	_weatherSummaryToday.setStrListId(strListId);
	  	_weatherSummaryToday.setStrDayFlag(dayFlag);
	  	wenduDbDatalist = BusinessRequest.getWenduTabDetailListById(_weatherSummaryToday);
	  	
		List<HashMap<String, Object>> wenDuList = new ArrayList<HashMap<String, Object>>(); 
		for (WeatherDetailItem oneRec: wenduDbDatalist) 
		{   
			HashMap<String, Object> item = new HashMap<String, Object>();
			String time = oneRec.getW_time();
			String strDate = DateHelper.getShortDate(time, "mm/dd/yyyy", getApplicationContext());
			String strDayOfWeek = DateHelper.getDayOfWeek(time, "mm/dd/yyyy", getApplicationContext());
		    item.put("date", strDate);
		    item.put("day_of_week", strDayOfWeek); 
		    item.put("tempreture", oneRec.getW_wendu() + getString(R.string.degree_unit)); 
		    item.put("weather", oneRec.getW_iconId()); 
		    wenDuList.add(item);
		}
		
		//return
		return wenDuList;
     } 
	 

	public class WeatherFragmentPagerAdapter extends FragmentPagerAdapter {
	    private final int TAB_POSITION_TODAY = 0;
	    private final int TAB_POSITION_YESTERDAY = 3;
	    private final int TAB_POSITION_WEEK = 1;
	    private final int TAB_POSITION_HISTORY = 2;
	    private final int TAB_POSITION_STATIONS = 3;
	    private final int TAB_COUNT = 4;
	    
		public WeatherFragmentPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			switch (position) {
			case TAB_POSITION_TODAY:
				return _frgToday;
			case TAB_POSITION_WEEK:
				return _frgWeek;
			case TAB_POSITION_HISTORY:
				return _frgHistory;
			case TAB_POSITION_STATIONS:
				return _frgYesterday;

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
			case TAB_POSITION_TODAY:
				tabLabel = getString(R.string.today);
				break;
			case TAB_POSITION_WEEK:
				tabLabel = getString(R.string.week);
				break;
			case TAB_POSITION_HISTORY:
				tabLabel = getString(R.string.history);
				break;
			case TAB_POSITION_STATIONS:
				tabLabel = getString(R.string.weather_stations);
			}
			return tabLabel;
		}
	}

}
