package com.ui;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.chart.impl.AverageTemperatureChart;
import com.ctral.MainScrollLayout;
import com.util.BaseHelper;
import com.util.ConstDefine;
import com.util.ViewPageAdapter;
import com.util.ViewPageChangeListener;

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
				AverageTemperatureChart temchart = new AverageTemperatureChart();
				Intent intent_7 = temchart.execute(MainPageActivity.this);
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
				BaseHelper.showToastMsg(MainPageActivity.this,ConstDefine.I_MSG_0004);
				break;
			case R.id.conect_menu:
				BaseHelper.showToastMsg(MainPageActivity.this,ConstDefine.I_MSG_0004);
				break;
			case R.id.main_about:
				BaseHelper.showToastMsg(MainPageActivity.this,ConstDefine.I_MSG_0004);
				break;
			case R.id.settingMenu:
				BaseHelper.showToastMsg(MainPageActivity.this,ConstDefine.I_MSG_0004);
				break;
			case R.id.userMenu:
				BaseHelper.showToastMsg(MainPageActivity.this,ConstDefine.I_MSG_0004);
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