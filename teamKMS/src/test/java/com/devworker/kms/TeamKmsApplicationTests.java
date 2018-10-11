package com.devworker.kms;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.List;
import java.util.concurrent.ExecutionException;

import org.hamcrest.collection.IsEmptyCollection;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.devworker.kms.dao.MenuDao;
import com.devworker.kms.dic.MenuType;
import com.devworker.kms.dto.MessageDao;
import com.devworker.kms.dto.MessageDetailDao;
import com.devworker.kms.ftsdao.PostFTSDao;
import com.devworker.kms.service.FTSService;
import com.devworker.kms.service.GroupService;
import com.devworker.kms.service.MenuService;
import com.devworker.kms.service.MessageService;
import com.devworker.kms.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.Gson;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TeamKmsApplicationTests {
	@Autowired
	MenuService menuService;
	@Autowired
	MessageService messageService;
	@Autowired
	UserService userService;
	@Autowired
	GroupService groupService;
	@Autowired
	FTSService ftsService;
	
	@Test
	public void makeMessageModel() throws JsonProcessingException, InterruptedException, ExecutionException {
		Gson mapper = new Gson();
		MessageDao dto = new MessageDao();
		dto.setText("ddd");
		dto.addAttach(new MessageDetailDao());
		System.out.println(mapper.toJson(dto));
		String sendMessage = messageService.sendMessage(dto);
		System.out.println(sendMessage);
	}

	@Test
	public void getMenuListTest() {
		List<MenuDao> menuList = menuService.getMenuList(MenuType.SOL);
		assertThat(menuList, not(IsEmptyCollection.empty()));
	}
	
	@Test
	public void userTest() {
		userService.countUser();
	}
	
	@Test
	public void groupTest() {
		groupService.countGroup();
	}
	
	@Test
	public void ftsgetCountTest() {
		System.out.println(ftsService.getCount());
		PostFTSDao dto = new PostFTSDao();
		dto.setId(1);
		dto.setCategory(1);
		dto.setName("aaa");
		dto.setUser("bbb");
		ftsService.save(dto);
		List<PostFTSDao> findbyuser = ftsService.findbyuser("bbb");
		System.out.println(findbyuser);
	}
	
	@Test
	public void ftsSaveTest() {
		PostFTSDao dto = new PostFTSDao();
		dto.setId(1);
		dto.setCategory(1);
		dto.setName("aaa");
		dto.setUser("bbb");
		ftsService.save(dto);
	}
	
	@Test
	public void ftsSearchTest() {
		List<PostFTSDao> findbyuser = ftsService.findbyuser("bbb");
		assertThat(findbyuser, not(findbyuser.isEmpty()));
	}	
}
