package com.ui;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.json.JSONException;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.support.v4.app.Fragment;
import android.app.DialogFragment;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.model.WeatherStationListItem;
import com.model.WeatherDetailItem;
import com.model.WeatherDetailTempInfo;
import com.model.WeatherPreChartItem;
import com.model.WeatherType;
import com.reqst.BusinessRequest;
import com.util.BaseHelper;
import com.util.ConstDefine;
import com.util.DateHelper;
import com.util.WeatherIconHelper;


public class WeatherDetailActivity extends FragmentActivity implements TabListener {

	private String strListId = "1";
	private ProgressDialog _diaLogProgress = null;
	private WeatherDetailTempInfo _weatherSummaryToday = null;
	private WeatherDetailTempInfo _weatherSummaryYesterday = null;
	private List<HashMap<String, Object>> _weatherDetailsToday;
    
	private List<WeatherPreChartItem> _weatherChartItems = null;
	private ArrayList<WeatherDetailItem>  _weatherDetailsHistory = new ArrayList<WeatherDetailItem>(); 
	
    private ArrayList<WeatherStationListItem>  _weatherStations = new ArrayList<WeatherStationListItem>(); 
	
	private ViewPager _viewPager;  
    private WeatherFragmentPagerAdapter _viewPagerAdapter; 
    private WeatherDetailTodayFragment _frgToday;
    private WeatherDetailWeekFragment _frgWeek;
    private WeatherDetailHistoryFragment _frgHistory;
    private WeatherDetailStationsFragment _frgStations;
    private EditText _fromDate;
    private EditText _toDate;
    
    private final int DATE_PICKER_FROM_DIALOG =1;
    private final int DATE_PICKER_TO_DIALOG = 2;
    
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
		_frgWeek = new WeatherDetailWeekFragment();
		_frgHistory = new WeatherDetailHistoryFragment();
		_frgStations = new WeatherDetailStationsFragment();
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
	    final ActionBar actionBar = getActionBar();
	    actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
	    actionBar.setDisplayHomeAsUpEnabled(true); 
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
	public void showTimePickerDialog(View v) {
	    DialogFragment newFragment = new TimePickerFragment();
	    newFragment.show(this.getFragmentManager(), "timePicker");
	}
	
	public void showDateFromDialog(View v) {
	    DialogFragment datePickerFromDialog = new DatePickerFragment();
	    datePickerFromDialog.show (this.getFragmentManager(), "from");
	}
	
	public void showDateToDialog(View v) {
	    DialogFragment datePickerToDialog = new DatePickerFragment();
	    datePickerToDialog.show(this.getFragmentManager(), "to");
	}
	
	public void searchWeatherHistory(View v) {
		_diaLogProgress = BaseHelper.showProgress(WeatherDetailActivity.this,ConstDefine.I_MSG_0003,false);
		new Thread() {
            public void run() { 
            	Message msgSend = new Message();
				try {
					_frgHistory.search();
					msgSend.what = ConstDefine.MSG_I_HANDLE_SEARCH_OK;
					oneTabhandler.sendMessage(msgSend);
				} catch (Exception e) {
					msgSend.what = ConstDefine.MSG_I_HANDLE_Fail;
					e.printStackTrace();
				}
            }
		}.start();
	}
	
	private void fetchWeatherDetialData(){
		 
    	_diaLogProgress = BaseHelper.showProgress(WeatherDetailActivity.this,ConstDefine.I_MSG_0003,false);
        new Thread() {
            public void run() { 
                Message msgSend = new Message();
        	    try {
        	    	WeatherDetailTempInfo[] weatherSummaryTodayAndYesterday = 
        	    			BusinessRequest.getWenduTabDetailById(WeatherType.TodayAndYesterday.getStrValue());
        	    	_weatherSummaryToday = weatherSummaryTodayAndYesterday[0];
        	    	_weatherSummaryYesterday = weatherSummaryTodayAndYesterday[1];
        		 	_weatherDetailsToday = getWeatherDetailListData(strListId,"1");
        		 	_weatherChartItems =  BusinessRequest.getWeatherChartList();
        		 	
        		 	Calendar fromDate = Calendar.getInstance(); 
        		 	fromDate.set(Calendar.DATE,  fromDate.get(Calendar.DATE) - 7);
        		 	Calendar toDate = Calendar.getInstance();
        		 	toDate.set(Calendar.DATE, toDate.get(Calendar.DATE) - 1);
        		 	_weatherDetailsHistory =  BusinessRequest.getWeatherHisListData(fromDate.getTime(), toDate.getTime());
        		 	_weatherStations =  BusinessRequest.getWeatherList();
        		 	
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
        		 	_frgToday.setYesterdayWeatherDetailInfo(_weatherSummaryYesterday);
        		    _frgToday.setWeatherDetailList(_weatherDetailsToday);
        		 	_frgWeek.setWeatherDetailList(_weatherChartItems);
        		 	_frgHistory.setOriginDataList(_weatherDetailsHistory);
        		 	_frgStations.setWeatherStationList(_weatherStations);
        		 	//render first two tab
        		 	_frgToday.renderWeatherDetailData();
        		 	_frgWeek.renderWeatherDetailData();

                    break;
                case ConstDefine.MSG_I_HANDLE_Fail:                                        
                	//close process
                	_diaLogProgress.dismiss();
                	BaseHelper.showToastMsg(WeatherDetailActivity.this,ConstDefine.E_MSG_0001);
                	break;
                case ConstDefine.MSG_I_HANDLE_SEARCH_OK:
                	_frgHistory.renderWeatherDetailData();
                	_diaLogProgress.dismiss();
                	break;
	            }
	        }
	  };
	 
	  /**
	   * 
	   * TODO : revise
	   */
	  @SuppressLint("SimpleDateFormat")
	private List<HashMap<String, Object>> getWeatherDetailListData(String strListId ,String dayFlag) throws Exception {  

	  	WeatherDetailTempInfo[] sevenDays = BusinessRequest.getWenduTabDetailById(WeatherType.SevenDays.getStrValue());
		List<HashMap<String, Object>> wenDuList = new ArrayList<HashMap<String, Object>>(); 
		for (WeatherDetailTempInfo oneDay: sevenDays) 
		{   
			HashMap<String, Object> item = new HashMap<String, Object>();
			Date day = oneDay.getDay();
			String strDate = new SimpleDateFormat("MM月dd日").format(day);
		    item.put("date", strDate);
		    item.put("day_of_week", DateHelper.getDayOfWeekInChinese(day)); 
		    item.put("tempreture", oneDay.getForecastAverage() + getString(R.string.degree_unit)); 
		    item.put("weather", WeatherIconHelper.getWeatherIconResourceId(oneDay.getWeatherIcon()));
		    wenDuList.add(item);
		}
		
		return wenDuList;
     } 

		

	public class WeatherFragmentPagerAdapter extends FragmentPagerAdapter {
	    private final int TAB_POSITION_TODAY = 0;
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
				return _frgStations;

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
				tabLabel = getString(R.string.forcast);
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
