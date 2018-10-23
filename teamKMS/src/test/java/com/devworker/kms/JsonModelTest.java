package com.devworker.kms;

import org.junit.Test;

import com.devworker.kms.dic.UserType;
import com.devworker.kms.dto.GroupDto;
import com.devworker.kms.dto.MessageDetailDto;
import com.devworker.kms.dto.MessageDto;
import com.devworker.kms.dto.UserDto;
import com.google.gson.Gson;

public class JsonModelTest {
	Gson mapper = new Gson();
	
	@Test
	public void makeMessageModel() {	
		MessageDto dto = new MessageDto();
		dto.setText("ddd");
		dto.addAttach(new MessageDetailDto());
		System.out.println(mapper.toJson(dto));
	}
	
	@Test
	public void makeUserModel() {
		UserDto dto = new UserDto();
		dto.setName("");
		dto.setType(UserType.USER.name());
		dto.setPassword("");
		dto.setGroupId(0);
		dto.setId("0");
		dto.setGroupName("");
		System.out.println(mapper.toJson(dto));
	}
	
	@Test
	public void makeGroupModel() {
		GroupDto dto = new GroupDto();
		dto.setId(0);
		dto.setParentid(0);
		dto.setName("");
		System.out.println(mapper.toJson(dto));
	}
}
