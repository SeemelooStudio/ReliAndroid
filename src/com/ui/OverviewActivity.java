package com.ui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.model.GenericListItem;
import com.model.Overview;
import com.reqst.BusinessRequest;
import com.util.AccountHelper;
import com.util.BaseHelper;
import com.util.ConstDefine;
import com.util.WeatherIconHelper;


import android.annotation.SuppressLint;

public class OverviewActivity extends Activity 
{
	Activity _activity;

	private Overview overview = null;
	private ProgressDialog diaLogProgress = null;
     
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.over_view);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		_activity=this;
		initOverviewData();
	}
	 
	@Override
	public void onResume()
	{
		super.onResume();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) 
	{
		 switch(item.getItemId()) 
		 {
			 case android.R.id.home:
			     this.finish();
			     return true;
			 default :
				 return super.onOptionsItemSelected(item);
		 }
	}
	
    private void initOverviewData()
    {
        diaLogProgress = BaseHelper.showProgress(OverviewActivity.this, ConstDefine.I_MSG_0003,false);
        new Thread() {
            public void run() {
                Message msgSend = new Message();
                try {
                    //get today weatherInfo
                    String userName = AccountHelper.getUserName(_activity);
                    overview = BusinessRequest.getOverview(userName, _activity);
                    msgSend.what = ConstDefine.MSG_I_HANDLE_OK;
                } catch (Exception e) {
                    msgSend.what = ConstDefine.MSG_I_HANDLE_Fail;
                }
                overviewHandler.sendMessage(msgSend);
            }
        }.start();
    }

    @SuppressLint("HandlerLeak")
    private Handler overviewHandler = new Handler() {
        public void handleMessage(Message message) {
            switch (message.what) {
                case ConstDefine.MSG_I_HANDLE_OK:
                    diaLogProgress.dismiss();
                    setOverviewData();
                    break;
                case ConstDefine.MSG_I_HANDLE_Fail:
                    diaLogProgress.dismiss();
                    BaseHelper.showToastMsg(OverviewActivity.this,ConstDefine.E_MSG_0001);
                    break;
            }
        }
    };

    private  void setOverviewData()
    {
    	TextView instant_heat_total = (TextView) findViewById(R.id.overview_instant_heat_total);
    	TextView instant_heat_east = (TextView) findViewById(R.id.overview_instant_heat_east);
    	TextView instant_heat_west = (TextView) findViewById(R.id.overview_instant_heat_west);
    	instant_heat_total.setText( String.format("%.0f", overview.getInstanceHeat2() ) );
    	instant_heat_east.setText( String.format("%.0f", overview.getInstanceHeat0() ) );
    	instant_heat_west.setText( String.format("%.0f", overview.getInstanceHeat1() ) );
    	
    	TextView instant_heat_total_gk = (TextView) findViewById(R.id.overview_instant_heat_total_gk);
    	TextView instant_heat_east_gk = (TextView) findViewById(R.id.overview_instant_heat_east_gk);
    	TextView instant_heat_west_gk = (TextView) findViewById(R.id.overview_instant_heat_west_gk);
    	instant_heat_total_gk.setText( String.format("%.0f(GK/h)", overview.getInstanceHeat2() * 0.239 ) );
    	instant_heat_east_gk.setText( String.format("%.0f(GK/h)", overview.getInstanceHeat0() * 0.239  ) );
    	instant_heat_west_gk.setText( String.format("%.0f(Gk/h)", overview.getInstanceHeat1() * 0.239  ) );
    	
    	TextView area_total = (TextView) findViewById(R.id.overview_area_total);
    	TextView areat_east = (TextView) findViewById(R.id.overview_area_east);
    	TextView area_west = (TextView) findViewById(R.id.overview_area_west);
    	area_total.setText( String.format("%.0f",overview.getArea2() ) );
    	areat_east.setText( String.format("%.0f",overview.getArea0() ) );
    	area_west.setText( String.format("%.0f", overview.getArea1() ) );
    	
    	TextView unit_heat_load_total = (TextView) findViewById(R.id.overview_unit_heat_load_total);
    	TextView unit_heat_load_east = (TextView) findViewById(R.id.overview_unit_heat_load_east);
    	TextView unit_heat_load_west = (TextView) findViewById(R.id.overview_unit_heat_load_west);
    	unit_heat_load_total.setText( String.format("%.1f", overview.getUnitHeatLoad2() ) );
    	unit_heat_load_east.setText( String.format("%.1f", overview.getUnitHeatLoad0() ) );
    	unit_heat_load_west.setText( String.format("%.1f", overview.getUnitHeatLoad1() ) );
    	
    	TextView water_total = (TextView) findViewById(R.id.overview_water_total);
    	TextView water_east = (TextView) findViewById(R.id.overview_water_east);
    	TextView water_west = (TextView) findViewById(R.id.overview_water_west);
    	water_total.setText( String.format("%.0f", overview.getWater2() ) );
    	water_east.setText( String.format("%.0f", overview.getWater0() ) );
    	water_west.setText( String.format("%.0f", overview.getWater1()) );
    	
    	TextView tenk_total = (TextView) findViewById(R.id.overview_tenk_total);
    	TextView tenk_east = (TextView) findViewById(R.id.overview_tenk_east);
    	TextView tenk_west = (TextView) findViewById(R.id.overview_tenk_west);
    	tenk_total.setText( String.format("%.2f", overview.getTenk2()) );
    	tenk_east.setText( String.format("%.2f", overview.getTenk0() ) );
    	tenk_west.setText( String.format("%.2f", overview.getTenk1() ) );
    	
    	TextView todays_gj_total = (TextView) findViewById(R.id.overview_todays_gj_total);
    	TextView todays_gj_east = (TextView) findViewById(R.id.overview_todays_gj_east);
    	TextView todays_gj_west = (TextView) findViewById(R.id.overview_todays_gj_west);
    	todays_gj_total.setText( String.format("%.0f", overview.getTodaysGJ2()) );
    	todays_gj_east.setText( String.format("%.0f", overview.getTodaysGJ0()) );
    	todays_gj_west.setText( String.format("%.0f", overview.getTodaysGJ1()) );
    	
    	TextView yesterdays_gj_total = (TextView) findViewById(R.id.overview_yesterdays_gj_total);
    	TextView yesterdays_gj_east = (TextView) findViewById(R.id.overview_yesterdays_gj_east);
    	TextView yesterdays_gj_west = (TextView) findViewById(R.id.overview_yesterdays_gj_west);
    	yesterdays_gj_total.setText( String.format("%.0f", overview.getYesterdaysGJ2() ) );
    	yesterdays_gj_east.setText( String.format("%.0f", overview.getYesterdaysGJ0() ) );
    	yesterdays_gj_west.setText( String.format("%.0f", overview.getYesterdaysGJ1() ) );
    	
    	TextView todays_perdict_gj_total = (TextView) findViewById(R.id.overview_todays_perdict_gj_total);
    	TextView todays_perdict_gj_east = (TextView) findViewById(R.id.overview_todays_perdict_gj_east);
    	TextView todays_perdict_gj_west = (TextView) findViewById(R.id.overview_todays_perdict_gj_west);
    	todays_perdict_gj_total.setText( String.format("%.0f", overview.getTodaysPerdict2() ) );
    	todays_perdict_gj_east.setText( String.format("%.0f", overview.getTodaysPerdict0() ) );
    	todays_perdict_gj_west.setText( String.format("%.0f", overview.getTodaysPerdict1() ) );
    }

}
