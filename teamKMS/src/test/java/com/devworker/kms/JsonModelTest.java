package com.devworker.kms;

import org.junit.Test;

import com.devworker.kms.dao.GroupDao;
import com.devworker.kms.dao.UserDao;
import com.devworker.kms.dic.UserType;
import com.devworker.kms.dto.MessageDao;
import com.devworker.kms.dto.MessageDetailDao;
import com.google.gson.Gson;

public class JsonModelTest {
	Gson mapper = new Gson();
	
	@Test
	public void makeMessageModel() {	
		MessageDao dto = new MessageDao();
		dto.setText("ddd");
		dto.addAttach(new MessageDetailDao());
		System.out.println(mapper.toJson(dto));
	}
	
	@Test
	public void makeUserModel() {
		UserDao dao = new UserDao();
		dao.setName("이것");
		dao.setType(UserType.USER.name());
		System.out.println(mapper.toJson(dao));
	}
	
	@Test
	public void makeGroupModel() {
		GroupDao dao = new GroupDao();
		dao.setName("저것");
		System.out.println(mapper.toJson(dao));
	}
}
