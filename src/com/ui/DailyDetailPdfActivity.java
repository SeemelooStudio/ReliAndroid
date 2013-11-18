package com.ui;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
	        requestWindowFeature(Window.FEATURE_NO_TITLE); 
	        setContentView(R.layout.daily_detail_pdf);
	        
	        Intent inten = this.getIntent();
	        Bundle mBundle = inten.getExtras();
		    if (mBundle != null ) 
		    {
		    	txtDailyDate  = (TextView) findViewById(R.id.txtDailyDate);  
		    	txtDailyDate.setText(mBundle.getString("list_name"));
		    }

		    webPdfView = (WebView) findViewById(R.id.webDialyPdfView); 
		    diaLogProgress = BaseHelper.showProgress(DailyDetailPdfActivity.this,ConstDefine.I_MSG_0003,false);
   	        new Thread() {
   	            public void run() { 
   	                    Message msgSend = new Message();
   	            	    try {
   	            	    	this.sleep(ConstDefine.HTTP_TIME_OUT);
   	            	    	//String pdfUrl = "http://www8.cao.go.jp/okinawa/8/2012/0409-1-1.pdf"; 
   	            	    	String pdfUrl = "http://localhost:8090/web1/zhunzheng.pdf";
   	            	    	loadDailPdf(webPdfView,pdfUrl); 
   	            	    	msgSend.what = ConstDefine.MSG_I_HANDLE_OK;
   						} catch (Exception e) {
   							msgSend.what = ConstDefine.MSG_I_HANDLE_Fail;
   						}
   	                    handler.sendMessage(msgSend);
   	            	}
   	        }.start();   	
	 }

	 
	 /**
	  * 
	  * @param pdfView
	  * @param strUrl
	 * @throws Exception 
	  */
	 private void loadDailPdf(WebView pdfView,String strUrl) throws Exception{
		 try {
			 pdfView.getSettings().setJavaScriptEnabled(true); 
			 pdfView.getSettings().setSupportZoom(true); 
			 pdfView.getSettings().setDomStorageEnabled(true); 
			 pdfView.getSettings().setAllowFileAccess(true); 
			 pdfView.getSettings().setPluginsEnabled(true); 
			 pdfView.getSettings().setUseWideViewPort(true); 
			 pdfView.getSettings().setBuiltInZoomControls(true); 
			 pdfView.requestFocus(); 
			 pdfView.getSettings().setLoadWithOverviewMode(true); 
			 pdfView.getSettings().setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN); 
			 pdfView.loadUrl("http://docs.google.com/gview?embedded=true&url=" +strUrl); 
		 } 
		 catch (Exception ex)
		 {
			 throw ex;
		 }
	 }
	 
	 
	 /**
	  * 
	  * @param pdfView
	  * @param strUrl
	 * @throws Exception 
	  */
	 private void loadPdfFile(WebView pdfView,String strUrl) throws Exception{
		 
		 try {
			 pdfView.getSettings().setJavaScriptEnabled(true);
			 pdfView.getSettings().setSupportZoom(true);
			 pdfView.getSettings().setDomStorageEnabled(true);
			 pdfView.getSettings().setAllowFileAccess(true);
			 pdfView.getSettings().setPluginsEnabled(true);
			 pdfView.getSettings().setUseWideViewPort(true);
			 pdfView.getSettings().setBuiltInZoomControls(true);
			 pdfView.requestFocus();
			 pdfView.getSettings().setLoadWithOverviewMode(true);
			 pdfView.getSettings().setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
			 String data="<iframesrc='http://docs.google.com/gview?embedded=true&url="+strUrl+"'"+"width='100%'height='100%'style='border:none;'></iframe>";
			 pdfView.loadData(data,"text/html","UTF-8");
		 } 
		 catch (Exception ex)
		 {
			 throw ex;
		 }
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



