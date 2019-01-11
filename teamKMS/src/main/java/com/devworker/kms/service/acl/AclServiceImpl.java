package com.devworker.kms.service.acl;

import java.util.List;
import java.util.stream.Collectors;

import com.devworker.kms.dao.acl.AclDao;
import com.devworker.kms.exception.NotExistException;
import com.devworker.kms.service.GroupService;
import com.devworker.kms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.devworker.kms.dic.PermissionType;
import com.devworker.kms.dto.acl.AclDto;
import com.devworker.kms.repo.acl.AclRepo;

@Service("AclService")
public class AclServiceImpl implements AclService{
	@Autowired
	AclRepo repo;

	@Override
	public String createAcl(AclDto acl) {
		return repo.save(acl.getDao()).getAclId();
	}

	@Override
	public AclDto getAcl(String aclId) {
		return repo.findById(aclId).
				orElseThrow(() -> {throw new NotExistException("acl Not Found");}).getDto();
	}

	@Override
	public void delete(String aclId) {
		repo.deleteById(aclId);
	}

	@Override
	public List<AclDto> getAclList() {
		List<AclDao> list = repo.findAll();
		return list.stream().map(aclDao -> aclDao.getDto()).collect(Collectors.toList());
	}

	@Override
	public void updateAcl(AclDto acl) {
		repo.save(acl.getDao());
	}

	@Override
	public boolean checkPermission(String aclId, PermissionType type) {
		// TODO Auto-generated method stub
		return false;
	}

}
