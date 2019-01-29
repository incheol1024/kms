package com.devworker.kms;

import com.devworker.kms.dic.UserType;
import com.devworker.kms.dto.*;
import com.devworker.kms.dto.base.BasePageReqDto;
import com.devworker.kms.dto.base.BasePageResDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class JsonModelTest {
	private ObjectMapper mapper = new ObjectMapper();
	
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
		dto.setParentId(0);
		dto.setName("");
		System.out.println(mapper.writeValueAsString(dto));
	}

	@Test
	public void makeBasePageResDto() throws JsonProcessingException {
		BasePageResDto<String> dto = new BasePageResDto<>();
		dto.getList().add("");
		dto.setTotal(30);
		System.out.println(mapper.writeValueAsString(dto));
	}

	@Test
	public void makeBasePageReqDto() throws JsonProcessingException {
		BasePageReqDto<String> dto = new BasePageReqDto<>();
		System.out.println(mapper.writeValueAsString(dto));
	}

	@Test
	public void test()  {
		Map<String,String> map = new HashMap<>();
		Map<String, String> collect = map.entrySet().stream().filter(entry -> "12".equals(entry.getKey().substring(0, 2))).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
		Map<String, String> collect1 = map.entrySet().stream().filter(entry -> "22".equals(entry.getKey().substring(0, 2))).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
		Map<String, String> collect2 =map.entrySet().stream().filter(entry -> "32".equals(entry.getKey().substring(0, 2))).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

	}
}
