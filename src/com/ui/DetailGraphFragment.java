package com.ui;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.achartengine.GraphicalView;

import com.chart.impl.SupplyAndBackwardDetailChart;
import com.model.SupplyAndBackwardItem;

import com.reqst.BusinessRequest;
import com.util.BaseHelper;
import com.util.ConstDefine;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class DetailGraphFragment extends Fragment {
	private List<SupplyAndBackwardItem> chartDataList = null;
	private int dataType = 0;
	private String sourceId = "";
	private int sourceType = 0;
	private String unitId = "";
	
	public static final int SOURCE_TYPE_STATION = 0;
	public static final int SOURCE_TYPE_HEAT_SOURCE = 1;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.detail_graph, container, false);
	}
	@Override
	public void onResume() {
		super.onResume();
		fetchData();
	}
	
	public void setDataType(int type) {
		dataType = type;
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
					switch( dataType ) {
					case SupplyAndBackwardDetailChart.TYPE_TEMPERATURE:
						if ( SOURCE_TYPE_STATION == sourceType ) {
							chartDataList = BusinessRequest.getStationSupplyAndReturnTemperatureList(sourceId, startDate, endDate);
						} else {
							chartDataList = BusinessRequest.getHeatSourceSupplyAndBackwardTemperatureList(sourceId, unitId, startDate, endDate);
						}
						
						break;
					case SupplyAndBackwardDetailChart.TYPE_PRESSURE:
						if ( SOURCE_TYPE_STATION == sourceType ) {
							chartDataList = BusinessRequest.getStationSupplyAndBackwardPressureList(sourceId, startDate, endDate);
						} else {
							chartDataList = BusinessRequest.getHeatSourceSupplyAndReturnPressureList(sourceId, unitId, startDate, endDate);
						}
						break;
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
        //set chart
		SupplyAndBackwardDetailChart  supplyAndBackwardChart = new SupplyAndBackwardDetailChart(chartDataList, dataType);
		LinearLayout layout = (LinearLayout) view.findViewById(R.id.detail_graph);
		
		GraphicalView mChartView = supplyAndBackwardChart.createChart(getActivity());
		layout.removeAllViews();
		layout.addView(mChartView);

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
