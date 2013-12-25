package com.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;


import com.model.DownloadPDFTask;
import com.util.BaseHelper;
import com.util.ConstDefine;

public class DailyDetailPdfActivity extends Activity {


	 private String strDailyReportName = "";
	
	 @Override
	 public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.daily_detail_pdf);
	        getActionBar().setDisplayHomeAsUpEnabled(true);
	        
	        Intent inten = this.getIntent();
	        Bundle mBundle = inten.getExtras();
		    if (mBundle != null ) 
		    {
		    	strDailyReportName = mBundle.getString("list_name");
		    	this.setTitle( strDailyReportName );
		    }
		    
		    //webPdfView = (WebView) findViewById(R.id.webDialyPdfView); 
		    String pdfUrl = ConstDefine.WEB_SERVICE_URL + ConstDefine.S_DAILY_REPORT_ROOT + strDailyReportName;
		    try {
		    	new DownloadPDFTask(this).execute(pdfUrl);
		    	//BaseHelper.loadNetPdfFile(webPdfView,pdfUrl); 
		    }
		    catch (Exception ex) {
		    	BaseHelper.showToastMsg(DailyDetailPdfActivity.this,ConstDefine.E_MSG_0001);
		    }
	 }
	 @Override
	 public boolean onOptionsItemSelected(MenuItem item) {
		 switch (item.getItemId()) {
		    // Respond to the action bar's Up/Home button
		    case android.R.id.home:
		        this.finish();
		        return true;
		    }
		    return super.onOptionsItemSelected(item);
	 }

}



