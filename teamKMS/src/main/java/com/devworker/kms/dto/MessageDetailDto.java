package com.devworker.kms.dto;

import com.devworker.kms.dic.MessageType;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class MessageDetailDto {
	@NotNull
	private MessageType color = MessageType.GOOD;
	@NotBlank
	private String title = "";
	@NotBlank
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
