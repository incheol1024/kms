package com.devworker.kms.dto;

import java.util.ArrayList;
import java.util.List;

public class MessageDao {
	private String text = "";
	private List<MessageDetailDao> attachments = new ArrayList<>(20);
	private String messageState = "";

	public void setText(String text) {
		if(text == null || text.isEmpty())
			throw new RuntimeException("you can't send empty Title");
		this.text = text;
	}
	
	protected String getText() {
		return text;
	}
	
	public void addAttach(MessageDetailDao detail) {
		if(attachments.size() > 20)
			throw new RuntimeException("you can't add Sub Message over 20");
		attachments.add(detail);
	}
	
	public void clearAttach() {
		attachments.clear();
	}

	public void setMessageState(String messageState) {
		this.messageState = messageState;
	}

	public String getMessageState() {
		return messageState;
	}	
}
