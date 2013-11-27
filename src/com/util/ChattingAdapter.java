package com.util;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.model.ChatMessage;
import com.ui.R;

public class ChattingAdapter extends BaseAdapter {
	protected static final String TAG = "ChattingAdapter";
	private Context context;

	private List<ChatMessage> chatMessages;

	public ChattingAdapter(Context context, List<ChatMessage> messages) {
		super();
		this.context = context;
		this.chatMessages = messages;

	}

	@Override
	public int getCount() {
		return chatMessages.size();
	}

	@Override
	public Object getItem(int position) {
		return chatMessages.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		ChatMessage message = chatMessages.get(position);
		if (convertView == null || (holder = (ViewHolder) convertView.getTag()).flag != message.getIntDirection()) {

			holder = new ViewHolder();
			if (message.getIntDirection() == ChatMessage.MESSAGE_FROM) {
				holder.flag = ChatMessage.MESSAGE_FROM;

				convertView = LayoutInflater.from(context).inflate(R.layout.msg_up_item_from, null);
			} else {
				holder.flag = ChatMessage.MESSAGE_TO;
				convertView = LayoutInflater.from(context).inflate(R.layout.msg_up_item_to, null);
			}

			holder.text = (TextView) convertView.findViewById(R.id.chatting_content_itv);
			convertView.setTag(holder);
		}
		holder.text.setText(message.getStrMessageContent());

		return convertView;
	}
    
	
	
	//�Ż�listview��Adapter
	static class ViewHolder {
		TextView text;
		int flag;
	}

}
