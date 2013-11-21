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
import android.view.ViewGroup;
import android.view.Window;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.model.HotSrcMainItem;
import com.model.HotSrcTitleInfo;
import com.util.BaseHelper;
import com.util.ConstDefine;
import com.util.ViewPageAdapter;
import com.util.ViewPageChangeListener;

public class HotPositionMainActivity extends Activity {

	    private ListView titleView;
	    private ViewPager viewpage;
	   
	    private ArrayList<View> views;
		private ViewGroup viewGroup;
		private ImageView imageView;  
		private ImageView[] imageViews; 
		
		private ProgressDialog diaLogProgress= null;
		 
	    @Override                                                                                            
	    public void onCreate(Bundle savedInstanceState) {                                                    
			super.onCreate(savedInstanceState);
			requestWindowFeature(Window.FEATURE_NO_TITLE); 
			setContentView(R.layout.hot_position_main);
			
			titleView = (ListView) findViewById(R.id.HotPosTitleView);
			titleView.setOnItemClickListener(new OnItemClickListener(){                                                                                    
	        	public void onItemClick(AdapterView<?> parent, View arg1, int position, long id) 
	        	{   
	        		//��ת����ϸ����
	 	        	Intent intent = new Intent(HotPositionMainActivity.this, HotPositionQueryActivity.class); 
	    			startActivity(intent);
	        	}
	        });
			
			viewpage = (ViewPager) findViewById(R.id.hotPosMainPager);
			
			this.getHotPositionList();
	  }
	  

    /**
     * 
     */
	private void getHotPositionList()
	{
		diaLogProgress = BaseHelper.showProgress(HotPositionMainActivity.this,ConstDefine.I_MSG_0003,false);
	    new Thread() {
	        public void run() { 
	                Message msgSend = new Message();
	        	    try {
	        	    	
	        	    	this.sleep(ConstDefine.HTTP_TIME_OUT);
	        	    	
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
        		 	
        		 	titleView.setAdapter(getHostTitleAdapter());
        			
        		 	InitGridView();
        		 	
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
	  * ��ȡ��Դͷ����Ϣ������
	  * @return
	  */
	 private SimpleAdapter getHostTitleAdapter(){
		  
		    //��װԭʼ�б�
	   
            HotSrcTitleInfo  titleInfo = new HotSrcTitleInfo();
            titleInfo.setTitle("����վ��Ϣ");
            titleInfo.setAll_num("��26���ؼ�����վ");
	        
	        //����Ӱ�ӹ�ϵ
            ArrayList<HashMap<String, Object>> hotSrcTitleList = new ArrayList<HashMap<String,Object>>();
        	HashMap<String, Object> hotSrcTitle = new HashMap<String, Object>();
        	hotSrcTitle.put("hotSrcTitleAllnum", titleInfo.getAll_num()); 
        	hotSrcTitle.put("hotSrcTitle", titleInfo.getTitle()); 
        	hotSrcTitleList.add(hotSrcTitle);
	  
	        
	        //���������
	        SimpleAdapter saItem = new SimpleAdapter(this,hotSrcTitleList, R.layout.hot_position_main_title_item,//xmlʵ��                                                               
	              new String[]{"hotSrcTitleAllnum","hotSrcTitle"},                                 
	              new int[]{R.id.hotPosTitleAllnum,R.id.hotPosTitle});
		  
			  //�������
			  return saItem;	
		  }
	
	
     /**
     * ��Դ��Ϣ�б�ƥ��
     * @return
     */
	  private void InitGridView()
	  {
		  
        //ԭʼ����
        ArrayList<HotSrcMainItem>  dbhostSrcLst = new ArrayList<HotSrcMainItem>();
        for(int i=0; i<30; i++)
        {
        	HotSrcMainItem  item = new HotSrcMainItem();
        	item.setHotsrcId(i+"");
        	item.setTitle(i+"������վ");
        	item.setWenduLeft("8" +i+".88");
        	item.setWenduLeftPa("1.35MPa");
        	item.setWenduRight("4" +i+".66");
        	item.setWenduRightPa("0.32MPa");
        	dbhostSrcLst.add(item);
        }
        
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
	        	hotSrcItem.put("hotItemId", oneItem.getTitle()); 
	        	hotSrcItem.put("hotItemTitle", oneItem.getTitle()); 
	        	hotSrcItem.put("hotItemLeftText", oneItem.getWenduLeft()); 
	        	hotSrcItem.put("hotItemLeftTxtPa", oneItem.getWenduLeftPa()); 
	        	hotSrcItem.put("hotItemRightText", oneItem.getWenduRight());
	        	hotSrcItem.put("hotItemRightTxtPa", oneItem.getWenduRightPa()); 
		        hotSrcItemList.add(hotSrcItem);
			}
	        
	        //���������
	        SimpleAdapter saItem = new SimpleAdapter(this,  hotSrcItemList, R.layout.hot_position_main_item,//xmlʵ��                                                               
	              new String[]{"hotItemId","hotItemTitle","hotItemLeftText","hotItemRightText"},                                 
	              new int[]{R.id.hotItemId,R.id.hotItemTitle,R.id.hotItemLeftText,R.id.hotItemRightText});
			gridview.setAdapter(saItem); 
			
			//��ӵ���¼�                                                                           
			gridview.setOnItemClickListener(
			new OnItemClickListener(){                                                                                    
			    public void onItemClick(AdapterView<?> arg0, View arg1,int arg2,long arg3)       
			    {                                                                                
			        int index=arg2+1;//id�Ǵ�0��ʼ�ģ�������Ҫ+1 
				
					//�����ձ� ����
	        		HashMap<String, Object> hotSrcItem = (HashMap<String, Object>) ((GridView)arg0).getItemAtPosition(arg2);
					Intent intent = new Intent(HotPositionMainActivity.this, HotPositionDetailActivity.class); 
					startActivity(intent);
					
					Bundle mBundle = new Bundle();
	        		mBundle.putString("hotItemId", hotSrcItem.get("hotItemId").toString());
	        		mBundle.putString("hotItemTitle", hotSrcItem.get("hotItemTitle").toString());
	        		
				    //Toast.makeText(getApplicationContext(), "�㰴����ѡ�"+index, 0).show();   
				    //Toast�������û���ʾһЩ����/��ʾ                                           
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
