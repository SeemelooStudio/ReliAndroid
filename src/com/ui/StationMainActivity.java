package com.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.GridLayout.Spec;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;

import com.model.StationListItem;
import com.model.StationMainItem;
import com.reqst.BusinessRequest;
import com.util.BaseHelper;
import com.util.CellBackgroundHelper;
import com.util.ConstDefine;
import com.util.ViewPageAdapter;
import com.util.ViewPageChangeListener;

public class StationMainActivity extends Activity {

	private ViewPager viewpage;
	private ViewGroup viewGroup;
	private View[] indicators;
	private ProgressDialog diaLogProgress = null;

	private ArrayList<View> views;
	private ArrayList<StationMainItem> _stations = null;
	private static int ROW_COUNT = 4;
	private static int COLUMN_COUNT = 3;
	private static int PAGE_SIZE = 10;
	private boolean _needRefresh = false;
	private Activity _activity;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.station_main);
		_activity = this;
	}
	
	@Override
	public void onResume()
	{
		super.onResume();
		initStationView();
	}
	private void initStationView() {

		if(_stations == null || _needRefresh) {
			_stations = new ArrayList<StationMainItem>();
			_needRefresh = false;
			diaLogProgress = BaseHelper.showProgress(StationMainActivity.this,
					ConstDefine.I_MSG_0003, false);
			new Thread() {
				public void run() {
					Message msgSend = new Message();
					try {
						// get itemList
						_stations = BusinessRequest.getStationMainList(_activity);
	
						msgSend.what = ConstDefine.MSG_I_HANDLE_OK;
					} catch (Exception e) {
						msgSend.what = ConstDefine.MSG_I_HANDLE_Fail;
					}
					handler.sendMessage(msgSend);
				}
			}.start();
		}
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
				// set grid view
				setStationGridView();
				break;
			case ConstDefine.MSG_I_HANDLE_Fail:
				// close process
				diaLogProgress.dismiss();
				BaseHelper.showToastMsg(StationMainActivity.this,
						ConstDefine.E_MSG_0001);
				break;
			}
		}
	};

	private void setStationGridView() {
		viewpage = (ViewPager) findViewById(R.id.stationMainPager);

		int pageNum = Math.max( (int) Math.ceil((float) _stations.size() / PAGE_SIZE), 1);
		
		int screenWidth = viewpage.getWidth();
		int screenHeight = viewpage.getHeight();

		int ceilMargin = (int) getResources()
				.getDimension(R.dimen.small_margin) * 2;

		int cellWidth = (int) (screenWidth / COLUMN_COUNT - ceilMargin);
		int cellHeight = (int) (screenHeight / ROW_COUNT - ceilMargin);

		int bigCellWidth = cellWidth * 2 + ceilMargin;

		// create page
		views = new ArrayList<View>();
		for (int pageIndex = 0; pageIndex < pageNum; pageIndex++) {

			GridLayout gridLayout = new GridLayout(this);
			gridLayout.setColumnCount(COLUMN_COUNT);
			gridLayout.setRowCount(ROW_COUNT);
			gridLayout.setOrientation(GridLayout.HORIZONTAL);
			// gridLayout.setUseDefaultMargins(true);
			for (int cell = 0, rowIndex = 0, columnIndex = 0, itemIndex = pageIndex
					* PAGE_SIZE; cell < PAGE_SIZE
					&& itemIndex < _stations.size(); cell++, columnIndex++, itemIndex++) {
				if (columnIndex == COLUMN_COUNT) {
					columnIndex = 0;
					rowIndex++;
				}
				// show title cell
				if (rowIndex == 1 && columnIndex == 0) {
					columnIndex += 1;
					cell--;
					itemIndex--;
				} else {
					gridLayout.addView(getStationCell(
							_stations.get(itemIndex), rowIndex, columnIndex,
							cellWidth, cellHeight));
				}
			}
			gridLayout.addView(getHeatStationTitleCell(
					_stations.size(), 1, 0,
					bigCellWidth, cellHeight));
			gridLayout.setId(pageIndex);
			views.add(gridLayout);
		}
		
		setIndicatorsView();

		// add pages
		viewpage.setAdapter(new ViewPageAdapter(views));
		viewpage.setOnPageChangeListener(new ViewPageChangeListener(indicators));
	}

	private void setStationItemContent(View viewStation, StationMainItem item) {
		
		TextView tvTemperatureOut = (TextView) viewStation
				.findViewById(R.id.hot_station_temperature_out);
		tvTemperatureOut.setText(item.getTemperatureOut()
				+ getString(R.string.degree_unit));
		TextView tvPressureOut = (TextView) viewStation
				.findViewById(R.id.hot_station_pressure_out);
		tvPressureOut.setText(item.getPressureOut()
				+ getString(R.string.pressure_unit));
		
		TextView tvTemperatureIn = (TextView) viewStation
				.findViewById(R.id.hot_station_temperature_in);
		tvTemperatureIn.setText(item.getTemperatureIn()
				+ getString(R.string.degree_unit));
		TextView tvPressureIn = (TextView) viewStation
				.findViewById(R.id.hot_station_pressure_in);
		tvPressureIn.setText(item.getPressureIn()
				+ getString(R.string.pressure_unit));

		TextView tvHeatStationName = (TextView) viewStation
				.findViewById(R.id.hot_station_name);
		tvHeatStationName.setText(item.getStationName());

		TextView tvStationId = (TextView) viewStation
				.findViewById(R.id.hotPosItemId);
		tvStationId.setText(item.getStationId() + "");
	}

	private View getHeatStationTitleCell(int allCount, int rowIndex,
			int columnIndex, int cellWidth, int cellHeight) {
		int ceilMargin = (int) getResources()
				.getDimension(R.dimen.small_margin);

		LayoutInflater inflater = (LayoutInflater) getBaseContext()
				.getSystemService(LAYOUT_INFLATER_SERVICE);
		View viewStationTitle = inflater.inflate(
				R.layout.station_main_title_item, null);
		TextView txtAllnum = (TextView) viewStationTitle
				.findViewById(R.id.hotPosTitleAllnum);
		txtAllnum.setText("共" + allCount + "个关键热力站");

		Spec row = GridLayout.spec(rowIndex, 1);
		Spec colspan = GridLayout.spec(columnIndex, 2);
		GridLayout.LayoutParams param = new GridLayout.LayoutParams(row,
				colspan);
		param.setGravity(Gravity.FILL);
		param.width = cellWidth;
		param.height = cellHeight;
		param.setMargins(ceilMargin, ceilMargin, ceilMargin, ceilMargin);
		viewStationTitle.setLayoutParams(param);

		viewStationTitle.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(StationMainActivity.this,
						StationQueryActivity.class);
				startActivityForResult(intent, BaseHelper.Saved_Station_Request);
			}
		});
		return viewStationTitle;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data){
		if(data != null) {
			Boolean isChanged = data.getBooleanExtra("isChanged", false);
			_needRefresh = isChanged;
		}
	}
	
	private View getStationCell(StationMainItem station, int rowIndex,
			int columnIndex, int cellWidth, int cellHeight) {
		int ceilMargin = (int) getResources()
				.getDimension(R.dimen.small_margin);

		LayoutInflater inflater = (LayoutInflater) getBaseContext()
				.getSystemService(LAYOUT_INFLATER_SERVICE);
		View viewStation = inflater.inflate(R.layout.station_main_item, null);
		setStationItemContent(viewStation, station);

		Spec row = GridLayout.spec(rowIndex, 1);
		Spec colspan = GridLayout.spec(columnIndex, 1);
		GridLayout.LayoutParams param = new GridLayout.LayoutParams(row,
				colspan);
		param.setGravity(Gravity.FILL);
		param.width = cellWidth;
		param.height = cellHeight;
		param.setMargins(ceilMargin, ceilMargin, ceilMargin, ceilMargin);
		viewStation.setLayoutParams(param);

		// TODO: set background color base on heat source state

		Integer state = new Random().nextInt(3);
		Integer intBackgroundResource = CellBackgroundHelper
				.getBackgroundResourceByCellState(state);

		viewStation.setBackgroundResource(intBackgroundResource);

		viewStation.setOnClickListener(new StationCellOnClickListener(station
				.getStationName(), station.getStationId()) {
			public void onClick(View v) {
				Intent intent = new Intent(StationMainActivity.this,
						StationDetailActivity.class);
				Bundle bundle = new Bundle();
				bundle.putString("station_name", stationName);
				bundle.putInt("station_id", stationId);
				intent.putExtras(bundle);
				startActivity(intent);
			}
		});

		return viewStation;
	}

	private void setIndicatorsView() {
		indicators = new View[views.size()];
		viewGroup = (ViewGroup) findViewById(R.id.stationMainIndicators);
		viewGroup.removeAllViews();
		int focusedSize = 40;
		int normalSize = 20;

		for (int i = 0; i < views.size(); i++) {

			TextView indicator = new TextView(StationMainActivity.this);
			indicators[i] = indicator;
			indicator.setGravity(Gravity.CENTER);
			indicator.setTextColor(Color.BLACK);

            if (i == 0) {
            	LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(focusedSize,focusedSize);
            	params.setMargins(0, 0, 10, 0);
            	indicator.setLayoutParams(params);
            	indicator.setText("1");
                indicators[i].setBackgroundResource(R.drawable.page_indicator_focused);
            } else {
            	LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(normalSize,normalSize);
            	params.setMargins(0, 0, 10, 0);
            	indicator.setLayoutParams(params);
                indicators[i].setBackgroundResource(R.drawable.page_indicator);  
            }  
			viewGroup.addView(indicators[i]);
		}
	}

	private abstract class StationCellOnClickListener implements
			OnClickListener {

		String stationName;
		int stationId;

		public StationCellOnClickListener(String stationName, int stationId) {
			this.stationName = stationName;
			this.stationId = stationId;
		}

	};
}
