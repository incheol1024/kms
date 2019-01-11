package com.devworker.kms;

import com.devworker.kms.dic.UserType;
import com.devworker.kms.dto.GroupDto;
import com.devworker.kms.dto.MessageDetailDto;
import com.devworker.kms.dto.MessageDto;
import com.devworker.kms.dto.UserDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

public class JsonModelTest {
	ObjectMapper mapper = new ObjectMapper();
	
	@Test
	public void makeMessageModel() throws JsonProcessingException {
		MessageDto dto = new MessageDto();
		dto.setText("ddd");
		dto.addAttach(new MessageDetailDto());
		System.out.println(mapper.writeValueAsString(dto));
	}
	
	@Test
	public void makeUserModel() throws JsonProcessingException {
		UserDto dto = new UserDto();
		dto.setName("");
		dto.setType(UserType.USER.name());
		dto.setPassword("");
		dto.setGroupId(0);
		dto.setId("0");
		dto.setGroupName("");
		System.out.println(mapper.writeValueAsString(dto));
	}
	
	@Test
	public void makeGroupModel() throws JsonProcessingException {
		GroupDto dto = new GroupDto();
		dto.setId(0);
		dto.setParentid(0);
		dto.setName("");
		System.out.println(mapper.writeValueAsString(dto));
	}
}
