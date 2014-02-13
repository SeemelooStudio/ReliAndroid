package com.ui;

import com.util.ConstDefine;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Window;

public class LogonActivity extends Activity {
	

	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); 
        setContentView(R.layout.logon_page);
        
   	       
    
    }
    @Override
    public void onResume() {
    	super.onResume();
    	 new Thread() {
             @SuppressWarnings("static-access")
 			public void run() {  
             	    try {
 						this.sleep(ConstDefine.HTTP_TIME_OUT);
 					} catch (InterruptedException e) {
 						e.printStackTrace();
 					}
 	            
                     Message msgSend = new Message();
                     msgSend.what = ConstDefine.MSG_I_HANDLE_OK;
                     handler.sendMessage(msgSend);
             	}
         	}.start();
    }
    
    /**
     * 
     */
    @SuppressLint("HandlerLeak")
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