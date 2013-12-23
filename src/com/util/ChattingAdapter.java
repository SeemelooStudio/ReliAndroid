package com.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapRegionDecoder;
import android.graphics.Rect;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.model.ChatMessage;
import com.ui.R;

public class ChattingAdapter extends BaseAdapter {
	
	static class ViewHolder {
		TextView text;
		ImageView image;
		int flag;
	}
	
	protected static final String TAG = "ChattingAdapter";
	private Context context;
	private ViewHolder holder;
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
		
		ChatMessage message = chatMessages.get(position);
		
		holder = new ViewHolder();
		holder.flag = message.getDirection();
		
		convertView = getView(message);
		if(!message.isImage() ) {
			setText(message, convertView);
		}
		else {
			setImage(message, convertView);
		}
		convertView.setTag(holder);
		
		return convertView;
	}
	
    private View getView(ChatMessage message) {
    	View convertView;
    	if(!message.isImage() ) {
			if (message.getDirection() == ChatMessage.MESSAGE_FROM) {
				convertView = LayoutInflater.from(context).inflate(R.layout.msg_up_item_from, null);
			} else {
				convertView = LayoutInflater.from(context).inflate(R.layout.msg_up_item_to, null);
			}
    	}
    	else {
    		if (message.getDirection() == ChatMessage.MESSAGE_FROM) {
				convertView = LayoutInflater.from(context).inflate(R.layout.msg_up_item_from_picture, null);
			} else {
				convertView = LayoutInflater.from(context).inflate(R.layout.msg_up_item_to_picture, null);
			}
    	}
    	return convertView;
    }
    
	private void setImage(ChatMessage message, View convertView)
	{
		try {
			holder.image = (ImageView) convertView.findViewById(R.id.chatting_content_iv);
			Bitmap bitmap;
			bitmap = BitmapFactory.decodeStream((InputStream)new URL("file://" + message.getImageUri()).getContent());
			holder.image.setImageBitmap(Bitmap.createScaledBitmap(bitmap, bitmap.getWidth()/8, bitmap.getHeight()/8, true)); 
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void setText(ChatMessage message, View convertView)
	{
		holder.text = (TextView) convertView.findViewById(R.id.chatting_content_itv);
		holder.text.setText(message.getMessageContent());
	}
}
