package com.ui;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
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

import com.model.HotPosMainItem;
import com.model.HotSrcMainItem;
import com.reqst.BusinessRequest;
import com.util.BaseHelper;
import com.util.ConstDefine;
import com.util.ViewPageAdapter;
import com.util.ViewPageChangeListener;

public class HotPositionMainActivity extends Activity {

	    private RelativeLayout titleView;
	    private ViewPager viewpage;
	    private TextView txtAllnum;
	    private ViewGroup viewGroup;
		private ImageView imageView;  
		private ImageView[] imageViews; 
		private ProgressDialog diaLogProgress= null;
		
		private ArrayList<View> views;
		private ArrayList<HotPosMainItem>  dbhostPosLst = null;
		private static int ROW_COUNT = 4;
		private static int COLUMN_COUNT = 3;
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
		txtAllnum = (TextView) findViewById(R.id.hotPosTitleAllnum);
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
     
	  private void setStationContent(View viewStation, HotPosMainItem item)
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
	  private View getHeatSourceCell(HotPosMainItem heatSource, int rowIndex, int columnIndex, int cellWidth, int cellHeight)
	  {
		  LayoutInflater inflater = (LayoutInflater)getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);
		  View viewHeatSource = inflater.inflate(R.layout.hot_position_main_item, null);
		  setStationContent(viewHeatSource, heatSource);
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
	  private void setHotPositionGridView()
	  {
		  
    	//create page
		int pageCount =2;
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
		for(int pageIndex = 0; pageIndex < pageCount; pageIndex++ )
		{ 
	       	LinearLayout pageLayout = new LinearLayout(this);
			pageLayout.removeAllViews();
			GridLayout gridLayout = new GridLayout(this);
			gridLayout.setColumnCount(COLUMN_COUNT);
			gridLayout.setRowCount(ROW_COUNT);
			
	        for(int cell = 0,itemIndex = pageIndex*pageSize, rowIndex=0, columnIndex=0 ; 
	        		cell < pageSize && itemIndex < dbhostPosLst.size()-1 ; 
	        		cell++, itemIndex++)
			{
	        	if(columnIndex == COLUMN_COUNT ){
	        		columnIndex = 0;
	        		rowIndex++;
	        	}
				HotPosMainItem item =(HotPosMainItem) dbhostPosLst.get(itemIndex); 
				gridLayout.addView(getHeatSourceCell(item, rowIndex, columnIndex, cellWidth, cellHeight ));
			}    
			gridLayout.setId(pageIndex);
			pageLayout.addView(gridLayout.getRootView());
			views.add(pageLayout);
		}
        
		imageViews = new ImageView[views.size()];  
		viewGroup = (ViewGroup)findViewById(R.id.hotPositionViewGroup);  
	    for (int i = 0; i < views.size(); i++) {  
            imageView = new ImageView(HotPositionMainActivity.this);  
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
