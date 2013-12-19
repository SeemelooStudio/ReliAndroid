package com.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SimpleAdapter;

import com.model.StationListItem;
import com.reqst.BusinessRequest;
import com.util.BaseHelper;
import com.util.ConstDefine;

public class StationQueryActivity  extends Activity implements  SearchView.OnQueryTextListener,android.view.View.OnClickListener {

	private ListView listView;  
    private SearchView searchView;
    private Button btnSearch;
    private ProgressDialog diaLogProgress= null;
    
    private StationListItem searchCon = null;
    private List<HashMap<String, Object>> listData; 
    private ArrayList<StationListItem>  dbHotPoslist = new ArrayList<StationListItem>();  
  
    
	 /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.station_list);
        
        searchView = (SearchView)findViewById(R.id.hotPositonsearch); 
        searchView.setOnQueryTextListener(this);  
        searchView.setSubmitButtonEnabled(false);
        
        searchCon = new StationListItem();
	    this.getHotPosListbyCondition();
	    
        listView = (ListView) findViewById(R.id.hotPosList);  
        listView.setTextFilterEnabled(true); 
        listView.setOnItemClickListener(new OnItemClickListener(){                                                                                    
        	public void onItemClick(AdapterView<?> parent, View arg1, int position, long id) 
        	{   
        		HashMap<String, Object> ListItem = (HashMap<String, Object>) listView.getItemAtPosition(position);
        		Intent intent = new Intent(StationQueryActivity.this, StationDetailActivity.class); 
        		Bundle mBundle = new Bundle();
        		mBundle.putString("strStationId", ListItem.get("strStationId").toString());
        		mBundle.putString("strStationName", ListItem.get("strStationName").toString());
        		intent.putExtras(mBundle);
        		startActivity(intent);
        	}
        });
        
        btnSearch = (Button) findViewById(R.id.btnHotPosSearch);
        btnSearch.setOnClickListener((android.view.View.OnClickListener) this);                                                                                  
    }
    
    /**
     * 
     */
    public void onClick(View v)
   	{
   		if(R.id.btnHotPosSearch == v.getId())
   		{
   			this.getHotPosListbyCondition();
   		}
   		
   	}
    
    
    @Override  
    public boolean onQueryTextChange(String newText) {
    	searchCon.setStrStationName(newText);
    	List<HashMap<String, Object>>  resultlst = searchItem(newText);  
        updateLayout(resultlst);  
        return false;  
    }  
    
    @Override  
    public boolean onQueryTextSubmit(String query) {  
 	   return false;  
    } 
    
    /***
     * 
     */
    private void getHotPosListbyCondition()
	{
		diaLogProgress = BaseHelper.showProgress(StationQueryActivity.this,ConstDefine.I_MSG_0003,false);
        new Thread() {
            public void run() { 
                    Message msgSend = new Message();
            	    try {
            	        //get mapList
            	    	listData = getHotPositionListData(searchCon);
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
        		    listView.setAdapter(new SimpleAdapter(getApplicationContext(),listData, R.layout.station_list_item,  
        		    		new String[] { "strStationId", "strStationName", "strOtherInfo" }, 
        					new int[] {R.id.hot_station_id, R.id.hot_station_name, R.id.hot_other_info})); 
        		 	break;
                case ConstDefine.MSG_I_HANDLE_Fail:                                        
                	//close process
                	diaLogProgress.dismiss();
                	BaseHelper.showToastMsg(StationQueryActivity.this,ConstDefine.E_MSG_0001);
                    break;
	            }
	        }
	  };
	
	  /**
	   * 
	   * @param pSearchCon
	   * @return
	   * @throws Exception
	   */
	  private List<HashMap<String, Object>> getHotPositionListData(StationListItem pSearchCon) throws Exception {  
	       
	    //get hot station list
		dbHotPoslist =  BusinessRequest.getStationQueryList(pSearchCon);
		
		//adapt hot station list
		List<HashMap<String, Object>> weatherList = new ArrayList<HashMap<String, Object>>(); 
		for (StationListItem oneRec: dbHotPoslist) 
		{   
			HashMap<String, Object> item = new HashMap<String, Object>();  
			item.put("strStationId", oneRec.getStrStationId()); 
		    item.put("strStationName", oneRec.getStrStationName());
		    String strOtherInfo = oneRec.getStrWarnColor() + "," 
		                        + oneRec.getStrAddress() + ","
		                        + oneRec.getStrDirect();
		    item.put("strOtherInfo", strOtherInfo); 
		    weatherList.add(item);  
		}
	
		//return
	    return weatherList;
	} 
    
    /**
     * 
     * @param resultList
     */
    private void updateLayout(List<HashMap<String, Object>> resultList) {  
    	  listView.setAdapter(new SimpleAdapter(getApplicationContext(),resultList, R.layout.station_list_item,  
				  new String[] { "strStationId", "strStationName", "strOtherInfo" }, 
				  new int[] {R.id.hot_station_id, R.id.hot_station_name, R.id.hot_other_info})); 
    }
    
    /**
     * 
     * @param name
     * @return
     */
    private List<HashMap<String, Object>> searchItem(String name) 
    {  
    	List<HashMap<String, Object>> mSearchList = new ArrayList<HashMap<String, Object>>(); 
        
        for (int i = 0; i < dbHotPoslist.size(); i++) 
        { 	
            int index =((StationListItem) dbHotPoslist.get(i)).getStrStationName().indexOf(name);  
            
            if (index != -1) {
            	HashMap<String, Object> item = new HashMap<String, Object>();  
     	        item.put("strStationId", dbHotPoslist.get(i).getStrStationId()); 
     	        item.put("strStationName", dbHotPoslist.get(i).getStrStationName());
     	        
     	        String strOtherInfo = dbHotPoslist.get(i).getStrWarnColor() + "," 
     	                            + dbHotPoslist.get(i).getStrAddress() + ","
     	                            + dbHotPoslist.get(i).getStrDirect();
     	        item.put("strOtherInfo", strOtherInfo); 
     	        mSearchList.add(item);  
            }  
        }  

        return mSearchList;  
    } 
}
