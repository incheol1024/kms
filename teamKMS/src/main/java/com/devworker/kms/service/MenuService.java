package com.devworker.kms.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.devworker.kms.entity.MenuDao;
import com.devworker.kms.dic.MenuType;
import com.devworker.kms.repo.MenuRepo;

@Service
public class MenuService {
	private final
	MenuRepo repo;

	@Autowired
	public MenuService(MenuRepo repo) {
		this.repo = repo;
	}

	public List<MenuDao> getMenuList(MenuType type) {
		return repo.getMenuList(type.name(), new Sort(Direction.ASC,"name"));
	}
}
