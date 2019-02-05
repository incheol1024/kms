package com.devworker.kms.service;

import com.devworker.kms.dto.GroupDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface GroupService {
	long countGroup();
	int addGroup(GroupDto dto);
	void deleteGroup(int id);
	void forceDeleteGroup(int id);
	void updateGroup(GroupDto dto);
	Page<GroupDto> getGroupChild(int id, Pageable pageable);
	GroupDto getGroup(int id);
	GroupDto getAllGroupList();
}
