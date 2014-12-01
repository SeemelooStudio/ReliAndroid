package com.ui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.model.WarnListItem;
import com.reqst.BusinessRequest;
import com.util.BaseHelper;
import com.util.ConstDefine;

public class WarnDetailActivity extends Activity{

	private TextView txtWarnTitle;
	private TextView txtWarnDate;
	private TextView txtWarnContent;
	
	private ProgressDialog diaLogProgress = null;
	private String warnId = "";
	private String warnTitle = "";
	private String warnDate = "";
	private String warnContent = "";
	
	private WarnListItem warnInfo = null;;
	private Activity _activity ;
	 @Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.warn_detail);
		_activity = this;
		Intent inten = this.getIntent();
		Bundle mBundle = inten.getExtras();
		if (mBundle == null )  return;
		   
			warnId = mBundle.getString("warn_id");
			warnTitle = mBundle.getString("warn_title");
			warnDate = mBundle.getString("warn_date");
			warnContent = mBundle.getString("warn_content");
    		
			if(warnId== null || warnId.length() <= 0) return;
		  
	    	txtWarnDate  = (TextView) findViewById(R.id.txtWarnDateTime); 
	    	txtWarnDate.setText(warnDate);
	    	
	    	txtWarnContent = (TextView) findViewById(R.id.txtWarnContent);
	    	txtWarnContent.setText(warnContent);
	    	setTxtWarnContent(txtWarnContent); 
	    	
	    	setWarnInfo(new WarnListItem());
	    	diaLogProgress = BaseHelper.showProgress(WarnDetailActivity.this,ConstDefine.I_MSG_0003,false);
	    	new Thread() {
	    		public void run() { 
	                Message msgSend = new Message();
	        	    try {
	        	    	
	        	    	setWarnInfo(BusinessRequest.getWarnDetailById(warnId, _activity));
	        	    	
	        	    	msgSend.what = ConstDefine.MSG_I_HANDLE_OK;
					} catch (Exception e) {
						msgSend.what = ConstDefine.MSG_I_HANDLE_Fail;
					}
	                handler.sendMessage(msgSend);
	    		}
	    	}.start();
		       
	    	this.setTitle(mBundle.getString("warn_title"));
	    	getActionBar().setDisplayHomeAsUpEnabled(true);
	    }

		public TextView getTxtWarnTitle() {
			return txtWarnTitle;
		}

		public void setTxtWarnTitle(TextView txtWarnTitle) {
			this.txtWarnTitle = txtWarnTitle;
		}

		public TextView getTxtWarnContent() {
			return txtWarnContent;
		}

		public void setTxtWarnContent(TextView txtWarnContent) {
			this.txtWarnContent = txtWarnContent;
		}

		public WarnListItem getWarnInfo() {
			return warnInfo;
		}

		public void setWarnInfo(WarnListItem warnInfo) {
			this.warnInfo = warnInfo;
		}

		@SuppressLint("HandlerLeak")
		private Handler handler = new Handler() {               
			public void handleMessage(Message message) {
                switch (message.what) {
                case ConstDefine.MSG_I_HANDLE_OK:                                        
        		 	diaLogProgress.dismiss();
        		 	//set details
        		 	//txtWarnContent.setText(warnInfo.getWarningContent());
                    break;
                case ConstDefine.MSG_I_HANDLE_Fail:                                        
                	//close process
                	diaLogProgress.dismiss();
                	BaseHelper.showToastMsg(WarnDetailActivity.this,ConstDefine.E_MSG_0001);
                    break;
	            }
	        }
		};
		
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
}
