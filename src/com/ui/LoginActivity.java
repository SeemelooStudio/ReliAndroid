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
        
        //��ť����
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnForgetPwd = (Button) findViewById(R.id.btnForgetPwd);
        btnLogin.setOnClickListener((android.view.View.OnClickListener) this);
        btnForgetPwd.setOnClickListener((android.view.View.OnClickListener) this);
        
        //�ı�����
        txtUserId = (EditText) findViewById(R.id.txtUserId);
        txtPwd = (EditText) findViewById(R.id.txtPwd);
    }
    
    /*
     * ��ť�����¼�
     * @see android.view.View.OnClickListener#onClick(android.view.View)
     */
    public void onClick(View v)
   	{
   		if(R.id.btnLogin == v.getId())
   		{
   			
   			//������û�������У��
   			if(checkInPutData(txtUserId.getText().toString(),txtPwd.getText().toString()) == false)
   				return;
   			
			//���ӷ�������������У��
			//String  strMsg = "�û�����"+ txtUserId.getText()+"\r\n"; 
			//strMsg  += "�� ��:" + txtPwd.getText(); 
			//BaseHelper.showDialog(this, "��Ϣ", strMsg);
				
			//У��ɹ���ת������
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
     * ������û�������У��
     */
    private boolean checkInPutData(String strkUserId ,String strPwd ) {
    	
    	 //��ֵУ��
		 if(strkUserId.trim().length() <= 0 || strkUserId.trim().length() <= 0)
		 {
			 
			//Toast.makeText(getApplicationContext(), "���������û��������롣", 0).show();   
			return true;  
		 }
		  
		 //�û������������У��
		 //TODO
		 return true;
    }
       
}