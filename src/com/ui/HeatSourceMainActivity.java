package com.ui;

import java.util.ArrayList;
 
import android.annotation.SuppressLint;
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
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.GridLayout.Spec;

import com.model.HeatSourceDetail;
import com.model.HeatSourceMainItem;
import com.model.HeatSourceTitle;
import com.reqst.BusinessRequest;
import com.util.BaseHelper;
import com.util.CellBackgroundHelper;
import com.util.ConstDefine;
import com.util.ViewPageAdapter;
import com.util.ViewPageChangeListener;

public class HeatSourceMainActivity extends Activity 
{
	private ViewPager viewpage;
	private ViewGroup viewGroup; 
	private View[] indicators; 
	private ProgressDialog diaLogProgress= null;
	private HeatSourceTitle  titleInfo = null;
	
	private ArrayList<View> views;
	private ArrayList<HeatSourceMainItem>  dbHeatSources = null;
	
	private static int ROW_COUNT = 1;
	private static int COLUMN_COUNT = 3;
	private static int PAGE_SIZE =3;
	
	private Activity _activity;
	
    @Override                                                                                            
    public void onCreate(Bundle savedInstanceState) 
    {                                                    
		super.onCreate(savedInstanceState);
		setContentView(R.layout.heat_source_main);
		_activity =  this;
		this.initHotSourceView();
    }
	  
	private void initHotSourceView()
	{
		titleInfo = new HeatSourceTitle();
		dbHeatSources = new ArrayList<HeatSourceMainItem>();
		diaLogProgress = BaseHelper.showProgress(HeatSourceMainActivity.this,ConstDefine.I_MSG_0003,false);
	    new Thread() 
	    {
	        public void run() 
	        { 
                Message msgSend = new Message();
        	    try 
        	    {
        	    	dbHeatSources = BusinessRequest.getHeatSourceMainList(_activity);
        	    	titleInfo = BusinessRequest.getHeatSourceAllStatic(_activity);
        	    	msgSend.what = ConstDefine.MSG_I_HANDLE_OK;
				} 
        	    catch (Exception e) 
        	    {
					msgSend.what = ConstDefine.MSG_I_HANDLE_Fail;
				}
                handler.sendMessage(msgSend);
        	}
	    }.start();
	}  
	
	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() 
	{               
		public void handleMessage(Message message) 
		{
		    switch (message.what) 
		    {
			    case ConstDefine.MSG_I_HANDLE_OK:                                        
				 	diaLogProgress.dismiss();
				 	setHotSourceGridView();
				    break;
			    case ConstDefine.MSG_I_HANDLE_Fail:                                        
			    	diaLogProgress.dismiss();
			    	BaseHelper.showToastMsg(HeatSourceMainActivity.this,ConstDefine.E_MSG_0001);
			        break;
		    }
		}
	};
	    
	private void setHeatSourceItemContent( View viewHeatSource, HeatSourceMainItem item, int recentIndex)
	{
		TextView tvSourceArea =  (TextView) viewHeatSource.findViewById(R.id.heat_source_area);
		tvSourceArea.setText(item.getEastOrWest() + ", " + item.getInnerOrOuter() );
		TextView tvHeatSourceName = (TextView) viewHeatSource.findViewById(R.id.hot_source_name);
		tvHeatSourceName.setText(item.getHeatSourceName());
        TextView tvHeatSourceRecentName = (TextView) viewHeatSource.findViewById(R.id.heat_source_recent_name);
        tvHeatSourceRecentName.setText(item.getHeatSourceRecents().get(recentIndex).getName());
        TextView tvHeatSourceRecentLastUpdatedAt = (TextView) viewHeatSource.findViewById(R.id.last_updated_at);
        tvHeatSourceRecentLastUpdatedAt.setText(item.getHeatSourceRecents().get(recentIndex).getLastUpdatedAt());
        TextView tvHeatSourcePressureOut = (TextView) viewHeatSource.findViewById(R.id.heat_source_pressure_out);
        tvHeatSourcePressureOut.setText( String.format("%.2f", item.getHeatSourceRecents().get(recentIndex).getPressureOut() ) );
        TextView tvHeatSourcePressureIn = (TextView) viewHeatSource.findViewById(R.id.heat_source_pressure_in);
        tvHeatSourcePressureIn.setText( String.format("%.2f", item.getHeatSourceRecents().get(recentIndex).getPressureIn () ) );
        TextView tvHeatSourceTemperatureOut = (TextView) viewHeatSource.findViewById(R.id.heat_source_temperature_out);
        tvHeatSourceTemperatureOut.setText( String.format("%.1f", item.getHeatSourceRecents().get(recentIndex).getTemperatureOut() ) );
        TextView tvHeatSourceTemperatureIn = (TextView) viewHeatSource.findViewById(R.id.heat_source_temperature_in);
        tvHeatSourceTemperatureIn.setText( String.format("%.1f", item.getHeatSourceRecents().get(recentIndex).getTemperatureIn() ) );
        TextView tvHeatSourceFlowOut = (TextView) viewHeatSource.findViewById(R.id.heat_source_flow_out);
        tvHeatSourceFlowOut.setText( String.format("%.0f", item.getHeatSourceRecents().get(recentIndex).getInstWater()) );
        TextView tvHeatSourceFlowIn = (TextView) viewHeatSource.findViewById(R.id.heat_source_flow_in);
        tvHeatSourceFlowIn.setText( String.format("%.0f", item.getHeatSourceRecents().get(recentIndex).getInstWaterIn() ) );
        TextView tvHeatSourceInstantHeat = (TextView) viewHeatSource.findViewById(R.id.heat_source_instant_heat);
        tvHeatSourceInstantHeat.setText( String.format("%.0f", item.getHeatSourceRecents().get(recentIndex).getInstHeat()) );
        TextView tvHeatSourceSupply = (TextView) viewHeatSource.findViewById(R.id.heat_source_supply);
        tvHeatSourceSupply.setText( String.format("%.0f", item.getHeatSourceRecents().get(recentIndex).getWaterSupply() ) );
	}

	private View getHeatSourceCell(HeatSourceMainItem heatSource, int rowIndex, int columnIndex, int cellWidth, int cellHeight, int recentIndex)
	{
		int ceilMargin = (int)getResources().getDimension(R.dimen.small_margin);
		LayoutInflater inflater = (LayoutInflater)getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);
		View viewHeatSource = inflater.inflate(R.layout.heat_source_main_item, null);
		setHeatSourceItemContent(viewHeatSource, heatSource, recentIndex);
		  
		Spec row = GridLayout.spec(rowIndex, 1);
		Spec colspan = GridLayout.spec(columnIndex, 1);
		GridLayout.LayoutParams param =new GridLayout.LayoutParams(row, colspan);
		param.setGravity(Gravity.FILL);
		param.width = cellWidth;
		param.height = cellHeight;
		param.setMargins(ceilMargin, ceilMargin, ceilMargin, ceilMargin);
	      
		int intState = CellBackgroundHelper.CELL_STATE_OUTTER;
        if ( ! heatSource.isEast() )
        {
            intState = CellBackgroundHelper.CELL_STATE_WEST;
        }
		Integer intBackgroundResource = CellBackgroundHelper.getBackgroundResourceByCellState(intState);
		  
      	viewHeatSource.setBackgroundResource(intBackgroundResource);
      	viewHeatSource.setLayoutParams (param);
      	viewHeatSource.setOnClickListener(
  			new heatSourceCellOnClickListener(
      			heatSource.getHeatSourceName(), 
      			heatSource.getHeatSourceId(),
      			heatSource.getEastOrWest(),
      			heatSource.getInnerOrOuter(),
      			heatSource.getPeakCoalCount(),
      			heatSource.getPeakGasCount(),
      			heatSource.getWaterLine(),
      			heatSource.getGasLine(),
      			heatSource.isInSystem(),
                heatSource.getHeatSourceRecents().get(recentIndex)
                )
  			{                                                                                    
  				public void onClick(View v) 
  				{
                    Intent intent = new Intent(HeatSourceMainActivity.this, UnitDetailActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("heat_source_recent_name", detail.getName());
                    bundle.putInt("heat_source_recent_id", detail.getHeatSourceRecentId());
                    bundle.putInt("heat_source_id", detail.getHeatSourceId());
                    bundle.putFloat("heat_source_recent_instHeat", detail.getInstHeat());
                    bundle.putFloat("heat_source_recent_instWater", detail.getInstWater());
                    bundle.putFloat("heat_source_recent_accuHeat", detail.getAccuHeat());
                    bundle.putFloat("heat_source_recent_accuWater", detail.getAccuWater());
                    bundle.putFloat("heat_source_recent_waterSupply", detail.getWaterSupply());

                    bundle.putFloat("heat_source_recent_temperatureIn", detail.getTemperatureIn());
                    bundle.putFloat("heat_source_recent_temperatureOut", detail.getTemperatureOut());
                    bundle.putFloat("heat_source_recent_pressureIn", detail.getPressureIn());
                    bundle.putFloat("heat_source_recent_pressureOut", detail.getPressureOut());


                    intent.putExtras(bundle);
                    startActivity(intent);

//					Intent intent = new Intent(HeatSourceMainActivity.this, HeatSourceDetailActivity.class);
//					Bundle bundle = new Bundle();
//					bundle.putString("heat_source_name", heatSourceName);
//					bundle.putInt("heat_source_id", heatSourceId);
//					bundle.putString("heat_source_east_or_west", eastOrWest);
//					bundle.putString("heat_source_inner_or_outer", innerOrOuter);
//					bundle.putInt("heat_source_peak_coal_count", peakCoalCount);
//					bundle.putInt("heat_source_peak_gas_count", peakGasCount);
//					bundle.putString("heat_source_water_line", waterLine);
//					bundle.putString("heat_source_gas_line", gasLine);
//					bundle.putBoolean("heat_source_is_in_system", isInSystem);
//					intent.putExtras(bundle);
//					startActivity(intent);
  				}
			}
		);
  		return viewHeatSource;
	}
	  
	private void setHotSourceGridView()
	{
		viewpage = (ViewPager) findViewById(R.id.hotMainPager);
        int totalItems = 0;
        for(int i = 0; i< dbHeatSources.size(); i++)
        {
            totalItems += dbHeatSources.get(i).getHeatSourceRecents().size();
        }
        int pageNum = (int)Math.ceil( (float)totalItems / PAGE_SIZE );
		int screenWidth = viewpage.getWidth();
		int screenHeight = viewpage.getHeight();
		int ceilMargin = (int)getResources().getDimension(R.dimen.small_margin) * 2;
		int cellWidth = (int)( screenWidth  / COLUMN_COUNT - ceilMargin); 
		int cellHeight = (int) ( screenHeight / ROW_COUNT - ceilMargin);
		int bigCellHeight = cellHeight * 4 + ceilMargin * 3;
		views = new ArrayList<View>();
		for(int pageIndex = 0, itemIndex = 0, recentIndex = 0; pageIndex < pageNum; pageIndex++ )
		{
			GridLayout gridLayout = new GridLayout(this);
			gridLayout.setColumnCount(COLUMN_COUNT);
			gridLayout.setRowCount(ROW_COUNT);
			gridLayout.setOrientation(GridLayout.HORIZONTAL);
			gridLayout.setUseDefaultMargins(true);

            for(int cell = 0, rowIndex = 0, columnIndex=0;
                cell < PAGE_SIZE && itemIndex < dbHeatSources.size();
                cell ++)
            {
                HeatSourceMainItem heatSource = dbHeatSources.get(itemIndex);
            	gridLayout.addView(getHeatSourceCell(heatSource, rowIndex, columnIndex, cellWidth, bigCellHeight, recentIndex));
            	if(recentIndex == heatSource.getHeatSourceRecents().size()-1)
            	{
            		recentIndex = 0;
            		itemIndex++;
            	}
            	else {
            		recentIndex++;
            	}
            	
            	if( columnIndex == COLUMN_COUNT -1 )
            	{
            		columnIndex = 0;
            	}
            	else {
            		columnIndex++;
            	}
            }
			gridLayout.setId(pageIndex);
			views.add(gridLayout);
		}
		setIndicatorView();
		viewpage.setAdapter(new ViewPageAdapter(views));
		viewpage.setOnPageChangeListener(new ViewPageChangeListener(indicators)); 
	}
	  
	private void setIndicatorView() 
	{
		indicators = new View[views.size()];  
		viewGroup = (ViewGroup)findViewById(R.id.hotSorceIndicators);
		int focusedSize = 40;
		int normalSize = 20;

		for (int i = 0; i < views.size(); i++) 
		{  
			TextView indicator = new TextView(HeatSourceMainActivity.this);
			indicators[i] = indicator;
			indicator.setGravity(Gravity.CENTER);
			indicator.setTextColor(Color.BLACK);
			if (i == 0) 
			{
				LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(focusedSize,focusedSize);
				params.setMargins(0, 0, 10, 0);
				indicator.setLayoutParams(params);
				indicator.setText("1");
			    indicators[i].setBackgroundResource(R.drawable.page_indicator_focused);
			} 
			else 
			{
				LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(normalSize,normalSize);
				params.setMargins(0, 0, 10, 0);
				indicator.setLayoutParams(params);
			    indicators[i].setBackgroundResource(R.drawable.page_indicator);  
			}             
			viewGroup.addView(indicators[i]);  
		} 	  
	}
	
	private abstract class heatSourceCellOnClickListener implements OnClickListener 
	{
		String heatSourceName;
		int heatSourceId;
		String eastOrWest;
		String innerOrOuter;
		int peakCoalCount;
		int peakGasCount;
		String waterLine;
		String gasLine;
		boolean isInSystem;
        HeatSourceDetail detail;

		public heatSourceCellOnClickListener(String heatSourceName, int heatSourceId, String eastOrWest, String innerOrOuter,
											 int peakCoalCount, int peakGasCount, String waterLine, String gasLine, boolean isInSystem,
                                             HeatSourceDetail detail)
		{
			this.heatSourceName = heatSourceName;
			this.heatSourceId = heatSourceId;
			this.eastOrWest = eastOrWest;
			this.innerOrOuter = innerOrOuter;
			this.peakCoalCount = peakCoalCount;
			this.peakGasCount = peakGasCount;
			this.waterLine = waterLine;
			this.gasLine = gasLine;
			this.isInSystem = isInSystem;
            this.detail = detail;
		}
	};
}  
