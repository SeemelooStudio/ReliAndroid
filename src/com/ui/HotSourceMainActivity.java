package com.ui;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.HashMap;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.model.HotSrcMainItem;
import com.model.HotSrcTitleInfo;
import com.reqst.BusinessRequest;
import com.util.BaseHelper;
import com.util.ConstDefine;
import com.util.ViewPageAdapter;
import com.util.ViewPageChangeListener;

public class HotSourceMainActivity extends Activity {

		private RelativeLayout titleView;
		private ViewPager viewpage;
		
		
		private ViewGroup viewGroup;
		private ImageView imageView;  
		private ImageView[] imageViews; 
		private ProgressDialog diaLogProgress= null;
		private HotSrcTitleInfo  titleInfo = null;
		
		private ArrayList<View> views;
		private ArrayList<HotSrcMainItem>  dbHeatSources = null;
		
		private static int ROW_COUNT = 4;
		private static int COLUMN_COUNT = 3;
	    @Override                                                                                            
	    public void onCreate(Bundle savedInstanceState) {                                                    
			super.onCreate(savedInstanceState);
			requestWindowFeature(Window.FEATURE_NO_TITLE); 
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
    private Handler handler = new Handler() {               
        public void handleMessage(Message message) {
                switch (message.what) {
                case ConstDefine.MSG_I_HANDLE_OK:                                        
        		 	diaLogProgress.dismiss();
        		 	//show grideview
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
		 TextView tvPressureOut =  (TextView) viewHeatSource.findViewById(R.id.hotSrcItemLeftText);
		 tvPressureOut.setText(item.getStrPressureOut());
		 TextView tvPressureIn =  (TextView) viewHeatSource.findViewById(R.id.hotSrcItemRightText);
		 tvPressureIn.setText(item.getStrPressureIn());
		 TextView tvTemperatureOut =  (TextView) viewHeatSource.findViewById(R.id.hotSrcItemLeftTxtPa);
		 tvTemperatureOut.setText(item.getStrPressureOut());
		 TextView tvTemperatureIn =  (TextView) viewHeatSource.findViewById(R.id.hotSrcItemRightTxtPa);
		 tvTemperatureIn.setText(item.getStrPressureIn());
		 TextView tvHeatSourceName = (TextView) viewHeatSource.findViewById(R.id.hotSrcItemTitle);
		 tvHeatSourceName.setText(item.getStrHeatSourceName());
	  }

	  private void setHeatSourceSummaryContent( View viewHeatSource, HotSrcTitleInfo title)
	  {			
		  TextView txtAllnum = (TextView) viewHeatSource.findViewById(R.id.hotSrcTitleAllnum);
		  TextView txtAllDay= (TextView) viewHeatSource.findViewById(R.id.hotSrcTitleAllDay);
		  TextView txtWest = (TextView) viewHeatSource.findViewById(R.id.hotSrcTitleAllWest);
		  TextView txtAllNet = (TextView) viewHeatSource.findViewById(R.id.hotSrcTitleAllNet);
		  txtAllnum.setText("共" + dbHeatSources.size() + "个"); 
		  txtAllDay.setText("东部面积:" + titleInfo.getStrEastArea() + "平方米");
		  txtWest.setText("西部面积:" +  titleInfo.getStrWestArea() + "平方米"); 
		  txtAllNet.setText("总热负荷:" + titleInfo.getStrHeatLoad() + "GJ"); 
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
	      param.bottomMargin=5;
	      param.rightMargin=5;
	      viewSummary.setBackgroundResource(R.color.dodger_blue);
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
	      if(columnIndex != COLUMN_COUNT - 1){
		      param.rightMargin=5;
	      }
	      param.bottomMargin=5;
      	  viewHeatSource.setBackgroundResource(R.color.dodger_blue);
      	  viewHeatSource.setLayoutParams (param);
      	  return viewHeatSource;
	  }
	  private void setHotSourceGridView()
	  {
		  viewpage = (ViewPager) findViewById(R.id.hotMainPager);
		  //create page
		  int pageNum =2;
		  int pageSize = 11;
		  Point size = new Point();
		  getWindowManager().getDefaultDisplay().getSize(size);
		  int screenWidth = size.x;
		  int screenHeight = size.y;
		  Resources resources = getResources();
		  DisplayMetrics metrics = resources.getDisplayMetrics();
		  float px = 10 * (metrics.densityDpi / 160f);
		  int cellWidth = (int)( (screenWidth - px*2 - 10 ) /3);
		  int cellHeight = (int) ( (screenHeight - px*2 - 10) /4 );
		  int bigCellWidth = cellWidth * 2;
        //create page
        views = new ArrayList<View>();
		for(int pageIndex = 0; pageIndex < pageNum; pageIndex++ )
		{ 
			LinearLayout pageLayout = new LinearLayout(this);
			pageLayout.removeAllViews();
			GridLayout gridLayout = new GridLayout(this);
			gridLayout.setColumnCount(COLUMN_COUNT);
			gridLayout.setRowCount(ROW_COUNT);
			gridLayout.setOrientation(gridLayout.HORIZONTAL);
	        for(int cell = 0, rowIndex = 0, columnIndex=0 ,itemIndex = pageIndex * pageSize + cell ; 
	        		cell < pageSize && itemIndex < dbHeatSources.size()-1 ; 
	        		cell ++, columnIndex++, itemIndex++)
			{
				if(columnIndex == COLUMN_COUNT) {
					columnIndex = 0;
					rowIndex++;
				}	
		        if(rowIndex == 2 && columnIndex == 0){
		        	gridLayout.addView(getHeatSourceSummaryCell(titleInfo, 2, 0, bigCellWidth, cellHeight));
		        	columnIndex+=1;
		        	cell--;
		        }
		        else {
		        	gridLayout.addView(getHeatSourceCell(dbHeatSources.get(itemIndex), rowIndex, columnIndex, cellWidth, cellHeight));
		        }
			}
	        
		    gridLayout.setId(pageIndex);
		    pageLayout.addView(gridLayout);
			views.add(pageLayout);
		}
        
		imageViews = new ImageView[views.size()];  
		viewGroup = (ViewGroup)findViewById(R.id.hotSorceViewGroup);  
	    for (int i = 0; i < views.size(); i++) {  
            imageView = new ImageView(HotSourceMainActivity.this);  
            imageView.setLayoutParams(new LayoutParams(20,20));  
            imageView.setPadding(30, 0, 10, 0);  
            imageViews[i] = imageView;            
            if (i == 0) {  
                imageViews[i].setBackgroundResource(R.drawable.page_indicator_focused);  
            } else {  
                imageViews[i].setBackgroundResource(R.drawable.page_indicator);  
            }             
            viewGroup.addView(imageViews[i]);  
        } 
	    
		//add pages
		viewpage.setAdapter(new ViewPageAdapter(views));
		viewpage.setOnPageChangeListener(new ViewPageChangeListener(imageViews)); 
	  }
     
}  
