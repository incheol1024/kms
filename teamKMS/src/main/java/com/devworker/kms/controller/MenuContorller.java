package com.devworker.kms.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.devworker.kms.dao.MenuDao;
import com.devworker.kms.dic.MenuType;
import com.devworker.kms.service.MenuService;

import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/menu")
public class MenuContorller {
	@Autowired
	MenuService service;
	
	@GetMapping("/{menuType}")
	public List<MenuDao> getMenuList(@PathVariable String menuType) {
		return service.getMenuList(MenuType.valueOf(menuType));
	}
}
