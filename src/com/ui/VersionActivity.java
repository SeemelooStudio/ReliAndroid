package com.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONException;

import com.model.DownloadPDFTask;
import com.model.GenericListItem;
import com.reqst.BusinessRequest;
import com.ui.R.string;
import com.util.BaseHelper;
import com.util.ConstDefine;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.SimpleAdapter;

public class VersionActivity extends Activity {

	Context context;
	Activity activity;
	String latestVersion;
	Button download_latest;
	private ProgressDialog diaLogProgress= null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_version);
		download_latest = (Button)findViewById(R.id.download_latest);
		context = this.getApplication().getBaseContext();
		activity = this;
		checkVersion();
		
        getActionBar().setDisplayHomeAsUpEnabled(true);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.version, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		 switch(item.getItemId()) {
		 case android.R.id.home:
		     this.finish();
		     return true;
		 default :
			 return super.onOptionsItemSelected(item);
		 }
	}

	private void checkVersion()
	{
	    diaLogProgress = BaseHelper.showProgress(VersionActivity.this,ConstDefine.I_MSG_0003,false);
	        new Thread() {
	            public void run() { 
	                    Message msgSend = new Message();
	            	    try {
	            	    	latestVersion = BusinessRequest.getLatestVersion(activity);
	            	    	latestVersion = latestVersion.replaceAll("\"", "");
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

    				try {
    					PackageInfo pInfo;
            		 	pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
    					String version = pInfo.versionName;
    					if( version.toLowerCase().equals(latestVersion.toLowerCase())) {
    						download_latest.setText(string.already_latest);
    						download_latest.setClickable(false);

    						download_latest.setEnabled(false);
    					}
    					else {
    						download_latest.setOnClickListener(new OnClickListener() {
    							@Override
    							public void onClick(View v) {
    								String versionUrl =  ConstDefine.S_DOWNLOAD_LATEST.replace("{VersionName}", latestVersion);
    							    try {
    							    	new DownloadPDFTask(activity).execute(versionUrl);
    							    }
    							    catch (Exception ex) {
    							    	BaseHelper.showToastMsg(VersionActivity.this,ConstDefine.E_MSG_0001);
    							    }
    								
    							}
    						});
    					}
    				} catch (NameNotFoundException e) {
    			    	BaseHelper.showToastMsg(VersionActivity.this,ConstDefine.E_MSG_0002);
    				}
    				
					
                    break;
                case ConstDefine.MSG_I_HANDLE_Fail:                                        
                	//close process
                	diaLogProgress.dismiss();
                	BaseHelper.showToastMsg(VersionActivity.this,ConstDefine.E_MSG_0001);
                    break;
	            }
	        }
	  };
}
