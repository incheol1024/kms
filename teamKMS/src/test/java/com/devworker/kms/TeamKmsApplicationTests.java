package com.devworker.kms;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.devworker.kms.dto.message.MessageDetailDto;
import com.devworker.kms.dto.message.MessageDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.Gson;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TeamKmsApplicationTests {

	@Test
	public void makeMessageModel() throws JsonProcessingException {
		Gson mapper = new Gson();
		MessageDto dto = new MessageDto();
		dto.setText("ddd");
		dto.addAttach(new MessageDetailDto());
		System.out.println(mapper.toJson(dto));
	}

}
