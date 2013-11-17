package com.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.util.BaseHelper;

public class WarnDetailActivity extends Activity implements android.view.View.OnClickListener{

	
	private Button   btnWarnCopy;
	private Button   btnWarnDel;
	private Button   btnWarnEdit;
	private TextView txtWarnTitle;
	private TextView txtWarnDate;
	private TextView txtWarnContent;
	
	 @Override
	 public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        requestWindowFeature(Window.FEATURE_NO_TITLE); 
	        setContentView(R.layout.warn_detail);
	        
	        //获取参数
	        Intent inten = this.getIntent();
	        Bundle mBundle = inten.getExtras();
		    if (mBundle == null )  return;
		    
	        //按钮实例化
		    btnWarnCopy = (Button) findViewById(R.id.btnWarnCopy);
	        btnWarnDel = (Button) findViewById(R.id.btnWarnDel);
	        btnWarnEdit = (Button) findViewById(R.id.btnWarnEdit);
	        btnWarnCopy.setOnClickListener((android.view.View.OnClickListener) this);
	        btnWarnDel.setOnClickListener((android.view.View.OnClickListener) this);
	        btnWarnEdit.setOnClickListener((android.view.View.OnClickListener) this);
	        
	    	txtWarnTitle  = (TextView) findViewById(R.id.txtWarnTitle);  
	    	txtWarnDate  = (TextView) findViewById(R.id.txtWarnDateTime); 
	    	txtWarnContent  = (TextView) findViewById(R.id.txtWarnContent); 
	    	
	    	//设置初始值
	    	txtWarnTitle.setText(mBundle.getString("warn_title"));
	    	txtWarnDate.setText(mBundle.getString("warn_date"));
	    	txtWarnContent.setText(mBundle.getString("warn_content"));
	 }
	 
	 /*
	 * 按钮处理事件
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	public void onClick(View v)
	{
		if(R.id.btnWarnCopy == v.getId())
		{
			BaseHelper.showDialog(this, "消息", "拷贝");
		}
		else if(R.id.btnWarnDel == v.getId())
		{
			BaseHelper.showDialog(this, "消息", "删除");
		}
		else if(R.id.btnWarnEdit == v.getId())
		{
			BaseHelper.showDialog(this, "消息", "编辑");
		}
	}
}
