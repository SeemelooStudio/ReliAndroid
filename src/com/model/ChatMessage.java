package com.model;

public class ChatMessage {

	public static final int MESSAGE_FROM = 0;
	public static final int MESSAGE_TO = 1;

	private int intDirection;
	private String strMessageContent;

	public ChatMessage(int intDirection, String strMessageContent) {
		super();
		this.intDirection = intDirection;
		this.strMessageContent = strMessageContent;
	}
	
	public ChatMessage()
	{
		
	}

	public int getIntDirection() {
		return intDirection;
	}

	public void setIntDirection(int intDirection) {
		this.intDirection = intDirection;
	}

	public void setStrMessageContent(String strMessageContent) {
		this.strMessageContent = strMessageContent;
	}

	public CharSequence getStrMessageContent() {
		return strMessageContent;
	}

}
