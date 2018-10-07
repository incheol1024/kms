package com.devworker.kms.repo;

import org.springframework.data.repository.CrudRepository;

import com.devworker.kms.dto.GroupDto;

public interface GroupRepo extends CrudRepository<GroupDto, Integer>{

}
