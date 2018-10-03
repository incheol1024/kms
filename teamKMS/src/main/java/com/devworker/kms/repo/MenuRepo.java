package com.devworker.kms.repo;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.devworker.kms.dto.MenuDto;

public interface MenuRepo extends CrudRepository<MenuDto, Integer>{	
	
	@Query("select t from MenuDto t where t.type = :menu_type")
	List<MenuDto> getMenuList(@Param("menu_type") String type,Sort sort);
}
