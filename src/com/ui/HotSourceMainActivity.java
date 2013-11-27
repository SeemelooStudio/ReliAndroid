package com.ui;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
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
		
		private TextView txtAllnum;
		private TextView txtAllDay;
		private TextView txtAllNet;
		private ViewGroup viewGroup;
		private ImageView imageView;  
		private ImageView[] imageViews; 
		private ProgressDialog diaLogProgress= null;
		private HotSrcTitleInfo  titleInfo = null;
		
		private ArrayList<View> views;
		private ArrayList<HotSrcMainItem>  dbhostSrcLst = null;
		
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
		titleView = (RelativeLayout) findViewById(R.id.HotSrcTitleView);                                                                  
		titleView.setOnClickListener(new OnClickListener(){                                                                                    
        	public void onClick(View v) 
        	{   
        		if(R.id.HotSrcTitleView == v.getId())
           		{
	 	        	Intent intent = new Intent(HotSourceMainActivity.this, HotSourceQueryActivity.class); 
	    			startActivity(intent);
           		}
        	}
        });
		
		txtAllnum = (TextView) findViewById(R.id.hotSrcTitleAllnum);
		txtAllDay = (TextView) findViewById(R.id.hotSrcTitleAllDay);
		txtAllNet = (TextView) findViewById(R.id.hotSrcTitleAllNet);

		titleInfo = new HotSrcTitleInfo();
		dbhostSrcLst = new ArrayList<HotSrcMainItem>();
		diaLogProgress = BaseHelper.showProgress(HotSourceMainActivity.this,ConstDefine.I_MSG_0003,false);
		
	    new Thread() {
	        public void run() { 
	                Message msgSend = new Message();
	        	    try {
	        	    	
	        	    	this.sleep(ConstDefine.HTTP_TIME_OUT);
	        	    	
	        	    	// get itemList
	        	    	dbhostSrcLst = BusinessRequest.getHotSourceMainList();
	        	    	
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
        		 	
        		 	//show title
        		 	txtAllnum.setText("��" + titleInfo.getAll_num() + "��"); 
        		 	txtAllDay.setText("�����ۼƹ���:" + titleInfo.getToday_num() + "GJ"); 
        		 	txtAllNet.setText("ȫ��˲ʱ����:" + titleInfo.getNet_num() + "GJ"); 
                
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
	    
	  /**
	   * 
	   */
	  private void setHotSourceGridView()
	  {
		  
		viewpage = (ViewPager) findViewById(R.id.hotMainPager);
		
    	//create page
		int pageNum =0;
		if(dbhostSrcLst.size() >= 9 && (dbhostSrcLst.size() % 9) == 0){			
			pageNum = dbhostSrcLst.size()/9;
		}
		else	
		{
			pageNum = (dbhostSrcLst.size()/9) + 1 ;
		}
		
        //create page
        views = new ArrayList<View>();
		for(int i = 0; i < pageNum; i++ )
		{ 
			
	       	LinearLayout pageLayout = new LinearLayout(this);
			pageLayout.removeAllViews();
			GridView gridview = new GridView(this);
			gridview.setNumColumns(3);
			
	        int itemIndex = 0;
	        ArrayList<HashMap<String, Object>> hotSrcItemList = new ArrayList<HashMap<String,Object>>();
	        for(int r = 0;r < 9 && itemIndex < dbhostSrcLst.size()-1 ;r++)
			{
				itemIndex = i * 9 + r;
				HotSrcMainItem oneItem =(HotSrcMainItem) dbhostSrcLst.get(itemIndex); 
				HashMap<String, Object> hotSrcItem = new HashMap<String, Object>();
	        	hotSrcItem.put("hotSrcItemId", oneItem.getStrHeatSourceName()); 
	        	hotSrcItem.put("hotSrcItemTitle", oneItem.getStrHeatSourceName()); 
	        	hotSrcItem.put("hotSrcItemLeftText", oneItem.getStrPressureOut()); 
	        	hotSrcItem.put("hotSrcItemLeftTxtPa", oneItem.getStrTemperatureOut()); 
	        	hotSrcItem.put("hotSrcItemRightText", oneItem.getStrPressureIn());
	        	hotSrcItem.put("hotSrcItemRightTxtPa", oneItem.getStrTemperatureIn()); 
		        hotSrcItemList.add(hotSrcItem);
			}
	        
	        SimpleAdapter saItem = new SimpleAdapter(this,  hotSrcItemList, R.layout.hot_source_main_item,//xmlʵ��                                                               
	              new String[]{"hotSrcItemId","hotSrcItemTitle","hotSrcItemLeftText","hotSrcItemRightText","hotSrcItemLeftTxtPa","hotSrcItemRightTxtPa"},                                 
	              new int[]{R.id.hotSrcItemId,R.id.hotSrcItemTitle,R.id.hotSrcItemLeftText,R.id.hotSrcItemRightText,R.id.hotSrcItemLeftTxtPa,R.id.hotSrcItemRightTxtPa});
			gridview.setAdapter(saItem); 
			                                                                     
			gridview.setOnItemClickListener(
			new OnItemClickListener(){                                                                                    
			    public void onItemClick(AdapterView<?> arg0, View arg1,int arg2,long arg3)       
			    {                                                                                
			        int index=arg2+1;
				
	        		HashMap<String, Object> hotSrcItem = (HashMap<String, Object>) ((GridView)arg0).getItemAtPosition(arg2);
					Intent intent = new Intent(HotSourceMainActivity.this, HotSourceDetailActivity.class); 
					startActivity(intent);
					
					Bundle mBundle = new Bundle();
	        		mBundle.putString("hotSrcItemId", hotSrcItem.get("hotSrcItemId").toString());
	        		mBundle.putString("hotSrcItemTitle", hotSrcItem.get("hotSrcItemTitle").toString());
                       
			    }                                                                                
			}); 
			
			gridview.setId(i);
			pageLayout.addView(gridview.getRootView());
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
