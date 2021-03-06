package com.ui;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.json.JSONException;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.SimpleAdapter;

import com.model.WarnListItem;
import com.reqst.BusinessRequest;
import com.util.BaseHelper;
import com.util.ConstDefine;
import com.util.DateHelper;

public class WarnListActivity extends Activity implements android.view.View.OnClickListener{

	private ListView listView;

	private ProgressDialog diaLogProgress= null;	
    private List<HashMap<String, Object>> listData;
    private ArrayList<WarnListItem>  warnings = new ArrayList<WarnListItem>();  
    private WarnListItem searchCon = null;
    private Activity _activity;
	 @Override
     public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.warn_list);
        _activity = this;
        listView = (ListView) findViewById(R.id.list2); 
        searchCon = new WarnListItem();
        this.getWarnListbyCondition();
        listView.setTextFilterEnabled(true); 
        listView.setOnItemClickListener(
    		new OnItemClickListener(){                                                                                    
	        	@SuppressWarnings("unchecked")
				public void onItemClick(AdapterView<?> parent, View arg1, int position, long id) 
	        	{   
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
				List<HashMap<String, Object>> resultlst = searchItem(newText);  
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
		 
		 case R.id.action_refresh:
			 this.getWarnListbyCondition();
			 return true;
		 case R.id.action_search :
			 return true;
		 case android.R.id.home:
		     this.finish();
		     return true;
		 default :
				 return super.onOptionsItemSelected(item);
		 }
		 
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
    @SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {               
        public void handleMessage(Message message) {
                switch (message.what) {
                case ConstDefine.MSG_I_HANDLE_OK:                                        
        		 	diaLogProgress.dismiss();
        		 	 listView.setAdapter(new SimpleAdapter(getApplicationContext(),listData, R.layout.warn_list_item,  
        					  new String[] { "warn_id", "warn_title", "warn_content", "warn_date"}, 
        					  new int[] {R.id.warn_id, R.id.warn_title,R.id.warn_content,R.id.warn_other}));
                    break;
                case ConstDefine.MSG_I_HANDLE_Fail:                                        
                	//close process
                	diaLogProgress.dismiss();
                	BaseHelper.showToastMsg(WarnListActivity.this,ConstDefine.E_MSG_0001);
                    break;
	            }
	        }
	  };
		
	  private void updateLayout( List<HashMap<String, Object>> hmWarnings)
	  {
		  listView.setAdapter(new SimpleAdapter(getApplicationContext(), hmWarnings, R.layout.warn_list_item,  
				  new String[] { "warn_id", "warn_title", "warn_content", "warn_date"}, 
				  new int[] {R.id.warn_id, R.id.warn_title,R.id.warn_content,R.id.warn_other}));
	  }
	  
    /**
     * 
     * @return
     * @throws JSONException 
     */
    @SuppressLint("SimpleDateFormat")
	private List<HashMap<String, Object>> getWarnListData(WarnListItem pSearchCon) throws Exception {  
       
        //get weatherList
    	warnings =  BusinessRequest.getWarnList(pSearchCon, _activity);
        
        //adapt weatherList
        List<HashMap<String, Object>> warnList = new ArrayList<HashMap<String, Object>>(); 

        for (WarnListItem oneRec: warnings) 
        {   
        	Date createAt = oneRec.getReportedAt();
        	
        	HashMap<String, Object> item = new HashMap<String, Object>();  
	        item.put("warn_id", oneRec.getWarningId()); 
	        item.put("warn_title", oneRec.getWarningTitle()); 
	        item.put("warn_content", oneRec.getWarningContent()); 
	        item.put("warn_date", new SimpleDateFormat("yyyy-MM-dd").format(createAt) + " " + DateHelper.getDayOfWeekInChinese(createAt) + " " + new SimpleDateFormat("HH:mm").format(createAt) ); 
	        item.put("list_other", oneRec.getStrWarnOther()); 
	        warnList.add(item);  
        }
        
        //return
        return warnList;
    }
    
    private List<HashMap<String, Object>> searchItem(String query) 
    {  
    	List<HashMap<String, Object>> results = new ArrayList<HashMap<String, Object>>(); 
        
        for (WarnListItem warning : warnings) 
        { 	
            int index = warning.getWarningContent().indexOf(query);
            if (index != -1) {
            	HashMap<String, Object> item = new HashMap<String, Object>();  
            	item.put("warn_id", warning.getWarningId()); 
    	        item.put("warn_title", warning.getWarningTitle()); 
    	        item.put("warn_content", warning.getWarningContent()); 
    	        item.put("warn_date", warning.getReportedAt()); 
    	        item.put("list_other", warning.getStrWarnOther()); 
     	        results.add(item);  
            }  
        }  
     
        return results;  
    }  
    
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	} 
	    

}
