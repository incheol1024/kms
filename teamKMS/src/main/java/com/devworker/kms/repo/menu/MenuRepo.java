package com.devworker.kms.repo.menu;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.devworker.kms.dao.menu.MenuDao;

public interface MenuRepo extends CrudRepository<MenuDao, Integer>{	
	
	@Query("select t from MenuDao t where t.type = :menu_type")
	List<MenuDao> getMenuList(@Param("menu_type") String type,Sort sort);
}
