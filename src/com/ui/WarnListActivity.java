package com.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONException;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.model.ListItem;
import com.model.WarnListItem;
import com.reqst.BusinessRequest;
import com.util.BaseHelper;
import com.util.ConstDefine;

public class WarnListActivity extends Activity implements android.view.View.OnClickListener{

	private ListView listView;
	private Button   btnWarnRefresh;
	private Button   btnWarnShow;
	private Button   btnWarnEdit;

	private ProgressDialog diaLogProgress= null;	
    private List<HashMap<String, Object>> listData;
    private ArrayList<WarnListItem>  dbWarnlist = new ArrayList<WarnListItem>();  
    private WarnListItem searchCon = null;
    
	 @Override
     public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.warn_list);
        listView = (ListView) findViewById(R.id.list); 
        
        searchCon = new WarnListItem();
        this.getWarnListbyCondition();
        
        //����¼�
        listView.setTextFilterEnabled(true); 
        listView.setOnItemClickListener(new OnItemClickListener(){                                                                                    
        	public void onItemClick(AdapterView<?> parent, View arg1, int position, long id) 
        	{   
        		//��ת����ϸ����
        		HashMap<String, Object> ListItem = (HashMap<String, Object>) listView.getItemAtPosition(position);
        		Intent intent = new Intent(WarnListActivity.this, WarnDetailActivity.class); 
        		Bundle mBundle = new Bundle();
        		mBundle.putString("warn_id", ListItem.get("warn_id").toString());
        		mBundle.putString("warn_title", ListItem.get("warn_title").toString());
        		mBundle.putString("warn_date", ListItem.get("warn_date").toString());
        		mBundle.putString("warn_content", ListItem.get("warn_content").toString());
        		intent.putExtras(mBundle);
        		startActivity(intent);
        	}
        });
        
        btnWarnRefresh = (Button) findViewById(R.id.btnWarnRefresh);
        btnWarnShow = (Button) findViewById(R.id.btnWarnShow);
        btnWarnEdit = (Button) findViewById(R.id.btnWarnEdit);
        btnWarnRefresh.setOnClickListener((android.view.View.OnClickListener) this);
        btnWarnShow.setOnClickListener((android.view.View.OnClickListener) this);
        btnWarnEdit.setOnClickListener((android.view.View.OnClickListener) this);
	 }
	 
	 
	 /**
	  * query list
	  */
	 private void getWarnListbyCondition()
	 {
		 diaLogProgress = BaseHelper.showProgress(WarnListActivity.this,ConstDefine.I_MSG_0003,false);
	        new Thread() {
	            public void run() { 
	                    Message msgSend = new Message();
	            	    try {
	            	    	
	            	    	this.sleep(ConstDefine.HTTP_TIME_OUT);
	            	    	
	            	        //get mapList
	            	    	listData = getWarnListData(searchCon);
	            	    	
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
        		 	 listView.setAdapter(new SimpleAdapter(getApplicationContext(),listData, R.layout.warn_list_item,  
        					  new String[] { "warn_id", "warn_title", "warn_content", "warn_date","warn_other"}, 
        					  new int[] {R.id.warn_id, R.id.warn_title,R.id.warn_content,R.id.warn_date,R.id.warn_other}));
                    break;
                case ConstDefine.MSG_I_HANDLE_Fail:                                        
                	//close process
                	diaLogProgress.dismiss();
                	BaseHelper.showToastMsg(WarnListActivity.this,ConstDefine.E_MSG_0001);
                    break;
	            }
	        }
	  };
		
	  
	  
    /**
     * 
     * @return
     * @throws JSONException 
     */
    private List<HashMap<String, Object>> getWarnListData(WarnListItem pSearchCon) throws Exception {  
       
        //get weatherList
    	dbWarnlist =  BusinessRequest.getWarnList(pSearchCon);
        
        //adapt weatherList
        List<HashMap<String, Object>> warnList = new ArrayList<HashMap<String, Object>>(); 
        for (WarnListItem oneRec: dbWarnlist) 
        {   
        	HashMap<String, Object> item = new HashMap<String, Object>();  
	        item.put("warn_id", oneRec.getStrWarnId()); 
	        item.put("warn_title", oneRec.getStrWarnTitle()); 
	        item.put("warn_content", oneRec.getStrWarnContent()); 
	        item.put("warn_date", oneRec.getStrWarnDate()); 
	        item.put("list_other", oneRec.getStrWarnOther()); 
	        warnList.add(item);  
        }
        
        //return
        return warnList;
    } 
	    
    /*
     * ��ť�����¼�
     * @see android.view.View.OnClickListener#onClick(android.view.View)
     */
    public void onClick(View v)
   	{
   		if(R.id.btnWarnRefresh == v.getId())
   		{
   		  searchCon.setStrWarnId("00001");
   		  this.getWarnListbyCondition();
   		}
   		else if(R.id.btnWarnShow == v.getId())
   		{
   			BaseHelper.showDialog(this, "��Ϣ", "�Ŵ�");
   		}
   		else if(R.id.btnWarnEdit == v.getId())
   		{
   			BaseHelper.showDialog(this, "��Ϣ", "�༭");
   		}
   	}
}
