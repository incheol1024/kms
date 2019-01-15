package com.devworker.kms.service.menu;

import org.aopalliance.intercept.Joinpoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class MenuAspect {
    @Autowired
    MenuService menuService;

    @Before("@annotation(MenuPermission)")
    public void before(Joinpoint joinpoint){

    }
}
