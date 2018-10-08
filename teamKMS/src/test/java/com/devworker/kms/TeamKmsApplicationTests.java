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

import com.devworker.kms.dic.MenuType;
import com.devworker.kms.dto.MenuDto;
import com.devworker.kms.dto.MessageDetailDto;
import com.devworker.kms.dto.MessageDto;
import com.devworker.kms.ftsdto.PostFTSDto;
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
		MessageDto dto = new MessageDto();
		dto.setText("ddd");
		dto.addAttach(new MessageDetailDto());
		System.out.println(mapper.toJson(dto));
		String sendMessage = messageService.sendMessage(dto);
		System.out.println(sendMessage);
	}

	@Test
	public void getMenuListTest() {
		List<MenuDto> menuList = menuService.getMenuList(MenuType.SOL);
		assertThat(menuList, not(IsEmptyCollection.empty()));
	}
	
	@Test
	public void userTest() {
		userService.countUser();
	}
	
	@Test
	public void groupTest() {
		userService.countUser();
	}
	
	@Test
	public void ftsgetCountTest() {
		System.out.println(ftsService.getCount());
		PostFTSDto dto = new PostFTSDto();
		dto.setId(1);
		dto.setCategory(1);
		dto.setName("aaa");
		dto.setUser("bbb");
		ftsService.save(dto);
		List<PostFTSDto> findbyuser = ftsService.findbyuser("bbb");
		System.out.println(findbyuser);
	}
	
	@Test
	public void ftsSaveTest() {
		PostFTSDto dto = new PostFTSDto();
		dto.setId(1);
		dto.setCategory(1);
		dto.setName("aaa");
		dto.setUser("bbb");
		ftsService.save(dto);
	}
	
	@Test
	public void ftsSearchTest() {
		List<PostFTSDto> findbyuser = ftsService.findbyuser("bbb");
		assertThat(findbyuser, not(findbyuser.isEmpty()));
	}	
}
