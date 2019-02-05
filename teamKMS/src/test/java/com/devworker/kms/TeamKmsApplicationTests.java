package com.devworker.kms;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.sql.DataSource;

import org.hamcrest.collection.IsEmptyCollection;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;

import com.devworker.kms.dao.GroupDao;
import com.devworker.kms.dao.MenuDao;
import com.devworker.kms.dic.MenuType;
import com.devworker.kms.dto.GroupDto;
import com.devworker.kms.dto.UserDto;
import com.devworker.kms.ftsdao.PostFTSDao;
import com.devworker.kms.service.FTSService;
import com.devworker.kms.service.GroupService;
import com.devworker.kms.service.MenuService;
import com.devworker.kms.service.UserService;
import com.devworker.kms.util.CommonUtil;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment  = SpringBootTest.WebEnvironment.RANDOM_PORT)
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
	@Autowired
	TestRestTemplate testRestTemplate;

	@WithMockUser(value = "ADMIN")
	@Test
	public void getCurrentUserTest()  {
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
		dto.setParentId(420);
		makeTree(dto);
		System.out.println(dto.getId());
	}
	
	private void makeTree(GroupDto dto) {
		int id = groupService.addGroup(dto);
		Random random = new Random();
		for(int i = 0 ; i < random.nextInt(4); i++) {
			GroupDto sub = new GroupDto();
			sub.setName("TDF");
			sub.setParentId(id);
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
	public void getUserListTest()  {
		Page<UserDto> userList = userService.getUserList(CommonUtil.getPage(Integer.MAX_VALUE));
		assertThat(userList, not(userList.hasContent()));
	}

	@WithMockUser(value = "ADMIN")
	@Test
	public void getUserListPagingTest() {
		List<UserDto> l = new ArrayList<>();
		List list = testRestTemplate.
				withBasicAuth("ADMIN", "ADMIN").
				getForEntity("/getUserListPage?page=1&size=10&sort=name,desc", l.getClass()).getBody();
		assertThat(list,notNullValue());
	}

	@Test
	public void groupTest() {
		groupService.countGroup();
		GroupDao dao = new GroupDao();
		dao.setName("Test");
		dao.setParentId(0);
		Page<GroupDto> list = groupService.getGroupChild(dao.getId(), CommonUtil.getPage(1));
		assertThat(list, not(list.hasContent()));
	}

	@Test
	public void addGroupTest() {
		GroupDto dto = new GroupDto();
		dto.setName("Test");
		dto.setParentId(0);
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
