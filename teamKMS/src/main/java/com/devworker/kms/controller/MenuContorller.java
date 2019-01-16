package com.devworker.kms.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.devworker.kms.dao.MenuDao;
import com.devworker.kms.dic.MenuType;
import com.devworker.kms.service.MenuService;

@RestController
public class MenuContorller {
	@Autowired
	MenuService service;
	
	@GetMapping("/getMenuList")
	public List<MenuDao> getMenuList(@RequestParam("type") MenuType type) {
		return service.getMenuList(type);
	}


}
