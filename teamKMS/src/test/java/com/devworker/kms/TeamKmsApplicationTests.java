package com.devworker.kms;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Random;

import javax.sql.DataSource;

import org.hamcrest.collection.IsEmptyCollection;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;

import com.devworker.kms.dao.GroupDao;
import com.devworker.kms.dao.menu.MenuDao;
import com.devworker.kms.dic.MenuType;
import com.devworker.kms.dto.GroupDto;
import com.devworker.kms.dto.UserDto;
import com.devworker.kms.ftsdao.PostFTSDao;
import com.devworker.kms.service.FTSService;
import com.devworker.kms.service.GroupService;
import com.devworker.kms.service.menu.MenuService;
import com.devworker.kms.service.UserService;
import com.devworker.kms.util.CommonUtil;
import com.fasterxml.jackson.core.JsonProcessingException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TeamKmsApplicationTests {
	@Autowired
	MenuService menuService;
	@Autowired
	UserService userService;
	@Autowired
	GroupService groupService;
	@Autowired
	FTSService ftsService;
	@Autowired
	PasswordEncoder encoder;
	@Autowired
	DataSource dataSource;

	@WithMockUser(value = "ADMIN")
	@Test
	public void getCurrentUserTest() throws Exception {
		String userName = CommonUtil.getCurrentUser();
		System.out.println(userName);
		assertThat(userName, notNullValue());
	}
	
	@Test
	public void passwordEncdoerTest() {
		String value = "USER";
		String encode = encoder.encode(value);
		System.out.println(encode);
		assertThat(encode, notNullValue());
	}
	
	@Test
	public void deleteGroupTest() {
		groupService.forceDeleteGroup(419);		
	}
	
	@Test
	public void makeTreeGroupTest() {
		GroupDto dto = new GroupDto();
		dto.setName("TT");
		dto.setParentid(420);
		makeTree(dto);
		System.out.println(dto.getId());
	}
	
	private void makeTree(GroupDto dto) {
		int id = groupService.addGroup(dto);
		Random random = new Random();
		for(int i = 0 ; i < random.nextInt(4); i++) {
			GroupDto sub = new GroupDto();
			sub.setName("TDF");
			sub.setParentid(id);
			makeTree(sub);
		}
	}

	@Test
	public void cleanAllTreeTest() throws SQLException {
		Connection connection = dataSource.getConnection();
		connection.createStatement().executeQuery("delete from KMS.KMSGroup where group_id != 0;");
		connection.commit();
	}

	@Test
	public void getMenuListTest() {
		List<MenuDao> menuList = menuService.getMenuList(MenuType.SOL);
		assertThat(menuList, not(IsEmptyCollection.empty()));
	}

	@Test
	public void getUserListTest() throws JsonProcessingException {		
		List<UserDto> userList = userService.getUserList();
		assertThat(userList, not(IsEmptyCollection.empty()));
	}

	@Test
	public void groupTest() {
		groupService.countGroup();
		GroupDao dao = new GroupDao();
		dao.setName("Test");
		dao.setParentid(0);
		List<GroupDto> list = groupService.getGroupChild(dao.getId());
		assertThat(list, not(IsEmptyCollection.empty()));
	}

	@Test
	public void addGroupTest() {
		GroupDto dto = new GroupDto();
		dto.setName("Test");
		dto.setParentid(0);
		groupService.addGroup(dto);
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
