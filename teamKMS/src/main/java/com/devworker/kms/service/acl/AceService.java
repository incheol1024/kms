package com.devworker.kms.service.acl;

import java.util.List;

import com.devworker.kms.dic.PermissionType;
import com.devworker.kms.dto.acl.AceDto;

public interface AceService {

	void addAce(AceDto ace);

	void removeAce(AceDto ace);

	List<AceDto> getAceList(AceDto dto);
	
	AceDto getAce(AceDto dto);
	
	public boolean checkPermission(String aceId, PermissionType type);
}
