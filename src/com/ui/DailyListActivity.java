package com.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONException;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SimpleAdapter;

import com.model.DownloadPDFTask;
import com.model.GenericListItem;
import com.reqst.BusinessRequest;
import com.util.AccountHelper;
import com.util.BaseHelper;
import com.util.ConstDefine;

/***
 * 
 * @author zhaors
 *
 */
public class DailyListActivity extends Activity {

	    private ListView listView;  
	    private SearchView searchView;  
	    private List<HashMap<String, Object>> listData; 
	    private ArrayList<GenericListItem>  dbDatalist = new ArrayList<GenericListItem>();  
	    private ProgressDialog diaLogProgress= null;
	    private GenericListItem searchCon = null;
		private Activity _activity;
	    @Override  
	    protected void onCreate(Bundle savedInstanceState) {  
    	    super.onCreate(savedInstanceState);
    	    setContentView(R.layout.daily_list); 
    	    listView = (ListView) findViewById(R.id.list); 
    	   
    	    searchCon = new GenericListItem();
    	    this.getDailyListbyCondition();
   	        _activity = this;
	        listView.setTextFilterEnabled(true); 
    	    listView.setOnItemClickListener(new OnItemClickListener(){                                                                                    
  	        	public void onItemClick(AdapterView<?> parent, View arg1, int position, long id) 
  	        	{   
  	        		@SuppressWarnings("unchecked")
					HashMap<String, Object> ListItem = (HashMap<String, Object>) listView.getItemAtPosition(position);
  	        		String pdfUrl = AccountHelper.getBaseUrl(_activity) + ConstDefine.S_DAILY_REPORT_ROOT + ListItem.get("list_name").toString();
  	        		new DownloadPDFTask(_activity).execute(pdfUrl);
  	        	}
    	    });
	        getActionBar().setDisplayHomeAsUpEnabled(true);
	    }  
	    
	    @Override
		 public boolean onCreateOptionsMenu(Menu menu) {
		     // Inflate the menu items for use in the action bar
			MenuInflater inflater = getMenuInflater();
			inflater.inflate(R.menu.warn_list_activity_actions, menu);
			 
			MenuItem searchItem = menu.findItem(R.id.action_search);
			SearchView searchView = (SearchView)searchItem.getActionView();
			 
			SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
			if(null!=searchManager ) {   
				searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
			}
			searchView.setIconifiedByDefault(false);
			searchView.setOnQueryTextListener(new OnQueryTextListener() {

				@Override
				public boolean onQueryTextChange(String newText) {
					List<HashMap<String, Object>>  resultlst = searchItem(newText);  
			        updateLayout(resultlst); 
					return false;
				}

				@Override
				public boolean onQueryTextSubmit(String query) {
					return false;
				}
				
			});  
			
	        searchView.setSubmitButtonEnabled(true);
		    return super.onCreateOptionsMenu(menu);
		}
	    
	    @Override
		public boolean onOptionsItemSelected(MenuItem item) {
			 switch(item.getItemId()) {
			 case R.id.action_search :
				 return true;
			 case R.id.action_refresh:
				 searchCon = new GenericListItem();
		    	 this.getDailyListbyCondition();
				 return true;
			 case android.R.id.home:
			     this.finish();
			     return true;
			 default :
				 return super.onOptionsItemSelected(item);
			 }
		}
	    
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
	    @SuppressLint("HandlerLeak")
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
	            int index =((GenericListItem) dbDatalist.get(i)).getStrListName().indexOf(name);  
	            
	            if (index != -1) {
	            	HashMap<String, Object> item = new HashMap<String, Object>();  
	     	        item.put("list_id", dbDatalist.get(i).getStrListId()); 
	     	        item.put("list_name", dbDatalist.get(i).getStrListName()); 
	     	        item.put("list_other", dbDatalist.get(i).getStrListOther()); 
	     	        mSearchList.add(item);  
	            }  
	        }  
            
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
	    private List<HashMap<String, Object>> getDailyListData(GenericListItem pSearchCon) throws Exception {  
	       
	        //get dailyList
	        dbDatalist =  BusinessRequest.getDailyList(pSearchCon, _activity);
	        //adapt dailyList
	        List<HashMap<String, Object>> dailyList = new ArrayList<HashMap<String, Object>>(); 
	        for (GenericListItem oneRec: dbDatalist) 
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