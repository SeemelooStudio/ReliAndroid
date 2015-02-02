package com.ui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.model.Overview;
import com.reqst.BusinessRequest;
import com.util.AccountHelper;
import com.util.BaseHelper;
import com.util.ConstDefine;


import android.annotation.SuppressLint;

public class OverviewActivity extends Activity 
{
     Activity _activity;

    private Overview overview = null;
    private ProgressDialog diaLogProgress = null;
	 @Override
	 public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.over_view);
        _activity=this;
	 }
	 @Override
	 public void onResume()
	 {
		 super.onResume();
	 }

    private void initOverviewData(){

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

    }

}
