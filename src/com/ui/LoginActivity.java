package com.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends Activity implements android.view.View.OnClickListener{
	
	private Button   btnLogin;
	private Button   btnForgetPwd;
	private EditText txtUserId;
	private EditText txtPwd;
	
	
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
   			
			//连接服务器进行密码校验
			//String  strMsg = "用户名："+ txtUserId.getText()+"\r\n"; 
			//strMsg  += "密 码:" + txtPwd.getText(); 
			//BaseHelper.showDialog(this, "消息", strMsg);
				
			//校验成功跳转主画面
			Intent intent = new Intent(LoginActivity.this, MainPageActivity.class); 
			startActivity(intent); 

   		}
   		else if(R.id.btnForgetPwd == v.getId())
   		{
   			//setContentView(R.layout.getpwd);
   			Intent intent = new Intent(LoginActivity.this, MainPageActivity.class); 
			startActivity(intent); 
   		}
   		
   	}
    
    
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