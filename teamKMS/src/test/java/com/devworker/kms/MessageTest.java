package com.devworker.kms;

import com.devworker.kms.dto.MessageDetailDto;
import com.devworker.kms.dto.MessageDto;
import com.devworker.kms.component.MessageComponent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.ExecutionException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MessageTest {
	@Autowired
	MessageComponent messageService;
	
	@Test
	public void sendMessageTest() throws JsonProcessingException, InterruptedException, ExecutionException {
		ObjectMapper mapper = new ObjectMapper();
		MessageDto dto = new MessageDto();
		dto.setText("ddd");
		dto.addAttach(new MessageDetailDto());
		System.out.println(mapper.writeValueAsString(dto));
		String sendMessage = messageService.sendMessage(dto);
		System.out.println(sendMessage);
	}
}
