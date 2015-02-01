package com.ui;

import java.util.ArrayList;
 
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.GridLayout.Spec;

import com.model.HeatSourceMainItem;
import com.model.HeatSourceTitle;
import com.reqst.BusinessRequest;
import com.util.BaseHelper;
import com.util.CellBackgroundHelper;
import com.util.ConstDefine;
import com.util.ViewPageAdapter;
import com.util.ViewPageChangeListener;

public class HeatSourceMainActivity extends Activity 
{
	private ViewPager viewpage;
	private ViewGroup viewGroup; 
	private View[] indicators; 
	private ProgressDialog diaLogProgress= null;
	private HeatSourceTitle  titleInfo = null;
	
	private ArrayList<View> views;
	private ArrayList<HeatSourceMainItem>  dbHeatSources = null;
	
	private static int ROW_COUNT = 4;
	private static int COLUMN_COUNT = 3;
	private static int PAGE_SIZE =12;
	
	private Activity _activity;
	
    @Override                                                                                            
    public void onCreate(Bundle savedInstanceState) 
    {                                                    
		super.onCreate(savedInstanceState);
		setContentView(R.layout.heat_source_main);
		_activity =  this;
		this.initHotSourceView();
    }
	  
	private void initHotSourceView()
	{
		titleInfo = new HeatSourceTitle();
		dbHeatSources = new ArrayList<HeatSourceMainItem>();
		diaLogProgress = BaseHelper.showProgress(HeatSourceMainActivity.this,ConstDefine.I_MSG_0003,false);
	    new Thread() 
	    {
	        public void run() 
	        { 
                Message msgSend = new Message();
        	    try 
        	    {
        	    	dbHeatSources = BusinessRequest.getHeatSourceMainList(_activity);
        	    	titleInfo = BusinessRequest.getHeatSourceAllStatic(_activity);
        	    	msgSend.what = ConstDefine.MSG_I_HANDLE_OK;
				} 
        	    catch (Exception e) 
        	    {
					msgSend.what = ConstDefine.MSG_I_HANDLE_Fail;
				}
                handler.sendMessage(msgSend);
        	}
	    }.start();
	}  
	
	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() 
	{               
		public void handleMessage(Message message) 
		{
		    switch (message.what) 
		    {
			    case ConstDefine.MSG_I_HANDLE_OK:                                        
				 	diaLogProgress.dismiss();
				 	setHotSourceGridView();
				    break;
			    case ConstDefine.MSG_I_HANDLE_Fail:                                        
			    	diaLogProgress.dismiss();
			    	BaseHelper.showToastMsg(HeatSourceMainActivity.this,ConstDefine.E_MSG_0001);
			        break;
		    }
		}
	};
	    
	private void setHeatSourceItemContent( View viewHeatSource, HeatSourceMainItem item)
	{
		TextView tvSourceArea =  (TextView) viewHeatSource.findViewById(R.id.heat_source_area);
		tvSourceArea.setText(item.getEastOrWest() + ", " + item.getInnerOrOuter() );
		TextView tvSourceType =  (TextView) viewHeatSource.findViewById(R.id.heat_source_unit_type);
		tvSourceType.setText(item.getHeatSourceType() );
		TextView tvHeatSourceName = (TextView) viewHeatSource.findViewById(R.id.hot_source_name);
		tvHeatSourceName.setText(item.getHeatSourceName());
	}

	private void setHeatSourceSummaryContent( View viewHeatSource, HeatSourceTitle title)
	{			
		TextView txtAllnum = (TextView) viewHeatSource.findViewById(R.id.hotSrcTitleAllnum);
		TextView txtAllDay= (TextView) viewHeatSource.findViewById(R.id.hotSrcTitleAllDay);
		TextView txtWest = (TextView) viewHeatSource.findViewById(R.id.hotSrcTitleAllWest);
		TextView txtAllNet = (TextView) viewHeatSource.findViewById(R.id.hotSrcTitleAllNet);
		TextView txtCountHeatSources = (TextView) viewHeatSource.findViewById(R.id.countHeatSources);
		TextView txtCountIC = (TextView) viewHeatSource.findViewById(R.id.countIC);
		TextView txtCountAuto = (TextView) viewHeatSource.findViewById(R.id.countAuto);
		TextView txtCountActive = (TextView) viewHeatSource.findViewById(R.id.countActive);
		TextView txtTodaysGJ = (TextView) viewHeatSource.findViewById(R.id.todaysGJ);
		TextView txtYesterdaysGJ = (TextView) viewHeatSource.findViewById(R.id.yesterdaysGJ);
		TextView txtArea = (TextView) viewHeatSource.findViewById(R.id.area);
		TextView txtEastArea = (TextView) viewHeatSource.findViewById(R.id.eastArea);
		TextView txtWestArea= (TextView) viewHeatSource.findViewById(R.id.westArea);
		TextView txtActualArea = (TextView) viewHeatSource.findViewById(R.id.actualArea);
		TextView txtHeatLoad = (TextView) viewHeatSource.findViewById(R.id.heatLoad);
		TextView txtEastHeatLoad = (TextView) viewHeatSource.findViewById(R.id.eastHeatLoad);
		TextView txtWestHeatLoad = (TextView) viewHeatSource.findViewById(R.id.westHeatLoad);
		TextView txtHeatLoadPlanned = (TextView) viewHeatSource.findViewById(R.id.heatLoadPlanned);
		TextView txtEastHeatLoadPlanned = (TextView) viewHeatSource.findViewById(R.id.heatLoadEastPlanned);
		TextView txtWestHeatLoadPlanned = (TextView) viewHeatSource.findViewById(R.id.heatLoadWestPlanned);
		TextView txtHeatLoadCalculated = (TextView) viewHeatSource.findViewById(R.id.heatLoadCalculated);
		TextView txtEastHeatLoadCalculated = (TextView) viewHeatSource.findViewById(R.id.heatLoadEastCalculated);
		TextView txtWestHeatLoadCalculated = (TextView) viewHeatSource.findViewById(R.id.heatLoadWestCalculated);
		
		txtAllnum.setText( getString(R.string.heat_source_count) + dbHeatSources.size() ); 
		txtAllDay.setText( getString(R.string.east_area) + titleInfo.getEastArea() + getString(R.string.area_unit) );
		txtWest.setText( getString(R.string.west_area) +  titleInfo.getWestArea() + getString(R.string.area_unit) ); 
		txtAllNet.setText( getString(R.string.total_heat_load) + titleInfo.getHeatLoad() + getString(R.string.heat_unit) ); 
		txtCountHeatSources.setText(  getString(R.string.heat_source_count) + titleInfo.getCountHeatSources() );
		txtCountAuto.setText( "监控站个数: "+ titleInfo.getCountAuto() + "" );
		txtCountIC.setText("智能卡站个数:" + titleInfo.getCountIC() + "" );
		txtCountActive.setText( "有效站个数:" + titleInfo.getCountActive() + "");
		txtTodaysGJ.setText( "今日累计供热量:" + titleInfo.getTodaysGJ() + "");
		txtYesterdaysGJ.setText( "昨日累计供热量:" + titleInfo.getYesterdaysGJ() + "");
		txtArea.setText("总面积:" + titleInfo.getArea() );
		txtEastArea.setText("东部面积:" + titleInfo.getEastArea() );
		txtWestArea.setText("西部面积:" + titleInfo.getWestArea() );
		txtActualArea.setText("实际投入面积面积:" + titleInfo.getActualArea() );
		txtHeatLoad.setText("全网GJ：" + titleInfo.getHeatLoad());
		txtEastHeatLoad.setText("东部GJ：" + titleInfo.getEastHeatLoad());
		txtWestHeatLoad.setText("西部GJ：" + titleInfo.getWestHeatLoad());
		txtHeatLoadPlanned.setText("全网计划GJ：" + titleInfo.getHeatLoadPlanned());
		txtEastHeatLoadPlanned.setText("东部计划GJ：" + titleInfo.getHeatLoadEastPlanned());
		txtWestHeatLoadPlanned.setText("西部计划GJ：" + titleInfo.getHeatLoadWestPlanned());
		txtHeatLoadCalculated.setText("全网核算GJ：" + titleInfo.getHeatLoadCalculated());
		txtEastHeatLoadCalculated.setText("东部核算GJ：" + titleInfo.getHeatLoadEastCalculated());
		txtWestHeatLoadCalculated.setText("西部核算GJ：" + titleInfo.getHeatLoadWestCalculated());

	}
	  
	private View getHeatSourceSummaryCell(HeatSourceTitle title, int rowIndex, int columnIndex, int cellWidth, int cellHeight)
	{
		int ceilMargin = (int)getResources().getDimension(R.dimen.small_margin);
		LayoutInflater inflater = (LayoutInflater)getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);
		View viewSummary = inflater.inflate(R.layout.heat_source_main_title_item, null);
		setHeatSourceSummaryContent(viewSummary, title);  
		Spec row = GridLayout.spec(rowIndex, 4);
		Spec colspan = GridLayout.spec(columnIndex, 3);
		GridLayout.LayoutParams param =new GridLayout.LayoutParams(row, colspan);
		param.setGravity(Gravity.FILL);
		param.width = cellWidth;
		param.height = cellHeight;
		param.setMargins(ceilMargin, ceilMargin, ceilMargin, ceilMargin);
		viewSummary.setLayoutParams (param);
	    viewSummary.setBackgroundResource(R.color.midnight_blue);
	     
    	param.setGravity(Gravity.FILL);                                                          
    	viewSummary.setOnClickListener(new OnClickListener()
    	{                                                                                    
			  public void onClick(View v) 
			  {   
				  Intent intent = new Intent(HeatSourceMainActivity.this, HeatSourceQueryActivity.class); 
				  startActivity(intent);
			  }
		});
    	return viewSummary;
    }
	  
	private View getHeatSourceCell(HeatSourceMainItem heatSource, int rowIndex, int columnIndex, int cellWidth, int cellHeight)
	{
		int ceilMargin = (int)getResources().getDimension(R.dimen.small_margin);
		LayoutInflater inflater = (LayoutInflater)getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);
		View viewHeatSource = inflater.inflate(R.layout.heat_source_main_item, null);
		setHeatSourceItemContent(viewHeatSource, heatSource);
		  
		Spec row = GridLayout.spec(rowIndex, 1);
		Spec colspan = GridLayout.spec(columnIndex, 1);
		GridLayout.LayoutParams param =new GridLayout.LayoutParams(row, colspan);
		param.setGravity(Gravity.FILL);
		param.width = cellWidth;
		param.height = cellHeight;
		param.setMargins(ceilMargin, ceilMargin, ceilMargin, ceilMargin);
	      
		int intState = CellBackgroundHelper.CELL_STATE_OUTTER;
	    if ( !heatSource.isOuter() ) 
	    {
	    	if ( heatSource.isEast() ) 
	    	{
	    		intState = CellBackgroundHelper.CELL_STATE_EAST;
	    	} 
	    	else 
	    	{
	    		  intState = CellBackgroundHelper.CELL_STATE_WEST;
	    	}
	    }
		Integer intBackgroundResource = CellBackgroundHelper.getBackgroundResourceByCellState(intState);
		  
      	viewHeatSource.setBackgroundResource(intBackgroundResource);
      	viewHeatSource.setLayoutParams (param);
      	viewHeatSource.setOnClickListener(
  			new heatSourceCellOnClickListener(
      			heatSource.getHeatSourceName(), 
      			heatSource.getHeatSourceId(),
      			heatSource.getEastOrWest(),
      			heatSource.getInnerOrOuter(),
      			heatSource.getPeakCoalCount(),
      			heatSource.getPeakGasCount(),
      			heatSource.getWaterLine(),
      			heatSource.getGasLine(),
      			heatSource.isInSystem())
  			{                                                                                    
  				public void onClick(View v) 
  				{   
					Intent intent = new Intent(HeatSourceMainActivity.this, HeatSourceDetailActivity.class);
					Bundle bundle = new Bundle();
					bundle.putString("heat_source_name", heatSourceName);
					bundle.putInt("heat_source_id", heatSourceId);
					bundle.putString("heat_source_east_or_west", eastOrWest);
					bundle.putString("heat_source_inner_or_outer", innerOrOuter);
					bundle.putInt("heat_source_peak_coal_count", peakCoalCount);
					bundle.putInt("heat_source_peak_gas_count", peakGasCount);
					bundle.putString("heat_source_water_line", waterLine);
					bundle.putString("heat_source_gas_line", gasLine);
					bundle.putBoolean("heat_source_is_in_system", isInSystem);
					intent.putExtras(bundle);
					startActivity(intent);
  				}
			}
		);
  		return viewHeatSource;
	}
	  
	private void setHotSourceGridView()
	{
		viewpage = (ViewPager) findViewById(R.id.hotMainPager);
		int pageNum = (int)Math.ceil( (float)dbHeatSources.size() / PAGE_SIZE );
		int screenWidth = viewpage.getWidth();
		int screenHeight = viewpage.getHeight();
		int ceilMargin = (int)getResources().getDimension(R.dimen.small_margin) * 2;
		int cellWidth = (int)( screenWidth  / COLUMN_COUNT - ceilMargin); 
		int cellHeight = (int) ( screenHeight / ROW_COUNT - ceilMargin);
		int bigCellWidth = cellWidth * 3 + ceilMargin * 2; 
		views = new ArrayList<View>();
		for(int pageIndex = 0; pageIndex < pageNum; pageIndex++ )
		{
			GridLayout gridLayout = new GridLayout(this);
			gridLayout.setColumnCount(COLUMN_COUNT);
			gridLayout.setRowCount(ROW_COUNT);
			gridLayout.setOrientation(GridLayout.HORIZONTAL);
			gridLayout.setUseDefaultMargins(true);
			if(pageIndex == 0)
			{
				gridLayout.addView( getHeatSourceSummaryCell(titleInfo, 0, 0, bigCellWidth, cellHeight) );
			}
			else 
			{
				for(int cell = 0, rowIndex = 0, columnIndex=0, itemIndex = (pageIndex-1) * PAGE_SIZE + cell; 
		        		cell < PAGE_SIZE && itemIndex < dbHeatSources.size(); 
		        		cell ++, columnIndex++, itemIndex++)
				{
					if(columnIndex == COLUMN_COUNT) 
					{
						columnIndex = 0;
						rowIndex++;
					}
					gridLayout.addView(getHeatSourceCell(dbHeatSources.get(itemIndex), rowIndex, columnIndex, cellWidth, cellHeight));
				}
			}
			    
			gridLayout.setId(pageIndex);
			views.add(gridLayout);
		}
        
		setIndicatorView();
		viewpage.setAdapter(new ViewPageAdapter(views));
		viewpage.setOnPageChangeListener(new ViewPageChangeListener(indicators)); 
	}
	  
	private void setIndicatorView() 
	{
		indicators = new View[views.size()];  
		viewGroup = (ViewGroup)findViewById(R.id.hotSorceIndicators);
		int focusedSize = 40;
		int normalSize = 20;

		for (int i = 0; i < views.size(); i++) 
		{  
			TextView indicator = new TextView(HeatSourceMainActivity.this);
			indicators[i] = indicator;
			indicator.setGravity(Gravity.CENTER);
			indicator.setTextColor(Color.BLACK);
			if (i == 0) 
			{
				LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(focusedSize,focusedSize);
				params.setMargins(0, 0, 10, 0);
				indicator.setLayoutParams(params);
				indicator.setText("1");
			    indicators[i].setBackgroundResource(R.drawable.page_indicator_focused);
			} 
			else 
			{
				LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(normalSize,normalSize);
				params.setMargins(0, 0, 10, 0);
				indicator.setLayoutParams(params);
			    indicators[i].setBackgroundResource(R.drawable.page_indicator);  
			}             
			viewGroup.addView(indicators[i]);  
		} 	  
	}
	
	private abstract class heatSourceCellOnClickListener implements OnClickListener 
	{
		String heatSourceName;
		int heatSourceId;
		String eastOrWest;
		String innerOrOuter;
		int peakCoalCount;
		int peakGasCount;
		String waterLine;
		String gasLine;
		boolean isInSystem; 
			
		public heatSourceCellOnClickListener(String heatSourceName, int heatSourceId, String eastOrWest, String innerOrOuter,
											 int peakCoalCount, int peakGasCount, String waterLine, String gasLine, boolean isInSystem) 
		{
			this.heatSourceName = heatSourceName;
			this.heatSourceId = heatSourceId;
			this.eastOrWest = eastOrWest;
			this.innerOrOuter = innerOrOuter;
			this.peakCoalCount = peakCoalCount;
			this.peakGasCount = peakGasCount;
			this.waterLine = waterLine;
			this.gasLine = gasLine;
			this.isInSystem = isInSystem;
		}
	};
}  
