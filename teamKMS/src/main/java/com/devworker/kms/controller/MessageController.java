package com.devworker.kms.controller;

import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devworker.kms.dto.MessageDto;
import com.devworker.kms.service.MessageService;
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
			Thread.currentThread().interrupt();
			return "send Message Fail";
		}
	}
	
}
