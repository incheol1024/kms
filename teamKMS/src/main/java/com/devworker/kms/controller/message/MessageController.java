package com.devworker.kms.controller.message;

import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devworker.kms.dto.message.MessageDto;
import com.devworker.kms.service.message.MessageService;
import com.fasterxml.jackson.core.JsonProcessingException;

@RestController
public class MessageController {
	@Autowired
	MessageService service;
	
	@PostMapping("/sendmessage")
	public String sendMessage(MessageDto message) {
		try {
			return service.sendMessage(message);
		} catch (JsonProcessingException e) {
			return "Json Process Has Error";
		} catch (InterruptedException | ExecutionException e) {
			return "send Message Fail";
		}
	}
	
}
