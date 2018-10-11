package com.devworker.kms.repo;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.devworker.kms.dao.MenuDao;

public interface MenuRepo extends CrudRepository<MenuDao, Integer>{	
	
	@Query("select t from MenuDto t where t.type = :menu_type")
	List<MenuDao> getMenuList(@Param("menu_type") String type,Sort sort);
}
