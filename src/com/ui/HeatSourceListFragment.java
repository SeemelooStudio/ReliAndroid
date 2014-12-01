package com.ui;

import java.util.ArrayList;
import java.util.HashMap;

import com.model.HeatSourceMainItem;
import com.reqst.BusinessRequest;
import com.util.BaseHelper;
import com.util.ConstDefine;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class HeatSourceListFragment extends Fragment {
	private ArrayList<HeatSourceMainItem>  heatSources = null;
	private ArrayList<HashMap<String, Object>> listData;
	private ListView _lvHeatSource;
	private Activity _activity;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.heat_source_list, container, false);	
	}
	@Override
	public void onStart(){
		super.onResume();
		_lvHeatSource = (ListView) getView().findViewById(R.id.heat_source_list);
		_lvHeatSource.setOnItemClickListener( new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View arg1, int position, long id) {
				HashMap<String, Object> ListItem = (HashMap<String, Object>) _lvHeatSource.getItemAtPosition(position);
				Intent intent = new Intent(_activity.getBaseContext(), HeatSourceDetailActivity.class);
				Bundle bundle = new Bundle();
				bundle.putString("heat_source_name", (String)ListItem.get("name"));
				bundle.putInt("heat_source_id", Integer.parseInt(ListItem.get("id").toString()));
				bundle.putString("heat_source_east_or_west", (String)ListItem.get("east_or_west"));
				bundle.putString("heat_source_inner_or_outer", (String)ListItem.get("inner_or_outer"));
				bundle.putInt("heat_source_peak_coal_count", Integer.parseInt(ListItem.get("peak_coal_count").toString()));
				bundle.putInt("heat_source_peak_gas_count", Integer.parseInt( ListItem.get("peak_gas_count").toString()));
				bundle.putString("heat_source_water_line", (String)ListItem.get("water_line"));
				bundle.putString("heat_source_gas_line", (String)ListItem.get("gas_line"));
				bundle.putBoolean("heat_source_is_in_system", Boolean.getBoolean(ListItem.get("is_in_system").toString()) );
				intent.putExtras(bundle);
				startActivity(intent);
			}
			
		});
		fetchData();
	}
	@Override
	public void onAttach(Activity activity)
	{
		super.onAttach(activity);
		_activity = activity;
	}
	private void fetchData(){
		new Thread() {
			Message msgSend = new Message();
			public void run() {
				try {
					heatSources =  BusinessRequest.getHeatSourceMainList(_activity);
					msgSend.what = ConstDefine.MSG_I_HANDLE_OK;
				} catch (Exception e) {
					msgSend.what = ConstDefine.MSG_I_HANDLE_Fail;
				}
				fetchDataHandler.sendMessage(msgSend);
			}
		}.start();
	}
	@SuppressLint("HandlerLeak")
	private Handler fetchDataHandler = new Handler() {
		public void handleMessage(Message message) {
			switch (message.what) {
			case ConstDefine.MSG_I_HANDLE_OK:
				renderData();
				break;
			case ConstDefine.MSG_I_HANDLE_Fail:
				BaseHelper.showToastMsg(getActivity(), ConstDefine.E_MSG_0001);
				break;
			}
		}
	};
	protected void renderData() {

		if ( null == heatSources) {
			return;
		}
		listData = new ArrayList<HashMap<String, Object>>();
		for (HeatSourceMainItem oneRec: heatSources) 
		{   
			HashMap<String, Object> item = new HashMap<String, Object>();
			item.put("name", oneRec.getHeatSourceName());
			item.put("id", oneRec.getHeatSourceId());
			item.put("inner_or_outer", oneRec.getInnerOrOuter());
			item.put("east_or_west", oneRec.getEastOrWest());
			item.put("peak_coal_count", oneRec.getPeakCoalCount());
			item.put("peak_gas_count", oneRec.getPeakGasCount());
			item.put("water_line", oneRec.getWaterLine());
			item.put("gas_line", oneRec.getGasLine());
			item.put("is_in_system", oneRec.isInSystem());
			item.put("info", oneRec.getEastOrWest() + ", " + oneRec.getInnerOrOuter() + " 机组类型:" + oneRec.getHeatSourceType() );
			listData.add(item);
		}
		
		_lvHeatSource.setAdapter( new SimpleAdapter(getActivity().getApplicationContext(), listData, R.layout.heat_source_list_item, 
				new String[] {"name", "id", "info" },
				new int[] {R.id.heat_source_item_name, R.id.heat_source_item_id, R.id.heat_source_item_info}));

	}
}
