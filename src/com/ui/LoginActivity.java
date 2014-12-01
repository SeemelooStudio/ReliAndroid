package com.ui;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.model.UserInfo;
import com.reqst.BusinessRequest;
import com.util.AccountHelper;
import com.util.BaseHelper;
import com.util.ConstDefine;

public class LoginActivity extends Activity implements android.view.View.OnClickListener{
	
	private Button   btnLogin;
	private Button   btnForgetPwd;
	private EditText txtUserId;
	private EditText txtPwd;
	private CheckBox cbkInnerOrOuter;
	
	private String strMenu ="";
	private Activity _activity;
	
	private ProgressDialog diaLogProgress= null;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnForgetPwd = (Button) findViewById(R.id.btnForgetPwd);
        cbkInnerOrOuter = (CheckBox) findViewById(R.id.cbkInnerOrOuter);
        cbkInnerOrOuter.setChecked(true);
        btnLogin.setOnClickListener((android.view.View.OnClickListener) this);
        btnForgetPwd.setOnClickListener((android.view.View.OnClickListener) this);
        
        txtUserId = (EditText) findViewById(R.id.txtUserId);
        txtPwd = (EditText) findViewById(R.id.txtPwd);
        _activity = this;
        txtUserId.setText(AccountHelper.getUserName(_activity));
        txtPwd.setText(AccountHelper.getSavedPassword(_activity));
    }
    
    public void onClick(View v)
   	{
   		if(R.id.btnLogin == v.getId())
   		{
   			if(checkInPutData(txtUserId.getText().toString(),txtPwd.getText().toString()) == false)
   				return;
   			diaLogProgress = BaseHelper.showProgress(LoginActivity.this,getString(R.string.log_in_message),false);
   	        new Thread() {
   	            public void run() { 
   	                    Message msgSend = new Message();
   	                    UserInfo userInfo = new UserInfo();
   	            	    try {
   	            	    	userInfo.setUserId(txtUserId.getText().toString());
   	            	    	userInfo.setUserName(txtUserId.getText().toString());
   	            	    	userInfo.setUserPwd(txtPwd.getText().toString());
   	            	    	AccountHelper.setBaseUrl(cbkInnerOrOuter.isChecked(), _activity);
   	            	    	Boolean isAuthenticated = BusinessRequest.Authentication(userInfo, _activity);
   	            	    	if(isAuthenticated) {
	   	            	    	AccountHelper.setUserName(userInfo.getUserName(), _activity);
	   	            	    	AccountHelper.setSavedPassword(userInfo.getUserPwd(), _activity);
	   	            	    	strMenu = BusinessRequest.getMainMenuByLoginUser(userInfo, _activity);
	   	            	    	msgSend.what = ConstDefine.MSG_I_HANDLE_OK;
   	            	    	}
   	            	    	else {
   	            	    		msgSend.what = ConstDefine.MSG_I_HANDLE_Fail;
   	            	    	}
   	            	    } catch (Exception e) {
   							msgSend.what = ConstDefine.MSG_I_HANDLE_Fail;
   						}
   	                    handler.sendMessage(msgSend);
   	            	}
   	        	}.start();
   		}
   		else if(R.id.btnForgetPwd == v.getId())
   		{
   			//setContentView(R.layout.getpwd);
   			Intent intent = new Intent(LoginActivity.this, MainPageActivity.class); 
			startActivity(intent); 
   		}
   	}
    
    /**
     * 
     */
    private Handler handler = new Handler() {               
        public void handleMessage(Message message) {
                switch (message.what) {
                case ConstDefine.MSG_I_HANDLE_OK:                                        
                	//close process
        			Intent intent = new Intent(LoginActivity.this, MainPageActivity.class);
        			Bundle mBundle = new Bundle();
            		mBundle.putString("lstMemu",strMenu);
            		intent.putExtras(mBundle);
        			startActivity(intent); 
        		 	diaLogProgress.dismiss();
                    break;
                case ConstDefine.MSG_I_HANDLE_Fail:                                        
                	//close process
                	diaLogProgress.dismiss();
                	BaseHelper.showToastMsg(LoginActivity.this,getString(R.string.connect_fail_message));
                    break;
                }
        }
    };
    
    private boolean checkInPutData(String strkUserId ,String strPwd ) {
    	
		 if(strkUserId.trim().length() <= 0 || strkUserId.trim().length() <= 0)
		 {
			return false;  
		 }
		 return true;
    }
       
}