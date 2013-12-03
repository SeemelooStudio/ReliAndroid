package com.ui;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.chart.impl.AverageTemperatureChart;
import com.ctral.MainScrollLayout;
import com.model.MainPageSummary;
import com.model.WeatherType;
import com.reqst.BusinessRequest;
import com.util.BaseHelper;
import com.util.ConstDefine;
import com.util.ViewPageAdapter;
import com.util.ViewPageChangeListener;
import com.util.WeatherIconHelper;

public class MainPageActivity extends Activity {
	
	public static final String TAG = "MainPageActivity";

	private MainScrollLayout scrollLayout;
	private ViewPager viewpage;
	private LayoutInflater layflater;
	private ArrayList<View> listViews;
	private ImageView imgMaskbg;
	
	//tool
	private TextView txthelpMenu;
	private TextView txtconectMenu;
	private TextView txtAboutMenu;
	
	private ImageView imgSetMenu;
	private ImageView imgUserMenu;
	
	private TextView txtShowPage;
	
	//event
	private MainItemOnClickListener mainItemClickListener;
	private ToolItemOnClickListener toolItemClickListener;

	private ProgressDialog diaLogProgress = null;
	private MainPageSummary mainPageSummary = null;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE); 
		setContentView(R.layout.main_page_layout);
		
		//get param
		Intent inten = this.getIntent();
        Bundle mBundle = inten.getExtras();
	    if (mBundle == null )  return;
	   
	    //init tool
	    this.initToolView();
	    
	    //init main page
		this.initView(mBundle.getString("lstMemu"));
		
		
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
		imgUserMenu = (ImageView) findViewById(R.id.userMenu);
		imgUserMenu.setOnClickListener(toolItemClickListener);
		
		//sub menu
		txthelpMenu = (TextView) findViewById(R.id.help_menu);
		txthelpMenu.setOnClickListener(toolItemClickListener);
		txtconectMenu = (TextView) findViewById(R.id.conect_menu);
		txtconectMenu.setOnClickListener(toolItemClickListener);
		txtAboutMenu = (TextView) findViewById(R.id.main_about);
		txtAboutMenu.setOnClickListener(toolItemClickListener);
	}
	
	
	/**
	 * init the pageView
	 */
	private void initView(String strMenu) {
		layflater = LayoutInflater.from(this);
		listViews = new ArrayList<View>();
		mainItemClickListener= new MainItemOnClickListener();
		
		viewpage = (ViewPager) findViewById(R.id.pager);
		
		//use mainScrollLayout
		scrollLayout = (MainScrollLayout) findViewById(R.id.mainScrollLayout);
		RelativeLayout item1 = (RelativeLayout) layflater.inflate(R.layout.main_page_item_1, null).findViewById(R.id.main_page_item1);
		RelativeLayout item2 = (RelativeLayout) layflater.inflate(R.layout.main_page_item_2, null).findViewById(R.id.main_page_item2);
		RelativeLayout item3 = (RelativeLayout) layflater.inflate(R.layout.main_page_item_3, null).findViewById(R.id.main_page_item3);
		RelativeLayout item4 = (RelativeLayout) layflater.inflate(R.layout.main_page_item_4, null).findViewById(R.id.main_page_item4);
		RelativeLayout item5 = (RelativeLayout) layflater.inflate(R.layout.main_page_item_5, null).findViewById(R.id.main_page_item5);
		RelativeLayout item6 = (RelativeLayout) layflater.inflate(R.layout.main_page_item_6, null).findViewById(R.id.main_page_item6);
		RelativeLayout item7 = (RelativeLayout) layflater.inflate(R.layout.main_page_item_7, null).findViewById(R.id.main_page_item7);
		
		
		//TODO
		
		List<RelativeLayout> itemList = new ArrayList<RelativeLayout>();
		for(int i = 0; i < strMenu.length(); i++ )
		{   
			if (strMenu.charAt(i) == '1') itemList.add(item1);
			if (strMenu.charAt(i) == '2') itemList.add(item2);
			if (strMenu.charAt(i) == '3') itemList.add(item3);
			if (strMenu.charAt(i) == '4') itemList.add(item4);
			if (strMenu.charAt(i) == '5') itemList.add(item5);
			if (strMenu.charAt(i) == '6') itemList.add(item6);
			if (strMenu.charAt(i) == '7') itemList.add(item7);
		}
		
		//create row
		int rowNum = 0;
		if(itemList.size() >= 2 && (itemList.size() % 2) == 0){			
			rowNum = (itemList.size())/2;
		}
		else	
		{
			rowNum = (itemList.size())/2+1;
		}
		List<TableRow> rowList = new ArrayList<TableRow>();
		for(int i = 0; i < rowNum; i++ )
		{ 
			TableRow rowlayout = new TableRow(this);
			rowlayout.removeAllViews();
			rowlayout.setId(i);
			
			RelativeLayout col1 = (RelativeLayout)itemList.get(i*2);
			col1.setOnClickListener(mainItemClickListener);
			rowlayout.addView(col1.getRootView(), 0);
			if((i*2+1) < itemList.size()){
				RelativeLayout col2 = (RelativeLayout)itemList.get(i*2+1);
				col2.setOnClickListener(mainItemClickListener);
				rowlayout.addView(col2.getRootView(), 1);
			}
			rowList.add(rowlayout);
		}
		
		//create page
		int pageNum =0;
		if(rowList.size()>= 3 && (rowList.size() % 3) == 0){			
			pageNum = rowList.size()/3;
		}
		else	
		{
			pageNum = (rowList.size()/3) + 1 ;
		}
		int rowIndex = 0;
		for(int i = 0; i < pageNum; i++ )
		{ 
			LinearLayout pageLayout=new LinearLayout(this);
			LayoutParams ltp=new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
			 
			TableLayout tbLayout = new TableLayout(this);
			tbLayout.removeAllViews();
			
			if(rowList.size() == 1){
				TableRow rowlayout = (TableRow)rowList.get(0);
				tbLayout.addView(rowlayout);
			}else{
				for(int r = 0;r < 3 && rowIndex < rowList.size() - 1 ;r++)
				{
					rowIndex = i * 3 + r;
					TableRow rowlayout = (TableRow)rowList.get(rowIndex);
					tbLayout.addView(rowlayout);
				}
			}
			pageLayout.addView(tbLayout, ltp);
			listViews.add(pageLayout);
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
        	    	mainPageSummary = BusinessRequest.getMainPageSummary();
        	    	msgSend.what = ConstDefine.MSG_I_HANDLE_OK;
					} catch (Exception e) {
						msgSend.what = ConstDefine.MSG_I_HANDLE_Fail;
					}
            	    mainPageSummaryHandler.sendMessage(msgSend);
            	}
        }.start();	 
	 }
	
	private Handler mainPageSummaryHandler = new Handler() {               
        public void handleMessage(Message message) {
                switch (message.what) {
                case ConstDefine.MSG_I_HANDLE_OK:                                        
        		 	diaLogProgress.dismiss();
        		 	
        		 	setTemperatureData();
        		 	setMessagerData();
                    break;
                case ConstDefine.MSG_I_HANDLE_Fail:                                        
                	//close process
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
		  tvHighest.setText(mainPageSummary.getStrForecastHighest() + "/" + mainPageSummary.getStrForecastLowest());	
		  ImageView imgWeatherIcon = (ImageView) findViewById(R.id.main_item_three_image);
		  
		  //TODO: read weather icon id and get resource id
		  Random rand = new Random();
		  Integer intWeatherId = rand.nextInt(34);
		  Integer intImageResource = WeatherIconHelper.getWeatherIconResourceId(intWeatherId);
		  
		  if(intImageResource != 0){
			  imgWeatherIcon.setImageDrawable(getResources().getDrawable(intImageResource));
		  }
		  Date today = new Date();
		  tvToday.setText("北京" + today.getDate() + "日");
		  tvWeather.setText(mainPageSummary.getStrWind() + " " + mainPageSummary.getStrWeather());
		  
	  }
	  private void setMessagerData( )
	  {
		  TextView tvPhotoCount = (TextView) findViewById(R.id.main_item_four_txt1);
		  TextView tvMessageCount = (TextView) findViewById(R.id.main_item_four_txt2);
		  tvPhotoCount.setText(mainPageSummary.getStrCountPhotos());
		  tvMessageCount.setText(mainPageSummary.getStrCountMessages());
	  }
    /**
     * click listren
     * @author zhaors
     *
     */
	private class MainItemOnClickListener implements OnClickListener {

		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.main_page_item1:
				Intent intent_1 = new Intent(MainPageActivity.this, DailyListActivity.class); 
				startActivity(intent_1);
				break;
			case R.id.main_page_item2:
				Intent intent_2 = new Intent(MainPageActivity.this,WarnListActivity.class); 
				startActivity(intent_2);
				break;				
			case R.id.main_page_item3:
				Intent intent_3 = new Intent(MainPageActivity.this, WeatherListActivity.class); 
				startActivity(intent_3);
				break;
			case R.id.main_page_item4:
				Intent intent_4 = new Intent(MainPageActivity.this, MsgUpMainActivity.class); 
				startActivity(intent_4);
				break;				
			case R.id.main_page_item5:
				Intent intent_5 = new Intent(MainPageActivity.this, HotSourceMainActivity.class); 
				startActivity(intent_5);
				break;				
			case R.id.main_page_item6:
				Intent intent_6 = new Intent(MainPageActivity.this, HotPositionMainActivity.class); 
				startActivity(intent_6);
				break;
			case R.id.main_page_item7:
				Intent intent_7 = new Intent(MainPageActivity.this, KnowledgeBaseListActivity.class); 
				startActivity(intent_7);
				break;
			case R.id.main_page_menu:
				Log.i(TAG, "main_menu");
				DisplayMetrics metrics = new DisplayMetrics();
				MainPageActivity.this.getWindowManager().getDefaultDisplay().getMetrics(metrics);
				int width = metrics.widthPixels;
				Log.i(TAG, "width=" + width);
				scrollLayout.scrollToRigth(width);
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
				BaseHelper.showToastMsg(MainPageActivity.this, getString(R.string.under_construction_message));
				break;
			case R.id.settingMenu:
				BaseHelper.showToastMsg(MainPageActivity.this, getString(R.string.under_construction_message));
				break;
			case R.id.userMenu:
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