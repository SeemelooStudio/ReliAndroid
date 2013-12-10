package com.ui;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;

import com.model.ChatMessage;
import com.reqst.BusinessRequest;
import com.util.BaseHelper;
import com.util.ChattingAdapter;
import com.util.ConstDefine;

public class MsgUpMainActivity extends Activity {
	protected static final String TAG = "MainActivity";
	private ChattingAdapter chatHistoryAdapter;
	private List<ChatMessage> messages = new ArrayList<ChatMessage>();
	private ListView chatHistoryLv;
	private Button sendBtn;
	private EditText textEditor;
	//private ImageView sendImageIv;
	//private ImageView captureImageIv;
	//private PopupWindow menuWindow = null;
	private ProgressDialog diaLogProgress = null;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.msg_up_main);
		chatHistoryLv = (ListView) findViewById(R.id.chatting_history_lv);
		setAdapterForThis();
		sendBtn = (Button) findViewById(R.id.send_button);
		textEditor = (EditText) findViewById(R.id.text_editor);
		sendBtn.setOnClickListener(l);
		this.getActionBar().setDisplayShowHomeEnabled(true);  
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu items for use in the action bar
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.messager_activity_actions, menu);
		return super.onCreateOptionsMenu(menu);
	}
	
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		    // Respond to the action bar's Up/Home button
			case R.id.action_camera:
				Intent i = new Intent();
				i.setType("image/*");
				i.setAction(Intent.ACTION_GET_CONTENT);
				startActivityForResult(i, Activity.DEFAULT_KEYS_SHORTCUT);
			    return true;
			case R.id.action_photos:
				Intent it = new Intent("android.media.action.IMAGE_CAPTURE");
				startActivityForResult(it, Activity.DEFAULT_KEYS_DIALER);
				return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	// set adapter
	private void setAdapterForThis() {
		initMessages();
	}

	// add listView data
	private void initMessages() {
		
		diaLogProgress = BaseHelper.showProgress(MsgUpMainActivity.this,ConstDefine.I_MSG_0003,false);
        new Thread() {
            public void run() { 
                Message msgSend = new Message();
        	    try {
        	    	//get today weatherInfo
        	    	messages = BusinessRequest.getMessages("zhaoyaqi");
        	    	msgSend.what = ConstDefine.MSG_I_HANDLE_OK;
					} catch (Exception e) {
						msgSend.what = ConstDefine.MSG_I_HANDLE_Fail;
					}
            	    messagerHandler.sendMessage(msgSend);
            	}
        }.start();	
		
	}
	private Handler messagerHandler = new Handler() {               
        public void handleMessage(Message message) {
                switch (message.what) {
                case ConstDefine.MSG_I_HANDLE_OK:                                        
        		 	diaLogProgress.dismiss();
        		 	chatHistoryAdapter = new ChattingAdapter(getBaseContext(), messages);
        			chatHistoryLv.setAdapter(chatHistoryAdapter);
                    break;
                case ConstDefine.MSG_I_HANDLE_Fail:                                        
                	//close process
                	diaLogProgress.dismiss();
                	BaseHelper.showToastMsg(MsgUpMainActivity.this,ConstDefine.E_MSG_0001);
                    break;
	            }
	        }
	  };
	
	  private View.OnClickListener l = new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (v.getId() == sendBtn.getId()) {
					String str = textEditor.getText().toString();
					String sendStr;
					if (str != null
							&& (sendStr = str.trim().replaceAll("\r", "").replaceAll("\t", "").replaceAll("\n", "")
									.replaceAll("\f", "")) != "") {
						sendMessage(sendStr);
			
					}
					textEditor.setText("");
				} 
			}
			
			private void sendMessage(String sendStr) {
				ChatMessage newMessage = new ChatMessage(ChatMessage.MESSAGE_TO, sendStr);
				messages.add(newMessage);
				newMessage.setCreatedAt(new Date());
				newMessage.setSendFromUserId(3);
				newMessage.setSendToUserId(1);
				chatHistoryAdapter.notifyDataSetChanged();
				BusinessRequest.SendMessage(newMessage);
			}
	  };

}