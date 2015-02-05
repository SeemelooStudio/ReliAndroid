package com.ui;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;

import android.view.Window;
import android.widget.GridLayout;
import android.widget.ImageView;

import android.widget.TextView;
import android.widget.GridLayout.Spec;

import com.ctral.MainScrollLayout;
import com.model.MainPageSummary;
import com.reqst.BusinessRequest;
import com.util.AccountHelper;
import com.util.BaseHelper;
import com.util.ConstDefine;
import com.util.ViewPageAdapter;
import com.util.WeatherIconHelper;

public class MainPageActivity extends Activity {
	
	public static final String TAG = "MainPageActivity";
	public static final char DailyReportCell = '1';
	public static final char AlertCell = '2';
	public static final char WeatherCell = '3';
	public static final char MessageCell = '4';
	public static final char HeatSourceCell = '5';
	public static final char StationCell = '6';
	public static final char KnowledgeCell = '7';
	public static final char CustomerReportCell = '8';
	
	
	private MainScrollLayout scrollLayout;
	private ViewPager viewpage;
	private LayoutInflater layflater;
	private ArrayList<View> listViews;
	private ImageView imgMaskbg;
	private Bundle bundle;
	
	//tool
	private TextView txthelpMenu;
	private TextView txtconectMenu;
	private TextView txtAboutMenu;
	
	private ImageView imgSetMenu;
	private TextView txtShowPage;
	
	//event
	private MainItemOnClickListener mainItemClickListener;
	private ToolItemOnClickListener toolItemClickListener;

	private ProgressDialog diaLogProgress = null;
	private MainPageSummary mainPageSummary = null;
	
	private final static int PAGE_SIZE = 6;
	private final static int COLUMN_COUNT = 2;
	private final static int ROW_COUNT = 3;
	
	private Activity _activity;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE); 
		setContentView(R.layout.main_page_layout);
		_activity = this;
		//get param
		Intent inten = this.getIntent();
        bundle = inten.getExtras();
        viewpage = (ViewPager) findViewById(R.id.pager);
        scrollLayout = (MainScrollLayout) findViewById(R.id.mainScrollLayout);
		if (bundle == null )  return;
		initToolView();
		initView(bundle.getString("lstMemu"));         

	}


	/**
	 * tool click
	 */
	private  void initToolView()
	{
		toolItemClickListener= new ToolItemOnClickListener();
		
		//right menu
		imgSetMenu = (ImageView) findViewById(R.id.settingMenu);
		imgSetMenu.setOnClickListener(toolItemClickListener);

		//sub menu
		txthelpMenu = (TextView) findViewById(R.id.help_menu);
		txthelpMenu.setOnClickListener(toolItemClickListener);
		txtconectMenu = (TextView) findViewById(R.id.conect_menu);
		txtconectMenu.setOnClickListener(toolItemClickListener);
		txtAboutMenu = (TextView) findViewById(R.id.main_about);
		txtAboutMenu.setOnClickListener(toolItemClickListener);
	}
	

	private void initView(String strMenu) {
		layflater = LayoutInflater.from(this);
		listViews = new ArrayList<View>();
		mainItemClickListener= new MainItemOnClickListener();
		List<View> itemList = new ArrayList<View>();
		itemList.add(layflater.inflate(R.layout.main_page_item_overview, null));
		if (strMenu.indexOf(WeatherCell) >= 0) itemList.add(layflater.inflate(R.layout.main_page_item_3, null));
		if (strMenu.indexOf(DailyReportCell) >= 0) itemList.add(layflater.inflate(R.layout.main_page_item_1, null));
		if (strMenu.indexOf(CustomerReportCell) >= 0) itemList.add(layflater.inflate(R.layout.main_page_item_8, null));
		if (strMenu.indexOf(HeatSourceCell) >= 0 ) itemList.add(layflater.inflate(R.layout.main_page_item_5, null));
		if (strMenu.indexOf(StationCell) >= 0) itemList.add( layflater.inflate(R.layout.main_page_item_6, null));
		if (strMenu.indexOf(AlertCell) >= 0 ) itemList.add(layflater.inflate(R.layout.main_page_item_2, null));
		if (strMenu.indexOf(MessageCell) >= 0 ) itemList.add(layflater.inflate(R.layout.main_page_item_4, null));
		if (strMenu.indexOf(KnowledgeCell) >= 0) itemList.add( layflater.inflate(R.layout.main_page_item_7, null));
		
		int pageNum = (int) Math.ceil((float) itemList.size() / PAGE_SIZE);
		

		DisplayMetrics outMetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(outMetrics);
		
		int screenWidth = (int)(outMetrics.widthPixels - BaseHelper.convertDpToPixel(60, getApplicationContext()));  
		int screenHeight = (int)((outMetrics.heightPixels - BaseHelper.convertDpToPixel(75, getApplicationContext())) * 0.8); 

		int ceilMargin = this.getResources().getDimensionPixelSize(R.dimen.small_margin);

		int cellWidth = (int) (screenWidth / COLUMN_COUNT - ceilMargin);
		int cellHeight = (int) (screenHeight / ROW_COUNT - ceilMargin);

		// create page
		listViews = new ArrayList<View>();
		for (int pageIndex = 0; pageIndex < pageNum; pageIndex++) {

			GridLayout gridLayout = new GridLayout(this);
			gridLayout.setColumnCount(COLUMN_COUNT);
			gridLayout.setRowCount(ROW_COUNT);
			gridLayout.setOrientation(GridLayout.HORIZONTAL);
			gridLayout.setUseDefaultMargins(true);
			for (int cell = 0, rowIndex = 0, columnIndex = 0, itemIndex = pageIndex
					* PAGE_SIZE; cell < PAGE_SIZE
					&& itemIndex < itemList.size(); cell++, columnIndex++, itemIndex++) {
				if (columnIndex == COLUMN_COUNT) {
					columnIndex = 0;
					rowIndex++;
				}
				View item = itemList.get(itemIndex);
				
				item.setOnClickListener(mainItemClickListener);

				Spec row = GridLayout.spec(rowIndex, 1);
				Spec colspan = GridLayout.spec(columnIndex, 1);
				GridLayout.LayoutParams param = new GridLayout.LayoutParams(row,
						colspan);
				param.setGravity(Gravity.FILL);
				param.width = cellWidth;
				param.height = cellHeight;
				param.setMargins(ceilMargin, ceilMargin, ceilMargin, ceilMargin);
				item.setLayoutParams(param);
				
				gridLayout.addView(item);
			}
			gridLayout.setId(pageIndex);
			listViews.add(gridLayout);
		}


		imgMaskbg = (ImageView) findViewById(R.id.main_page_menu);
		imgMaskbg.setOnClickListener(mainItemClickListener);
		
		viewpage.setAdapter(new ViewPageAdapter(listViews));
		viewpage.setOnPageChangeListener(new ViewPageChangeListener()); 
		
		txtShowPage = (TextView)findViewById(R.id.txtViewGroup); 
    	txtShowPage.setText("1/" + listViews.size());

    	initSummaryData();
	}


	private void initSummaryData(){
		 
    	diaLogProgress = BaseHelper.showProgress(MainPageActivity.this,ConstDefine.I_MSG_0003,false);
        new Thread() {
            public void run() { 
                Message msgSend = new Message();
        	    try {
        	    	//get today weatherInfo
        	    	String userName = AccountHelper.getUserName(_activity);
        	    	mainPageSummary = BusinessRequest.getMainPageSummary(userName, _activity);
        	    	msgSend.what = ConstDefine.MSG_I_HANDLE_OK;
					} catch (Exception e) {
						msgSend.what = ConstDefine.MSG_I_HANDLE_Fail;
					}
            	    mainPageSummaryHandler.sendMessage(msgSend);
            	}
        }.start();	 
	 }
	
	@SuppressLint("HandlerLeak")
	private Handler mainPageSummaryHandler = new Handler() {               
        public void handleMessage(Message message) {
                switch (message.what) {
                case ConstDefine.MSG_I_HANDLE_OK:                                        
        		 	diaLogProgress.dismiss();
        		 	setTemperatureData();
        		 	setMessagerData();
                    break;
                case ConstDefine.MSG_I_HANDLE_Fail:    
                	diaLogProgress.dismiss();
                	BaseHelper.showToastMsg(MainPageActivity.this,ConstDefine.E_MSG_0001);
                    break;
	            }
	        }
	  };
	  
	  private void setTemperatureData( )
	  {
		  TextView tvHighest = (TextView) findViewById(R.id.main_item_three_txt1);
		  TextView tvToday = (TextView) findViewById(R.id.main_item_three_txt2);
		  TextView tvWeather = (TextView) findViewById(R.id.main_item_three_txt3);
		  tvHighest.setText(mainPageSummary.getForecastHighest() + "/" + mainPageSummary.getForecastLowest());	
		  ImageView imgWeatherIcon = (ImageView) findViewById(R.id.main_item_three_image);
		  Integer intImageResource = WeatherIconHelper.getWeatherIconResourceId(mainPageSummary.getWeatherIcon());
		  
		  if(intImageResource != 0){
			  imgWeatherIcon.setImageDrawable(getResources().getDrawable(intImageResource));
		  }

		  tvToday.setText(mainPageSummary.getWindSpeedAndDirection());
		  tvWeather.setText(mainPageSummary.getWeatherDiscription());

	  }
	  private void setMessagerData( )
	  {
		  TextView tvPhotoCount = (TextView) findViewById(R.id.main_item_four_txt1);
		  TextView tvMessageCount = (TextView) findViewById(R.id.main_item_four_txt2);
		  tvPhotoCount.setText(mainPageSummary.getCountPhotos()+"");
		  tvMessageCount.setText(mainPageSummary.getCountMessages()+" ");
	  }
    /**
     * click listren
     * @author zhaors
     *
     */
	private class MainItemOnClickListener implements OnClickListener {

		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.main_page_overview:
				Intent intent_overview = new Intent(MainPageActivity.this, OverviewActivity.class); 
				startActivity(intent_overview);
				break;
			case R.id.main_page_item1:
				Intent intent_1 = new Intent(MainPageActivity.this, DailyListActivity.class); 
				startActivity(intent_1);
				break;
			case R.id.main_page_item2:
				Intent intent_2 = new Intent(MainPageActivity.this, WarnListActivity.class); 
				startActivity(intent_2);
				break;				
			case R.id.main_page_item3:
				Intent intent_3 = new Intent(MainPageActivity.this, WeatherDetailActivity.class); 
				startActivity(intent_3);
				break;
			case R.id.main_page_item4:
				Intent intent_4 = new Intent(MainPageActivity.this, MsgUpMainActivity.class); 
				startActivity(intent_4);
				break;				
			case R.id.main_page_item5:
				Intent intent_5 = new Intent(MainPageActivity.this, HeatSourceMainActivity.class); 
				startActivity(intent_5);
				break;				
			case R.id.main_page_item6:
				Intent intent_6 = new Intent(MainPageActivity.this, StationMainActivity.class); 
				startActivity(intent_6);
				break;
			case R.id.main_page_item7:
				Intent intent_7 = new Intent(MainPageActivity.this, KnowledgeBaseListActivity.class); 
				startActivity(intent_7);
				break;
			case R.id.main_page_item8:
				Intent intent_8 = new Intent(MainPageActivity.this, CustomerServiceListActivity.class); 
				startActivity(intent_8);
				break;
			case R.id.main_page_menu:
				Intent intent_version = new Intent(MainPageActivity.this, VersionActivity.class); 
				startActivity(intent_version);
				break;
			default:
				break;
			}
		}
	}

	 /**
     * click listren
     * @author zhaors
     *
     */
	private class ToolItemOnClickListener implements OnClickListener {

		public void onClick(View v) {
			
			switch (v.getId()) {
			case R.id.help_menu:
				BaseHelper.showToastMsg(MainPageActivity.this, getString(R.string.under_construction_message));
				break;
			case R.id.conect_menu:
				BaseHelper.showToastMsg(MainPageActivity.this, getString(R.string.under_construction_message));
				break;
			case R.id.main_about:
				Intent intent_version = new Intent(MainPageActivity.this, VersionActivity.class); 
				startActivity(intent_version);
				break;
			case R.id.settingMenu:
				BaseHelper.showToastMsg(MainPageActivity.this, getString(R.string.under_construction_message));
				break;

			default:
				break;
			}
		}
	}
	
	private class ViewPageChangeListener implements OnPageChangeListener {  

	     @Override  
	     public void onPageScrollStateChanged(int arg0) {  
	         // TODO Auto-generated method stub  

	     }  

	     @Override  
	     public void onPageScrolled(int arg0, float arg1, int arg2) {  
	         // TODO Auto-generated method stub  

	     }  

	     @Override  
	     public void onPageSelected(int arg0) { 
	    	 String pageNum = (arg0+1) + "/" + listViews.size();
	    	 txtShowPage.setText(pageNum);
	     }  
	 }  

}