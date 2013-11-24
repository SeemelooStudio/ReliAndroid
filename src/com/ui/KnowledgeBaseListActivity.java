package com.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONException;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SimpleAdapter;

import com.model.KnowledgeBaseItem;
import com.model.ListItem;
import com.reqst.BusinessRequest;
import com.util.BaseHelper;
import com.util.ConstDefine;

/***
 * 
 * @author zhaors
 *
 */
public class KnowledgeBaseListActivity extends Activity implements  SearchView.OnQueryTextListener{

	    private ListView listView;  
	    private SearchView searchView;  
	    private List<HashMap<String, Object>> listData; 
	    private ArrayList<KnowledgeBaseItem>  dbDatalist = new ArrayList<KnowledgeBaseItem>();  
	    private ProgressDialog diaLogProgress= null;
	    private KnowledgeBaseItem searchCon = null;
		
	    @Override  
	    protected void onCreate(Bundle savedInstanceState) {  
    	    super.onCreate(savedInstanceState);
    	    requestWindowFeature(Window.FEATURE_NO_TITLE);
    	    setContentView(R.layout.knowledge_base_list); 
    	    listView = (ListView) findViewById(R.id.knowledgeList); 
    	   
    	    searchCon = new KnowledgeBaseItem();
    	    this.getDailyListbyCondition();
   	        	
	        listView.setTextFilterEnabled(true); 
    	    listView.setOnItemClickListener(new OnItemClickListener(){                                                                                    
  	        	public void onItemClick(AdapterView<?> parent, View arg1, int position, long id) 
  	        	{   
  	        		//跳转到详细画面
  	        		HashMap<String, Object> ListItem = (HashMap<String, Object>) listView.getItemAtPosition(position);
  	        		Intent intent = new Intent(KnowledgeBaseListActivity.this, KnowledgeBaseDetailActivity.class); 
  	        		Bundle mBundle = new Bundle();
  	        		mBundle.putString("strDocId", ListItem.get("strDocId").toString());
  	        		mBundle.putString("strDocName", ListItem.get("strDocName").toString());
  	        		intent.putExtras(mBundle);
  	        		startActivity(intent);
  	        	}
    	    });
    	    
    	    searchView = (SearchView) findViewById(R.id.knowledgeSearch);    
	        searchView.setOnQueryTextListener(this);  
	        searchView.setSubmitButtonEnabled(false);
	    }  
	  
	    @Override  
	    public boolean onQueryTextChange(String newText) {  
	    	List<HashMap<String, Object>>  resultlst = searchItem(newText);  
	        updateLayout(resultlst);  
	        return false;  
	    }  
	  
	    @Override  
	    public boolean onQueryTextSubmit(String query) {  
	        // TODO Auto-generated method stub  
	        return false;  
	    }  
	    
	    /***
	     * 
	     */
	    private void getDailyListbyCondition()
		{
    	    diaLogProgress = BaseHelper.showProgress(KnowledgeBaseListActivity.this,ConstDefine.I_MSG_0003,false);
   	        new Thread() {
   	            public void run() { 
   	                    Message msgSend = new Message();
   	            	    try {
   	            	    	
   	            	    	this.sleep(ConstDefine.HTTP_TIME_OUT);
   	            	    	
   	            	    	listData = getKnowBaseListData(searchCon);
   	            	    	
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
	        		 	listView.setAdapter(new SimpleAdapter(getApplicationContext(),listData, R.layout.knowledge_base_list_item,  
	        		 			 new String[] { "strDocId", "strDocName", "strDocUptime" }, 
	   	           				 new int[] {R.id.txtDocId, R.id.txtDocName, R.id.txtDocUptime}));
	                    break;
	                case ConstDefine.MSG_I_HANDLE_Fail:                                        
	                	//close process
	                	diaLogProgress.dismiss();
	                	BaseHelper.showToastMsg(KnowledgeBaseListActivity.this,ConstDefine.E_MSG_0001);
	                    break;
		            }
		        }
		  };
		  
	    /**
	     * 根据名称查询
	     * @param name
	     * @return
	     */
	    private List<HashMap<String, Object>> searchItem(String name) 
	    {  
	    	List<HashMap<String, Object>> mSearchList = new ArrayList<HashMap<String, Object>>(); 
	        
	        for (int i = 0; i < dbDatalist.size(); i++) 
	        { 	
	            int index =((KnowledgeBaseItem) dbDatalist.get(i)).getStrDocName().indexOf(name);  
	            
	            // 存在匹配的数据  重新组装List
	            if (index != -1) {
	            	HashMap<String, Object> item = new HashMap<String, Object>();  
	     	        item.put("strDocId", dbDatalist.get(i).getStrDocId()); 
	     	        item.put("strDocName", dbDatalist.get(i).getStrDocName()); 
	     	        item.put("strDocUptime", dbDatalist.get(i).getStrDocUpTime());
	     	        mSearchList.add(item);  
	            }  
	        }  
            
	        //返回结果
	        return mSearchList;  
	    }  
	  
	    
        /**
         * 
         * @param resultList
         */
	    private void updateLayout(List<HashMap<String, Object>> resultList) {  
	    	  listView.setAdapter(new SimpleAdapter(getApplicationContext(),resultList, R.layout.knowledge_base_list_item,  
					  new String[] { "strDocId", "strDocName", "strDocUptime" }, 
					  new int[] {R.id.txtDocId, R.id.txtDocName, R.id.txtDocUptime})); 
	    }  
	   
	    /**
	     * 
	     * @return
	     * @throws JSONException 
	     */
	    private List<HashMap<String, Object>> getKnowBaseListData(KnowledgeBaseItem pSearchCon) throws JSONException {  
	       
	        //get dailyList
	        dbDatalist =  BusinessRequest.getKnowledgeBaseList(pSearchCon);
	        
	        //adapt dailyList
	        List<HashMap<String, Object>> dailyList = new ArrayList<HashMap<String, Object>>(); 
	        for (KnowledgeBaseItem oneRec: dbDatalist) 
	        {   
	        	HashMap<String, Object> item = new HashMap<String, Object>();  
		        item.put("strDocId", oneRec.getStrDocId()); 
		        item.put("strDocName", oneRec.getStrDocName()); 
		        item.put("strDocUptime", oneRec.getStrDocUpTime()); 
		        dailyList.add(item);  
	        }
	        
	        //return
	        return dailyList;
	    } 
	    

	}  