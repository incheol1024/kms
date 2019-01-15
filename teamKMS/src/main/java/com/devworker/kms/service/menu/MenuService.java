package com.devworker.kms.service.menu;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.devworker.kms.dao.menu.MenuDao;
import com.devworker.kms.dic.MenuType;
import com.devworker.kms.repo.menu.MenuRepo;

@Service
public class MenuService {
	@Autowired
	MenuRepo repo;
	
	public List<MenuDao> getMenuList(MenuType type) {
		return repo.getMenuList(type.name(), new Sort(Direction.ASC,"name"));
	}
}
