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

import com.model.HotPosMainItem;
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
	  	titleView = (RelativeLayout) findViewById(R.id.HotPosTitleView);
		titleView.setOnClickListener(new OnClickListener(){                                                                                    
        	public void onClick(View v) 
        	{   
        		if(R.id.HotPosTitleView == v.getId())
           		{
	 	        	Intent intent = new Intent(HotPositionMainActivity.this, HotPositionQueryActivity.class); 
	    			startActivity(intent);
           		}
        	}
        });
		
		txtAllnum = (TextView) findViewById(R.id.hotPosTitleAllnum);
		viewpage = (ViewPager) findViewById(R.id.hotPosMainPager);
		dbhostPosLst = new ArrayList<HotPosMainItem>();
		diaLogProgress = BaseHelper.showProgress(HotPositionMainActivity.this,ConstDefine.I_MSG_0003,false);
	    new Thread() {
	        public void run() { 
	                Message msgSend = new Message();
	        	    try {
	        	    	
	        	    	 this.sleep(ConstDefine.HTTP_TIME_OUT);
	        	    	
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
        		 	txtAllnum.setText("共" + dbhostPosLst.size() + "个热力站");
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
     * ��Դ��Ϣ�б�ƥ��
     * @return
     */
	  private void setHotPositionGridView()
	  {
		  
    	//create page
		int pageNum =0;
		if(dbhostPosLst.size() >= 9 && (dbhostPosLst.size() % 9) == 0){			
			pageNum = dbhostPosLst.size()/9;
		}
		else	
		{
			pageNum = (dbhostPosLst.size()/9) + 1 ;
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
	        ArrayList<HashMap<String, Object>> hotPosItemList = new ArrayList<HashMap<String,Object>>();
	        for(int r = 0;r < 9 && itemIndex < dbhostPosLst.size()-1 ;r++)
			{
				itemIndex = i * 9 + r;
				HotPosMainItem oneItem =(HotPosMainItem) dbhostPosLst.get(itemIndex); 
				HashMap<String, Object> hotPosItem = new HashMap<String, Object>();
				hotPosItem.put("hotPosItemId", oneItem.getStrStationId()); 
				hotPosItem.put("hotPosItemTitle", oneItem.getStrStationName()); 
				hotPosItem.put("hotPosItemLeftText", oneItem.getStrActualGJToday()); 
				hotPosItem.put("hotPosItemLeftTxtPa", oneItem.getStrPlannedGJToday()); 
				hotPosItem.put("hotPosItemRightText", oneItem.getStrActualGJYesterday());
				hotPosItem.put("hotPosItemRightTxtPa", oneItem.getStrPlannedGJYesterday()); 
				hotPosItem.put("hotPosItemRightTxtPa2", oneItem.getStrCalculatedGJYesterday()); 
				hotPosItemList.add(hotPosItem);
			}
	        
	        SimpleAdapter saItem = new SimpleAdapter(this,  hotPosItemList, R.layout.hot_position_main_item,//xml                                                              
	              new String[]{"hotPosItemId","hotPosItemTitle","hotPosItemLeftText","hotPosItemRightText","hotPosItemLeftTxtPa","hotPosItemRightTxtPa", "hotPosItemRightTxtPa2"},                                 
	              new int[]{R.id.hotPosItemId,R.id.hotPosItemTitle,R.id.hotPosItemLeftText,R.id.hotPosItemRightText,R.id.hotPosItemLeftTxtPa,R.id.hotPosItemRightTxtPa, R.id.hotPosItemRightTxtPa2});
			gridview.setAdapter(saItem); 
			                                                                    
			gridview.setOnItemClickListener(
			new OnItemClickListener(){                                                                                    
			    public void onItemClick(AdapterView<?> arg0, View arg1,int arg2,long arg3)       
			    {                                                                                
			        int index=arg2+1;
				
	        		HashMap<String, Object> hotSrcItem = (HashMap<String, Object>) ((GridView)arg0).getItemAtPosition(arg2);
					Intent intent = new Intent(HotPositionMainActivity.this, HotPositionDetailActivity.class); 
					startActivity(intent);
					
					Bundle mBundle = new Bundle();
	        		mBundle.putString("hotPosItemId", hotSrcItem.get("hotPosItemId").toString());
	        		mBundle.putString("hotPosItemTitle", hotSrcItem.get("hotPosItemTitle").toString());
			    }                                                                                
			}); 
			
			gridview.setId(i);
			pageLayout.addView(gridview.getRootView());
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
