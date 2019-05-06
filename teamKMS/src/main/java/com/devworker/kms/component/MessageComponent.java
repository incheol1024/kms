package com.devworker.kms.component;

import com.devworker.kms.dto.MessageDto;
import com.devworker.kms.util.MessageClient;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.Future;

@Component
public class MessageComponent {
	@Value("${message.hook.url}")
	String url;

	
	public String sendMessage(MessageDto message) {
		try {
			Future<String> sendInnerMessage = sendAsyncMessage(message);
			return sendInnerMessage.get();
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
	}
	
	
	@Async
	public Future<String> sendAsyncMessage(MessageDto message) throws JsonProcessingException {
		MessageDto sendMessage = MessageClient.getInstance().sendMessage(url,message);
		return new AsyncResult<>(sendMessage.getMessageState());
	}

}
