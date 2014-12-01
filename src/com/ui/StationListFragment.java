package com.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.model.StationListItem;
import com.reqst.BusinessRequest;
import com.util.AccountHelper;
import com.util.BaseHelper;
import com.util.ConstDefine;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.SimpleAdapter;
import android.widget.AdapterView.OnItemClickListener;


public class StationListFragment extends Fragment 
{
	private ListView listView;  
    private ProgressDialog diaLogProgress= null;
    
    private StationListItem searchCon = null;
    private List<HashMap<String, Object>> listData; 
    private ArrayList<StationListItem>  _stations ; 
  
    private Activity _activity;
    @Override
	public void onStart(){
		super.onResume();
    }
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        _activity = getActivity();
    }
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View view = inflater.inflate(R.layout.station_list, container, false);	
        searchCon = new StationListItem();
	    this.getHotPosListbyCondition();
	    
        listView = (ListView) view.findViewById(R.id.hotPosList);  
        listView.setTextFilterEnabled(true); 
        listView.setOnItemClickListener(new OnItemClickListener(){                                                                                    
        	public void onItemClick(AdapterView<?> parent, View arg1, int position, long id) 
        	{   
        		@SuppressWarnings("unchecked")
				HashMap<String, Object> ListItem = (HashMap<String, Object>) listView.getItemAtPosition(position);
        		Intent intent = new Intent(_activity.getBaseContext(), StationDetailActivity.class); 
        		Bundle mBundle = new Bundle();
        		mBundle.putInt("station_id", Integer.parseInt(ListItem.get("strStationId").toString()));
        		mBundle.putString("station_name", ListItem.get("strStationName").toString());
        		intent.putExtras(mBundle);
        		startActivity(intent);
        	}
        });
        return view;
	}
	
	public void search(String text) {
		List<HashMap<String, Object>> resultlst = searchItem(text);  
        updateLayout(resultlst); 
	}
	
    
    private void getHotPosListbyCondition()
	{
		diaLogProgress = BaseHelper.showProgress(_activity.getApplicationContext(),ConstDefine.I_MSG_0003,false);
        new Thread() {
            public void run() { 
                    Message msgSend = new Message();
            	    try {
            	    	listData = getHotPositionListData(searchCon);
            	    	msgSend.what = ConstDefine.MSG_I_HANDLE_OK;
					} catch (Exception e) {
						msgSend.what = ConstDefine.MSG_I_HANDLE_Fail;
					}
                    handler.sendMessage(msgSend);
            	}
        }.start();
		
	}
	 
    @SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {               
        public void handleMessage(Message message) {
                switch (message.what) {
                case ConstDefine.MSG_I_HANDLE_OK:                                        
        		 	diaLogProgress.dismiss();
        		    listView.setAdapter(new SimpleAdapter(_activity.getApplicationContext(),listData, R.layout.station_list_item,  
        		    		new String[] { "strStationId", "strStationName", "strOtherInfo" }, 
        					new int[] {R.id.hot_station_id, R.id.hot_station_name, R.id.hot_other_info})); 
        		 	break;
                case ConstDefine.MSG_I_HANDLE_Fail:   
                	diaLogProgress.dismiss();
                	BaseHelper.showToastMsg(_activity,ConstDefine.E_MSG_0001);
                    break;
	            }
	        }
	  };
	  
	  private List<HashMap<String, Object>> getHotPositionListData(StationListItem pSearchCon) throws Exception {  
		  if(_stations == null ) {
			  _stations = AccountHelper.getRegistedStationNames(_activity);
		  if(_stations.size() == 0) {
			  _stations =  BusinessRequest.getStationQueryList(pSearchCon, _activity);
			  AccountHelper.setRegistedStationNames(_activity, _stations);
		  }
		}
		
		List<HashMap<String, Object>> weatherList = new ArrayList<HashMap<String, Object>>(); 
		for (StationListItem oneRec: _stations) 
		{   
			HashMap<String, Object> item = new HashMap<String, Object>();  
			item.put("strStationId", oneRec.getStationId()+""); 
		    item.put("strStationName", oneRec.getStationName());
		    String strOtherInfo = oneRec.getEastOrWest() + "," 
		                        + oneRec.getType();
		    item.put("strOtherInfo", strOtherInfo); 
		    weatherList.add(item);  
		}
	
	    return weatherList;
	} 
    
    private void updateLayout(List<HashMap<String, Object>> resultList) {  
    	  listView.setAdapter(new SimpleAdapter(_activity.getApplicationContext(),resultList, R.layout.station_list_item,  
				  new String[] { "strStationId", "strStationName", "strOtherInfo" }, 
				  new int[] {R.id.hot_station_id, R.id.hot_station_name, R.id.hot_other_info})); 
    }
 
    private List<HashMap<String, Object>> searchItem(String name) 
    {  
    	List<HashMap<String, Object>> mSearchList = new ArrayList<HashMap<String, Object>>(); 
        
        for (int i = 0; i < _stations.size(); i++) 
        { 	
            int index =((StationListItem) _stations.get(i)).getStationName().indexOf(name);  
            
            if (index != -1) {
            	HashMap<String, Object> item = new HashMap<String, Object>();  
     	        item.put("strStationId", _stations.get(i).getStationId()); 
     	        item.put("strStationName", _stations.get(i).getStationName());
     	        
     	        String strOtherInfo = _stations.get(i).getEastOrWest() + "," 
     	                            + _stations.get(i).getType();
     	        item.put("strOtherInfo", strOtherInfo); 
     	        mSearchList.add(item);  
            }  
        }  

        return mSearchList;  
    }
}