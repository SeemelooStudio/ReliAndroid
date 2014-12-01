package com.ui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.model.StationListItem;
import com.reqst.BusinessRequest;
import com.util.AccountHelper;
import com.util.BaseHelper;
import com.util.ConstDefine;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView.MultiChoiceModeListener;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SimpleAdapter;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.TextView;

public class EditStationListFragment extends Fragment {
	private ListView listView;  
	private Activity _activity;
	private Context _context;
	private ProgressDialog diaLogProgress= null;
	private ArrayList<StationListItem> _listData; 
	private ArrayList<StationListItem> _stations;
	private ActionMode mActionMode;
	private StationSelectListAdapter _adapter;
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        _activity = getActivity();
        _context = _activity.getApplicationContext();
    }
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View view = inflater.inflate(R.layout.station_list, container, false);	
		listView = (ListView) view.findViewById(R.id.hotPosList);  
        listView.setTextFilterEnabled(true); 
	    this.getStations();
	    
	    
        
        return view;
	}

	public void search(String text) {
		searchItem(text);  
		if(_adapter != null) {
	        _adapter.notifyDataSetChanged(); 
		}
	}
	private void searchItem(String name) 
	{  
		_listData = new ArrayList<StationListItem>();
		Iterator it = _stations.iterator();
		while(it.hasNext()) {
			StationListItem station = (StationListItem)it.next();
			int index =  station.getStationName().indexOf(name);  
			if(index != -1) {
				_listData.add(station);
			}
		} 
	}
	private void getStations()
	{
		diaLogProgress = BaseHelper.showProgress(getActivity(),ConstDefine.I_MSG_0003,false);
        new Thread() {
            public void run() { 
                Message msgSend = new Message();
        	    try {
        	    	if(_stations == null) {
            	    	_stations = AccountHelper.getAllStationNames(_activity );
            	    	if(_stations.size() == 0){
            	    		_stations = BusinessRequest.getStationNames(_activity);
            	    		AccountHelper.setAllStationNames(_activity, _stations);
            	    	}
        	    	}
        	    	_listData = _stations;
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
        		 	_adapter = new StationSelectListAdapter(_context);
        		 	listView.setAdapter(_adapter);
        		 	
        		 	break;
                case ConstDefine.MSG_I_HANDLE_Fail:                       
                	diaLogProgress.dismiss();
                	BaseHelper.showToastMsg(_activity,ConstDefine.E_MSG_0001);
                    break;
	            }
	        }
	  };
	  
	StationListItem findStationByStationId(final int stationId){
		for (StationListItem station : _stations) {
	        if (station.getStationId() == stationId) {
	        	return station;
	        }
		}
		return null; 
	}
	  
	private ActionMode.Callback mActionModeCallback = new ActionMode.Callback() {

		// Called when the action mode is created; startActionMode() was called
		@Override
		public boolean onCreateActionMode(ActionMode mode, Menu menu) {
		    MenuInflater inflater = mode.getMenuInflater();
		    inflater.inflate(R.menu.edit_station_list_actions, menu);
		    return true;
		}

	    // Called each time the action mode is shown. Always called after onCreateActionMode, but
	    // may be called multiple times if the mode is invalidated.
	    @Override
	    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
	        return false; // Return false if nothing is done
	    }
	
	    // Called when the user selects a contextual menu item
	    @Override
	    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
	        switch (item.getItemId()) {
	            default:
	                return false;
	        }
	    }
	
	    // Called when the user exits the action mode
	    @Override
	    public void onDestroyActionMode(ActionMode mode) {
	    	
	    	ArrayList<Integer> selectedStations = new ArrayList<Integer>();
	    	ArrayList<StationListItem> selectedStationNames = new ArrayList<StationListItem>();
	    	Iterator it = _adapter.getIsSelected().entrySet().iterator();
	        while (it.hasNext()) {
	            Map.Entry pairs = (Map.Entry)it.next();
	            if((Boolean)pairs.getValue()) {
	            	int stationId = (Integer)pairs.getKey();
	            	selectedStations.add(stationId);
	            	selectedStationNames.add(findStationByStationId(stationId));
	            }
	            it.remove(); // avoids a ConcurrentModificationException
	        }
	        AccountHelper.setRegistedStationIds(selectedStations, _activity);
	        AccountHelper.setRegistedStationNames(_activity, selectedStationNames);
	        mActionMode = null;
	        Intent intent = new Intent();
	        intent.putExtra("isChanged", true);
	        _activity.setResult(1, intent);
	        _activity.finish();
	    }
	};
		
	public final class StationSelectListViewHolder {
		public CheckBox cbkSelectStation;
		public TextView StationName;
		public TextView StationInfo;
		public int StationId;
		public boolean IsChecked;
	}
		
	public class StationSelectListAdapter extends BaseAdapter implements OnItemClickListener {
		private LayoutInflater _inflater;
		
		private HashMap<Integer,Boolean> _isSelected;
		private int _selectedCount = 0;
		
		public StationSelectListAdapter( Context context ){
			this._inflater = LayoutInflater.from(context);
		}
		@Override
		public int getCount() {
			return _listData.size();
		}

		@Override
		public Object getItem(int position) {
			return _listData.get(position);
		}

		@Override
		public long getItemId(int position) {
			return _listData.get(position).getStationId();
		}
		
		public HashMap<Integer,Boolean> getIsSelected() {
			if(_isSelected == null) {
				_isSelected = new HashMap<Integer,Boolean>();  
				ArrayList<Integer> selectedStations = AccountHelper.getRegistedStationIds(_activity);
				for (int i = 0; i < _listData.size(); i++) 
		        { 	
					int stationId = _listData.get(i).getStationId();
					_isSelected.put(stationId, selectedStations.contains(stationId));
		        } 
				_selectedCount = selectedStations.size();
			}
	        return _isSelected;
	    }
		
		public void setIsSelected(HashMap<Integer,Boolean> isSelected) {
			_isSelected = isSelected;
	    }
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			StationSelectListViewHolder holder = null;
			if(convertView == null) {
				holder = new StationSelectListViewHolder();
				convertView = _inflater.inflate(R.layout.station_list_select_item, null);
				holder.cbkSelectStation = (CheckBox) convertView.findViewById(R.id.cbk_select_station);
				holder.StationName = (TextView) convertView.findViewById(R.id.station_name);
				holder.StationInfo = (TextView) convertView.findViewById(R.id.station_info);
				convertView.setOnClickListener( new OnClickListener () {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						StationSelectListViewHolder theHolder = (StationSelectListViewHolder) v.getTag();
						String stationName = ( (TextView)v.findViewById(R.id.station_name)).getText().toString();
						int stationId = theHolder.StationId;
						Intent intent = new Intent(_activity.getBaseContext(), StationDetailActivity.class); 
		        		Bundle mBundle = new Bundle();
		        		mBundle.putInt("station_id", stationId);
		        		mBundle.putString("station_name", stationName);
		        		intent.putExtras(mBundle);
		        		startActivity(intent);
					}
				
				});
				convertView.setTag(holder);
			}
			else {
				holder = (StationSelectListViewHolder)convertView.getTag();
			}
			
			StationListItem station = _listData.get(position);
			holder.StationName.setText(station.getStationName());
			holder.StationInfo.setText(station.getEastOrWest() + "," + station.getType());
			holder.StationId = station.getStationId();
			Boolean isChecked = getIsSelected().get(station.getStationId());
			holder.cbkSelectStation.setChecked(isChecked);
			holder.cbkSelectStation.setTag(station.getStationId());
			holder.cbkSelectStation.setOnClickListener( new OnClickListener () {
				@Override
				public void onClick(View view) {
					int stationId = (Integer)view.getTag();
					CheckBox cbk = (CheckBox)view;
					_isSelected.put(stationId, cbk.isChecked());
					if(cbk.isChecked()) {
						_selectedCount ++;
					}
					else {
						_selectedCount --;
					}
					if(mActionMode == null){
						mActionMode = getActivity().startActionMode(mActionModeCallback);
					}
					mActionMode.setTitle( "已选择" + _selectedCount + "个热力站" );
				}
			});
			
			return convertView;
		}
		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
            Log.v("clicked", "clicked");
		}
		
	}
}
