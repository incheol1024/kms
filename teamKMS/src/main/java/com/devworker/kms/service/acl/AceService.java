package com.devworker.kms.service.acl;

import java.util.List;

import com.devworker.kms.dic.PermissionType;
import com.devworker.kms.dto.acl.AceDto;

public interface AceService {

	void addAce(AceDto ace);

	void removeAce(String aclId,String aceId);

	List<AceDto> getAceList(String aceId);
	
	AceDto getAce(String aclId,String aceId);


	
}
