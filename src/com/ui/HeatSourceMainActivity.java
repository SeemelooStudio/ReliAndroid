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

public class HeatSourceMainActivity extends Activity {

		private ViewPager viewpage;
		private ViewGroup viewGroup; 
		private View[] indicators; 
		private ProgressDialog diaLogProgress= null;
		private HeatSourceTitle  titleInfo = null;
		
		private ArrayList<View> views;
		private ArrayList<HeatSourceMainItem>  dbHeatSources = null;
		
		private static int ROW_COUNT = 4;
		private static int COLUMN_COUNT = 3;
		private static int PAGE_SIZE =10;
		
	    @Override                                                                                            
	    public void onCreate(Bundle savedInstanceState) {                                                    
			super.onCreate(savedInstanceState);
			setContentView(R.layout.heat_source_main);
			
			//init view
			this.initHotSourceView();
	  }
	  
    /**
     * get hostSoruce list
     */
	private void initHotSourceView()
	{
		
		titleInfo = new HeatSourceTitle();
		dbHeatSources = new ArrayList<HeatSourceMainItem>();
		diaLogProgress = BaseHelper.showProgress(HeatSourceMainActivity.this,ConstDefine.I_MSG_0003,false);
		
	    new Thread() {
	        public void run() { 
	                Message msgSend = new Message();
	        	    try {
	        	    	// get itemList
	        	    	dbHeatSources = BusinessRequest.getHeatSourceMainList();
	        	    	
	        	    	//get static Info
	        	    	titleInfo = BusinessRequest.getHeatSourceAllStatic();

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
		  txtAllnum.setText( getString(R.string.heat_source_count) + dbHeatSources.size() ); 
		  txtAllDay.setText( getString(R.string.east_area) + titleInfo.getStrEastArea() + getString(R.string.area_unit) );
		  txtWest.setText( getString(R.string.west_area) +  titleInfo.getStrWestArea() + getString(R.string.area_unit) ); 
		  txtAllNet.setText( getString(R.string.total_heat_load) + titleInfo.getStrHeatLoad() + getString(R.string.heat_unit) ); 
	  }
	  
	  private View getHeatSourceSummaryCell(HeatSourceTitle title, int rowIndex, int columnIndex, int cellWidth, int cellHeight)
	  {
		  int ceilMargin = (int)getResources().getDimension(R.dimen.small_margin);
		  LayoutInflater inflater = (LayoutInflater)getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);
		  View viewSummary = inflater.inflate(R.layout.heat_source_main_title_item, null);
		  setHeatSourceSummaryContent(viewSummary, title);
		  
		  Spec row = GridLayout.spec(rowIndex, 1);
		  Spec colspan = GridLayout.spec(columnIndex, 2);
		  GridLayout.LayoutParams param =new GridLayout.LayoutParams(row, colspan);
		  param.setGravity(Gravity.FILL);
		  param.width = cellWidth;
		  param.height = cellHeight;
		  param.setMargins(ceilMargin, ceilMargin, ceilMargin, ceilMargin);
		  viewSummary.setLayoutParams (param);
	      viewSummary.setBackgroundResource(R.color.midnight_blue);
	     
    	  param.setGravity(Gravity.FILL);                                                          
    	  viewSummary.setOnClickListener(new OnClickListener(){                                                                                    
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
	      
	      //TODO: set background color base on heat source state
		  Random rand = new Random();
		  Integer intState = rand.nextInt(3);
		  Integer intBackgroundResource = CellBackgroundHelper.getBackgroundResourceByCellState(intState);
		  
      	  viewHeatSource.setBackgroundResource(intBackgroundResource);
      	  viewHeatSource.setLayoutParams (param);
      	  viewHeatSource.setOnClickListener(new heatSourceCellOnClickListener(
      			  heatSource.getHeatSourceName(), 
      			  heatSource.getHeatSourceId(),
      			  heatSource.getEastOrWest(),
      			  heatSource.getInnerOrOuter(),
      			  heatSource.getPeakCoalCount(),
      			  heatSource.getPeakGasCount(),
      			  heatSource.getWaterLine(),
      			  heatSource.getGasLine(),
      			  heatSource.isInSystem()){                                                                                    
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
		  });
      	
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
		        if(rowIndex == 1 && columnIndex == 0  ){
		        	gridLayout.addView( getHeatSourceSummaryCell(titleInfo, rowIndex, columnIndex, bigCellWidth, cellHeight) );
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
	            
		    	TextView indicator = new TextView(HeatSourceMainActivity.this);
		    	indicators[i] = indicator;
		    	indicator.setGravity(Gravity.CENTER);
		    	indicator.setTextColor(Color.BLACK);
		    	
		    	
	            if (i == 0) {
	            	LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(focusedSize,focusedSize);
	            	params.setMargins(0, 0, 10, 0);
	            	indicator.setLayoutParams(params);
	            	indicator.setText("1");
	                indicators[i].setBackgroundResource(R.drawable.page_indicator_focused);
	            } else {
	            	LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(normalSize,normalSize);
	            	params.setMargins(0, 0, 10, 0);
	            	indicator.setLayoutParams(params);
	                indicators[i].setBackgroundResource(R.drawable.page_indicator);  
	            }             
	            viewGroup.addView(indicators[i]);  
	        } 	  
	  }
	  private abstract class heatSourceCellOnClickListener implements
		OnClickListener {
			String heatSourceName;
			int heatSourceId;
			String eastOrWest;
			String innerOrOuter;
			int peakCoalCount;
			int peakGasCount;
			String waterLine;
			String gasLine;
			boolean isInSystem; // shifou bingwanggongre
			
			public heatSourceCellOnClickListener(String heatSourceName, int heatSourceId, String eastOrWest, String innerOrOuter,
												 int peakCoalCount, int peakGasCount, String waterLine, String gasLine, boolean isInSystem) {
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
