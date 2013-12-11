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
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SimpleAdapter;

import com.model.WeatherStationListItem;
import com.reqst.BusinessRequest;
import com.util.BaseHelper;
import com.util.ConstDefine;

public class WeatherListActivity extends Activity implements  SearchView.OnQueryTextListener{

	private ListView listView;  
    private SearchView searchView;  
    private List<HashMap<String, Object>> listData; 
    private ArrayList<WeatherStationListItem>  dbWeatherlist = new ArrayList<WeatherStationListItem>();  
	  
    private ProgressDialog diaLogProgress= null;
    private WeatherStationListItem searchCon = null;
    
	 @Override
     public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weather_list);
	        

        searchView = (SearchView)findViewById(R.id.weather_search_view); 

        searchView.setOnQueryTextListener(this);  
        searchView.setSubmitButtonEnabled(false);
        
        listView = (ListView) findViewById(R.id.station_list);  
        listView.setTextFilterEnabled(true); 
        
        searchCon = new WeatherStationListItem();
	    this.getWeatherListbyCondition();
	    
        listView.setOnItemClickListener(new OnItemClickListener(){                                                                                    
        	public void onItemClick(AdapterView<?> parent, View arg1, int position, long id) 
        	{   
        		//��ת����ϸ����
        		HashMap<String, Object> ListItem = (HashMap<String, Object>) listView.getItemAtPosition(position);
        		Intent intent = new Intent(WeatherListActivity.this, WeatherDetailActivity.class); 
        		Bundle mBundle = new Bundle();
        		mBundle.putString("list_id", ListItem.get("list_id").toString());
        		mBundle.putString("list_name", ListItem.get("list_name").toString());
        		intent.putExtras(mBundle);
        		startActivity(intent);
        	}
        });
        
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
	    
    /**
     * 
     */
	private void getWeatherListbyCondition()
	{
		
		diaLogProgress = BaseHelper.showProgress(WeatherListActivity.this,ConstDefine.I_MSG_0003,false);
        new Thread() {
            public void run() { 
                    Message msgSend = new Message();
            	    try {
            	        //get mapList
            	    	listData = getWeatherListData(searchCon);
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
        		    listView.setAdapter(new SimpleAdapter(getApplicationContext(),listData, R.layout.weather_station_list_item,  
        					  new String[] { "list_id", "list_name", "list_other" }, 
        					  new int[] {R.id.list_id, R.id.list_name, R.id.list_other}));
        		 	break;
                case ConstDefine.MSG_I_HANDLE_Fail:                                        
                	//close process
                	diaLogProgress.dismiss();
                	BaseHelper.showToastMsg(WeatherListActivity.this,ConstDefine.E_MSG_0001);
                    break;
	            }
	        }
	  };
	
	
	
	/**
	* 
	* @param pSearchCon
	* @return
	* @throws JSONException
	*/
	   private List<HashMap<String, Object>> getWeatherListData(WeatherStationListItem pSearchCon) throws Exception {  
	       
	        //get weatherList
	   dbWeatherlist =  BusinessRequest.getWeatherList();
	    
	    //adapt weatherList
	    List<HashMap<String, Object>> weatherList = new ArrayList<HashMap<String, Object>>(); 
	    for (WeatherStationListItem oneRec: dbWeatherlist) 
	    {   
	    	HashMap<String, Object> item = new HashMap<String, Object>();  
	        item.put("list_id", oneRec.getStrListId()); 
	        item.put("list_name", oneRec.getStrListName()); 
	        item.put("list_other", oneRec.getStrListOther()); 
	        weatherList.add(item);  
	    }
	    
	    //return
	    return weatherList;
	} 
	 
    /**
     * �����Ʋ�ѯ
     * @param name
     * @return
     */
    private List<HashMap<String, Object>> searchItem(String name) 
    {  
    	List<HashMap<String, Object>> mSearchList = new ArrayList<HashMap<String, Object>>(); 
        
        for (int i = 0; i < dbWeatherlist.size(); i++) 
        { 	
            int index =((WeatherStationListItem) dbWeatherlist.get(i)).getStrListName().indexOf(name);  
            

            if (index != -1) {
            	HashMap<String, Object> item = new HashMap<String, Object>();  
     	        item.put("list_id", dbWeatherlist.get(i).getStrListId()); 
     	        item.put("list_name", dbWeatherlist.get(i).getStrListName()); 
     	        item.put("list_other", dbWeatherlist.get(i).getStrListOther()); 
     	        mSearchList.add(item);  
            }  
        }  

        return mSearchList;  
    }  
  
    
    /**
     * ����ListViewֵ
     * @param resultList
     */
    private void updateLayout(List<HashMap<String, Object>> resultList) {  
    	  listView.setAdapter(new SimpleAdapter(getApplicationContext(),resultList, R.layout.weather_station_list_item,  
				  new String[] { "list_id", "list_name", "list_other" }, 
				  new int[] {R.id.list_id, R.id.list_name, R.id.list_other})); 
    }  
}
