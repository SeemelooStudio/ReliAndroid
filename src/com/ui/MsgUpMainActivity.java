package com.ui;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import java.util.List;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;

import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;


import com.model.ChatMessage;
import com.reqst.BusinessRequest;
import com.util.BaseHelper;
import com.util.ChattingAdapter;
import com.util.ConstDefine;

public class MsgUpMainActivity extends Activity {
	protected static final String TAG = "MainActivity";
	private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
	private static final int SELECT_PHOTO_ACTIVITY_REQUEST_CODE = 200;
	
	private ChattingAdapter chatHistoryAdapter;
	private List<ChatMessage> messages = new ArrayList<ChatMessage>();
	private ListView chatHistoryLv;
	private Button sendBtn;
	private EditText textEditor;
	
	private ProgressDialog diaLogProgress = null;
	
	private String _currentPhotoPath;
	
	@SuppressLint("SimpleDateFormat")
	private File createImageFile() throws IOException{
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        
        String state = Environment.getExternalStorageState();
        Boolean isSDPresent = Environment.MEDIA_MOUNTED.equals(state);
        File storageDir;
        
        if(isSDPresent) {
        	storageDir = Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_PICTURES);
        	if(!storageDir.mkdirs()) {
        		Log.e("..", "Directory not created");
        	}
        }
        else {
        	storageDir = getApplicationContext().getFilesDir();
        }
        
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
            );
        _currentPhotoPath = image.getAbsolutePath();
        return image;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.msg_up_main);
		chatHistoryLv = (ListView) findViewById(R.id.chatting_history_lv);
		setAdapterForThis();
		sendBtn = (Button) findViewById(R.id.send_button);
		textEditor = (EditText) findViewById(R.id.text_editor);
		sendBtn.setOnClickListener(l);
		getActionBar().setDisplayHomeAsUpEnabled(true); 
		setTitle(getString(R.string.main_item_four_name));
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
				Intent it = new Intent( MediaStore.ACTION_IMAGE_CAPTURE);
				File image = null;
				try {
					image = createImageFile();
				}catch(Exception ex) {
					ex.printStackTrace();
				}
				it.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(image));  
				startActivityForResult(it, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
				return true;
				
			case R.id.action_photos:
				Intent i = new Intent();
				i.setType("image/*");
				i.setAction(Intent.ACTION_GET_CONTENT);
				startActivityForResult(i, SELECT_PHOTO_ACTIVITY_REQUEST_CODE );
			    return true;
			    
			case android.R.id.home:
				this.finish();
				return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
	        if (resultCode == RESULT_OK) {
	        	if (data != null) {
		        	String imagePath = getRealPathFromURI( this.getApplicationContext(), data.getData());
		        	sendImage(imagePath);
	        	}
	        	else {
	        		sendImage(_currentPhotoPath);
	        	}
	        } else if (resultCode == RESULT_CANCELED) {
	        	
	        } else {
	        	// maybe the file got saved but...
	        	sendImage(_currentPhotoPath);
	        }
	    }
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
	@SuppressLint("HandlerLeak")
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
					String sendStr = str.trim().replaceAll("\r", "").replaceAll("\t", "").replaceAll("\n", "")
							.replaceAll("\f", "");
					if (sendStr != "") {
						sendMessage(sendStr);
					}
					textEditor.setText("");
				} 
			}
	  };
	  
	  private void sendMessage(String sendStr) {
			ChatMessage newMessage = new ChatMessage(ChatMessage.MESSAGE_TO, sendStr);
			messages.add(newMessage);
			newMessage.setCreatedAt(new Date());
			newMessage.setSendFromUserId(3);
			newMessage.setSendToUserId(1);
			chatHistoryAdapter.notifyDataSetChanged();
			BusinessRequest.SendMessage(newMessage);
		}
	  
	  private void sendImage(String imagePath)
	  {
		  	ChatMessage newMessage = new ChatMessage(ChatMessage.MESSAGE_TO, "");
		  	newMessage.setImageUri(imagePath);
			messages.add(newMessage);
			newMessage.setCreatedAt(new Date());
			newMessage.setSendFromUserId(3);
			newMessage.setSendToUserId(1);
			chatHistoryAdapter.notifyDataSetChanged();
			BusinessRequest.SendImage(newMessage);
	  }
	  
	  public String getRealPathFromURI(Context context, Uri contentUri) {
		  Cursor cursor = null;
		  try { 
		    String[] proj = { MediaStore.Images.Media.DATA };
		    cursor = context.getContentResolver().query(contentUri,  proj, null, null, null);
		    int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
		    cursor.moveToFirst();
		    return cursor.getString(column_index);
		  } finally {
		    if (cursor != null) {
		      cursor.close();
		    }
		  }
		}
}