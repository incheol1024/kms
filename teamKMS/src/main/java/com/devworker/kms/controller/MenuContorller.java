package com.devworker.kms.controller;

import java.util.List;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.devworker.kms.entity.MenuDao;
import com.devworker.kms.dic.MenuType;
import com.devworker.kms.service.MenuService;

@RestController
@RequestMapping("/menu")
public class MenuContorller {
	@Autowired
	MenuService service;

	@ApiOperation (value = "왼쪽 메뉴 항목 가져오기")
	@GetMapping("/{menuType}")
	public List<MenuDao> getMenuList(@PathVariable String menuType) {
		return service.getMenuList(MenuType.valueOf(menuType));
	}
}
