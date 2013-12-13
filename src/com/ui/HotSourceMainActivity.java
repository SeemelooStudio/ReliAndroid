package com.ui;

import java.util.ArrayList;
import java.util.Random;
 
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
import android.view.ViewGroup.LayoutParams;
import android.widget.GridLayout;
import android.widget.TextView;

import com.model.HotSrcMainItem;
import com.model.HotSrcTitleInfo;
import com.reqst.BusinessRequest;
import com.util.BaseHelper;
import com.util.CellBackgroundHelper;
import com.util.ConstDefine;
import com.util.ViewPageAdapter;
import com.util.ViewPageChangeListener;

public class HotSourceMainActivity extends Activity {

		private ViewPager viewpage;
		private ViewGroup viewGroup; 
		private View[] indicators; 
		private ProgressDialog diaLogProgress= null;
		private HotSrcTitleInfo  titleInfo = null;
		
		private ArrayList<View> views;
		private ArrayList<HotSrcMainItem>  dbHeatSources = null;
		
		private static int ROW_COUNT = 4;
		private static int COLUMN_COUNT = 3;
		private static int PAGE_SIZE =10;
		
	    @Override                                                                                            
	    public void onCreate(Bundle savedInstanceState) {                                                    
			super.onCreate(savedInstanceState);
			setContentView(R.layout.hot_source_main);
			
			//init view
			this.initHotSourceView();
	  }
	  
    /**
     * get hostSoruce list
     */
	private void initHotSourceView()
	{
		
		titleInfo = new HotSrcTitleInfo();
		dbHeatSources = new ArrayList<HotSrcMainItem>();
		diaLogProgress = BaseHelper.showProgress(HotSourceMainActivity.this,ConstDefine.I_MSG_0003,false);
		
	    new Thread() {
	        public void run() { 
	                Message msgSend = new Message();
	        	    try {
	        	    	// get itemList
	        	    	dbHeatSources = BusinessRequest.getHotSourceMainList();
	        	    	
	        	    	//get static Info
	        	    	titleInfo = BusinessRequest.getHotSourceAllStatic();

	        	        //get mapList
	        	    	msgSend.what = ConstDefine.MSG_I_HANDLE_OK;
					} catch (Exception e) {
						msgSend.what = ConstDefine.MSG_I_HANDLE_Fail;
					}
	                handler.sendMessage(msgSend);
	        	}
	    }.start();
		
	}  
	
	 /**
     * http handler result
     */
    @SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {               
        public void handleMessage(Message message) {
                switch (message.what) {
                case ConstDefine.MSG_I_HANDLE_OK:                                        
        		 	diaLogProgress.dismiss();
        		 	//show Gridview
        		 	setHotSourceGridView();
        		 	
        		    break;
                case ConstDefine.MSG_I_HANDLE_Fail:                                        
                	//close process
                	diaLogProgress.dismiss();
                	BaseHelper.showToastMsg(HotSourceMainActivity.this,ConstDefine.E_MSG_0001);
                    break;
	            }
	        }
	  };
	    
	  private void setHeatSourceItemContent( View viewHeatSource, HotSrcMainItem item)
	  {
		 TextView tvPressureOut =  (TextView) viewHeatSource.findViewById(R.id.hot_source_pressure_out);
		 tvPressureOut.setText(item.getPressureOut() + getString(R.string.pressure_unit) );
		 TextView tvPressureIn =  (TextView) viewHeatSource.findViewById(R.id.hot_source_pressure_in);
		 tvPressureIn.setText(item.getPressureIn() + getString(R.string.pressure_unit) );
		 TextView tvTemperatureOut =  (TextView) viewHeatSource.findViewById(R.id.hot_source_temperature_out);
		 tvTemperatureOut.setText(item.getPressureOut() + getString(R.string.degree_unit) );
		 TextView tvTemperatureIn =  (TextView) viewHeatSource.findViewById(R.id.hot_source_temperature_in);
		 tvTemperatureIn.setText(item.getPressureIn() + getString(R.string.degree_unit) );
		 
		 TextView tvHeatSourceName = (TextView) viewHeatSource.findViewById(R.id.hot_source_name);
		 tvHeatSourceName.setText(item.getHeatSourceName());
	  }

	  private void setHeatSourceSummaryContent( View viewHeatSource, HotSrcTitleInfo title)
	  {			
		  TextView txtAllnum = (TextView) viewHeatSource.findViewById(R.id.hotSrcTitleAllnum);
		  TextView txtAllDay= (TextView) viewHeatSource.findViewById(R.id.hotSrcTitleAllDay);
		  TextView txtWest = (TextView) viewHeatSource.findViewById(R.id.hotSrcTitleAllWest);
		  TextView txtAllNet = (TextView) viewHeatSource.findViewById(R.id.hotSrcTitleAllNet);
		  txtAllnum.setText( getString(R.string.heat_source_count) + dbHeatSources.size() ); 
		  txtAllDay.setText( getString(R.string.east_area) + titleInfo.getStrEastArea() + getString(R.string.area_unit) );
		  txtWest.setText( getString(R.string.west_area) +  titleInfo.getStrWestArea() + getString(R.string.area_unit) ); 
		  txtAllNet.setText( getString(R.string.total_heat_load) + titleInfo.getStrHeatLoad() + getString(R.string.heat_unit) ); 
	  }
	  
	  private View getHeatSourceSummaryCell(HotSrcTitleInfo title, int rowIndex, int columnIndex, int cellWidth, int cellHeight)
	  {
		  LayoutInflater inflater = (LayoutInflater)getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);
		  View viewSummary = inflater.inflate(R.layout.hot_source_main_title_item, null);
		  setHeatSourceSummaryContent(viewSummary, title);
		  GridLayout.LayoutParams param =new GridLayout.LayoutParams();
	      param.rowSpec = GridLayout.spec(rowIndex);
	      param.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 2);
	      param.width = cellWidth;
	      param.height = cellHeight;
	      viewSummary.setBackgroundResource(R.color.midnight_blue);
	      viewSummary.setLayoutParams (param);
    	  param.setGravity(Gravity.FILL);                                                          
    	  viewSummary.setOnClickListener(new OnClickListener(){                                                                                    
			  public void onClick(View v) 
			  {   
				  Intent intent = new Intent(HotSourceMainActivity.this, HotSourceQueryActivity.class); 
				  startActivity(intent);
			  }
		  });
    	  return viewSummary;
	  }
	  
	  private View getHeatSourceCell(HotSrcMainItem heatSource, int rowIndex, int columnIndex, int cellWidth, int cellHeight)
	  {
		  LayoutInflater inflater = (LayoutInflater)getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);
		  View viewHeatSource = inflater.inflate(R.layout.hot_source_main_item, null);
		  setHeatSourceItemContent(viewHeatSource, heatSource);
		  GridLayout.LayoutParams param =new GridLayout.LayoutParams();
	      param.rowSpec = GridLayout.spec(GridLayout.UNDEFINED);
	      param.columnSpec = GridLayout.spec(GridLayout.UNDEFINED);
	      param.width = cellWidth;
	      param.height = cellHeight;
	      
	      //TODO: set background color base on heat source state
		  Random rand = new Random();
		  Integer intState = rand.nextInt(3);
		  Integer intBackgroundResource = CellBackgroundHelper.getBackgroundResourceByCellState(intState);
		  
      	  viewHeatSource.setBackgroundResource(intBackgroundResource);
      	  viewHeatSource.setLayoutParams (param);
      	  viewHeatSource.setOnClickListener(new OnClickListener(){                                                                                    
			  public void onClick(View v) 
			  {   
				  Intent intent = new Intent(HotSourceMainActivity.this, HotSourceDetailActivity.class); 
				  startActivity(intent);
			  }
		  });
      	
      	  return viewHeatSource;
	  }
	  
	  private void setHotSourceGridView()
	  {
		  viewpage = (ViewPager) findViewById(R.id.hotMainPager);

		  int pageNum = (int)Math.ceil( (float)dbHeatSources.size() / PAGE_SIZE );

		  int screenWidth = viewpage.getWidth();
		  int screenHeight = viewpage.getHeight();
		  
		  int ceilMargin = (int)getResources().getDimension(R.dimen.small_margin);

		  int cellWidth = (int)( screenWidth  / COLUMN_COUNT - ceilMargin); 
		  int cellHeight = (int) ( screenHeight / ROW_COUNT - ceilMargin);
		  
		  int bigCellWidth = cellWidth * 2 + ceilMargin;
		  
        //create page
		  views = new ArrayList<View>();
		for(int pageIndex = 0; pageIndex < pageNum; pageIndex++ )
		{ 

			GridLayout gridLayout = new GridLayout(this);
			gridLayout.setColumnCount(COLUMN_COUNT);
			gridLayout.setRowCount(ROW_COUNT);
			gridLayout.setOrientation(GridLayout.HORIZONTAL);
			gridLayout.setUseDefaultMargins(true);
	        for(int cell = 0, rowIndex = 0, columnIndex=0 ,itemIndex = pageIndex * PAGE_SIZE + cell ; 
	        		cell < PAGE_SIZE && itemIndex < dbHeatSources.size(); 
	        		cell ++, columnIndex++, itemIndex++)
			{
				if(columnIndex == COLUMN_COUNT) {
					columnIndex = 0;
					rowIndex++;
				}
				//show title cell
		        if(rowIndex == 1 && columnIndex == 0){
		        	gridLayout.addView( getHeatSourceSummaryCell(titleInfo, 2, 0, bigCellWidth, cellHeight) );
		        	columnIndex+=1;
		        	cell--;
		        	itemIndex--;
		        }
		        else {
		        	gridLayout.addView(getHeatSourceCell(dbHeatSources.get(itemIndex), rowIndex, columnIndex, cellWidth, cellHeight));
		        }
			}
		    gridLayout.setId(pageIndex);
			views.add(gridLayout);
		}
        
		setIndicatorView();
	    
		//add pages
		viewpage.setAdapter(new ViewPageAdapter(views));
		viewpage.setOnPageChangeListener(new ViewPageChangeListener(indicators)); 
	  }
	  
	  private void setIndicatorView() {
			indicators = new View[views.size()];  
			viewGroup = (ViewGroup)findViewById(R.id.hotSorceIndicators);
			int focusedSize = 40;
			int normalSize = 20;
			
		    for (int i = 0; i < views.size(); i++) {  
	            
		    	TextView indicator = new TextView(HotSourceMainActivity.this);
		    	indicators[i] = indicator;
		    	indicator.setGravity(Gravity.CENTER);
		    	indicator.setTextColor(Color.BLACK);
		    	
	            if (i == 0) {  
	            	indicator.setLayoutParams(new LayoutParams(focusedSize,focusedSize));
	            	indicator.setText("1");
	                indicators[i].setBackgroundResource(R.drawable.page_indicator_focused);
	            } else {
	            	indicator.setLayoutParams(new LayoutParams(normalSize,normalSize));
	                indicators[i].setBackgroundResource(R.drawable.page_indicator);  
	            }             
	            viewGroup.addView(indicators[i]);  
	        } 	  
	  }
     
}  
