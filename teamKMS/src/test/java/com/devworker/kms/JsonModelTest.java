package com.devworker.kms;

import com.devworker.kms.dic.UserType;
import com.devworker.kms.dto.*;
import com.devworker.kms.dto.acl.AceDto;
import com.devworker.kms.dto.acl.AclDto;
import com.devworker.kms.dto.base.BasePageReqDto;
import com.devworker.kms.dto.base.BasePageResDto;
import com.devworker.kms.dto.site.ProjectDto;
import com.devworker.kms.dto.site.SiteDto;
import com.devworker.kms.entity.acl.AclDao;
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
	public void makeAclModel() throws JsonProcessingException {
		AclDto d = new AclDto();
		System.out.println(mapper.writeValueAsString(d));
	}

	@Test
	public void makeAceModel() throws JsonProcessingException {
		AceDto dto = new AceDto();
		System.out.println(mapper.writeValueAsString(dto));
	}

	@Test
	public void makeProjectModel() throws JsonProcessingException {
		ProjectDto dto = new ProjectDto();
		System.out.println(mapper.writeValueAsString(dto));
	}

	@Test
	public void makeSiteModel() throws JsonProcessingException{
		SiteDto dto = new SiteDto();
		System.out.println(mapper.writeValueAsString(dto));
	}
}
