package com.ui;

import java.util.Calendar;
import java.util.Date;

import java.util.List;

import org.achartengine.GraphicalView;

import com.chart.impl.SupplyAndBackwardDetailChart;
import com.model.SupplyAndBackwardItem;

import com.reqst.BusinessRequest;
import com.util.BaseHelper;
import com.util.ConstDefine;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class DetailGraphFragment extends Fragment {
	private List<SupplyAndBackwardItem> chartDataList = null;
	private int dataType = 0;
	private String sourceId = "";
	private int sourceType = 0;
	private String unitId = "";
	private Date fromDate ;
	private Date toDate ;
	public static final int SOURCE_TYPE_STATION = 0;
	public static final int SOURCE_TYPE_HEAT_SOURCE = 1;
	private Activity _activity;
	private RadioGroup radioGroup;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.detail_graph, container, false);
		radioGroup = (RadioGroup) view.findViewById(R.id.detail_graph_radios);
		if(radioGroup != null) {
			radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
				@Override
				public void onCheckedChanged(RadioGroup group, int checkedId) {
					try{
					Calendar calendar =  Calendar.getInstance();
					
					if(checkedId == R.id.detail_graph_radio_day) {
						toDate = calendar.getTime();
						calendar.add(Calendar.HOUR, -24);
						fromDate = calendar.getTime();
					}
					else if(checkedId == R.id.detail_graph_radio_week) {
						toDate = calendar.getTime();
						calendar.add(Calendar.HOUR, -24*7);
						fromDate = calendar.getTime();
					}
					else if(checkedId == R.id.detail_graph_radio_month) {
						toDate = calendar.getTime();
						calendar.add(Calendar.HOUR, -24*30);
						fromDate = calendar.getTime();
					}
					fetchData();
					}catch(Exception ex){
					
					}
				}
				
			});
		};
		
		return view;
	}
	
	public void setDefaultSpan()
	{
		Calendar calendar =  Calendar.getInstance();
		toDate = calendar.getTime();
		calendar.add(Calendar.HOUR, -24);
		fromDate = calendar.getTime();
	}
	
	@Override
	public void onAttach(Activity activity)
	{
		super.onAttach(activity);
		_activity = activity;
	}
	
	@Override 
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public void onResume() {
		super.onResume();
	}
	
	
	public void setDataType(int type) {
		dataType = type;
	}
	
	public void setFromDate (Date date){
		fromDate = date;
	}

	public void setToDate (Date date) throws Exception{
		toDate = date;
	}
	
	public void setData(List<SupplyAndBackwardItem> data) {
		chartDataList = data;
		renderData();
	}
	
	public void fetchData() {
		new Thread() {
			public void run() {
				Message msgSend = new Message();
				try {
					if ( SOURCE_TYPE_STATION == sourceType ) {
						chartDataList = BusinessRequest.getStationSupplyAndReturnList(sourceId, fromDate, toDate, _activity);
					} else {
						chartDataList = BusinessRequest.getHeatSourceSupplyAndBackwardList(sourceId, unitId, fromDate, toDate, _activity);
					}
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

		if ( null == chartDataList ) {
			return;
		}
		View view = getView();
		if(view != null ){
        //set chart
		SupplyAndBackwardDetailChart  supplyAndBackwardChart = new SupplyAndBackwardDetailChart(chartDataList, dataType);
		LinearLayout layout = (LinearLayout) view.findViewById(R.id.detail_graph);
		
		GraphicalView mChartView = supplyAndBackwardChart.createChart(getActivity());
		layout.removeAllViews();
		layout.addView(mChartView);
		}
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
	public String getUnitId() {
		return unitId;
	}
	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}
}
