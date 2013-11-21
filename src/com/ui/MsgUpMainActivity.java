package com.ui;

import java.util.ArrayList;
import java.util.List;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
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

import com.model.ChatMessage;
import com.util.ChattingAdapter;

public class MsgUpMainActivity extends Activity {
	protected static final String TAG = "MainActivity";
	private ChattingAdapter chatHistoryAdapter;
	private List<ChatMessage> messages = new ArrayList<ChatMessage>();

	private ListView chatHistoryLv;
	private Button sendBtn;
	private EditText textEditor;
	private ImageView sendImageIv;
	private ImageView captureImageIv;
	private View recording;
	private PopupWindow menuWindow = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.msg_up_main);
		
		this.getActionBar().setDisplayShowHomeEnabled(false);  
        this.getActionBar().setDisplayShowTitleEnabled(false);  
        this.getActionBar().setDisplayShowCustomEnabled(true);  
		LayoutInflater mInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);  
	    View mTitleView = mInflater.inflate(R.layout.msg_up_title_bar, null);  
	    getActionBar().setCustomView(mTitleView,new ActionBar.LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.FILL_PARENT));  

		chatHistoryLv = (ListView) findViewById(R.id.chatting_history_lv);
		setAdapterForThis();
		sendBtn = (Button) findViewById(R.id.send_button);
		textEditor = (EditText) findViewById(R.id.text_editor);
		sendImageIv = (ImageView) findViewById(R.id.send_image);
		captureImageIv = (ImageView) findViewById(R.id.capture_image);
		sendBtn.setOnClickListener(l);
		sendImageIv.setOnClickListener(l);
		captureImageIv.setOnClickListener(l);
		recording = findViewById(R.id.recording);
		recording.setOnTouchListener(new View.OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				int action = event.getAction();

				switch (action) {
				case MotionEvent.ACTION_UP:
					v.setBackgroundResource(R.drawable.hold_to_talk_normal);
					if (menuWindow != null)
						menuWindow.dismiss();
					Log.d(TAG, "---onTouchEvent action:ACTION_UP");
					break;
				case MotionEvent.ACTION_DOWN:
					v.setBackgroundResource(R.drawable.hold_to_talk_pressed);
					ViewGroup root = (ViewGroup) findViewById(R.id.chat_root);
					View view = LayoutInflater.from(MsgUpMainActivity.this).inflate(R.layout.msg_up_audio_recorder_ring, null);
					menuWindow = new PopupWindow(view, 180, 180);
					// @+id/recorder_ring
					view.findViewById(R.id.recorder_ring).setVisibility(View.VISIBLE);
					view.setBackgroundResource(R.drawable.pls_talk);
					menuWindow.showAtLocation(root, Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL, 0, 0);
					Log.d(TAG, "---onTouchEvent action:ACTION_DOWN");
					// AudioRecorder recorder=new AudioRecorder();

					break;

				}

				return true;
			}
		});

	}

	// set adapter
	private void setAdapterForThis() {
		initMessages();
		chatHistoryAdapter = new ChattingAdapter(this, messages);
		chatHistoryLv.setAdapter(chatHistoryAdapter);
	}

	// add listView data
	private void initMessages() {
		messages.add(new ChatMessage(ChatMessage.MESSAGE_FROM, "hello"));
		messages.add(new ChatMessage(ChatMessage.MESSAGE_TO, "hello"));
		messages.add(new ChatMessage(ChatMessage.MESSAGE_FROM, "你好吗？"));
		messages.add(new ChatMessage(ChatMessage.MESSAGE_TO, "非常好!"));
		messages.add(new ChatMessage(ChatMessage.MESSAGE_FROM, "欢迎光临我的博客，http://hi.csdn.net/lyfi01"));
		messages.add(new ChatMessage(ChatMessage.MESSAGE_TO, "恩，好的，谢谢"));
	}

	/**
	 * 按键时间监听
	 */
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

			} else if (v.getId() == sendImageIv.getId()) {
				Intent i = new Intent();
				i.setType("image/*");
				i.setAction(Intent.ACTION_GET_CONTENT);
				startActivityForResult(i, Activity.DEFAULT_KEYS_SHORTCUT);
			} else if (v.getId() == captureImageIv.getId()) {
				Intent it = new Intent("android.media.action.IMAGE_CAPTURE");
				startActivityForResult(it, Activity.DEFAULT_KEYS_DIALER);
			}
		}

		private void sendMessage(String sendStr) {
			messages.add(new ChatMessage(ChatMessage.MESSAGE_TO, sendStr));
			chatHistoryAdapter.notifyDataSetChanged();
		}

	};

}