package com.devworker.kms.controller;

import com.devworker.kms.dto.MessageDto;
import com.devworker.kms.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class MessageController {
	@Autowired
	MessageService service;
	
	@PostMapping("/sendmessage")
	public String sendMessage(@Valid  @RequestBody  MessageDto message) {
		return service.sendMessage(message);
	}
	
}
