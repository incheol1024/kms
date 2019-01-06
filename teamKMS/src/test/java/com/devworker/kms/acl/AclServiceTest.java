package com.devworker.kms.acl;

import static org.junit.Assert.*;

import com.devworker.kms.dao.acl.AceDao;
import com.devworker.kms.dic.PermissionType;
import com.devworker.kms.dto.acl.AclDto;
import com.devworker.kms.service.acl.AceService;
import com.devworker.kms.service.acl.AclService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AclServiceTest {
	@Autowired
	AclService aclService;
	@Autowired
	AceService aceService;

	@Test
	public void createAclTest() {
		AclDto dto = new AclDto();
		dto.setAclId("acl");
		dto.setAclName("name");
		dto.setPermission(List.of(PermissionType.CREATE,PermissionType.DELETE));
		aclService.createAcl(dto);
	}

	@Test
	public void getAclTest() {
		aclService.getAcl("123");
	}

	@Test
	public void deleteAclTest() {
		aclService.delete("123");
	}

	@Test
	public void	getAclLIstTest() {
		aclService.getAclList();
	}
}
