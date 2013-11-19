package com.ui;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.model.WarnListItem;
import com.reqst.BusinessRequest;
import com.util.BaseHelper;
import com.util.ConstDefine;

public class WarnDetailActivity extends Activity implements android.view.View.OnClickListener{

	
	private Button   btnWarnCopy;
	private Button   btnWarnDel;
	private Button   btnWarnEdit;
	private TextView txtWarnTitle;
	private TextView txtWarnDate;
	private TextView txtWarnContent;
	
	
	private ProgressDialog diaLogProgress = null;
	private String strWarnId = "";
	private WarnListItem warnInfo = null;;

	 @Override
	 public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        requestWindowFeature(Window.FEATURE_NO_TITLE); 
	        setContentView(R.layout.warn_detail);
	        
	        //获取参数
	        Intent inten = this.getIntent();
	        Bundle mBundle = inten.getExtras();
		    if (mBundle == null )  return;
		   
		    strWarnId = mBundle.getString("warn_id");
		    if(strWarnId== null || strWarnId.length() <= 0) return;
		  
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
	    	
	    	 warnInfo = new WarnListItem();
	    	 diaLogProgress = BaseHelper.showProgress(WarnDetailActivity.this,ConstDefine.I_MSG_0003,false);
		        new Thread() {
		            public void run() { 
		                    Message msgSend = new Message();
		            	    try {
		            	    	
		            	    	this.sleep(ConstDefine.HTTP_TIME_OUT);
		            	    	
		            	    	warnInfo = BusinessRequest.getWarnDetailById(strWarnId);
		            	    	
		            	    	msgSend.what = ConstDefine.MSG_I_HANDLE_OK;
							} catch (Exception e) {
								msgSend.what = ConstDefine.MSG_I_HANDLE_Fail;
							}
		                    handler.sendMessage(msgSend);
		            	}
		        }.start();
	    	
	    	//设置初始值
	    	txtWarnTitle.setText(mBundle.getString("warn_title"));
	    	txtWarnDate.setText(mBundle.getString("warn_date"));
	    	
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
	
    /**
     * http handler result
     */
    private Handler handler = new Handler() {               
        public void handleMessage(Message message) {
                switch (message.what) {
                case ConstDefine.MSG_I_HANDLE_OK:                                        
        		 	diaLogProgress.dismiss();
        		 	//set details
        		 	txtWarnContent.setText(warnInfo.getWarn_content());
                    break;
                case ConstDefine.MSG_I_HANDLE_Fail:                                        
                	//close process
                	diaLogProgress.dismiss();
                	BaseHelper.showToastMsg(WarnDetailActivity.this,ConstDefine.E_MSG_0001);
                    break;
	            }
	        }
	  };
	
}
