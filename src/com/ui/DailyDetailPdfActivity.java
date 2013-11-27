package com.ui;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import android.view.Window;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebView;
import android.widget.TextView;

import com.util.BaseHelper;
import com.util.ConstDefine;

public class DailyDetailPdfActivity extends Activity {

	 private WebView webPdfView; 
	 private TextView txtDailyDate;
	 private ProgressDialog diaLogProgress= null;
		
	 @Override
	 public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.daily_detail_pdf);
	        getActionBar().setDisplayHomeAsUpEnabled(true);
	        
	        Intent inten = this.getIntent();
	        Bundle mBundle = inten.getExtras();
		    if (mBundle != null ) 
		    {
		    	this.setTitle( mBundle.getString( "list_name" ) );
		    }

		    webPdfView = (WebView) findViewById(R.id.webDialyPdfView); 
		    diaLogProgress = BaseHelper.showProgress(DailyDetailPdfActivity.this,ConstDefine.I_MSG_0003,false);
   	        new Thread() {
   	            public void run() { 
   	                    Message msgSend = new Message();
   	            	    try {
   	            	    	this.sleep(ConstDefine.HTTP_TIME_OUT);
   	            	    	String pdfUrl = "http://manuals.info.apple.com/MANUALS/1000/MA1643/en_US/macbook_air-13-inch-mid-2013_quick_start.pdf"; 
   	            	    	BaseHelper.loadNetPdfFile(webPdfView,pdfUrl); 
   	            	    	msgSend.what = ConstDefine.MSG_I_HANDLE_OK;
   						} catch (Exception e) {
   							msgSend.what = ConstDefine.MSG_I_HANDLE_Fail;
   						}
   	                    handler.sendMessage(msgSend);
   	            	}
   	        }.start();   	
	 }
	 @Override
	 public boolean onOptionsItemSelected(MenuItem item) {
		 switch (item.getItemId()) {
		    // Respond to the action bar's Up/Home button
		    case android.R.id.home:
		        NavUtils.navigateUpFromSameTask(this);
		        return true;
		    }
		    return super.onOptionsItemSelected(item);
	 }

    /**
     * 
     */
    private Handler handler = new Handler() {               
        public void handleMessage(Message message) {
                switch (message.what) {
                case ConstDefine.MSG_I_HANDLE_OK:                                        
        		 	diaLogProgress.dismiss();
                    break;
                case ConstDefine.MSG_I_HANDLE_Fail:                                        
                	//close process
	                	diaLogProgress.dismiss();
	                	BaseHelper.showToastMsg(DailyDetailPdfActivity.this,ConstDefine.E_MSG_0001);
	                    break;
	            }
	        }
	  };
}



