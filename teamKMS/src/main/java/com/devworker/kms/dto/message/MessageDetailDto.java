package com.devworker.kms.dto.message;

import com.devworker.kms.dic.MessageType;

public class MessageDetailDto {
	private MessageType color = MessageType.GOOD;
	private String title = "";
	private String text =  "";
	
	public void setType(MessageType type) {
		this.color = type;
	}
	
	 public void setTitle(String title) {
		 if(title == null || title.isEmpty())
				throw new RuntimeException("you can't send empty Sub Title");
		this.title = title;
	}
	public void setText(String text) {
		if(text == null || text.isEmpty())
			throw new RuntimeException("you can't send empty Sub Text");
		this.text = text;
	}
	 
	 
}
