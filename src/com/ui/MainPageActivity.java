package com.ui;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;

import com.chart.impl.AverageTemperatureChart;
import com.ctral.MainScrollLayout;
import com.util.ViewPageAdapter;

public class MainPageActivity extends Activity {
	
	public static final String TAG = "MainPageActivity";
	private boolean isOpen = false;

	private MainScrollLayout scrollLayout;
	private ViewPager viewpage;
	private LayoutInflater mInflater;
	private ArrayList<View> views;
	private ImageView main_mask_bg;

	private MyOnClickListener myOnClickListener;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE); 
		setContentView(R.layout.main_page_layout);
		initView();
	}

	/**
	 * init the pageView
	 */
	private void initView() {
		mInflater = LayoutInflater.from(this);
		views = new ArrayList<View>();
		myOnClickListener = new MyOnClickListener();
		
		viewpage = (ViewPager) findViewById(R.id.pager);
		
		//use mainScrollLayout
		scrollLayout = (MainScrollLayout) findViewById(R.id.mainScrollLayout);
		String strMenu="123456";
		RelativeLayout item1 = (RelativeLayout) mInflater.inflate(R.layout.main_page_item_1, null).findViewById(R.id.main_page_item1);
		RelativeLayout item2 = (RelativeLayout) mInflater.inflate(R.layout.main_page_item_2, null).findViewById(R.id.main_page_item2);
		RelativeLayout item3 = (RelativeLayout) mInflater.inflate(R.layout.main_page_item_3, null).findViewById(R.id.main_page_item3);
		RelativeLayout item4 = (RelativeLayout) mInflater.inflate(R.layout.main_page_item_4, null).findViewById(R.id.main_page_item4);
		RelativeLayout item5 = (RelativeLayout) mInflater.inflate(R.layout.main_page_item_5, null).findViewById(R.id.main_page_item5);
		RelativeLayout item6 = (RelativeLayout) mInflater.inflate(R.layout.main_page_item_6, null).findViewById(R.id.main_page_item6);
		RelativeLayout item7 = (RelativeLayout) mInflater.inflate(R.layout.main_page_item_7, null).findViewById(R.id.main_page_item7);
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
			col1.setOnClickListener(myOnClickListener);
			rowlayout.addView(col1.getRootView(), 0);
			if((i*2+1) < itemList.size()){
				RelativeLayout col2 = (RelativeLayout)itemList.get(i*2+1);
				col2.setOnClickListener(myOnClickListener);
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
			TableLayout pageLayout = new TableLayout(this);
			pageLayout.removeAllViews();
			for(int r = 0;r < 3 && rowIndex < rowList.size()-1 ;r++)
			{
				rowIndex = i * 3 + r;
				TableRow rowlayout = (TableRow)rowList.get(rowIndex);
				pageLayout.addView(rowlayout);
			}
			views.add(pageLayout);
		}
		
		main_mask_bg = (ImageView) findViewById(R.id.main_page_menu);
		main_mask_bg.setOnClickListener(myOnClickListener);
		viewpage.setAdapter(new ViewPageAdapter(views));
	}

    /**
     * click listren
     * @author zhaors
     *
     */
	public class MyOnClickListener implements OnClickListener {

		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.main_page_item1:
				Intent intent_1 = new Intent(MainPageActivity.this, DailyListActivity.class); 
				startActivity(intent_1);
				break;
			case R.id.main_page_item2:
				//预警 画面
				Intent intent_2 = new Intent(MainPageActivity.this,WarnListActivity.class); 
				startActivity(intent_2);
				break;
				
			case R.id.main_page_item3:
				//预警 画面
				Intent intent_3 = new Intent(MainPageActivity.this, WeatherListActivity.class); 
				startActivity(intent_3);
				break;
				
			case R.id.main_page_item4:
				//预警 画面
				Intent intent_4 = new Intent(MainPageActivity.this, HotSourceMainActivity.class); 
				startActivity(intent_4);
				break;
				
			case R.id.main_page_item5:
				//预警 画面
				Intent intent_5 = new Intent(MainPageActivity.this, HotSourceMainActivity.class); 
				startActivity(intent_5);
				break;
				
			case R.id.main_page_item6:
				//预警 画面
				//AverageTemperatureChart temchart = new AverageTemperatureChart();
				//Intent intent_6 = temchart.execute(MainPageActivity.this);
				//startActivity(intent_6);
				Intent intent_6 = new Intent(MainPageActivity.this, HotPositionMainActivity.class); 
				startActivity(intent_6);
				break;
				
			case R.id.main_page_menu:
				Log.i(TAG, "main_menu");
				//这里主要是为了适配一些屏幕不同的分辨率
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
}