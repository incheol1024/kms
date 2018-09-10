package com.devworker.kms.dto.message;

import java.util.ArrayList;
import java.util.List;

public class MessageDto {
	private String text = "";
	private List<MessageDetailDto> attachments = new ArrayList<>(20);
	private String messageState = "";

	public void setText(String text) {
		if(text == null || text.isEmpty())
			throw new RuntimeException("you can't send empty Title");
		this.text = text;
	}
	
	protected String getText() {
		return text;
	}
	
	public void addAttach(MessageDetailDto detail) {
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
