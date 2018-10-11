package com.devworker.kms.service;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import com.devworker.kms.dto.MessageDao;
import com.devworker.kms.util.MessageClient;
import com.fasterxml.jackson.core.JsonProcessingException;

@Service
public class MessageService {
	@Value("${message.hook.url}")
	String url;

	
	public String sendMessage(MessageDao message) throws InterruptedException, ExecutionException, JsonProcessingException {
		Future<String> sendInnerMessage = sendAsyncMessage(message);
		return sendInnerMessage.get();
	}
	
	
	@Async
	public Future<String> sendAsyncMessage(MessageDao message) throws JsonProcessingException {
		MessageDao sendMessage = MessageClient.getInstance().sendMessage(url,message);
		return new AsyncResult<>(sendMessage.getMessageState());
	}

}
