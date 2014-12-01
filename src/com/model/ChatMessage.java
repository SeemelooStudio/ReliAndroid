package com.model;

import java.util.Date;

public class ChatMessage {

	public static final int MESSAGE_FROM = 0;
	public static final int MESSAGE_TO = 1;

	private int direction;
	private String messageContent;
	private int messageId;
	private Date createdAt;
	private String imageUri;
	private String sendFromUserName;
	private String sendToUserName;
	
	public ChatMessage(int direction, String messageContent) {
		super();
		this.direction = direction;
		this.messageContent = messageContent;
	}
	
	public ChatMessage()
	{
	
	}

	public boolean isImage() {
		return imageUri != null && imageUri != "" && imageUri != "null";
	}
	
	public int getDirection() {
		return direction;
	}

	public void setDirection(int direction) {
		this.direction = direction;
	}

	public void setMessageContent(String messageContent) {
		this.messageContent = messageContent;
	}

	public String getMessageContent() {
		return messageContent;
	}

	public int getMessageId() {
		return messageId;
	}

	public void setMessageId(int messageId) {
		this.messageId = messageId;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public String getImageUri() {
		return imageUri;
	}

	public void setImageUri(String imageUri) {
		this.imageUri = imageUri;
	}

	public String getImageUrl() {
		return imageUri;
	}

	public void setImageUrl(String imageUri) {
		this.imageUri = imageUri;
	}
	
	public String getSendFromUserName() {
		return sendFromUserName;
	}

	public void setSendFromUserName(String sendFromUserName) {
		this.sendFromUserName = sendFromUserName;
	}

	public String getSendToUserName() {
		return sendToUserName;
	}

	public void setSendToUserName(String sendToUserName) {
		this.sendToUserName = sendToUserName;
	}

}
