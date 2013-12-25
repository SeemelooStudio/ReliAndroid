package com.ui;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.model.StationHistoryListItem;

import com.reqst.BusinessRequest;
import com.util.BaseHelper;
import com.util.ConstDefine;
import com.util.DateHelper;

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

public class DetailHistoryFragment extends Fragment {
	private List<StationHistoryListItem> historyDataList = null;
	//private int dataType = 0;
	private String sourceId = "";
	private int sourceType = 0;
	
	public static final int SOURCE_TYPE_STATION = 0;
	public static final int SOURCE_TYPE_HEAT_SOURCE = 1;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.detail_history, container, false);
	}
	@Override
	public void onResume() {
		super.onResume();
		fetchData();
	}
	
	public void setDataType(int type) {
		//dataType = type;
	}

	private void fetchData() {

		
		new Thread() {
			public void run() {
				//TODO: set start and end time base on user selection
				Calendar calendar = new GregorianCalendar();
				calendar.set(2013, 11, 2);
				Date startDate = calendar.getTime();
				calendar.set(2013, 11, 9);
				Date endDate = calendar.getTime();
				
				Message msgSend = new Message();
				try {
					historyDataList = BusinessRequest.getStationHistoryList(sourceId, startDate, endDate);
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
	
	public void renderData() {

		if ( null == historyDataList ) {
			return;
		}
		
		List<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>(); 
		HashMap<String, Object> title = new HashMap<String, Object>();
		title.put("date", "日期");
		title.put("actual_gj", "实际GJ");
		title.put("calculate_gj", "核算GJ");
		title.put("plan_gj",  "计划GJ");
		title.put("actual_over_calculate", "多耗%" );
		title.put("actual_temperature", "实际温度");
		title.put("forecast_temperature", "预报温度");
	    data.add(title);
	    
		for (StationHistoryListItem oneRec: historyDataList) 
		{   
			HashMap<String, Object> item = new HashMap<String, Object>();
			Context context = getActivity().getApplicationContext();
			
			String formatedDate = DateHelper.getShortDate(oneRec.getDate(), context);
		    item.put("date", formatedDate);
		    item.put("actual_gj", oneRec.getActualGJ() + context.getString(R.string.heat_unit));
		    item.put("calculate_gj", oneRec.getCalculateGJ() + context.getString(R.string.heat_unit));
		    item.put("plan_gj",  oneRec.getPlanGJ() + context.getString(R.string.heat_unit));
		    
		    double actualOverCalculateGJ = oneRec.getActualOverCalculateGJ();
		    if ( actualOverCalculateGJ > 0 ) {
		    	item.put("actual_over_calculate", "+" + actualOverCalculateGJ + "%");
		    } else if ( actualOverCalculateGJ < 0 ) {
		    	item.put("actual_over_calculate", "" + actualOverCalculateGJ + "%" );
		    }
		    
		    item.put("actual_temperature", oneRec.getActualTemperature() + context.getString(R.string.degree_unit));
		    item.put("forecast_temperature", oneRec.getForcastTemperature() + context.getString(R.string.degree_unit));
		    data.add(item);
		}
		
		View view = getView();
		ListView historyList = (ListView)view.findViewById(R.id.detail_history_list);

		historyList.setAdapter( new HistoryListAdapter(historyDataList,
				getActivity().getApplicationContext(), data, R.layout.station_detail_history_list_item,  
	 			 new String[] { "date","plan_gj","calculate_gj","actual_gj","actual_over_calculate","actual_temperature","forecast_temperature"}, 
				  new int[] {R.id.station_history_date, R.id.station_history_plan_gj, R.id.station_history_calculate_gj, R.id.station_history_actual_gj, R.id.station_history_actual_over_plan, R.id.station_history_actual_temperature, R.id.station_history_forcast_temperature}));

	}
	public String getSourceId() {
		return sourceId;
	}
	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}
	public int getSourceType() {
		return sourceType;
	}
	public void setSourceType(int sourceType) {
		this.sourceType = sourceType;
	}
	
	private class HistoryListAdapter extends SimpleAdapter {
		private List<StationHistoryListItem> _originData;
		
		public HistoryListAdapter(List<StationHistoryListItem> originData, Context context,
				List<? extends Map<String, ?>> data, int resource,
				String[] from, int[] to) {
			super(context, data, resource, from, to);
			_originData = originData;
		}
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view = super.getView(position, convertView, parent);
			if ( position > 0 ) {
				StationHistoryListItem item = _originData.get(position - 1);
				if ( item.getActualOverCalculateGJ() > 0) {
					view.findViewById(R.id.station_history_actual_over_plan ).setBackgroundResource(R.drawable.round_rect_red);
				} else if ( item.getActualOverCalculateGJ() < 0) {
					view.findViewById(R.id.station_history_actual_over_plan ).setBackgroundResource(R.drawable.round_rect_darkblue);
				}
			}
			
			return view;
		}
	}
}
