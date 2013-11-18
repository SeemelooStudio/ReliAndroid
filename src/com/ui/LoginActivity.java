package com.ui;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.model.UserInfo;
import com.reqst.BusinessRequest;
import com.util.BaseHelper;
import com.util.ConstDefine;

public class LoginActivity extends Activity implements android.view.View.OnClickListener{
	
	private Button   btnLogin;
	private Button   btnForgetPwd;
	private EditText txtUserId;
	private EditText txtPwd;
	
	private String strMenu ="";
	
	
	private ProgressDialog diaLogProgress= null;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        
        //按钮处理
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnForgetPwd = (Button) findViewById(R.id.btnForgetPwd);
        btnLogin.setOnClickListener((android.view.View.OnClickListener) this);
        btnForgetPwd.setOnClickListener((android.view.View.OnClickListener) this);
        
        //文本框处理
        txtUserId = (EditText) findViewById(R.id.txtUserId);
        txtPwd = (EditText) findViewById(R.id.txtPwd);
    }
    
    /*
     * 按钮处理事件
     * @see android.view.View.OnClickListener#onClick(android.view.View)
     */
    public void onClick(View v)
   	{
   		if(R.id.btnLogin == v.getId())
   		{
   			
   			//输入的用户名密码校验
   			if(checkInPutData(txtUserId.getText().toString(),txtPwd.getText().toString()) == false)
   				return;
   			
   			diaLogProgress = BaseHelper.showProgress(LoginActivity.this,ConstDefine.I_MSG_0002,false);
   	        new Thread() {
   	            public void run() { 
   	                    Message msgSend = new Message();
   	            	    try {
   	            	    	
   	            	    	UserInfo userInfo = new UserInfo();
   	            	    	userInfo.setStrUserId(txtUserId.getText().toString());
   	            	    	userInfo.setStrUserName(txtUserId.getText().toString());
   	            	    	userInfo.setStrUserPwd(txtPwd.getText().toString());
   	            	    	strMenu = BusinessRequest.getMainMenuByLoginUser(userInfo);
   	            	    	this.sleep(ConstDefine.HTTP_TIME_OUT);
   	            	    	msgSend.what = ConstDefine.MSG_I_HANDLE_OK;
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
                	BaseHelper.showToastMsg(LoginActivity.this,ConstDefine.E_MSG_0001);
                    break;
                }
        }
    };
    
    /*
     * 输入的用户名密码校验
     */
    private boolean checkInPutData(String strkUserId ,String strPwd ) {
    	
    	 //空值校验
		 if(strkUserId.trim().length() <= 0 || strkUserId.trim().length() <= 0)
		 {
			 
			//Toast.makeText(getApplicationContext(), "请先输入用户名和密码。", 0).show();   
			return true;  
		 }
		  
		 //用户名密码服务器校验
		 //TODO
		 return true;
    }
       
}