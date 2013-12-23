package com.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.model.HeatSourceMainItem;
import com.reqst.BusinessRequest;
import com.util.BaseHelper;
import com.util.ConstDefine;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class HeatSourceListFragment extends Fragment {
	private ArrayList<HeatSourceMainItem>  heatSources = null;
	private ArrayList<HashMap<String, Object>> listData;
	private ListView _lvHeatSource;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.heat_source_list, container, false);	
	}
	@Override
	public void onStart(){
		super.onResume();
		_lvHeatSource = (ListView) getView().findViewById(R.id.heat_source_list);
		fetchData();
	}
	
	private void fetchData(){
		new Thread() {
			Message msgSend = new Message();
			public void run() {
				try {
					heatSources =  BusinessRequest.getHeatSourceMainList();
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
			Context context = getActivity().getApplicationContext();
			item.put("name", oneRec.getHeatSourceName());
			item.put("id", oneRec.getHeatSourceId());
			item.put("info", oneRec.getArea() + ", " + oneRec.getCombineMode() + " 机组类型" + oneRec.getUnitType() );

			listData.add(item);
		}
		
		_lvHeatSource.setAdapter( new SimpleAdapter(getActivity().getApplicationContext(), listData, R.layout.heat_source_list_item, 
				new String[] {"name", "id", "info" },
				new int[] {R.id.heat_source_item_name, R.id.heat_source_item_id, R.id.heat_source_item_info}));

	}
}
