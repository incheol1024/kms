package com.devworker.kms.controller.menu;

import com.devworker.kms.service.menu.MenuRightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MenuRightController {
    @Autowired
    MenuRightService  menuRightService;


}
