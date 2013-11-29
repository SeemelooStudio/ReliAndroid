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
import android.support.v4.app.NavUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SimpleAdapter;

import com.model.DownloadPDFTask;
import com.model.ListItem;
import com.model.WarnListItem;
import com.reqst.BusinessRequest;
import com.util.BaseHelper;
import com.util.ConstDefine;

/***
 * 
 * @author zhaors
 *
 */
public class DailyListActivity extends Activity implements  SearchView.OnQueryTextListener{

	    private ListView listView;  
	    private SearchView searchView;  
	    private List<HashMap<String, Object>> listData; 
	    private ArrayList<ListItem>  dbDatalist = new ArrayList<ListItem>();  
	    private ProgressDialog diaLogProgress= null;
	    private ListItem searchCon = null;
		private Activity mActivity;
	    @Override  
	    protected void onCreate(Bundle savedInstanceState) {  
    	    super.onCreate(savedInstanceState);
    	    setContentView(R.layout.daily_list); 
    	    listView = (ListView) findViewById(R.id.list); 
    	   
    	    searchCon = new ListItem();
    	    this.getDailyListbyCondition();
   	        mActivity = this;
	        listView.setTextFilterEnabled(true); 
    	    listView.setOnItemClickListener(new OnItemClickListener(){                                                                                    
  	        	public void onItemClick(AdapterView<?> parent, View arg1, int position, long id) 
  	        	{   
  	        		HashMap<String, Object> ListItem = (HashMap<String, Object>) listView.getItemAtPosition(position);
  	        		String pdfUrl = ConstDefine.WEB_SERVICE_URL + ConstDefine.S_DAILY_REPORT_ROOT + ListItem.get("list_name").toString();
  	        		new DownloadPDFTask(mActivity).execute(pdfUrl);
  	        	}
    	    });
    	    
            //init search view
	        searchView = (SearchView) findViewById(R.id.daily_search_view);    
	        searchView.setOnQueryTextListener(this);  
	        searchView.setSubmitButtonEnabled(true);
	        getActionBar().setDisplayHomeAsUpEnabled(true);
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
	    @Override
		public boolean onOptionsItemSelected(MenuItem item) {
			 switch (item.getItemId()) {
			    // Respond to the action bar's Up/Home button
			    case android.R.id.home:
			        NavUtils.navigateUpFromSameTask(this);
			        return true;
			    }
			    return super.onOptionsItemSelected(item);
		 }
	    
	    /***
	     * 
	     */
	    private void getDailyListbyCondition()
		{
    	    diaLogProgress = BaseHelper.showProgress(DailyListActivity.this,ConstDefine.I_MSG_0003,false);
   	        new Thread() {
   	            public void run() { 
   	                    Message msgSend = new Message();
   	            	    try {
   	            	    	listData = getDailyListData(searchCon);
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
	        		 	listView.setAdapter(new SimpleAdapter(getApplicationContext(),listData, R.layout.list_item,  
	   	           				  new String[] { "list_id", "list_name", "list_other" }, 
	   	           				  new int[] {R.id.list_id, R.id.list_name, R.id.list_other}));
	                    break;
	                case ConstDefine.MSG_I_HANDLE_Fail:                                        
	                	//close process
	                	diaLogProgress.dismiss();
	                	BaseHelper.showToastMsg(DailyListActivity.this,ConstDefine.E_MSG_0001);
	                    break;
		            }
		        }
		  };
		  
	    private List<HashMap<String, Object>> searchItem(String name) 
	    {  
	    	List<HashMap<String, Object>> mSearchList = new ArrayList<HashMap<String, Object>>(); 
	        
	        for (int i = 0; i < dbDatalist.size(); i++) 
	        { 	
	            int index =((ListItem) dbDatalist.get(i)).getStrListName().indexOf(name);  
	            
	            if (index != -1) {
	            	HashMap<String, Object> item = new HashMap<String, Object>();  
	     	        item.put("list_id", dbDatalist.get(i).getStrListId()); 
	     	        item.put("list_name", dbDatalist.get(i).getStrListName()); 
	     	        item.put("list_other", dbDatalist.get(i).getStrListOther()); 
	     	        mSearchList.add(item);  
	            }  
	        }  
            
	        //���ؽ��
	        return mSearchList;  
	    }  
	  
	    
        /**
         * 
         * @param resultList
         */
	    private void updateLayout(List<HashMap<String, Object>> resultList) {  
	    	  listView.setAdapter(new SimpleAdapter(getApplicationContext(),resultList, R.layout.list_item,  
					  new String[] { "list_id", "list_name", "list_other" }, 
					  new int[] {R.id.list_id, R.id.list_name, R.id.list_other})); 
	    }  
	   
	    /**
	     * 
	     * @return
	     * @throws JSONException 
	     */
	    private List<HashMap<String, Object>> getDailyListData(ListItem pSearchCon) throws Exception {  
	       
	        //get dailyList
	        dbDatalist =  BusinessRequest.getDailyList(pSearchCon);
	        
	        //adapt dailyList
	        List<HashMap<String, Object>> dailyList = new ArrayList<HashMap<String, Object>>(); 
	        for (ListItem oneRec: dbDatalist) 
	        {   
	        	HashMap<String, Object> item = new HashMap<String, Object>();  
		        item.put("list_id", oneRec.getStrListId()); 
		        item.put("list_name", oneRec.getStrListName()); 
		        item.put("list_other", oneRec.getStrListOther()); 
		        dailyList.add(item);  
	        }
	        
	        //return
	        return dailyList;
	    } 
	    

	}  