package com.devworker.kms;

import org.junit.Test;

import com.devworker.kms.dao.GroupDao;
import com.devworker.kms.dao.UserDao;
import com.devworker.kms.dic.UserType;
import com.devworker.kms.dto.MessageDto;
import com.devworker.kms.dto.MessageDetailDto;
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
		UserDao dao = new UserDao();
		dao.setName("");
		dao.setType(UserType.USER.name());
		dao.setPassword("");
		dao.setGroupId(0);
		dao.setId("0");
		System.out.println(mapper.toJson(dao));
	}
	
	@Test
	public void makeGroupModel() {
		GroupDao dao = new GroupDao();
		dao.setName("저것");
		System.out.println(mapper.toJson(dao));
	}
}
