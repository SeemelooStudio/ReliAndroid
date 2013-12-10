package com.model;

import java.util.Date;

public class ChatMessage {

	public static final int MESSAGE_FROM = 0;
	public static final int MESSAGE_TO = 1;

	private int direction;
	private String messageContent;
	private int messageId;
	private Date createdAt;
	private int sendFromUserId;
	private int sendToUserId;
	
	public ChatMessage(int direction, String messageContent) {
		super();
		this.direction = direction;
		this.messageContent = messageContent;
	}
	
	public ChatMessage()
	{
		
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

	public int getSendFromUserId() {
		return sendFromUserId;
	}

	public void setSendFromUserId(int sendFromUserId) {
		this.sendFromUserId = sendFromUserId;
	}

	public int getSendToUserId() {
		return sendToUserId;
	}

	public void setSendToUserId(int sendToUserId) {
		this.sendToUserId = sendToUserId;
	}

}
