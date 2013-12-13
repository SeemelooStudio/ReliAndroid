package com.ui;

import java.util.ArrayList;
import java.util.Random;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.model.HotPosMainItem;
import com.reqst.BusinessRequest;
import com.util.BaseHelper;
import com.util.CellBackgroundHelper;
import com.util.ConstDefine;
import com.util.ViewPageAdapter;
import com.util.ViewPageChangeListener;

public class HotPositionMainActivity extends Activity {


	    private ViewPager viewpage;
	    private ViewGroup viewGroup;
		private View[] indicators; 
		private ProgressDialog diaLogProgress= null;
		
		private ArrayList<View> views;
		private ArrayList<HotPosMainItem>  dbhostPosLst = null;
		private static int ROW_COUNT = 4;
		private static int COLUMN_COUNT = 3;
		private static int PAGE_SIZE =10;
		
	    @Override                                                                                            
	    public void onCreate(Bundle savedInstanceState) {                                                    
			super.onCreate(savedInstanceState);
			requestWindowFeature(Window.FEATURE_NO_TITLE); 
			setContentView(R.layout.hot_position_main);
			
			//init view
			this.initHotPositionView();
	  }
	  
      /**
       * 
       */
	  private void initHotPositionView()
	  {

		viewpage = (ViewPager) findViewById(R.id.hotPosMainPager);
		dbhostPosLst = new ArrayList<HotPosMainItem>();
		diaLogProgress = BaseHelper.showProgress(HotPositionMainActivity.this,ConstDefine.I_MSG_0003,false);
	    new Thread() {
	        public void run() { 
	                Message msgSend = new Message();
	        	    try {
	        	    	  // get itemList
	        		 	 dbhostPosLst = BusinessRequest.getHotPositionMainList();
	        		 	 
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
                    //set grid view
        		 	setHotPositionGridView();
        		    break;
                case ConstDefine.MSG_I_HANDLE_Fail:                                        
                	//close process
                	diaLogProgress.dismiss();
                	BaseHelper.showToastMsg(HotPositionMainActivity.this,ConstDefine.E_MSG_0001);
                    break;
	            }
	        }
	  };
     
	  
	  /**
	   * 
	   * @param viewStation
	   * @param item
	   */
	  private void setStationItemContent(View viewStation, HotPosMainItem item)
	  {
		  TextView tvStationName = (TextView)viewStation.findViewById(R.id.hotPosItemTitle);
		  TextView tvStationId = (TextView)viewStation.findViewById(R.id.hotPosItemId);
		  TextView tvTodayActualGJ = (TextView)viewStation.findViewById(R.id.hotPosItemLeftText);
		  TextView tvTodayPlannedGJ = (TextView)viewStation.findViewById(R.id.hotPosItemLeftTxtPa);
		  TextView tvYesterdayActualGJ =  (TextView)viewStation.findViewById(R.id.hotPosItemRightText);
		  TextView tvYesterdayPlannedGJ = (TextView)viewStation.findViewById(R.id.hotPosItemRightTxtPa);
		  TextView tvYesterdayCalculatedGJ =  (TextView)viewStation.findViewById(R.id.hotPosItemRightTxtPa2);
		  
		  tvStationId.setText(item.getStrStationId()); 
		  tvStationName.setText(item.getStrStationName()); 
		  tvTodayActualGJ.setText(item.getStrActualGJToday()); 
		  tvTodayPlannedGJ.setText(item.getStrPlannedGJToday()); 
		  tvYesterdayActualGJ.setText(item.getStrActualGJYesterday());
		  tvYesterdayPlannedGJ.setText(item.getStrPlannedGJYesterday()); 
		  tvYesterdayCalculatedGJ.setText(item.getStrCalculatedGJYesterday()); 
	  }
	  

	  /***
	   * 
	   * @param title
	   * @param rowIndex
	   * @param columnIndex
	   * @param cellWidth
	   * @param cellHeight
	   * @return
	   */
	  private View getHeatStationTitleCell(int allCount, int rowIndex, int columnIndex, int cellWidth, int cellHeight)
	  {
		  LayoutInflater inflater = (LayoutInflater)getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);
		  View viewHeatStationTitle = inflater.inflate(R.layout.hot_position_main_title_item, null);
		  TextView txtAllnum = (TextView) viewHeatStationTitle.findViewById(R.id.hotPosTitleAllnum);
		  txtAllnum.setText("共" + allCount + "个关键热力站");
		  GridLayout.LayoutParams param =new GridLayout.LayoutParams();
	      param.rowSpec = GridLayout.spec(rowIndex);
	      param.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 2);
	      param.width = cellWidth;
	      param.height = cellHeight;

	      viewHeatStationTitle.setBackgroundResource(R.color.midnight_blue);
	      viewHeatStationTitle.setLayoutParams (param);
    	  param.setGravity(Gravity.FILL);                                                          
    	  viewHeatStationTitle.setOnClickListener(new OnClickListener(){                                                                                    
			  public void onClick(View v) 
			  {   
				  Intent intent = new Intent(HotPositionMainActivity.this, HotPositionQueryActivity.class); 
				  startActivity(intent);
			  }
		  });
    	  return viewHeatStationTitle;
	  }
	  
	  
	 
	  /***
	   * 
	   * @param heatSource
	   * @param rowIndex
	   * @param columnIndex
	   * @param cellWidth
	   * @param cellHeight
	   * @return
	   */
	  private View getHeatStationCell(HotPosMainItem heatSource, int rowIndex, int columnIndex, int cellWidth, int cellHeight)
	  {
		  LayoutInflater inflater = (LayoutInflater)getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);
		  View viewHeatStation= inflater.inflate(R.layout.hot_position_main_item, null);
		  setStationItemContent(viewHeatStation, heatSource);
		  GridLayout.LayoutParams param =new GridLayout.LayoutParams();
	      param.rowSpec = GridLayout.spec(GridLayout.UNDEFINED);
	      param.columnSpec = GridLayout.spec(GridLayout.UNDEFINED);
	      param.width = cellWidth;
	      param.height = cellHeight;

	      //TODO: set background color base on heat source state
		  Random rand = new Random();
		  Integer intState = rand.nextInt(3);
		  Integer intBackgroundResource = CellBackgroundHelper.getBackgroundResourceByCellState(intState);
		  
		  viewHeatStation.setBackgroundResource(intBackgroundResource);
      	  viewHeatStation.setLayoutParams (param);
      	  return viewHeatStation;
	  }
	  
	  /***
	   * 
	   */
	  private void setHotPositionGridView()
	  {
		  viewpage = (ViewPager) findViewById(R.id.hotPosMainPager);

		  int pageNum = (int)Math.ceil( (float)dbhostPosLst.size() / PAGE_SIZE );

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
	        		cell < PAGE_SIZE && itemIndex < dbhostPosLst.size(); 
	        		cell ++, columnIndex++, itemIndex++)
			{
				if(columnIndex == COLUMN_COUNT) {
					columnIndex = 0;
					rowIndex++;
				}
				//show title cell
		        if(rowIndex == 1 && columnIndex == 0){
		        	gridLayout.addView(getHeatStationTitleCell(dbhostPosLst.size(), 2, 0, bigCellWidth, cellHeight));
		        	columnIndex+=1;
		        	cell--;
		        	itemIndex--;
		        }
		        else {
		        	gridLayout.addView(getHeatStationCell(dbhostPosLst.get(itemIndex), rowIndex, columnIndex, cellWidth, cellHeight ));
		        }
			}
		    gridLayout.setId(pageIndex);
			views.add(gridLayout);
		}
        
        
		indicators = new View[views.size()];  
		viewGroup = (ViewGroup)findViewById(R.id.hotPositionViewGroup);  

		setIndicatorView();
		
		//add pages
		viewpage.setAdapter(new ViewPageAdapter(views));
		viewpage.setOnPageChangeListener(new ViewPageChangeListener(indicators)); 
	  }
	  
	  private void setIndicatorView() {
			indicators = new View[views.size()];  
			viewGroup = (ViewGroup)findViewById(R.id.hotPositionViewGroup);
			int focusedSize = 40;
			int normalSize = 20;
			
		    for (int i = 0; i < views.size(); i++) {  
	            
		    	TextView indicator = new TextView(HotPositionMainActivity.this);
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
