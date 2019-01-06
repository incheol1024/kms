package com.devworker.kms.service.acl;

import java.util.List;
import java.util.stream.Collectors;

import com.devworker.kms.dao.acl.AceDao;
import com.devworker.kms.service.GroupService;
import com.devworker.kms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.devworker.kms.dic.PermissionType;
import com.devworker.kms.dto.acl.AceDto;
import com.devworker.kms.repo.acl.AceRepo;

@Service("AceService")
public class AceServiceImpl implements AceService{
	@Autowired
	AclService service;
	@Autowired
	AceRepo repo;
	@Autowired
	UserService userService;
	@Autowired
	GroupService groupService;

	@Override
	public void addAce(AceDto ace) {
		repo.save(ace.getDao());
	}

	@Override
	public void removeAce(String aclId, String aceId) {
		AceDao dao = new AceDao();
		dao.setAclId(aclId);
		dao.setAceId(aceId);
		repo.delete(dao);
	}

	@Override
	public List<AceDto> getAceList(String aceId) {
		List<AceDao> list = repo.getAceList(aceId);
		List<AceDto> dtoList = list.stream().map(aceDao -> aceDao.getDto()).collect(Collectors.toList());
		return dtoList;
	}

	@Override
	public AceDto getAce(String aclId,String aceId) {
		AceDao dao = repo.getAce(aclId,aceId);
		return dao.getDto();

	}
}
