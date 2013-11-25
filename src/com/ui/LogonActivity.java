package com.ui;

import com.util.BaseHelper;
import com.util.ConstDefine;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Window;

public class LogonActivity extends Activity {
	

	private ProgressDialog diaLogProgress= null;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); 
        setContentView(R.layout.logon_page);
        
    	//diaLogProgress = BaseHelper.showProgress(LogonActivity.this,ConstDefine.I_MSG_0001,false);
   	        new Thread() {
            public void run() {  
            	    try {
						this.sleep(ConstDefine.HTTP_TIME_OUT);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
                    //TODO show logpage
	            
                    Message msgSend = new Message();
                    msgSend.what = ConstDefine.MSG_I_HANDLE_OK;
                    handler.sendMessage(msgSend);
            	}
        	}.start();
    
    }
    
    /**
     * 
     */
    private Handler handler = new Handler() {               
        public void handleMessage(Message message) {
                switch (message.what) {
                case ConstDefine.MSG_I_HANDLE_OK:                                        
                	//close process
                	//diaLogProgress.dismiss(); 
                	Intent intent = new Intent(LogonActivity.this, LoginActivity.class); 
	    			startActivity(intent); 
                    break;
                }
        }
    };
       
}